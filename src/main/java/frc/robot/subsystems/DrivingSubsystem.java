// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.Constants;
import frc.robot.commands.DrivingCommand;

public class DrivingSubsystem extends SubsystemBase {
  // set up motor

  public WPI_TalonSRX leftmotor1 = new WPI_TalonSRX(Constants.leftmotor1);
  public WPI_TalonSRX leftmotor2 = new WPI_TalonSRX(Constants.leftmotor2);

  public SpeedControllerGroup leftgroup = new SpeedControllerGroup(leftmotor1, leftmotor2);

  public WPI_TalonSRX rightmotor1 = new WPI_TalonSRX(Constants.rightmotor1);
  public WPI_TalonSRX rightmotor2 = new WPI_TalonSRX(Constants.rightmotor2);

  public SpeedControllerGroup rightgroup = new SpeedControllerGroup(rightmotor1, rightmotor2);

  public WPI_TalonSRX leftEncoder = new WPI_TalonSRX(Constants.leftEncoder);
  public WPI_TalonSRX rightEncoder = new WPI_TalonSRX(Constants.rightEncoder);
  
  public DifferentialDrive differentialDrive = new DifferentialDrive(leftgroup, rightgroup);
  
  public AHRS ahrs;
  
  public void oDrive(double y1, double y2) {

    // if (Math.abs(y2) < 0.1 && Math.abs(y1) > 0.1) { 
    //   differentialDrive.arcadeDrive(-y1, , true);
    // } 
    
    // else {
    // System.out.println("y1: " + y1 + " y2: " + y2);
    differentialDrive.arcadeDrive(y1, y2, true);
    // }
  }
 
  public void tDrive(double left, double right) {
    differentialDrive.tankDrive(-left, -right, true);
  }
  
  /** Creates a new Driving. */
  public DrivingSubsystem() { 
    ahrs = new AHRS();
    ahrs.zeroYaw();
  }

  public void periodic() {
    // This method will be called once per scheduler
    setDefaultCommand(new DrivingCommand());
  }
}