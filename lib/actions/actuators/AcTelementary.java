package spiderling.lib.actions.actuators;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import spiderling.core.AutonomousBase;
import spiderling.core.HardwareMapBase;
import spiderling.lib.actions.Action;
import spiderling.lib.checks.ChFalse;
import spiderling.lib.checks.ChTrue;

/**
 * An action that exists to print debugging messages. Ends instantly and prints the string it was given.
 * @author Ben Schwarz
 */
public class AcTelementary extends Action
{
    /** The string that the action will print when it ends */
    String stringToPrint;

    /**The telementry which it will output to*/
    AutonomousBase autonomousFile;

    /**
     * Constructor creating an action to print a specified string.
     *
     * @param string The string to print.
     */
    public AcTelementary(String string, AutonomousBase autonomousFile) {
        super(new ChTrue());
        stringToPrint = string;
        this.autonomousFile = autonomousFile;
    }

    protected void onFinish() {
        autonomousFile.telemetry.addLine(stringToPrint);
        autonomousFile.telemetry.update();
    }
}