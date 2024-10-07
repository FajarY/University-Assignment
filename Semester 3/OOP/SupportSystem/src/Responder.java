import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Responder {
    private HashMap<String, String> respondMapping;
    private ArrayList<String> defaultResponse;
    private Random randomizer;

    public Responder()
    {
        respondMapping = new HashMap<>();
        defaultResponse = new ArrayList<>();
        randomizer = new Random();

        initializeResponse();
        initializeDefaultResponse();
    }
    private void initializeResponse()
    {
        respondMapping.put("disappear", "If ads dissapear while playing, it may be a problem on your code implementation. A common issue is when opening a ads, you start load another ad, which make the current one obsolete");
        respondMapping.put("impression", "If impressions are low, make sure ads are being properly requested and check the fill rate in your AdMob dashboard.");
        respondMapping.put("click", "If you're seeing low click-through rates, try adjusting ad placements or experimenting with different formats like interstitial or rewarded ads.");
        respondMapping.put("revenue", "Revenue fluctuations are normal due to seasonal trends. If revenue drops significantly, review the performance of your ad units.");
        respondMapping.put("limit", "Ad serving limits can happen if invalid traffic is detected. Make sure you're following AdMob's policies and reducing any suspicious traffic.");
        respondMapping.put("fill", "Low fill rates usually mean a lack of available ads for your region or audience. Try enabling more ad networks or adjusting your mediation setup.");
        respondMapping.put("policy", "Policy violations can result in ad serving being disabled. Check the 'Policy Center' in your AdMob account to resolve any issues.");
        respondMapping.put("mediation", "Mediation allows you to serve ads from multiple networks. Make sure your mediation setup is properly configured and up-to-date.");
        respondMapping.put("reward", "If rewarded ads aren't working, check if the ad is properly initialized and the reward callback is correctly implemented in your code.");
        respondMapping.put("banner", "Banner ads not showing could be due to improper sizing or placement. Verify that your ad unit is set up for the correct ad size.");
    }
    private void initializeDefaultResponse()
    {
        defaultResponse.add("This information is currently not registered right now, please ask in community support https://support.google.com/admob/community?hl=en");
        defaultResponse.add("Information currently unknown, refer to our mail if its persist");
    }
    public String getResponse(InputReader input)
    {
        for (String item : respondMapping.keySet()) {
            if(input.containsIgnoreCasing(item))
            {
                return respondMapping.get(item);
            }
        }

        return defaultResponse.get(randomizer.nextInt(0, defaultResponse.size()));
    }
}
