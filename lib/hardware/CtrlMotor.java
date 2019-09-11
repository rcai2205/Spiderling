package spiderling.lib.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CtrlMotor {
    public DcMotor motor;
    String name;
    private double power;
    private HardwareMap hwMap;
    DcMotor.Direction direction;
    DcMotor.RunMode runMode;

    public CtrlMotor(HardwareMap hwMap, String name, DcMotor.Direction direction, DcMotor.RunMode runMode) {
        this.motor = hwMap.get(DcMotor.class, name);
        this.name = name;
        this.motor.setDirection(direction);
        this.motor.setPower(0);
        this.motor.setMode(runMode);

    }

    public void setPower(double power) {
        this.motor.getController().setMotorPower(motor.getPortNumber(), power);
    }
}

