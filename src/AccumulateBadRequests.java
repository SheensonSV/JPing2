import java.util.ArrayList;
import java.util.List;

public class AccumulateBadRequests
{
    private static List<String> accumulatedData = new ArrayList<>();

    public static void addData(String badRequest)
    {
        accumulatedData.add(badRequest);
    }

    public static List<String> getData()
    {
        return accumulatedData;
    }
}
