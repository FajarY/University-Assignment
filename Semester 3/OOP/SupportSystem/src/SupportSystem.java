import java.util.ArrayList;

public class SupportSystem {
    private Responder responder;
    private InputReader inputReader;

    public SupportSystem()
    {
        responder = new Responder();
        inputReader = new InputReader();
    }
    public void start()
    {
        System.out.println("Welcome to Google AdMob Support, please specify your problem");
        while (true)
        {
            System.out.print("> ");
            inputReader.read();

            if(inputReader.containsIgnoreCasing("exit"))
            {
                System.out.println("Exiting...");
                return;
            }

            String respond = responder.getResponse(inputReader);
            System.out.println(respond);
        }
    }
}
