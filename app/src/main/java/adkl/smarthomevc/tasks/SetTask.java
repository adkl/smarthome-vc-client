package adkl.smarthomevc.tasks;

import java.io.Serializable;

public class SetTask implements Serializable {
    private String whatToSet;
    private float value;

    public SetTask(String whatToSet, float value) {
        this.whatToSet = whatToSet;
        this.value = value;
    }

    public String getWhatToSet() {
        return whatToSet;
    }

    public void setWhatToSet(String whatToSet) {
        this.whatToSet = whatToSet;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
