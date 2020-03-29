package management;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;


/**
 * Represents a menu animation.
 * @param <T> Type.
 */
public class MenuAnimation<T> implements Menu, Animation {
    private ArrayList<SelectionInfo<T>> selections;
    private KeyboardSensor sensor;
    private boolean shouldStop;
    private T selected;

    /**
     * Represents a menu animation.
     * @param sensor Sensor.
     */
    public MenuAnimation(KeyboardSensor sensor) {
        this.sensor = sensor;
        this.selections = new ArrayList<SelectionInfo<T>>();
        this.shouldStop = false;
        this.selected = null;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLUE);
        for (int i = 0; i < this.selections.size(); i++) {
            d.drawText(50, 50 + (50) * i, this.selections.get(i).getKey()
                    + "   " + this.selections.get(i).getTitle(), 30);
        }
        for (int i = 0; i < selections.size(); i++) {
            if (this.sensor.isPressed(this.selections.get(i).getKey())) {
                this.shouldStop = true;

                this.selected = this.selections.get(i).getOption();
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return shouldStop;
    }

    /**
     * Reset stop.
     */
    public void resetStop() {
        this.shouldStop = false;
        this.selected = null; //TODO
    }
    @Override
    public void addSelection(String key, String message, Object returnVal) {
        this.selections.add(new SelectionInfo<T>(key, message, (T) returnVal));
    }

    @Override
    public Object getStatus() {
        return this.selected;
    }

    @Override
    public void addSubMenu(String key, String message, Menu subMenu) {
        this.selections.add(new SelectionInfo<T>(key, message, (T) subMenu.getStatus()));
    }
}
