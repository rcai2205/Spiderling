package spiderling.lib.actions.actuators;

import com.qualcomm.robotcore.hardware.Servo;

import spiderling.lib.actions.Action;
import spiderling.lib.checks.ChFalse;
import spiderling.lib.checks.Check;

public class AcServo extends Action {
    Servo servo;
    double position;

    /**
     * Constructor for an action which sets a servoPosition
     * @param servo The servo which position will be set.
     * @param position Sets the position of the servo.
     */
    public AcServo(Servo servo, double position) {
        super(new ChFalse());
        this.servo = servo;
        this.position = position;
    }

    protected void onStart() {
        servo.setPosition(position);
    }

}
