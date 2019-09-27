package spiderling.lib.actions;

import spiderling.lib.checks.ChFalse;
import spiderling.lib.checks.ChMulti;
import spiderling.lib.checks.ChTrue;
import spiderling.lib.checks.Check;
import spiderling.lib.checks.digital.ChGettableBoolean;
import spiderling.lib.logic.LogicOperators;

/**
 * @author Ben Schwarz
 */
public class MultiAction {

    public static class Sequential extends Action{
        Check check = null;
        int counter = 0;
        Action[] actions;

        /**
         * Constructor to run multiple actions consecutively
         * Will only finish after all actions in the sequence are complete.
         * @param actions The sequence of actions to be completed.
         */
        public Sequential(Action... actions) {
            super(new ChFalse());
            this.check = new ChGettableBoolean(() -> counter >= actions.length, true);
            this.actions = actions;
        }

        /**
         * Constructor to run multiple actions consecutively
         * @param check The check to determine if the action should finish.
         * @param mustFinishSequence Determines if the action will conclude if either the inputted check is completed or the sequence is complete (FALSE),
         *                           or if both the check and the sequence need to complete in order for the action to complete (TRUE).
         * @param actions The sequence of actions to be completed.
         */
        public Sequential (Check check, boolean mustFinishSequence, Action... actions) {
            super(new ChFalse());
            if (mustFinishSequence) this.check = new ChMulti(LogicOperators.AND, new ChGettableBoolean(() -> counter >= actions.length, true), check);
            else this.check = new ChMulti(LogicOperators.OR, new ChGettableBoolean(() -> counter >= actions.length, true), check);
            this.actions = actions;
        }


        protected boolean isDone() {
            return check.isFinished();
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

        /**
         * Calls the onFinish Command of each Action
         */
        protected void onFinish() {
            for (Action action: actions) action.onFinish();
        }

    }

    public static class Parallel extends Action{
        Check check = null;
        int counter = 0;
        Action[] actions;

        /**
         * Constructor to run multiple actions consecutively
         * Will only finish after all actions in the sequence are complete.
         * @param actions The sequence of actions to be completed.
         */
        public Parallel(Action... actions) {
            super(new ChFalse());
            this.check = new ChGettableBoolean(() -> counter >= actions.length, true);
            this.actions = actions;
        }

        /**
         * Constructor to run multiple actions consecutively
         * @param mustFinishSequence Determines if the action will conclude if either the inputted check is completed or the sequence is complete, or if both the check and the sequence need to complete.
         * @param check The check to determine
         * @param actions The sequence of actions to be completed.
         */
        public Parallel (Check check, boolean mustFinishSequence, Action... actions) {
            super((new ChFalse()));
            if (mustFinishSequence) this.check = new ChMulti(LogicOperators.AND, new ChGettableBoolean(() -> counter >= actions.length, true), check);
            else this.check = new ChMulti(LogicOperators.OR, new ChGettableBoolean(() -> counter >= actions.length, true), check);
            this.actions = actions;
        }

        /**
         * Used by individual actions to set other conditions for the action to be complete.
         * @return Whether the action should stop running.
         */
        protected boolean isDone() {
            return check.isFinished();
        }


        /**
         * This runs each action in the parrallel sequence once.
         */
        @Override
        protected void onStart() {
            for (Action action: actions) if(action.actionLoop(action)) counter++;
        }

        /**
         * Calls the action loop for each action that hasn't yet concluded.
         */
        protected void onRun() {
            for(Action action: actions) {
                if (action.isFinished()) continue;
                if (action.actionLoop(action)) counter++;
            }
        }

        /**
         * Calls the onFinish Command of each Action
         */
        protected void onFinish() {
            for (Action action: actions) action.onFinish();
        }


    }
}

