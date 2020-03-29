package management;

/**
 * Represents a task interface.
 * @param <T> Type.
 */
public interface Task<T> {
    /**
     * Run task.
     * @return Type.
     * @throws Exception Exception.
     */
    T run() throws Exception;
}
