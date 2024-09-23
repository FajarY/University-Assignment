import java.awt.*;
import java.sql.Time;
import java.time.LocalTime;

public class Game {
    public ClockDisplay clockDisplay;

    public Game()
    {
        clockDisplay = new ClockDisplay();

        LocalTime time = java.time.LocalTime.now();
        System.out.println(time.toString());
        clockDisplay.setTime(time.getHour(), time.getMinute(), time.getSecond());
    }
    public void update()
    {
        clockDisplay.update();

        System.out.println(clockDisplay.getTimeString());
    }
}
