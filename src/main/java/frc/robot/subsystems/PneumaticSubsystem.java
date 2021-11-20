// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

public class PneumaticSubsystem extends SubsystemBase {

  public Compressor compressor = new Compressor();
  public Solenoid solenoid = new Solenoid(0, 3);
  
  public void charge() {
    compressor.start();
  }

  public void stop() {
    compressor.stop();
  }
  
  public void fire() {
    solenoid.set(true);
  }
  
  public void close() {
    solenoid.set(false);
  }
  
  /** Creates a new PneumaticSubsystem. */
  public PneumaticSubsystem() {
    compressor.stop();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
