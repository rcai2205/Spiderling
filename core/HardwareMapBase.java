package spiderling.core;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

import spiderling.lib.hardware.HardwareDevice;

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
