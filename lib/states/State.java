package spiderling.lib.states;


import com.qualcomm.robotcore.util.ElapsedTime;

public interface State<T> {
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    default void constructState() {
        timer.reset();
    }

    default void deconstructState() {
    }

    default void run() {

    }

    default boolean isDone() {
        return false;
    }

    T nextState();
}

