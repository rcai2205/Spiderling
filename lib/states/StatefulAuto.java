package spiderling.lib.states;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Disabled
@Autonomous(name="StatefulAuto", group="Spiderling")
public abstract class StatefulAuto<StateT extends Enum<?> & State<StateT>> extends OpMode {
    protected StateT currentState, endState;

    public void setEndState(StateT state) {
        endState = state;
    }

    public void setCurrentState(StateT currentState) {
        this.currentState.deconstructState();
        this.currentState = currentState;
        this.currentState.constructState();
    }

    public StateT getCurrentState() {
        return currentState;
    }

    public boolean isFinished() {
        return currentState == endState;
    }
}
