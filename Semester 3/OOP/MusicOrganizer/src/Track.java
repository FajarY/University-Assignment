import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Track {
    private String title;
    private String filePath;

    private long durationMillisecond;

    public Track(String title, String filePath)
    {
        this.title = title;
        this.filePath = filePath;

        readFile();
    }

    public String getTitle()
    {
        return title;
    }
    public String getFilePath()
    {
        return filePath;
    }
    public long getDurationMillisecond()
    {
        return durationMillisecond;
    }
    private void readFile()
    {
        //Reads the music file
        //Since this is just a test, we make the data random
        durationMillisecond = Utility.getRandomLong(TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(15));
    }
    public String getTrackMetadata()
    {
        float duration = (float)durationMillisecond / 1000f;
        return "Title : " + title + ", File : " + filePath + ", Duration : " + duration;
    }
}
