package spiderling.lib.actions;

import spiderling.lib.checks.ChTime;

/**
 * A convenient shortcut action that does nothing for a specified amount of time before starting the next action.
 * Functionally equivalent to {@link AcDoNothing AcDoNothing} with an instance of {@link ChTime ChTime} as the parameter for the constructor.
 *
 */
public class AcWait extends Action {
    /**
     * Constructor for an action that does nothing for a specified amount of time.
     *
     * @param timeout The number of seconds that the action will wait for.
     */
    public AcWait(double timeout) {
        super(new ChTime(timeout));
    }
}
