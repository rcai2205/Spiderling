package spiderling.lib.actions;

import spiderling.lib.checks.Check;

/**
 * This action should be called when the robot should not perform any function.
 * It can function as a placeholder so that a sequence of actions does not end or a break until a check's condition is met.
 *
 */
public class AcDoNothing extends Action
{
    /**
     * Constructor for a blank action that ends once a condition is met.
     *
     * @param check The condition to be met.
     */
    public AcDoNothing(Check check) {
        super(check);
    }
}