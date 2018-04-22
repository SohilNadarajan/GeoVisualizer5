import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

public class WolframAlphaAPIHandler
{

    private static String appid = "HURWW4-8W2GT5H3GR";

    public static LatLongLocation getResults(String args)
    {

        String input = args + "coordinates";
        LatLongLocation loc = new LatLongLocation();

        WAEngine engine = new WAEngine();

        engine.setAppID(appid);
        engine.addFormat("plaintext");

        WAQuery query = engine.createQuery();

        query.setInput(input);

        try
        {
            WAQueryResult queryResult = engine.performQuery(query);

            if (queryResult.isError())
            {
                System.out.println("Query error");
                System.out.println("  error code: " + queryResult.getErrorCode());
                System.out.println("  error message: " + queryResult.getErrorMessage());
            }
            else if (!queryResult.isSuccess())
            {
                System.out.println("Query was not understood; no results available.");
            }
            else
            {

                for (WAPod pod : queryResult.getPods())
                {
                    if (!pod.isError())
                    {
                        if (pod.getTitle().equals("Result"))
                        {
                            for (WASubpod subpod : pod.getSubpods())
                            {
                                for (Object element : subpod.getContents())
                                {
                                    if (element instanceof WAPlainText)
                                    {
                                        String[] latlong = (((WAPlainText) element).getText()).split(",");
                                        loc.setLatitude(LatLongLocation.dmsToDecimal(latlong[0]));
                                        loc.setLongitude(LatLongLocation.dmsToDecimal(latlong[1]));
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        catch (WAException e)
        {
            e.printStackTrace();
        }

        return loc;
    }
}
