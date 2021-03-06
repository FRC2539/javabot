package frc.robot.util;

import com.pathplanner.lib.PathPlannerTrajectory;

public class TrajectoryLoader {
    private static final String TWO_BALL_PATH = "twoball";
    private static final String THREE_BALL_PATH = "threeball";
    private static final String FIVE_BALL_PATH = "fiveball";
    private static final String FIVE_BALL_1_PATH = "fiveball 1";
    private static final String FIVE_BALL_2_PATH = "fiveball 2";
    private static final String FOUR_BALL_PATH = "fourball 1";

    private LazyPathPlannerTrajectory threeBall;
    private LazyPathPlannerTrajectory twoBall;
    private LazyPathPlannerTrajectory fiveBall;
    private LazyPathPlannerTrajectory fourBall;
    private LazyPathPlannerTrajectory fiveBall1;
    private LazyPathPlannerTrajectory fiveBall2;

    public TrajectoryLoader() {
        threeBall = new LazyPathPlannerTrajectory(THREE_BALL_PATH, 5, 2.5);
        twoBall = new LazyPathPlannerTrajectory(TWO_BALL_PATH, 5, 2.5);
        fiveBall = new LazyPathPlannerTrajectory(FIVE_BALL_PATH, 5, 2.5);
        fourBall = new LazyPathPlannerTrajectory(FOUR_BALL_PATH, 5, 2.5);
        fiveBall1 = new LazyPathPlannerTrajectory(FIVE_BALL_1_PATH, 5, 2.5);
        fiveBall2 = new LazyPathPlannerTrajectory(FIVE_BALL_2_PATH, 5, 2.5);
    }

    public PathPlannerTrajectory getThreeBall() {
        return threeBall.getTrajectory();
    }

    public PathPlannerTrajectory getTwoBall() {
        return twoBall.getTrajectory();
    }

    public PathPlannerTrajectory getFiveBall() {
        return fiveBall.getTrajectory();
    }

    public PathPlannerTrajectory getFiveBall1() {
        return fiveBall1.getTrajectory();
    }

    public PathPlannerTrajectory getFiveBall2() {
        return fiveBall2.getTrajectory();
    }

    public PathPlannerTrajectory getFourBall() {
        return fourBall.getTrajectory();
    }
}
