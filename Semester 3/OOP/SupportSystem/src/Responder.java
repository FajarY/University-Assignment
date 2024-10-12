import java.util.*;

public class Responder {
    private HashMap<HashSet<String>, String> respondMapping;
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
        respondMapping.put(Utility.splitString("ads disappear, ad vanishing, ads not showing during play"), "If ads disappear while playing, it may be a problem in your code implementation. A common issue is when opening an ad, you start loading another ad, which makes the current one obsolete.");
        respondMapping.put(Utility.splitString("ad impression low, impression issues, no ad impressions"), "If impressions are low, make sure ads are being properly requested and check the fill rate in your AdMob dashboard.");
        respondMapping.put(Utility.splitString("ad click rate low, low click-through, ad clicks issues"), "If you're seeing low click-through rates, try adjusting ad placements or experimenting with different formats like interstitial or rewarded ads.");
        respondMapping.put(Utility.splitString("ad revenue drop, revenue fluctuation, low ad revenue"), "Revenue fluctuations are normal due to seasonal trends. If revenue drops significantly, review the performance of your ad units.");
        respondMapping.put(Utility.splitString("ad limit, ad serving limits, ad limit issues"), "Ad serving limits can happen if invalid traffic is detected. Make sure you're following AdMob's policies and reducing any suspicious traffic.");
        respondMapping.put(Utility.splitString("low ad fill rate, fill rate problem, no ad fill"), "Low fill rates usually mean a lack of available ads for your region or audience. Try enabling more ad networks or adjusting your mediation setup.");
        respondMapping.put(Utility.splitString("policy violation, ad policy issue, ad policy violation"), "Policy violations can result in ad serving being disabled. Check the 'Policy Center' in your AdMob account to resolve any issues.");
        respondMapping.put(Utility.splitString("ad mediation setup, mediation configuration, mediation issues"), "Mediation allows you to serve ads from multiple networks. Make sure your mediation setup is properly configured and up-to-date.");
        respondMapping.put(Utility.splitString("rewarded ads not working, reward ads issue, reward callback problem"), "If rewarded ads aren't working, check if the ad is properly initialized and the reward callback is correctly implemented in your code.");
        respondMapping.put(Utility.splitString("banner ad not showing, banner ad issue, banner sizing problem"), "Banner ads not showing could be due to improper sizing or placement. Verify that your ad unit is set up for the correct ad size.");
        respondMapping.put(Utility.splitString("ad load failure, ad not loading, ads failing to load"), "If ads fail to load, ensure that you have a stable network connection and that the ad unit IDs are correct. Also, check for any errors in the AdMob SDK.");
        respondMapping.put(Utility.splitString("slow ad loading, ads loading slowly, ad latency issues"), "Slow ad loading can result from network latency or server-side issues. Try reducing ad request intervals and ensure that caching is enabled where possible.");
        respondMapping.put(Utility.splitString("ad refresh issue, ads not refreshing, no ad refresh"), "If ads are not refreshing properly, verify your refresh rate settings and ensure that auto-refresh is enabled in your ad configuration.");
        respondMapping.put(Utility.splitString("ad request failure, ad request not sent, ads not requesting"), "Ad request failures may be caused by incorrect ad unit IDs or network issues. Check your request logs and ensure the correct parameters are being sent.");
        respondMapping.put(Utility.splitString("interstitial ad issue, interstitial not showing, interstitial ad error"), "If interstitial ads aren't showing, check if they are being requested and loaded correctly. Make sure you're displaying the ad in the appropriate activity or fragment.");
        respondMapping.put(Utility.splitString("reward not granted, reward ad callback missing, no reward after ad"), "If users aren't receiving rewards after watching ads, ensure that the reward callback is implemented properly and that the reward conditions are met.");
        respondMapping.put(Utility.splitString("ad network error, ad network issues, error with ad networks"), "Ad network errors can occur if there are configuration problems or if the network is temporarily down. Verify your network settings and retry the ad request.");
        respondMapping.put(Utility.splitString("ad unit invalid, incorrect ad unit ID, invalid ad unit ID"), "If you're getting an invalid ad unit ID error, double-check your ad unit setup in the AdMob dashboard and ensure the IDs match your implementation.");
        respondMapping.put(Utility.splitString("native ad error, native ads not displaying, native ad not showing"), "If native ads aren't displaying correctly, ensure that the ad format is properly configured and that all required assets are being loaded.");
        respondMapping.put(Utility.splitString("ad mediation error, mediation not working, mediation issue with ad networks"), "Ad mediation errors may result from misconfigured ad networks or outdated SDKs. Make sure all mediation partners are set up correctly and that you're using the latest versions.");
    }
    private void initializeDefaultResponse()
    {
        defaultResponse.add("This information is currently not registered right now, please ask in community support https://support.google.com/admob/community?hl=en");
        defaultResponse.add("Information currently unknown, refer to our mail if its persist");
    }
    public String getResponse(InputReader input)
    {
        int bestMatch = 0;
        String bestMatchString = defaultResponse.get(randomizer.nextInt(0, defaultResponse.size()));

        for (var item : respondMapping.entrySet())
        {
            HashSet<String> key = item.getKey();
            int matchCount = 0;
            for(int i = 0; i < input.getInputCount(); i++)
            {
                if(key.contains(input.getInputAt(i)))
                {
                    matchCount++;
                }
            }

            if(matchCount > bestMatch)
            {
                bestMatchString = item.getValue();
                bestMatch = matchCount;
            }
        }

        return bestMatchString;
    }
}
