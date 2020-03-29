package management;

/**
 * Represents a menu.
 * @param <T> Type.
 */
public interface Menu<T> extends Animation {

    /**
     * Add selection.
     * @param key Key.
     * @param message Message.
     * @param returnVal Return value.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Get status.
     * @return Status.
     */
    T getStatus();

    /**
     * Add sub menu.
     * @param key Key.
     * @param message Message.
     * @param subMenu Return value.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * Reset.
     */
    void resetStop();
}
