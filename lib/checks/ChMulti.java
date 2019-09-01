package spiderling.lib.checks;

import spiderling.lib.logic.ListLogic;

public class ChMulti extends Check {
    /** The list of subchecks for the multi-check. */
    public Check[] checklist;

    ListLogic operator;

    /**
     * Constructor for a check with multiple subchecks as conditions.
     *
     * @param operator The {@link ListLogic ListLogic} operator that determines the use of the list of conditions.
     * @param checks The list of subchecks for the multi-check.
     */
    public ChMulti(ListLogic operator, Check... checks) {
        this.operator = operator;
        if(checks.length == 0) checklist = new Check[] {new ChTrue()};
        else checklist = checks;
        this.operator.populateWorkingList(checklist);
    }

    public void onStart() {
        for(Check check : checklist) check.initialise(action);
        operator.populateWorkingList(checklist);
    }

    public void onRun() {
        for(Check check : checklist) check.onRun();
    }

    public void onFinish() {
        for(Check check : checklist) check.onFinish();
    }

    protected boolean isDone() {
        return operator.get();
    }

}
