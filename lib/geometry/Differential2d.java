package spiderling.lib.geometry;


/**
 * A movement along an arc at constant curvature and velocity. We can use ideas from "differential calculus" to create
 * new Pose2d's from a Differential2d and visa versa.
 * <p>
 * A Differential can be used to represent a difference between two poses, a velocity, an acceleration, etc.
 * <p>
 * The Differential can also hold time information, but this isn't strictly required by the constructor. This time information can be used in many ways.
 * This is an inherently messy class in the sense it can be used in a large number of (conflicting) ways.
 *
 * @author Ben Schwarz
 */
public class Differential2d extends Twist2d {
    protected double timestamp, period;
    private final static double EPSILON = 1E-9;

    /**
     * Creates an Differential2d object with all values at 0.
     */
    public Differential2d() {
        super();
    }

    /**
     * Creates a Differential2d object which is identical to a Twist2d object.
     * It has a period of 0.
     * This is primarily useful for instantaneous positions.
     *
     * @param dx     Change in x component
     * @param dy     Change in y component
     * @param dtheta Change in Angular component (radians)
     */
    public Differential2d(double dx, double dy, double dtheta) {
        super(dx, dy, dtheta);
    }

    /**
     * @param dx     Change in x component
     * @param dy     Change in y component
     * @param dtheta Change in Angular component (radians)
     * @param period The time over which the change occurred.
     */
    public Differential2d(double dx, double dy, double dtheta, double period) {
        super(dx, dy, dtheta);
        this.period = period;
    }

    /**
     * @param dx        Change in x component
     * @param dy        Change in y component
     * @param dtheta    Change in Angular component (radians)
     * @param period    The time over which the change occurred.
     * @param timestamp The time at which the measurement occurred (The end of the period).
     */
    public Differential2d(double dx, double dy, double dtheta, double period, double timestamp) {
        super(dx, dy, dtheta);
        this.timestamp = timestamp;
        this.period = period;
    }

    /**
     * Scales the Differential2d by a scalar. The timestamp is kept constant.
     *
     * @param scalar
     * @return A Differential2d with the dx, dy, dtheta and period being scaled by the provided scalar.
     */
    public Differential2d scaleBy(double scalar) {
        return new Differential2d(dx * scalar, dy * scalar, dtheta * scalar, period * scalar, timestamp);
    }

    /**
     * Divides the Differential2d by a divisor. The timestamp is kept constant.
     *
     * @param divisor
     * @return A Differential2d with the dx, dy, dtheta and period being scaled by the provided divisor.
     */
    public Differential2d divideBy(double divisor) {
        return scaleBy(1 / divisor);
    }

    public double norm() {
        //Common Cases of dx or dy being 0.
        if (dy == 0.0) return Math.abs(dx);
        if (dx == 0.0) return Math.abs(dy);
        return Math.hypot(dx, dy);
    }

    /**
     * Note: If the angle and norm are less that 1E-9 then this returns 0 (The same equality constant used in the equals method of WPI's {@link Twist2d})
     */
    public double curvature() {
        if (Math.abs(dtheta) < EPSILON && norm() < EPSILON)
            return 0.0;
        return dtheta / norm();
    }

    /**
     * If this returns 0 is it likely this value wasn't initialized.
     *
     * @return The time over which the change occurred.
     */
    public double getPeriod() {
        return period;
    }

    /**
     * @param period The time over which the change occurred.
     */
    public void setPeriod(double period) {
        this.period = period;
    }

    /**
     * If this returns 0 is it likely this value wasn't initialized.
     *
     * @return the timestamp time when the period ended
     */
    public double getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the time at which the measurement occurred (The end of the period).
     *
     * @param timeStamp time when the period ended
     */
    public void setTimestamp(double timeStamp) {
        this.timestamp = timeStamp;
    }

    /**
     * Checks equality between this Differential2d and another object.
     * Ignores time as as a factor in equality checking.
     *
     * @param obj The other object.
     * @return Whether the two objects are equal or not.
     */
    public boolean equalsDiscountingTime(Object obj) {
        if (obj instanceof Differential2d) {
            return Math.abs(((Differential2d) obj).dx - dx) < EPSILON
                    && Math.abs(((Differential2d) obj).dy - dy) < EPSILON
                    && Math.abs(((Differential2d) obj).dtheta - dtheta) < EPSILON;
        }
        return false;
    }

    /**
     * Checks equality between this Differential2d and another object.
     *
     * @param obj The other object.
     * @return Whether the two objects are equal or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Differential2d) {
            return Math.abs(((Differential2d) obj).dx - dx) < EPSILON
                    && Math.abs(((Differential2d) obj).dy - dy) < EPSILON
                    && Math.abs(((Differential2d) obj).dtheta - dtheta) < EPSILON
                    && Math.abs(((Differential2d) obj).period - period) < EPSILON
                    && Math.abs(((Differential2d) obj).timestamp - timestamp) < EPSILON;
        }
        return false;
    }

    /**
     * Returns a Twist2d object. This creates a new object rather then pointing to the existing Differential2d.
     * This is a faster way of calling the Twist2d constructor.
     */
    public Twist2d getAsTwist() {
        return new Twist2d(dx, dy, dtheta);
    }
}
