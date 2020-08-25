package spiderling.lib.motion;


import spiderling.lib.geometry.Differential2d;
import spiderling.lib.geometry.Pose2d;

/**
 * Class to describe a state of 2d motion at a point in time.
 *
 * @author Ben Schwarz
 */
public class MotionState2d {
    protected Pose2d pose;
    protected Differential2d velocity;
    protected Differential2d acceleration;
    protected double timestamp; //seconds
    final static double EPSILON = 1E-9;

    /**
     * Constructs a pose at the origin facing toward the positive X axis.(Translation2d{0, 0} and Rotation{0}) with a time of 0.
     */
    public MotionState2d() {
        this(new Pose2d(), 0);
    }

    public MotionState2d(Pose2d position, double timestamp) {
        this(position, new Differential2d(), timestamp);
    }

    public MotionState2d(Pose2d position, Differential2d velocity, double timestamp) {
        this(position, velocity, new Differential2d(), timestamp);
    }

    public MotionState2d(Pose2d position, Differential2d velocity, Differential2d acceleration, double timestamp) {
        this.pose = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.timestamp = timestamp;
    }

    //TODO consider using Pose2d.exp() to better predict with respect to curves.

    /**
     * Predicts what the state of motion will be at a specified time in the future or past based on the current motion state.
     * Acceleration is assumed constant.
     * This is innacurate when travelling in an arc.
     *
     * @param timeFromState The length of time since this state.
     * @return The predicted motion state
     */
    public MotionState2d predictState(double timeFromState) {
        final double dxDisplacement = velocity.dx * timeFromState + 0.5 * acceleration.dx * timeFromState * timeFromState;
        final double dyDisplacement = velocity.dy * timeFromState + 0.5 * acceleration.dy * timeFromState * timeFromState;
        final double dthetaDisplacement = velocity.dtheta * timeFromState + 0.5 * acceleration.dtheta * timeFromState * timeFromState;
        Differential2d displacement = new Differential2d(dxDisplacement, dyDisplacement, dthetaDisplacement, timeFromState, timestamp + timeFromState);
        Pose2d predictedPose = pose.exp(displacement);

        final double dxVelocity = velocity.dx + acceleration.dx * timeFromState;
        final double dyVelocity = velocity.dy + acceleration.dy * timeFromState;
        final double dthetaVelocity = velocity.dtheta + acceleration.dtheta * timeFromState;
        Differential2d newVelocity = new Differential2d(dxVelocity, dyVelocity, dthetaVelocity, timeFromState, timeFromState + timestamp);

        return new MotionState2d(predictedPose, newVelocity, acceleration, timestamp + timeFromState);
    }

    public Pose2d getPose() {
        return pose;
    }

    public Differential2d getVelocity() {
        return velocity;
    }

    public Differential2d getAcceleration() {
        return acceleration;
    }

    public double getTimestamp() {
        return timestamp;
    }

    /**
     * Returns if two Motion States are identical aside from their time differences.
     */
    public boolean equalsDiscountingTime(Object obj) {
        if (obj instanceof MotionState2d) {
            return (
                    this.getPose().equals(((MotionState2d) obj).getPose()) &&
                            this.getVelocity().equalsDiscountingTime(((MotionState2d) obj).getVelocity()) &&
                            this.getAcceleration().equalsDiscountingTime(((MotionState2d) obj).getAcceleration())
            );
        }

        return false;
    }

    /**
     * Returns if two Motion States are identical aside from the time data contained in the {@link Differential2d} objects.
     */
    public boolean equalsDiscountingDifferentialTimeData(Object obj) {
        if (obj instanceof MotionState2d) {
            return (
                    this.getPose().equals(((MotionState2d) obj).getPose()) &&
                            this.getVelocity().equalsDiscountingTime(((MotionState2d) obj).getVelocity()) &&
                            this.getAcceleration().equalsDiscountingTime(((MotionState2d) obj).getAcceleration()) &&
                            Math.abs(((MotionState2d) obj).timestamp - timestamp) < EPSILON
            );
        }

        return false;
    }

    /**
     * Checks equality between this MotionState2d and another object.
     *
     * @param obj The other object.
     * @return Whether the two objects are equal or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MotionState2d) {
            return (
                    this.getPose().equals(((MotionState2d) obj).getPose()) &&
                            this.getVelocity().equals(((MotionState2d) obj).getVelocity()) &&
                            this.getAcceleration().equals(((MotionState2d) obj).getAcceleration()) &&
                            Math.abs(((MotionState2d) obj).timestamp - timestamp) < EPSILON
            );
        }

        return false;
    }
}
