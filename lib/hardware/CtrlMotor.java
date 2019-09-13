package spiderling.lib.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import spiderling.core.HardwareMapBase;

/**
 * Wrapper Class for a DcMotor
 * @author Ben Schwarz
 */
public class CtrlMotor extends HardwareDevice {
    public DcMotor motor;
    private double initialPower;
    DcMotor.Direction initialDirection;
    DcMotor.RunMode initialRunMode;

    public CtrlMotor(HardwareMapBase hardwareMap, String name, DcMotor.Direction direction, DcMotor.RunMode runMode) {
        super(hardwareMap);
        this.name = name;
        this.initialDirection = direction;
        this.initialRunMode= runMode;
    }

    public void init(HardwareMap hwMap) {
        this.motor = hwMap.get(DcMotor.class, name);
        this.motor.setDirection(initialDirection);
        this.motor.setPower(0);
        this.motor.setMode(initialRunMode);
    }

    /**
     * Sets the power level of the motor, expressed as a fraction of the maximum
     * possible power / speed supported according to the run mode in which the
     * motor is operating.
     *
     * <p>Setting a power level of zero will brake the motor</p>
     *
     * @param power the new power level of the motor, a value in the interval [-1.0, 1.0]
     * @see #getPower()
     * @see DcMotor#setMode(DcMotor.RunMode)
     * @see DcMotor#setPowerFloat()
     */
    public void setPower(double power) {
        this.motor.setPower(power);
    }
    /**
     * Returns the current logical direction in which this motor is set as operating.
     * @return the current logical direction in which this motor is set as operating.
     * @see #setDirection(DcMotorSimple.Direction)
     */
    public DcMotor.Direction getDirection() {
        return motor.getDirection();
    }
    /**
     * Sets the logical direction in which this motor operates.
     * @param direction the direction to set for this motor
     *
     * @see #getDirection()
     */
    public void setDirection(DcMotor.Direction direction) {
        this.motor.setDirection(direction);
    }

    /**
     * Returns the current run mode for this motor
     * @return the current run mode for this motor
     * @see DcMotor.RunMode
     * @see #setRunMode(DcMotor.RunMode)
     */
    public DcMotor.RunMode getRunMode() {
        return motor.getMode();
    }

    /**
     * Sets the current run mode for this motor
     * @param runMode the new current run mode for this motor
     * @see DcMotor.RunMode
     * @see #getRunMode()
     */
    public void setRunMode(DcMotor.RunMode runMode) {
        this.motor.setMode(runMode);
    }

    /**
     * Returns the current configured power level of the motor.
     * @return the current level of the motor, a value in the interval [0.0, 1.0]
     * @see #setPower(double)
     */
    public double getPower() {
        return motor.getPower();
    }

    /**
     * Returns the assigned type for this motor. If no particular motor type has been
     * configured, then {@link MotorConfigurationType#getUnspecifiedMotorType()} will be returned.
     * Note that the motor type for a given motor is initially assigned in the robot
     * configuration user interface, though it may subsequently be modified using methods herein.
     * @return the assigned type for this motor
     */
    public MotorConfigurationType getMotorType() {
        return motor.getMotorType();
    }

    /**
     * Sets the assigned type of this motor. Usage of this method is very rare.
     * @param motorType the new assigned type for this motor
     * @see #getMotorType()
     */
    public void setMotorType(MotorConfigurationType motorType) {
        motor.setMotorType(motorType);
    };

    /**
     * Returns the underlying motor controller on which this motor is situated.
     * @return the underlying motor controller on which this motor is situated.
     * @see #getPortNumber()
     */
    public DcMotorController getController() {
        return motor.getController();
    }

    /**
     * Returns the port number on the underlying motor controller on which this motor is situated.
     * @return the port number on the underlying motor controller on which this motor is situated.
     * @see #getController()
     */
    public int getPortNumber() {
        return motor.getPortNumber();
    }


    /**
     * Sets the behavior of the motor when a power level of zero is applied.
     * @param zeroPowerBehavior the new behavior of the motor when a power level of zero is applied.
     * @see DcMotor.ZeroPowerBehavior
     * @see #setPower(double)
     */
    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        motor.setZeroPowerBehavior(zeroPowerBehavior);
    };

    /**
     * Returns the current behavior of the motor were a power level of zero to be applied.
     * @return the current behavior of the motor were a power level of zero to be applied.
     */
    public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() {
        return motor.getZeroPowerBehavior();
    };


    /**
     * Returns whether the motor is currently in a float power level.
     * @return whether the motor is currently in a float power level.
     */
    public boolean getPowerFloat() {
        return motor.getPowerFloat();
    };
    /**
     * Sets the desired encoder target position to which the motor should advance or retreat
     * and then actively hold thereat. This behavior is similar to the operation of a servo.
     * The maximum speed at which this advance or retreat occurs is governed by the power level
     * currently set on the motor. While the motor is advancing or retreating to the desired
     * taget position, {@link #isBusy()} will return true.
     *
     * <p>Note that adjustment to a target position is only effective when the motor is in
     * {@link DcMotor.RunMode#RUN_TO_POSITION RUN_TO_POSITION}
     * RunMode. Note further that, clearly, the motor must be equipped with an encoder in order
     * for this mode to function properly.</p>
     *
     * @param position the desired encoder target position
     * @see #getCurrentPosition()
     * @see DcMotor.RunMode#RUN_TO_POSITION
     * @see #getTargetPosition()
     * @see #isBusy()
     */
    public void setTargetPosition(int position) {
        motor.setTargetPosition(position);
    }

    /**
     * Returns the current target encoder position for this motor.
     * @return the current target encoder position for this motor.
     * @see #setTargetPosition(int)
     */
    public int getTargetPosition() {
        return motor.getTargetPosition();
    }

    /**
     * Returns true if the motor is currently advancing or retreating to a target position.
     * @return true if the motor is currently advancing or retreating to a target position.
     * @see #setTargetPosition(int)
     */
    public boolean isBusy() {
        return motor.isBusy();
    };

    /**
     * Returns the current reading of the encoder for this motor. The units for this reading,
     * that is, the number of ticks per revolution, are specific to the motor/encoder in question,
     * and thus are not specified here.
     * @return the current reading of the encoder for this motor
     * @see #getTargetPosition()
     * @see DcMotor.RunMode#STOP_AND_RESET_ENCODER
     */
    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    };


}

