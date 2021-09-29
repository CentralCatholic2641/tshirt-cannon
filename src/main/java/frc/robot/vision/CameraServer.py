# -*- coding: utf-8 -*-
"""
Created on Sat Mar 20 11:03:36 2021

@author: jjtom
"""
from cscore import CameraServer
from networktables import NetworkTables, NetworkTablesInstance
#import networktables
import sys
# To see messages from networktables, you must setup logging
import logging


import cv2
import json
import numpy as np
import time

def readConfig():
    """Read configuration file."""
    global team
    global server

    # parse file
    try:
        with open(configFile, "rt", encoding="utf-8") as f:
            j = json.load(f)
    except OSError as err:
        print("could not open '{}': {}".format(configFile, err), file=sys.stderr)
        return False

    # top level must be an object
    if not isinstance(j, dict):
        parseError("must be JSON object")
        return False

    # team number
    try:
        team = j["team"]
    except KeyError:
        parseError("could not read team number")
        return False

    # ntmode (optional)
    if "ntmode" in j:
        str = j["ntmode"]
        if str.lower() == "client":
            server = False
        elif str.lower() == "server":
            server = True
        else:
            parseError("could not understand ntmode value '{}'".format(str))

    # cameras
    try:
        cameras = j["cameras"]
    except KeyError:
        parseError("could not read cameras")
        return False
    for camera in cameras:
        if not readCameraConfig(camera):
            return False

    # switched cameras
    if "switched cameras" in j:
        for camera in j["switched cameras"]:
            if not readSwitchedCameraConfig(camera):
                return False

    return True

configFile = "/boot/frc.json"

class CameraConfig: pass

team = None
server = False
cameraConfigs = []
switchedCameraConfigs = []
cameras = []

def startCamera(config):
    """Start running the camera."""
    print("Starting camera '{}' on {}".format(config.name, config.path))
    inst = CameraServer.getInstance()
    camera = UsbCamera(config.name, config.path)
    server = inst.startAutomaticCapture(camera=camera, return_server=True)

    camera.setConfigJson(json.dumps(config.config))
    camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen)

    if config.streamConfig is not None:
        server.setConfigJson(json.dumps(config.streamConfig))

    return camera
def readCameraConfig(config):
    """Read single camera configuration."""
    cam = CameraConfig()

    # name
    try:
        cam.name = config["name"]
    except KeyError:
        parseError("could not read camera name")
        return False

    # path
    try:
        cam.path = config["path"]
    except KeyError:
        parseError("camera '{}': could not read path".format(cam.name))
        return False

    # stream properties
    cam.streamConfig = config.get("stream")

    cam.config = config

    cameraConfigs.append(cam)
    return True


def main():
   # To see messages from networktables, you must setup logging    
   logging.basicConfig(level=logging.DEBUG)
    
    
   # start NetworkTables
   NetworkTables.initialize(server='10.26.41.2')

   #ntinst = NetworkTablesInstance.getDefault()
   #ntinst.startClientTeam(2641)

   # Table for vision output information

 
   vision_nt = NetworkTables.getTable('Vision')
   
   

   with open('/boot/frc.json','r') as json_file:
       config = json.load(json_file)
       camera = config['cameras'][0]
   #readconfig()
   
   #camera = startCamera(config)
   
   
   width = camera['width']
   height = camera['height']

   cs = CameraServer.getInstance()
   cs.enableLogging()
   camera =  cs.startAutomaticCapture(name="Automatic Capture Camera") #This allows you to see and send the image back to the dashboard
   camera.setResolution(640, 480)


   input_stream = cs.getVideo()
   output_stream = cs.putVideo("Output Stream", 320, 240)



   # Wait for NetworkTables to start
   time.sleep(0.5)
   
   # establish initial array for input image   
   input_img = np.zeros(shape=(240, 320, 3), dtype=np.uint8)

   while True:
      start_time = time.time()

      frame_time, input_img = input_stream.grabFrame(input_img)
      output_img = np.copy(input_img)

      # Notify output of error and skip iteration
      if frame_time == 0:
         output_stream.notifyError(input_stream.getError())
         continue

      # Convert to HSV and threshold image
      hsv_img = cv2.cvtColor(input_img, cv2.COLOR_BGR2HSV)
      binary_img = cv2.inRange(hsv_img, (65, 65, 200), (85, 255, 255))

      _, contour_list, hierarchy = cv2.findContours(binary_img, mode=cv2.RETR_EXTERNAL, method=cv2.CHAIN_APPROX_SIMPLE)

      print(contour_list)


      x_list = []
      y_list = []

      for contour in contour_list:

         # Ignore small contours that could be because of noise/bad thresholding
         if cv2.contourArea(contour) < 0:
            continue

         output_img = cv2.drawContours(output_img, contour, -1, color = (255, 255, 255), thickness = -1)

         rect = cv2.minAreaRect(contour)
         center, size, angle = rect
         center = [int(dim) for dim in center] # Convert to int so we can draw

         # Draw rectangle and circle
         #output_img = cv2.drawContours(output_img, np.int0(cv2.boxPoints(rect)), -1, color = (0, 0, 255), thickness = 2)
         output_img = cv2.circle(output_img, tuple(center), 3, color = (0, 0, 255), thickness = -1)

         x_list.append((center[0] - width / 2) / (width / 2))
         y_list.append((center[1] - width / 2) / (width / 2))

      vision_nt.putNumberArray('target_x', x_list)
      vision_nt.putNumberArray('target_y', y_list)

      processing_time = time.time() - start_time
      fps = 1 / processing_time
    #   cv2.putText(output_img, str(round(fps, 1)), (0, 40), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255))
      output_stream.putFrame(output_img)
      if ((sum(y_list) > 0) & (sum(x_list)>0)): time.sleep(10000)
      
      
if __name__ == "__main__":
    main()