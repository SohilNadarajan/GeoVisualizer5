import java.util.ArrayList;

public class Parser
{
    String[] RaceKey = {"White","Black","Hispanic","Asian",
            "American Indian/Alaska Native","Native Hawaiian/Other Pacific Islander",
            "Two Or More Races","Total"};
    
    public ArrayList<String> getLinePieces(String line)
    {
        ArrayList<String> pieces = new ArrayList<String>();
        boolean quoted = false;
        int start = 0;

        for (int i = 0; i < line.length(); i++)
        {
            char thisChar = line.charAt(i);
            if (thisChar == '"')
                quoted = !quoted;  
            else if (thisChar == ',' && !quoted)
            {
                pieces.add(line.substring(start, i));
                start = i + 1;
            }
        }

        pieces.add(line.substring(start));

        return pieces;
    }
    
    public Location parseGVLocations(String line)
    {
        ArrayList<String> data = getLinePieces(line);
        Location loc = new Location();
        
        String cityName = data.get(1);
        String lat = data.get(2);
        String lon = data.get(3);

        loc.setCityName(cityName);
        
        if (!lat.equals("") && !lon.equals(""))
        {
            loc.setLatitude(Double.parseDouble(lat));
            loc.setLongitude(Double.parseDouble(lon));
        }
        else
        {
            Location temp = WolframAlphaAPIHandler.getResults(data.get(1));
            loc.setLatitude(temp.getLatitude());
            loc.setLongitude(temp.getLongitude());
        }
        
        return loc;
    }

    public String[] parseRaceData(String line)
    {
        ArrayList<String> data = getLinePieces(line);
        String[] result = new String[2];
        
        result[0] = data.get(0);
        result[1] = maxValue(data);
        
        return result;
    }

    private String maxValue(ArrayList<String> data)
    {
        double max = -1;
        int index = 0;
        
        for (int i = 1; i < data.size() - 1; i++)
        {
            if (data.get(i).equals("\"N/A\"") || data.get(i).equals("\"<.01\""))
                continue;
            
            double k = Double.parseDouble(data.get(i).replaceAll("\"", ""));
            
            if (k > 0.5)
            {
                index = i - 1;
                max = k;
                break;
            } 
            
            if (max < k)
            {
                index = i - 1;
                max = k;
            }
            
            
        }
        
        System.out.println(max);

        return RaceKey[index];
       
    }

    public String[] parseSData(String line)
    {
        ArrayList<String> data = getLinePieces(line);
        String[] result = new String[2];
        
        String parse = data.get(1);
        int start = "\"Entity[\"\"AdministrativeDivision\"\", {\"\"".length();

        parse = parse.substring(start);
        int end = parse.indexOf("\"\",");

        parse = parse.substring(0, end);
        
        result[0] = parse;
        
        parse = data.get(2);
        
        start = "\"TemporalData[TimeSeries, {{{Quantity[".length();
        
        parse = parse.substring(start);
        end = parse.indexOf(", \"\"People\"\"");
        
       

        if (end != -1 && !parse.startsWith("\"NotAvailable\"\"]"))
        {
            parse = parse.substring(0, end);
            result[1] = parse;
        }
    
        System.out.println(result[1]);
        
        return result;
    }
    
    

    

}
