package spiderling.lib.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import spiderling.core.HardwareMapBase;

/**
 * Base Class of all Spiderling HardwareDevice Wrappers
 * @author Ben Schwarz
 */
public abstract class HardwareDevice{
    protected String name;


    public abstract void init(HardwareMap hwMap);

    /**
     * Constructor which adds device to list of devices to be initialised in initialisation.
     * @param hardwareMap The hardwareMap in which the device is created.
     */
    public HardwareDevice(HardwareMapBase hardwareMap) {
        hardwareMap.addDevice(this);
    }

    /**
     * @return the Name of the device as listed in the configureHardware menu
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the device as listed on the configureHardware menu
     */
    public void setName(String name) {
        this.name = name;
    }


};