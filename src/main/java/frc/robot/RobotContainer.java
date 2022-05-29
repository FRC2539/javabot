package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.*;
import frc.robot.common.controller.Axis;
import frc.robot.common.controller.ThrustmasterJoystick;
import frc.robot.common.controller.LogitechController;
import frc.robot.subsystems.*;
import frc.robot.util.AutonomousManager;

public class RobotContainer {
    private final ThrustmasterJoystick leftDriveController = new ThrustmasterJoystick(Constants.LEFT_DRIVE_CONTROLLER);
    private final ThrustmasterJoystick rightDriveController = new ThrustmasterJoystick(Constants.RIGHT_DRIVE_CONTROLLER);
    private final LogitechController operatorController = new LogitechController(Constants.OPERATOR_CONTROLLER);
    
    private final SwerveDriveSubsystem drivetrainSubsystem = new SwerveDriveSubsystem();
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final LightsSubsystem lightsSubsystem = new LightsSubsystem();
    private final BalltrackSubsystem balltrackSubsystem = new BalltrackSubsystem(shooterSubsystem);
    private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
    private final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();
    private final MachineLearningSubsystem machineLearningSubsystem = new MachineLearningSubsystem();

    private AutonomousManager autonomousManager;

    public RobotContainer() {
        autonomousManager = new AutonomousManager();

        CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);
        CommandScheduler.getInstance().registerSubsystem(shooterSubsystem);
        CommandScheduler.getInstance().registerSubsystem(lightsSubsystem);
        CommandScheduler.getInstance().registerSubsystem(balltrackSubsystem);
        CommandScheduler.getInstance().registerSubsystem(climberSubsystem);
        CommandScheduler.getInstance().registerSubsystem(limelightSubsystem);
        CommandScheduler.getInstance().registerSubsystem(machineLearningSubsystem);
        
        CommandScheduler.getInstance().setDefaultCommand(drivetrainSubsystem, new DriveCommand(drivetrainSubsystem, getDriveForwardAxis(), getDriveStrafeAxis(), getDriveRotationAxis()));
        CommandScheduler.getInstance().setDefaultCommand(lightsSubsystem, new DefaultLightsCommand(lightsSubsystem));

        configureControllerLayout();
    }

    private void configureControllerLayout() {
        leftDriveController.getLeftTopLeft().whenPressed(() -> drivetrainSubsystem.resetGyroAngle());

        leftDriveController.getLeftThumb().whileHeld(() -> climberSubsystem.lowerClimber(), climberSubsystem);
        leftDriveController.getRightThumb().whileHeld(() -> climberSubsystem.raiseClimber(), climberSubsystem);
        leftDriveController.getBottomThumb().whileHeld(() -> climberSubsystem.toggleClimberArm(), climberSubsystem);

        leftDriveController.getTrigger().whileHeld(new SimpleShootCommand(shooterSubsystem, balltrackSubsystem, () -> shooterSubsystem.setFenderHighGoalShot()));
        rightDriveController.getTrigger().whileHeld(new SimpleShootCommand(shooterSubsystem, balltrackSubsystem, () -> shooterSubsystem.setFenderLowGoalShot()));

        rightDriveController.getLeftThumb().whileHeld(new IntakeCommand(balltrackSubsystem));
        rightDriveController.getBottomThumb().whileHeld(new LimelightDriveCommand(drivetrainSubsystem, getDriveForwardAxis(), getDriveStrafeAxis(), limelightSubsystem, lightsSubsystem));

        operatorController.getRightTrigger().whileHeld(new LimelightShootCommand(shooterSubsystem, balltrackSubsystem, limelightSubsystem));

        operatorController.getA().whenPressed(() -> limelightSubsystem.decrementYOffset(), limelightSubsystem);
        operatorController.getX().whenPressed(() -> limelightSubsystem.incrementXOffset(), limelightSubsystem);
        operatorController.getB().whenPressed(() -> limelightSubsystem.decrementXOffset(), limelightSubsystem);
        operatorController.getY().whenPressed(() -> limelightSubsystem.incrementYOffset(), limelightSubsystem);

        operatorController.getBack().whileHeld(new ReverseBalltrackCommand(balltrackSubsystem));
    }

    public Command getAutonomousCommand() {
        return autonomousManager.getAutonomousCommand(this);
    }

    private Axis getDriveForwardAxis() {
        return leftDriveController.getXAxis();
    }

    private Axis getDriveStrafeAxis() {
        return leftDriveController.getYAxis();
    }

    private Axis getDriveRotationAxis() {
        return rightDriveController.getXAxis();
    }

    public SwerveDriveSubsystem getSwerveDriveSubsystem() {
        return drivetrainSubsystem;
    }

    public ShooterSubsystem getShooterSubsystem() {
        return shooterSubsystem;
    }

    public LightsSubsystem getLightsSubsystem() {
        return lightsSubsystem;
    }

    public BalltrackSubsystem getBalltrackSubsystem() {
        return balltrackSubsystem;
    }

    public ClimberSubsystem getClimberSubsystem() {
        return climberSubsystem;
    }

    public LimelightSubsystem getLimelightSubsystem() {
        return limelightSubsystem;
    }

    public MachineLearningSubsystem getMachineLearningSubsystem() {
        return machineLearningSubsystem;
    }
}
