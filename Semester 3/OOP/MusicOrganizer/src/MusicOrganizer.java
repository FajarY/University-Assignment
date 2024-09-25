import java.util.ArrayList;
import java.util.Iterator;

public class MusicOrganizer {
    private ArrayList<Track> tracks;
    private MusicPlayer musicPlayer;

    public MusicOrganizer()
    {
        tracks = new ArrayList<>();
        musicPlayer = new MusicPlayer();
    }
    public void addTrack(String title, String filePath)
    {
        Track track = new Track(title, filePath);
        tracks.add(track);
    }
    public void playTrack(int index)
    {
        if(index < 0 || index > tracks.size())
        {
            System.out.println("Index is out of bounds!");
            return;
        }

        musicPlayer.playTrack(tracks.get(index));
    }
    public void playRandomTrack()
    {
        playTrack((int)Utility.getRandomLong(0, tracks.size()));
    }
    public boolean isCurrentlyPlaying()
    {
        return musicPlayer.isCurrentlyPlaying();
    }
    public void update()
    {
        musicPlayer.update();
    }
    public void listAllTracks()
    {
        Iterator<Track> iterator = tracks.iterator();

        System.out.println("Listing tracks");
        int index = 0;
        while (iterator.hasNext())
        {
            System.out.println(index + ". " + iterator.next().getTrackMetadata());
            index++;
        }
        System.out.println("");
    }
}
