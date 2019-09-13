package spiderling.core;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class HardwareMapBase {
    /* local OpMode members. */
    protected HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareMapBase(){
    }

    public final void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        initHardware(hwMap);
    }

    protected abstract void initHardware(HardwareMap hwMap);


}
