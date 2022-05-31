package frc.robot.util;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.FollowTrajectoryCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.LimelightDriveCommand;
import frc.robot.commands.LimelightShootCommand;
import frc.robot.commands.PrepareToShootCommand;

public class AutonomousManager {
    private TrajectoryLoader trajectoryLoader;

    private NetworkTable autonomousTable;

    private NetworkTableEntry selectedAuto;

    private final String[] autoStrings = {"twoballmain"};

    public AutonomousManager(TrajectoryLoader trajectoryLoader) {
        this.trajectoryLoader = trajectoryLoader;

        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        autonomousTable = inst.getTable("Autonomous");

        autonomousTable.getEntry("autos").setStringArray(autoStrings);

        selectedAuto = autonomousTable.getEntry("selectedAuto");

        // Choose the first auto as the default
        selectedAuto.setString(autoStrings[0]);
    }

    public Command getTwoBallMainCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectoryLoader.getTwoBallMain());
    
        followAndIntake(command, container, trajectoryLoader.getTwoBallMain());
        shootBallsAndAim(command, container, 2);

        return command;
    }

    private void shootBalls(SequentialCommandGroup command, RobotContainer container, double timeout) {
        command.addCommands(new LimelightShootCommand(container.getShooterSubsystem(), container.getBalltrackSubsystem(), container.getLimelightSubsystem())
                            .withTimeout(timeout));
    }

    private void shootBallsAndAim(SequentialCommandGroup command, RobotContainer container, double timeout) {
        command.addCommands(new LimelightShootCommand(container.getShooterSubsystem(), container.getBalltrackSubsystem(), container.getLimelightSubsystem())
                            .alongWith(new LimelightDriveCommand(container.getSwerveDriveSubsystem(), () -> 0.0, () -> 0.0, container.getLimelightSubsystem(), container.getLightsSubsystem()))
                            .withTimeout(timeout));
    }

    private void follow(SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
        command.addCommands(new FollowTrajectoryCommand(container.getSwerveDriveSubsystem(), trajectory)
                            .deadlineWith(new PrepareToShootCommand(container.getBalltrackSubsystem(), container.getShooterSubsystem(), container.getLimelightSubsystem())));
    }

    private void followAndIntake(SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
        command.addCommands(new FollowTrajectoryCommand(container.getSwerveDriveSubsystem(), trajectory)
                            .deadlineWith(new IntakeCommand(container.getBalltrackSubsystem())));
    }

    private void resetRobotPose(SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
        command.addCommands(new InstantCommand(() -> container.getSwerveDriveSubsystem().resetGyroAngle()));
        command.addCommands(new InstantCommand(() -> container.getSwerveDriveSubsystem().resetPose(trajectory.getInitialPose())));
    }

    public Command getAutonomousCommand(RobotContainer container) {
        switch (selectedAuto.getString(autoStrings[0])) {
            case "twoballmain":
                return getTwoBallMainCommand(container);        
        }

        // Return an empty command group if no auto is specified
        return new SequentialCommandGroup();
    }
}
