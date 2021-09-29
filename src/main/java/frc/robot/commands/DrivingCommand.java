// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class DrivingCommand extends CommandBase {

  /** Creates a new Driving. */
  public DrivingCommand() {
    addRequirements(Robot.drivingSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Called every time the scheduler runs while the command is scheduled.
    SmartDashboard.putNumber("axis", Robot.drivingSubsystem.ahrs.getAngle());
    double joystick1 = Robot.robotContainer.gamepad1.getRawAxis(Constants.joystick1);
    double joystick2 = Robot.robotContainer.gamepad1.getRawAxis(Constants.joystick2);
    Robot.drivingSubsystem.oDrive(joystick1, joystick2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
