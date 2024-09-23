import java.sql.Time;
import java.sql.Timestamp;
import java.util.Timer;

public class ClockDisplay {

    private NumberDisplay hours;
    private NumberDisplay minutes;
    private NumberDisplay second;

    private long deltaTime;

    public ClockDisplay()
    {
        this.hours = new NumberDisplay(0, 24);
        this.minutes = new NumberDisplay(0, 60);
        this.second = new NumberDisplay(0, 60);
    }
    public void incrementSecond(int secondAmount)
    {
        if(secondAmount < 0)
        {
            return;
        }

        int minutesAdd = this.second.increment(secondAmount);
        int hoursAdd = this.minutes.increment(minutesAdd);
        this.hours.increment(hoursAdd);
    }
    public void update()
    {
        deltaTime += Main.getDeltaMilliSecond();

        long addSecond = deltaTime / 1000;
        deltaTime = deltaTime % 1000;

        this.incrementSecond((int)addSecond);
    }
    public int getSecond()
    {
        return second.getValue();
    }
    public int getMinutes()
    {
        return minutes.getValue();
    }
    public int getHours()
    {
        return hours.getValue();
    }
    public void setTime(int hours, int minute, int second)
    {
        this.hours.setValue(hours);
        this.minutes.setValue(minute);
        this.second.setValue(second);
    }
    public String getTimeString()
    {
        return getHours() + ":" + getMinutes() + ":" + getSecond();
    }
}


