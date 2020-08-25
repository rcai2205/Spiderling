package spiderling.lib.states;


import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface State<T, RobotT> {
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    default void constructState(RobotT robot) {
        timer.reset();
    }

    default void deconstructState(RobotT robot) {
    }

    default void run(RobotT robot) {
    }

    default boolean isDone(RobotT robot) {
        return false;
    }

    default void updateTelemetry(Telemetry telemetry) {
    }
}

