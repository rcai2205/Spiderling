package spiderling.lib.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import spiderling.core.HardwareMapBase;

public abstract class HardwareDevice{
    protected String name;


    public HardwareDevice(HardwareMapBase hardwareMapBase) {

    }
    public abstract void init(HardwareMap hwMap);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


};