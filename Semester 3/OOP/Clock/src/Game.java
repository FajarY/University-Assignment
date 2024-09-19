import java.awt.*;

public class Game {
    public ClockDisplay clockDisplay;
    public Canvas canvas;

    public Game()
    {
        clockDisplay = new ClockDisplay();

        canvas = new Canvas();
        canvas.setVisible(true);
        canvas.setFont(new Font("Dialog", Font.PLAIN, 96));
    }
    public void update()
    {
        clockDisplay.update();

        System.out.println(clockDisplay.getTimeString());
    }
}
