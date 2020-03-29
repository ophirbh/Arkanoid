package management;

/**
 * Represent a submenu.
 * @param <T> Type.
 */
public class SubMenu<T> implements Task<Void> {

    private Menu<Task<T>> subMenu;
    private AnimationRunner animationRunner;

    /**
     * Constructor.
     * @param subMenu Submenu.
     * @param runner Runner.
     */
    public SubMenu(MenuAnimation<Task<T>> subMenu, AnimationRunner runner) {
        this.subMenu = subMenu;
        this.animationRunner = runner;
    }

    /**
     * Get menu.
     * @return Menu.
     */
    public Menu<Task<T>> getMenu() {
        return this.subMenu;
    }

    /**
     * Run menu.
     * @return null.
     * @throws Exception Exception.
     */
    public Void run() throws Exception {
        this.animationRunner.run(this.subMenu);

        Task<T> menuTask = this.subMenu.getStatus();
        menuTask.run();
        this.subMenu.resetStop();
        return null;
    }
}
