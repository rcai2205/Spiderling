package spiderling.lib.checks;

/**
 * A check which always returns true.
 *
 */
public class ChTrue extends Check
{
    public boolean isDone() {
        return true;
    }
}