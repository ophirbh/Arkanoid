package management;

public class TaskQuit implements Task<Void> {

    @Override
    public Void run() {
        System.exit(0);
        return null;
    }
}
