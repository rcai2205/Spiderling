package spiderling.lib.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import spiderling.core.HardwareMapBase;

import static java.lang.Thread.sleep;

public class RevIMU extends HardwareDevice{
    public BNO055IMU imu;
    public BNO055IMU.Parameters parameters;
    Orientation lastAngles = new Orientation();
    double correction;

    public RevIMU(HardwareMapBase hardwareMap, String name, BNO055IMU.Parameters parameters) {
        super(hardwareMap, name);
        this.parameters = parameters;
    }

    public void init(HardwareMap hwMap) {
        imu = hwMap.get(BNO055IMU.class, name);
        imu.initialize(parameters);
    }

    /**
     * If for some reason you want to adjust the heading manually, use this method.
     */
    public void setCorrection(double heading)
    {
        correction = heading;
    }

    /**
     * Get current heading + correction.
     */
    public double getHeading() {
        return correction + imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }


}
