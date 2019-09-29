package adkl.smarthomevc;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

import adkl.smarthomevc.smarthome.AirConditioner;
import adkl.smarthomevc.smarthome.Windows;
import adkl.smarthomevc.tasks.OnOffTask;
import adkl.smarthomevc.tasks.SetTask;

public class TaskProcessor {
    public static Object processTask(DataSnapshot task) {
        HashMap taskData = (HashMap) task.getValue();
        try {
            String taskSpec = (String) taskData.get("taskSpec");
            if (taskSpec.equals("onOff")) {
                OnOffTask onOffTask = (OnOffTask) Utils.parseHashMapToObject((HashMap) taskData.get("payload"), OnOffTask.class);
                return processOnOffTask(onOffTask);
            }
            else if (taskSpec.equals("set")) {
                SetTask setTask = (SetTask) Utils.parseHashMapToObject((HashMap) taskData.get("payload"), SetTask.class);
                return processSetTask(setTask);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object processSetTask(SetTask task) {
        if (task.getWhatToSet().toLowerCase().equals("climate")) {
            AirConditioner.switchOn();
            AirConditioner.set(task.getValue());
        }
        return new Object() {
            public boolean success = true;
        };
    }

    private static Object processOnOffTask(OnOffTask task) {
        if (task.getWhatToToggle().equals("windows")) {
            if (task.getToggle()) {
                Windows.open();
            }
            else {
                Windows.close();
            }
        }
        else if (task.getWhatToToggle().equals("climate")) {
            if (task.getToggle()) {
                AirConditioner.switchOn();
            }
            else {
                AirConditioner.switchOff();
            }
        }
        return new Object() {
            public boolean success = true;
        };
    }
}
