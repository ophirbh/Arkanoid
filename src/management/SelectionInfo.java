package management;

/**
 * Represents selection information.
 * @param <T> Type.
 */
public class SelectionInfo<T> {
    private String key;
    private String title;
    private T option;

    /**
     * Constructor.
     * @param key Key.
     * @param title Title.
     * @param option Option.
     */
    public SelectionInfo(String key, String title, T option) {
        this.key = key;
        this.title = title;
        this.option = option;
    }

    /**
     * Get key.
     * @return Key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get title.
     * @return Title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get option.
     * @return Option.
     */
    public T getOption() {
        return this.option;
    }

}
