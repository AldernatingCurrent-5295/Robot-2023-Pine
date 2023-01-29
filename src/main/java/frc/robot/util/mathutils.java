package frc.robot.util;

public class mathutils {
    public static double Clamp(double input, double max, double min) {
        return input > max ? max:(input < min ? min:input);
    }
}
