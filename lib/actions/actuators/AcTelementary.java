package spiderling.lib.actions.actuators;

import spiderling.lib.actions.Action;
import spiderling.lib.checks.ChFalse;
import spiderling.lib.checks.ChTrue;

/**
 * An action that exists to print debugging messages. Ends instantly and prints the string it was given.
 *FIXME Change to Telementary
 * @author Sean Zammit
 */
@Deprecated
public class AcTelementary extends Action
{
    /** The string that the action will print when it ends */
    String stringToPrint;

    /**
     * Constructor creating an action to print a specified string.
     *
     * @param string The string to print.
     */
    public AcTelementary(String string) {
        super(new ChFalse());
        stringToPrint = string;
    }

    protected void onFinish() {
        System.out.println(stringToPrint);
    }
}