import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final long FPS = 60;
    public static final long DELAY = 1000 / FPS;
    private static long lastTime = -1;
    private static long deltaTime;
    private static Game game;

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        game = new Game();

        Runnable task = new Runnable() {

            @Override
            public void run() {
                runTask();
            }
        };

        service.scheduleAtFixedRate(task, 0, DELAY, TimeUnit.MILLISECONDS);
    }
    private static void runTask()
    {
        long newTime = System.currentTimeMillis();
        if(lastTime == -1)
        {
            lastTime = newTime;
        }
        deltaTime = newTime - lastTime;
        lastTime = newTime;

        game.update();
    }
    public static long getDeltaMilliSecond()
    {
        return deltaTime;
    }
}