package spiderling.lib.actions.actuators;

import com.qualcomm.robotcore.hardware.Servo;

import spiderling.lib.actions.Action;
import spiderling.lib.checks.ChTrue;
import spiderling.lib.checks.Check;
import spiderling.lib.hardware.NonrotationalServo;

/**
 * Sets a Servo to a position.
 * @author Ben Schwarz
 */
public class AcServo extends Action {
    NonrotationalServo servo;
    double position;

    /**
     * Constructor for an action which sets a servoPosition
     * @param servo The servo which position will be set.
     * @param position Sets the position of the servo.
     */
    public AcServo(NonrotationalServo servo, double position) {
        super(new ChTrue());
        this.servo = servo;
        this.position = position;
    }

    protected void onStart() {
        servo.setPosition(position);
    }

}
