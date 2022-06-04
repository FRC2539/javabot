package frc.robot.util;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import java.io.IOException;
import java.nio.file.Path;

public class TrajectoryLoader {
    private static final String THREE_BALL_ONE_PATH = "output/threeball1.wpilib.json";
    private static final String THREE_BALL_TWO_PATH = "output/threeball2.wpilib.json";
    private static final String TWO_BALL_MAIN_PATH = "output/twoballmain.wpilib.json";
    private static final String TWO_BALL_SIDE_PATH = "output/twoballside.wpilib.json";
    private static final String TWO_BALL_PATH = "twoball";
    private static final String PATH_PLANNER_THREE_BALL_PATH = "threeball";

    private Trajectory threeBallOne;
    private Trajectory threeBallTwo;
    private Trajectory twoBallMain;
    private Trajectory twoBallSide;
    private PathPlannerTrajectory threeBall;
    private PathPlannerTrajectory twoball;

    public TrajectoryLoader() throws IOException {
        threeBallOne = loadTrajectory(THREE_BALL_ONE_PATH);
        threeBallTwo = loadTrajectory(THREE_BALL_TWO_PATH);
        twoBallMain = loadTrajectory(TWO_BALL_MAIN_PATH);
        twoBallSide = loadTrajectory(TWO_BALL_SIDE_PATH);
        threeBall = loadTrajectory(PATH_PLANNER_THREE_BALL_PATH, 5, 2.5);
        twoball = loadTrajectory(TWO_BALL_PATH, 5, 2.5);

        System.out.println("\nAll trajectories loaded successfully.\n");
    }

    private PathPlannerTrajectory loadTrajectory(String path, double maxVel, double maxAccel) throws IOException {
        return PathPlanner.loadPath(path, maxVel, maxAccel);
    }

    private Trajectory loadTrajectory(String path) throws IOException {
        return TrajectoryUtil.fromPathweaverJson(getPath(path));
    }

    private Path getPath(String path) {
        return Filesystem.getDeployDirectory().toPath().resolve(path);
    }

    public Trajectory getThreeBallOne() {
        return threeBallOne;
    }

    public Trajectory getThreeBallTwo() {
        return threeBallTwo;
    }

    public Trajectory getTwoBallMain() {
        return twoBallMain;
    }

    public Trajectory getTwoBallSide() {
        return twoBallSide;
    }

    public PathPlannerTrajectory getThreeBall() {
        return threeBall;
    }

    public PathPlannerTrajectory getTwoBall() {
        return twoball;
    }
}