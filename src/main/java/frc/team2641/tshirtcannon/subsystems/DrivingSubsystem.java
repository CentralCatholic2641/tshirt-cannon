// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2641.tshirtcannon.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2641.tshirtcannon.Constants;
import frc.team2641.tshirtcannon.commands.DrivingCommand;

public class DrivingSubsystem extends SubsystemBase {
  // set up motor

  public WPI_TalonSRX leftmotor1 = new WPI_TalonSRX(Constants.leftmotor1);
  public WPI_TalonSRX leftmotor2 = new WPI_TalonSRX(Constants.leftmotor2);

  public MotorControllerGroup leftgroup = new MotorControllerGroup(leftmotor1, leftmotor2);

  public WPI_TalonSRX rightmotor1 = new WPI_TalonSRX(Constants.rightmotor1);
  public WPI_TalonSRX rightmotor2 = new WPI_TalonSRX(Constants.rightmotor2);

  public MotorControllerGroup rightgroup = new MotorControllerGroup(rightmotor1, rightmotor2);

  public DifferentialDrive differentialDrive = new DifferentialDrive(leftgroup, rightgroup);
  
  public void aDrive(double speed, double rotation) {
    differentialDrive.arcadeDrive(speed * 0.6, rotation * 0.8, true);
  }
 
  public void tDrive(double left, double right) {
    differentialDrive.tankDrive(-left, -right, true);
  }
  
  /** Creates a new Driving. */
  public DrivingSubsystem() { 
    leftmotor1.setInverted(true);
    leftmotor2.setInverted(true);
  }

  public void periodic() {
    // This method will be called once per scheduler
    setDefaultCommand(new DrivingCommand());
  }
}