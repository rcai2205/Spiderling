package spiderling.lib;
@Deprecated
public class PIDController {
        private double kP;// factor for "proportional" control
        private double kI;                     // factor for "integral" control
        private double kD;                     // factor for "derivative" control
        private double kF;                     //factor for "Feed-Forward"
        private double input;                 // sensor input for pid controller
        private double maximumOutput = 1.0;	// |maximum output|
        private double minimumOutput = -1.0;	// |minimum output|
        private double maximumInput = 0.0;	// maximum input - limit setpoint to this
        private double minimumInput = 0.0;	// minimum input - limit setpoint to this
        private boolean continuous = false;	// do the endpoints wrap around? eg. Absolute encoder
        private boolean enabled = false;      // is the pid controller enabled
        private double prevError = 0.0;       // the prior sensor input (used to compute velocity)
        private double totalError = 0.0;      // the sum of the errors for use in the integral calc
        private double tolerance = 0.05;      // the percentage error that is considered on target
        private double setpoint = 0.0;
        private double error = 0.0;
        private double result = 0.0;

        /**
         * Allocate a PID object with the given constants for kP, kI, kD
         * @param Kp the proportional coefficient
         * @param Ki the integral coefficient
         * @param Kd the derivative coefficient
         */
    public PIDController(double Kp, double Ki, double Kd)
        {
            kP = Kp;
            kI = Ki;
            kD = Kd;
            kF = 0;
        }
    /**
     * Allocate a PID object with the given constants for kP, kI, kD
     * @param Kp the proportional coefficient
     * @param Ki the integral coefficient
     * @param Kd the derivative coefficient
     * @param Kf the feedforward coefficient
     */
    public PIDController(double Kp, double Ki, double Kd, double Kf)
    {
        kP = Kp;
        kI = Ki;
        kD = Kd;
        kF = Kf;
    }

        /**
         * Read the input, calculate the output accordingly, and write to the output.
         * This should only be called by the PIDTask
         * and is created during initialization.
         */
        private void calculate()
        {
            int     sign = 1;

            // If enabled then proceed into controller calculations
            if (enabled)
            {
                // Calculate the error signal
                error = setpoint - input;

                // If continuous is set to true allow wrap around
                if (continuous)
                {
                    if (Math.abs(error) > (maximumInput - minimumInput) / 2)
                    {
                        if (error > 0)
                            error = error - maximumInput + minimumInput;
                        else
                            error = error + maximumInput - minimumInput;
                    }
                }

                // Integrate the errors as long as the upcoming integrator does
                // not exceed the minimum and maximum output thresholds.

                if ((Math.abs(totalError + error) * kI < maximumOutput) &&
                        (Math.abs(totalError + error) * kI > minimumOutput))
                    totalError += error;

                // Perform the primary PID calculation
                result = kP * error + kI * totalError + kD * (error - prevError) + kF;

                // Set the current error to the previous error for the next cycle.
                prevError = error;

                if (result < 0) sign = -1;    // Record sign of result.

                // Make sure the final result is within bounds. If we constrain the result, we make
                // sure the sign of the constrained result matches the original result sign.
                if (Math.abs(result) > maximumOutput)
                    result = maximumOutput * sign;
                else if (Math.abs(result) < minimumOutput)
                    result = minimumOutput * sign;
            }
        }

        /**
         * Set the PID Controller gain parameters.
         * Set the proportional, integral, and differential coefficients.
         * @param p Proportional coefficient
         * @param i Integral coefficient
         * @param d Differential coefficient
         */
        public void setPID(double p, double i, double d)
        {
            kP = p;
            kI = i;
            kD = d;
        }

        /**
         * Get the Proportional coefficient
         * @return proportional coefficient
         */
        public double getkP()
        {
            return kP;
        }

        /**
         * Get the Integral coefficient
         * @return integral coefficient
         */
        public double getkI()
        {
            return kI;
        }

        /**
         * Get the Differential coefficient
         * @return differential coefficient
         */
        public double getkD()
        {
            return kD;
        }

        /**
         * Return the current PID result for the last input set with setInput().
         * This is always centered on zero and constrained the the max and min outs
         * @return the latest calculated output
         */
        public double performPID()
        {
            calculate();
            return result;
        }

