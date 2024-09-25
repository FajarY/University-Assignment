import java.util.concurrent.TimeUnit;

public class MusicPlayer {
    private Track currentActiveTrack;
    private long currentTrackPointer;
    private long elapsedFromLast;

    public MusicPlayer()
    {

    }
    public void playTrack(Track track)
    {
        if(currentActiveTrack != null)
        {
            System.out.println("Cannot play another track while one is playing!");
            return;
        }

        currentActiveTrack = track;
        System.out.println("Playing " + track.getTrackMetadata());
        currentTrackPointer = 0;
        System.out.print('[');
    }
    public void update()
    {
        if(currentActiveTrack != null)
        {
            elapsedFromLast += Main.getDeltaTimeMillis();
            currentTrackPointer += Main.getDeltaTimeMillis();

            if(elapsedFromLast >= 1000)
            {
                elapsedFromLast = 0;
                System.out.print('*');
            }
            if(currentTrackPointer > currentActiveTrack.getDurationMillisecond())
            {
                currentActiveTrack = null;
                System.out.println("]");
                System.out.println("Music is done playing!\n");
            }
        }
    }
    public boolean isCurrentlyPlaying()
    {
        return currentActiveTrack != null;
    }
}
