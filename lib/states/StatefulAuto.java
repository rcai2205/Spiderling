package spiderling.lib.states;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Disabled
@Autonomous(name="StatefulAuto", group="Spiderling")
public abstract class StatefulAuto<StateT extends Enum<?> & State<StateT, RobotT>, RobotT> extends OpMode {
    protected StateT currentState, endState;
    protected RobotT robot;

    public void setInitialState(StateT state) {
        currentState = state;
        currentState.constructState(robot);
    }

    public void setEndState(StateT state) {
        endState = state;
    }

    public void setCurrentState(StateT state) {
        this.currentState.deconstructState(robot);
        this.currentState = state;
        this.currentState.constructState(robot);
    }

    public StateT getCurrentState() {
        return currentState;
    }

    public boolean isFinished() {
        return currentState == endState;
    }

    protected abstract void nextState();
}
