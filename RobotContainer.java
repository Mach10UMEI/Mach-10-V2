// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants;
import frc.robot.commands.AutoLevel;
import frc.robot.commands.Autos;
import frc.robot.commands.CloseHopper;
import frc.robot.commands.ControlDrive;
import frc.robot.commands.ControlExtend;
import frc.robot.commands.ControlIntake;
import frc.robot.commands.ControlPivotOne;
import frc.robot.commands.ControlPivotTwo;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.GrabPositionAuto;
import frc.robot.commands.GrabPositionTeleop;
import frc.robot.commands.GroundJunction;
import frc.robot.commands.HighJunctionAuto;
import frc.robot.commands.GrabPositionTeleop;
import frc.robot.commands.HighJunctionTeleop;
import frc.robot.commands.HomePosition;
import frc.robot.commands.HopperGrabCone;
import frc.robot.commands.MiddleJunction;
import frc.robot.commands.OmniIn;
import frc.robot.commands.OmniOut;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.OpenHopper;
import frc.robot.commands.closeClaw;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Slidingomi;
import frc.robot.subsystems.pneumatics;
import frc.robot.subsystems.ArmExtend;
import frc.robot.subsystems.ArmPivotOne;
import frc.robot.subsystems.ArmPivotTwo;
import frc.robot.subsystems.Claw;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Auto1;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  //WHERE TO DEFINE AUTOS OT BE CHOSEN
  private final Command m_auto1 = new Auto1();
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  public static DriveTrain drive = new DriveTrain();
  public static ArmExtend extend = new ArmExtend();
  public static ArmPivotOne pivotOne = new ArmPivotOne();
  public static ArmPivotTwo pivotTwo = new ArmPivotTwo();
  public static Intake intake = new Intake();
  public static Gyro gyro= new Gyro();
  public static pneumatics comp = new pneumatics();
  public static Claw claw = new Claw();
  public static Hopper hopper = new Hopper();
  public static Slidingomi omni = new Slidingomi();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(Constants.kDriverControllerPort);

  public static XboxController driveController = new XboxController(Constants.XBOX_CONTROLLER_PORT_0);
  public static XboxController OpController = new XboxController(Constants.XBOX_CONTROLLER_PORT_1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition).onTrue(new ExampleCommand(m_exampleSubsystem));

    //WHERE TO PUT THE AUTOS TO BE SELECTED
    m_chooser.setDefaultOption("auto 1", m_auto1);
    SmartDashboard.putData(m_chooser);

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    drive.setDefaultCommand(new ControlDrive());
    pivotOne.setDefaultCommand(new ControlPivotOne());
    pivotTwo.setDefaultCommand(new ControlPivotTwo());
    intake.setDefaultCommand(new ControlIntake());
    extend.setDefaultCommand(new ControlExtend());

    JoystickButton OpaButton = new JoystickButton(OpController, 1);
    JoystickButton DraButton = new JoystickButton(driveController, 1);
    JoystickButton DrLbumper = new JoystickButton(driveController, 5);
    JoystickButton DrRbumper = new JoystickButton(driveController, 6);
    JoystickButton OpLbumper = new JoystickButton(OpController, 5);
    JoystickButton OpRbumper = new JoystickButton(OpController, 6);
    JoystickButton OpbButton = new JoystickButton(OpController,2);
    JoystickButton OpyButton = new JoystickButton(OpController, 4);
    JoystickButton OpxButton = new JoystickButton(OpController, 3);
    JoystickButton OpstartButton = new JoystickButton(OpController,8);
    JoystickButton OpbackButton = new JoystickButton(OpController,7);
    //Op Buttons
    OpaButton.onTrue(new GrabPositionTeleop());
    OpaButton.onFalse(new ControlPivotOne());
    OpbButton.onTrue(new HomePosition());
    OpbButton.onFalse(new ControlPivotOne());
    OpyButton.onTrue(new HighJunctionTeleop()); //change to auto for testing
    OpyButton.onFalse(new ControlPivotOne());
    OpxButton.onTrue(new MiddleJunction());
    OpxButton.onFalse(new ControlPivotOne());
    OpstartButton.onTrue(new GroundJunction());
    OpstartButton.onFalse(new ControlPivotOne());
    OpbackButton.onTrue(new HopperGrabCone());
    OpbackButton.onFalse(new ControlPivotOne());

    OpLbumper.onTrue(new OpenClaw());
    OpRbumper.onTrue(new closeClaw());
    
    //driver buttons
    DraButton.toggleOnTrue(new AutoLevel());
    DraButton.toggleOnFalse(new ControlDrive());

    DrLbumper.onTrue(new OmniIn());
    DrRbumper.onTrue(new OmniOut());

  

    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_chooser.getSelected();
  }

public static double getLeftX(XboxController controller){
  if(controller.getLeftX()<0.1 && controller.getLeftX()>-0.1){
    return 0.0;
  }else{
    return controller.getLeftX();
  }
}

public static double getLeftY(XboxController controller){
  if(controller.getLeftY()<0.1 && controller.getLeftY()>-0.1){
    return 0.0;
  }else{
    return controller.getLeftY();
  }
}

public static double getRightX(XboxController controller){
  if(controller.getRightX()<0.1 && controller.getRightX()>-0.1){
    return 0.0;
  }else{
    return controller.getRightX();
  }
}

public static double getRightY(XboxController controller){
  if(controller.getRightY()<0.1 && controller.getRightY()>-0.1){
    return 0.0;
  }else{
    return controller.getRightY();
  }
  
}


}
