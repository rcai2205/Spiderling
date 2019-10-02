package spiderling.core;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.ArrayList;
import java.util.List;

import spiderling.lib.actions.AcWait;
import spiderling.lib.actions.Action;
import spiderling.lib.actions.MultiAction;
import spiderling.lib.actions.SwitchAction;
import spiderling.lib.actions.actuators.AcMotor;
import spiderling.lib.actions.actuators.AcServo;
import spiderling.lib.checks.ChTime;
import spiderling.lib.hardware.CtrlMotor;
import spiderling.lib.hardware.HardwareDevice;
import spiderling.lib.hardware.NonrotationalServo;

/**
 * The base class for all Spiderling HardwareMaps.
 * It handles the initialisation of all Spiderling HardwareDevices @see HardwareDevice.
 * All Hardware Devices should be created and defined in a subclass of this class.
 *Spiderling Hardware Devices should be defined at the top of the class.
 * Non-Spiderling Hardware Devices should be listed at the top, but with null listed instead of a constructor as done in - @see org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot
 * To initialise Non-Spiderling Hardware Devices, implement the initHardware method.
 *
 * @author Ben Schwarz.
 */
public abstract class HardwareMapBase {
    /* local OpMode members. */
    protected HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /** Stores List of Hardware Devices which are then initialised*/
    private List<HardwareDevice> hardwareDeviceList = new ArrayList<>();


    /* Constructor */
    public HardwareMapBase(){
    }

    public final void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        for (HardwareDevice hardwareDevice : hardwareDeviceList) hardwareDevice.init(hwMap);
        initHardware(hwMap);
    }

    protected abstract void initHardware(HardwareMap hwMap);

    /**
     *Adds a device to the list of devices to be initialised. This will automatically initialise the device.
     * @param hardwareDevice
     */
    public void addDevice(HardwareDevice hardwareDevice) {
        hardwareDeviceList.add(hardwareDevice);
    }

    /**
     * Removes a device from the list of devices to be automatically initalised.
     * @param hardwareDevice
     */
    public void removeDevice(HardwareDevice hardwareDevice) {
        hardwareDeviceList.remove(hardwareDevice);
    }
//    public class HardwareMapTemplate extends HardwareMapBase {
//
//        /* Public OpMode members. */
//        public CtrlMotor motorFL   = new CtrlMotor(this,"motorFL", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        public CtrlMotor  motorFR  = new CtrlMotor(this,"motorFR", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        public CtrlMotor motorBL = new CtrlMotor(this,"motorBL", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        public CtrlMotor motorBR = new CtrlMotor(this,"motorBR", DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        public CtrlMotor motorIntake = new CtrlMotor(this,"motorIntake", DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        public NonrotationalServo servoAL = new NonrotationalServo(this,"servoAL", Servo.Direction.FORWARD);
//        public NonrotationalServo servoAR = new NonrotationalServo(this,"servoAR", Servo.Direction.REVERSE);
//        public NonrotationalServo servoC = new NonrotationalServo(this,"servoC", Servo.Direction.FORWARD);
//
//        public ColorSensor sensorColor;
//        public DistanceSensor sensorDistance;
//
//        // State used for updating telemetry
//        public Orientation angles;
//        public Acceleration gravity;
//
//
//
//
//        //NonrotationalServo Locations
//        public final double AL_HOME = 0.2;
//        public final double AR_HOME = 0.0;
//        public final double AL_MIN_RANGE  = 0.00;
//        public final double AL_MAX_RANGE  = 0.00;
//        public final double AR_MIN_RANGE  = 0.2;
//        public final double AR_MAX_RANGE  = 0.75;
//        public final double sC_HOME = 0.46;
//        public final double sC_RIGHT = 0;
//        public final double sC_LEFT = 0.958;
//
//        /* local OpMode members. */
//        HardwareMap hwMap           =  null;
//        private ElapsedTime period  = new ElapsedTime();
//
//        public Boolean checkColour() {
//            double blue = sensorColor.blue();
//            double red = sensorColor.red();
//            if (blue - red > 1) return true;
//            else if (red - blue >= 1) return false;
//            return false;
//        }
//
//        //How to declare a sequence of actions in hardwareMap
//        public Action getColour = new MultiAction.Sequential(
//                new AcServo(servoC, sC_HOME),
//                new AcWait(3),
//                new SwitchAction(
//                        new AcServo(servoC, sC_RIGHT),
//                        new SwitchAction.OptionAction(() -> checkColour(), new AcServo(servoC, sC_LEFT)
//                        )),
//                new AcMotor.Set(motorIntake, 0.3, new ChTime(3), true)
//        );
//
//
//        @Override
//        protected void initHardware(HardwareMap hwMap) {
//
//            //note the colour and distance sensor is actually one device
//            // get a reference to the color sensor.
//            sensorColor = hwMap.get(ColorSensor.class, "sensor_color_distance");
//
//        }
    }

}
