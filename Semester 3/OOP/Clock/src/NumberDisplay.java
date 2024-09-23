public class NumberDisplay {

    private int value;
    private int limit;
    public NumberDisplay(int initialValue, int limit)
    {
        if(initialValue < 0)
        {
            initialValue = 0;
        }
        if(limit < 0)
        {
            limit = 0;
        }

        this.value = initialValue;
        this.limit = limit;
    }
    public int increment(int value)
    {
        if(value < 0)
        {
            return 0;
        }

        int newVal = this.value + value;
        int residu = newVal / limit;

        this.value = newVal % limit;

        return residu;
    }
    public void setValue(int value)
    {
        if(value < 0 || value > limit)
        {
            return;
        }

        this.value = value;
    }
    public int getValue()
    {
        return this.value;
    }
    public int getLimit()
    {
        return this.limit;
    }
}
