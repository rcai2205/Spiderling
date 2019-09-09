package spiderling.core;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import spiderling.lib.actions.Action;


@Disabled
@Autonomous(name="AutonomousBase", group="Redbacks")
public abstract class AutonomousBase extends OpMode {
    final double moveSp = 0.4D;

    double lastTime;
    int counter;
    Action[] actions = new Action[]{

    };


    //FIXME Suspect code here.
    //Code that runs when INT is pressed
    @Override
    public final void init() {
        getHardwareMap().init(hardwareMap); //Made this change to if the cast to the regular hard
        initialise();
    }

    /**
     * Code that runs ONCE when the driver hits int.
     */
    protected void initialise() {

    }

    //Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public final void init_loop() {
    }

    //Code that runs ONCE when the driver hits play
    @Override
    public  final void start() {
        lastTime = System.currentTimeMillis();
    }

    //Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public final void loop() {
        Action[] actions = getAutonomous();
        if(counter < actions.length) {
            telemetry.addData("actions progress", counter);
          if (actions[counter].actionLoop(actions[counter])) {
              telemetry.addData("action finished", actions.length);
              counter++;
          }
        }
        updateTelemetry(telemetry);
    }

    //Code that runs once when driver hits stop
    @Override
    public void stop() {
    }

    /**
     * Implemented by Each Subclass.
     * @return the list of actions to be followed.
     */
    protected abstract Action[] getAutonomous();

    /**
     * Implemented by Each Subclass
     * @return the HardwareMap being used.
     */
    protected abstract HardwareMapBase getHardwareMap();
}