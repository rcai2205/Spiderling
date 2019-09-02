package spiderling.lib.actions;

import spiderling.lib.actions.Action;
import spiderling.lib.checks.ChMulti;
import spiderling.lib.checks.ChTrue;
import spiderling.lib.checks.Check;
import spiderling.lib.checks.digital.ChGettableBoolean;
import spiderling.lib.logic.GettableBoolean;
import spiderling.lib.logic.LogicOperators;

/**
 * @author Ben Schwarz
 */
public class MultiAction {


    public static class Sequential{
        Check check = null;


        int counter = 0;
        Action[] actions;

        /**
         * Constructor to run multiple actions consecutively
         * Will only finish after all actions in the sequence are complete.
         * @param actions The sequence of actions to be completed.
         */
        public Sequential(Action... actions) {
            this.actions = actions;
            this.check = new ChGettableBoolean(() -> counter >= actions.length, true);
        }
        /**
         * Constructor to run multiple actions consecutively
         * @param mustFinishSequence Determines if the action will conclude if either the inputted check is completed or the sequence is complete, or if both the check and the sequence need to complete.
         * @param check The check to determine
         * @param actions The sequence of actions to be completed.
         */
        public Sequential (Check check, boolean mustFinishSequence, Action... actions) {
            if (mustFinishSequence) this.check = new ChMulti(LogicOperators.AND, new ChGettableBoolean(() -> counter >= actions.length, true), check);
            else this.check = new ChMulti(LogicOperators.OR, new ChGettableBoolean(() -> counter >= actions.length, true), check);
        }

        /**
         * Used by individual actions to set other conditions for the action to be complete.
         *
         * @return Whether the action should stop running.
         */
        protected boolean isDone() {
            return false;
        }

        /**
         * Used by individual actions to perform any operations while the action is running.
         */
        protected void onRun() {
            if(counter < actions.length) {
                if (actions[counter].actionLoop(actions[counter])) {
                    counter++;
                }
            }
        }





    }

    public static class Parallel{

    }
}
