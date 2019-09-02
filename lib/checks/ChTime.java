package spiderling.lib.checks;

public class ChTime extends Check {
    long time;

    /**
     * Constructor for a check that will return true after a specified period of time.
     *
     * @param timeout The number of seconds until the condition should be fulfilled.
     */
    public ChTime(double timeout) {
        super();
        time = (long) timeout*1000;
    }

    public boolean isDone() {
        return timeSinceInitialized() >= time;
    }
}
