package spiderling.lib.actions;

import spiderling.lib.checks.Check;
import spiderling.lib.logic.Consumer;
import spiderling.lib.logic.GettableBoolean;

/**
 * An action that can be fully defined by functional interfaces.
 * The intent of this class is to remove the requirement for teams to create a new action class for specialised one-off
 * actions like running an existing function or printing a reading. If you're using it for the same thing in multiple
 * places, it's probably better to just make a new class.
 *
 * @author Sean Zammit
 */
public class AcLambda extends Action
{
    Consumer<Action> onStart, onRun, onFinish;
    GettableBoolean isDone;

    /**
     * Constructor for an action whose functionality is fully defined by functional interfaces.
     *
     */
    public AcLambda(Check check, Consumer<Action> onRun) {
        this(check, action -> {}, onRun, action -> {}, () -> false);
    }

    /**
     * Constructor for an action whose functionality is fully defined by functional interfaces.
     *
     * @param check The condition that will finish the action.
     * @param onStart A function to run when the action starts. The function takes this action as input and returns no output.
     * @param onRun A function to run repeatedly while the action runs. The function takes this action as input and returns no output.
     * @param onFinish A function to run when the action finishes. The function takes this action as input and returns no output.
     */
    public AcLambda(Check check, Consumer<Action> onStart, Consumer<Action> onRun, Consumer<Action> onFinish) {
        this(check, onStart, onRun, onFinish, () -> false);
    }

    /**
     * Constructor for an action whose functionality is fully defined by functional interfaces.
     *
     * @param check The condition that will finish the action.
     * @param onStart A function to run when the action starts. The function takes this action as input and returns no output.
     * @param onRun A function to run repeatedly while the action runs. The function takes this action as input and returns no output.
     * @param onFinish A function to run when the action finishes. The function takes this action as input and returns no output.
     * @param isDone A function to determine whether the action should stop running. The function takes no input and returns a boolean output.
     */
    public AcLambda(Check check, Consumer<Action> onStart, Consumer<Action> onRun, Consumer<Action> onFinish, GettableBoolean isDone) {
        super(check);
        this.onStart = onStart;
        this.onRun = onRun;
        this.onFinish = onFinish;
        this.isDone = isDone;
    }

    public void onStart() {
        onStart.accept(this);
    }

    public void onRun() {
        onRun.accept(this);
    }

    public void onFinish() {
        onFinish.accept(this);
    }

    public boolean isDone() {
        return isDone.get();
    }
}