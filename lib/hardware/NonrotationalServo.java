package spiderling.lib.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import spiderling.core.HardwareMapBase;

public class NonrotationalServo extends HardwareDevice {
    public Servo servo;
    private Servo.Direction initialDirection;

    public NonrotationalServo(String name, Servo.Direction direction) {
      this.name = name;
      this.initialDirection = direction;
    }

    @Override
    public void init(HardwareMap hwMap) {
        servo = hwMap.get(Servo.class, name);
        servo.setDirection(initialDirection);
    }

    /**
     * Returns the underlying servo controller on which this servo is situated.
     * @return the underlying servo controller on which this servo is situated.
     * @see #getPortNumber()
     */
    public ServoController getController() {
        return servo.getController();
    }

    /**
     * Returns the port number on the underlying servo controller on which this motor is situated.
     * @return the port number on the underlying servo controller on which this motor is situated.
     * @see #getController()
     */
    public int getPortNumber(){
        return servo.getPortNumber();
    }

    /**
     * Sets the logical direction in which this servo operates.
     * @param direction the direction to set for this servo
     *
     * @see #getDirection()
     * @see com.qualcomm.robotcore.hardware.Servo.Direction
     */
    public void setDirection(Servo.Direction direction) {
        servo.setDirection(direction);
    }

    /**
     * Returns the current logical direction in which this servo is set as operating.
     * @return the current logical direction in which this servo is set as operating.
     * @see #setDirection(Servo.Direction)
     */
    public Servo.Direction getDirection() {
        return servo.getDirection();
    }

    /**
     * Sets the current position of the servo, expressed as a fraction of its available
     * range. If PWM power is enabled for the servo, the servo will attempt to move to
     * the indicated position.
     *
     * @param position the position to which the servo should move, a value in the range [0.0, 1.0]
     * @see ServoController#pwmEnable()
     * @see #getPosition()
     */
    public void setPosition(double position) {
        servo.setPosition(position);
    }

    /**
     * Returns the position to which the servo was last commanded to move. Note that this method
     * does NOT read a position from the servo through any electrical means, as no such electrical
     * mechanism is, generally, available.
     * @return the position to which the servo was last commanded to move, or Double.NaN
     *         if no such position is known
     * @see #setPosition(double)
     * @see Double#NaN
     * @see Double#isNaN()
     */
    public double getPosition() {
        return servo.getPosition();
    }

    /**
     * Scales the available movement range of the servo to be a subset of its maximum range. Subsequent
     * positioning calls will operate within that subset range. This is useful if your servo has
     * only a limited useful range of movement due to the physical hardware that it is manipulating
     * (as is often the case) but you don't want to have to manually scale and adjust the input
     * to {@link #setPosition(double) setPosition()} each time.
     *
     * <p>For example, if scaleRange(0.2, 0.8) is set; then servo positions will be
     * scaled to fit in that range:<br>
     * setPosition(0.0) scales to 0.2<br>
     * setPosition(1.0) scales to 0.8<br>
     * setPosition(0.5) scales to 0.5<br>
     * setPosition(0.25) scales to 0.35<br>
     * setPosition(0.75) scales to 0.65<br>
     * </p>
     *
     * <p>Note the parameters passed here are relative to the underlying full range of motion of
     * the servo, not its currently scaled range, if any. Thus, scaleRange(0.0, 1.0) will reset
     * the servo to its full range of movement.</p>
     *
     * @param min    the lower limit of the servo movement range, a value in the interval [0.0, 1.0]
     * @param max    the upper limit of the servo movement range, a value in the interval [0.0, 1.0]
     *
     * @see #setPosition(double)
     */
    public void scaleRange(double min, double max) {
        servo.scaleRange(min, max);
    }

}
