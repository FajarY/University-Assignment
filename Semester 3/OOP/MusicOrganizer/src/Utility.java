import java.util.Random;

public class Utility {
    private static Random randomSingleton;

    public static void Initialize()
    {
        randomSingleton = new Random();
    }
    public static float getRandomFloat(float min, float max)
    {
        return min + (randomSingleton.nextFloat() * (max - min));
    }
    public static long getRandomLong(long min, long max)
    {
        return randomSingleton.nextLong(min, max);
    }
}
