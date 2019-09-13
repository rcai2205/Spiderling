package spiderling.core;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

import spiderling.lib.hardware.HardwareDevice;

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


}
