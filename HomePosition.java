// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class HomePosition extends CommandBase {
  /** Creates a new GrabPosition. */
  double x;
  boolean flag;
  public HomePosition() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.pivotOne);
    addRequirements(RobotContainer.pivotTwo);
    addRequirements(RobotContainer.extend);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    flag=false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // ADJUSTING LENGTH VALUES MUST CHANGE OF EXTEND ENCODER
    //change x to actual values
    System.out.println(flag);
    if(!flag){
      if(RobotContainer.extend.get_extend_encoder() <= -3.0){
        RobotContainer.extend.extend(0.65);
      }else{
        flag= true;
        RobotContainer.extend.extend(0.0);
      }
    }else{
        RobotContainer.extend.extend(0.0);
        flag= true;
      if(RobotContainer.pivotOne.get_first_pivot_encoder() >= 5.0){
        RobotContainer.pivotOne.move_pivot_one(-0.2);
      }else if(RobotContainer.pivotOne.get_first_pivot_encoder() <= -2.0){
        RobotContainer.pivotOne.move_pivot_one(0.2);
      }else{
        RobotContainer.pivotOne.move_pivot_one(0.0);
      }

      if(RobotContainer.pivotTwo.get_second_pivot_encoder() >= -1.0){
        RobotContainer.pivotTwo.move_pivot_two(-0.3);
      }else if(RobotContainer.pivotTwo.get_second_pivot_encoder() <= -3.0){
        RobotContainer.pivotTwo.move_pivot_two(0.3);
      }else{
        RobotContainer.pivotTwo.move_pivot_two(0.0);
      }
      // System.out.println(((RobotContainer.pivotOne.get_first_pivot_encoder() >= (2.0-5.0)&&RobotContainer.pivotOne.get_first_pivot_encoder() <= (2.0+5.0))));
      if((RobotContainer.pivotTwo.get_second_pivot_encoder() <= (-2.0+5.0)&&RobotContainer.pivotTwo.get_second_pivot_encoder() >= (-2.0-5.0)) && (RobotContainer.pivotOne.get_first_pivot_encoder() >= (2.0-5.0)&&RobotContainer.pivotOne.get_first_pivot_encoder() <= (2.0+5.0))){
        if(RobotContainer.extend.get_extend_encoder() >= -2){
          RobotContainer.extend.extend(-0.65);
        }else{
          RobotContainer.extend.extend(0.0);
          end(true);
        }
      }
    }
  }



  // }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.extend.extend(0.0);
    RobotContainer.pivotTwo.move_pivot_two(0.0);
    RobotContainer.pivotOne.move_pivot_one(0.0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
