package spiderling.lib.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

import spiderling.core.HardwareMapBase;

public class CtrlMotor extends HardwareMapBase.HardwareDevice {
    public DcMotor motor;
    String name;
    private double power;
    DcMotor.Direction direction;
    DcMotor.RunMode runMode;

    public CtrlMotor(String name, DcMotor.Direction direction, DcMotor.RunMode runMode) {
        this.name = name;
        this.direction = direction;
        this.runMode = runMode;
    }

    public void init(HardwareMap hwMap) {
        this.motor = hwMap.get(DcMotor.class, name);
        this.motor.setDirection(direction);
        this.motor.setPower(0);
        this.motor.setMode(runMode);
    }

    public void setPower(double power) {
        //this.motor.getController().setMotorPower(motor.getPortNumber(), power);
        this.motor.setPower(power);
    }

}

