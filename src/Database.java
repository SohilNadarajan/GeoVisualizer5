import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Database
{
    public static ArrayList<LatLongLocation> stateByGV;
    public static HashMap<String, String> stateByRace;
    public static HashMap<String, Double> stateByPoverty;
    public static HashMap<LatLongLocation, Integer> stateBySuicide;

    
    public Database()
    {
     
        ArrayList<String> stateByGVData = FileIO.readFile("datasets//GunViolence.csv");
        ArrayList<String> stateByRaceData = FileIO.readFile("datasets//Race.csv");
        ArrayList<String> stateByPovertyData = FileIO.readFile("datasets//Poverty.csv");
        ArrayList<String> stateBySuicideData = FileIO.readFile("datasets//Suicide.csv");
        ArrayList<String> locations = FileIO.readFile("datasets//LatLong.csv");
        
        ArrayList<String> places = new ArrayList<String>();
        
        for (int i = 0; i < locations.size(); i++)
        {
            places.add(locations.get(0));
        }
                
        
        Parser translator = new Parser();
        stateByGV = new ArrayList<LatLongLocation>();
        stateByPoverty = new HashMap<String, Double>();
        stateByRace= new HashMap<String, String>();
        stateBySuicide = new HashMap<LatLongLocation, Integer>();


        for (int i = 1; i < stateByGVData.size(); i++)
        {
            LatLongLocation m = translator.parseGVLocations(stateByGVData.get(i));
            stateByGV.add(m);
            System.out.println(m);
        }
        
        for (int i = 2; i < stateByRaceData.size(); i++)
        {
            String[] result = translator.parseRaceData(stateByRaceData.get(i));
            stateByRace.put(result[0], result[1]);
            
        }
        
        for (int i = 1; i < stateByPovertyData.size(); i++)
        {
            ArrayList<String> data = translator.getLinePieces(stateByPovertyData.get(i));
            stateByPoverty.put(data.get(0).replace(".", ""), Double.parseDouble(data.get(1)));
        }
        
        for (int i = 1; i < stateBySuicideData.size(); i++)
        {
            String[] result = translator.parseSData(stateBySuicideData.get(i));
            String place = result[0];
            int numSuc = -1;
            
            
            if (result[1] != null)
            {
                int specialChar = result[1].indexOf("/");
                
                if (specialChar < 0)
                {
                    numSuc = Integer.parseInt(result[1]);

                }
            }
                
            LatLongLocation loc = new LatLongLocation();
            
            if (!place.equals("\"MessageTemplate\"\" :> Interpreter::semantictype, \"\"MessageParameters\"\" -> <|\"\"Type\"\" -> \"\"administrative division\r\n"))
            {
                int index = Collections.binarySearch(places, place);
                
                if (index >= 0)
                {
                    String[] data = locations.get(index).split(",");
                    
                    loc.setCityName(data[0]);
                    loc.setLatitude(Double.parseDouble(data[1]));
                    loc.setLongitude(Double.parseDouble(data[2]));
                    
                }
                
                stateBySuicide.put(loc, numSuc);
            }
                //stateByPoverty.put(data.get(0).replace(".", ""), Double.parseDouble(data.get(1)));
        }
     
        System.out.println(stateByRace.get("\"California\""));
        
        
        
    }
    
    
}
