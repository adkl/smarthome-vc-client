package adkl.smarthomevc.smarthome;

public class AirConditioner {
    private static float currentSet = 20f;
    private static boolean isOn = false;

    public static boolean isOn() {
        return isOn;
    }

    public static void switchOn() {
        if (isOn) {
            // throw already on
        }
        else {
            isOn = true;
        }
    }

    public static void switchOff() {
        if (!isOn) {
            // throw already off
        }
        else {
            isOn = false;
        }
    }
}