        /**
         * Return the current PID result for the specified input.
         * @param input The input value to be used to calculate the PID result.
         * This is always centered on zero and constrained the the max and min outs
         * @return the latest calculated output
         */
        public double performPID(double input)
        {
            setInput(input);
            return performPID();
        }

        /**
         *  Set the PID controller to consider the input to be continuous,
         *  Rather then using the max and min in as constraints, it considers them to
         *  be the same point and automatically calculates the shortest route to
         *  the setpoint.
         * @param continuous Set to true turns on continuous, false turns off continuous
         */
        public void setContinuous(boolean continuous)
        {
            this.continuous = continuous;
        }

        /**
         *  Set the PID controller to consider the input to be continuous,
         *  Rather then using the max and min in as constraints, it considers them to
         *  be the same point and automatically calculates the shortest route to
         *  the setpoint.
         */
        public void setContinuous()
        {
            this.setContinuous(true);
        }

        /**
         * Sets the maximum and minimum values expected from the input.
         *
         * @param minimumInput the minimum value expected from the input
         * @param maximumInput the maximum value expected from the output
         */
        public void setInputRange(double minimumInput, double maximumInput)
        {
            this.minimumInput = minimumInput;
            this.maximumInput = maximumInput;
            setSetpoint(setpoint);
        }

        /**
         * Sets the minimum and maximum values to write.
         *
         * @param minimumOutput the minimum value to write to the output
         * @param maximumOutput the maximum value to write to the output
         */
        public void setOutputRange(double minimumOutput, double maximumOutput)
        {
            this.minimumOutput = minimumOutput;
            this.maximumOutput = maximumOutput;
        }

        /**
         * Set the setpoint for the PIDController
         * @param setpoint the desired setpoint
         */
        public void setSetpoint(double setpoint)
        {
            int     sign = 1;

            if (maximumInput > minimumInput)
            {
                if (setpoint < 0) sign = -1;

                if (Math.abs(setpoint) > maximumInput)
                    this.setpoint = maximumInput * sign;
                else if (Math.abs(setpoint) < minimumInput)
                    this.setpoint = minimumInput * sign;
                else
                    this.setpoint = setpoint;
            }
            else
                this.setpoint = setpoint;
        }

        /**
         * Returns the current setpoint of the PIDController
         * @return the current setpoint
         */
        public double getSetpoint()
        {
            return setpoint;
        }

        /**
         * Retruns the current difference of the input from the setpoint
         * @return the current error
         */
        public synchronized double getError()
        {
            return error;
        }

        /**
         * Set the percentage error which is considered tolerable for use with
         * OnTarget. (Input of 15.0 = 15 percent)
         * @param percent error which is tolerable
         */
        public void setTolerance(double percent)
        {
            tolerance = percent;
        }

        /**
         * Return true if the error is within the percentage of the total input range,
         * determined by setTolerance. This assumes that the maximum and minimum input
         * were set using setInputRange.
         * @return true if the error is less than the tolerance
         */
        public boolean onTarget()
        {
            return (Math.abs(error) < Math.abs(tolerance / 100.0 * (maximumInput - minimumInput)));
        }

        /**
         * Begin running the PIDController
         */
        public void enable()
        {
            enabled = true;
        }

        /**
         * Stop running the PIDController.
         */
        public void disable()
        {
            enabled = false;
        }

        /**
         * Reset the previous error,, the integral term, and disable the controller.
         */
        public void reset()
        {
            disable();
            prevError = 0;
            totalError = 0;
            result = 0;
        }

        /**
         * Set the input value to be used by the next call to performPID().
         * @param input Input value to the PID calculation.
         */
        public void setInput(double input)
        {
            int     sign = 1;

            if (maximumInput > minimumInput)
            {
                if (input < 0) sign = -1;

                if (Math.abs(input) > maximumInput)
                    this.input = maximumInput * sign;
                else if (Math.abs(input) < minimumInput)
                    this.input = minimumInput * sign;
                else
                    this.input = input;
            }
            else
                this.input = input;
        }
}
