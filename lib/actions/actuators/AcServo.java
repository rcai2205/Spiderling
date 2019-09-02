package spiderling.lib.actions.actuators;

import com.qualcomm.robotcore.hardware.Servo;

import spiderling.lib.actions.Action;
import spiderling.lib.checks.Check;

public class AcServo extends Action {
    Servo servo;
    double position;
    boolean continouslySet;

    /**
     * Constructor for an action with a check to determine when it will finish.
     * @param servo The servo which position will be set.
     * @param position Sets the position of the servo.
     * @param continouslySet Sets whether or not the position should be constantly updated or set once.
     * @param check The check associated with the action that determines when the action is complete
     */
    protected AcServo(Servo servo, double position, boolean continouslySet, Check check) {
        super(check);
        this.servo = servo;
        this.position = position;
        this.continouslySet = continouslySet;
    }

    protected void onStart() {
        servo.setPosition(position);
    }

    protected void onRun() {
        if(continouslySet) servo.setPosition(position);
    }
}
