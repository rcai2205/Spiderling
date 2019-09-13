package spiderling.lib.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class HardwareDevice{
    protected String name;
    public abstract void init(HardwareMap hwMap);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


};