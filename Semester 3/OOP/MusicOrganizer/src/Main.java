import java.sql.Time;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final long FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private static long lastTime = -1;
    private static long deltaTime = 0;

    private static Runnable task;
    private static MusicOrganizer musicOrganizer;
    public static void main(String[] args) {
        Utility.Initialize();

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        musicOrganizer = new MusicOrganizer();
        initialize();

        task = new Runnable() {
            @Override
            public void run() {
                long newTime = System.currentTimeMillis();

                if(lastTime == -1)
                {
                    lastTime = newTime;
                }
                deltaTime = newTime - lastTime;
                lastTime = newTime;

                try
                {
                    update();
                    musicOrganizer.update();
                }
                catch (InterruptedException interrupt)
                {
                    System.out.print("Exitting gracefully!");
                    service.shutdown();
                }
            }
        };

        service.scheduleAtFixedRate(task, 0, FRAME_TIME, TimeUnit.MILLISECONDS);
    }
    public static void initialize()
    {
        musicOrganizer.addTrack("Rivers Flow In You", "./musics/riversflowinyou.mp3");
        musicOrganizer.addTrack("Invincible", "./musics/invincible.mp3");
        musicOrganizer.addTrack("Alan Walker - Faded", "./musics/faded.mp3");
    }
    public static void update() throws InterruptedException
    {
        if(musicOrganizer.isCurrentlyPlaying())
        {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please specify the action");
        System.out.println("1. Add Track");
        System.out.println("2. Play Track");
        System.out.println("3. Play Random Track");
        System.out.println("4. List all tracks");
        System.out.println("5. Exit");

        System.out.print("Insert command : ");
        String type = scanner.nextLine();

        if(type.equals("1"))
        {
            System.out.print("Insert track title : ");
            String title = scanner.nextLine();
            System.out.print("Insert track filepath : ");
            String filePath = scanner.nextLine();

            musicOrganizer.addTrack(title, filePath);
            System.out.println("Succesfully added track!\n");
        }
        else if(type.equals("2"))
        {
            System.out.print("Insert track index : ");
            int index = scanner.nextInt();
            musicOrganizer.playTrack(index);

            lastTime = System.currentTimeMillis();
        }
        else if (type.equals("3"))
        {
            musicOrganizer.playRandomTrack();

            lastTime = System.currentTimeMillis();
        }
        else if(type.equals("4"))
        {
            musicOrganizer.listAllTracks();
        }
        else if(type.equals("5"))
        {
            throw new InterruptedException("Exit!");
        }
    }
    public static long getDeltaTimeMillis()
    {
        return deltaTime;
    }
}