package spiderling.lib.checks;

/**
 * A check which always returns false.
 *
 */
public class ChFalse extends Check {
    public boolean isDone() {
        return false;
    }
}