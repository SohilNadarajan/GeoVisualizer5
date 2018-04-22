import java.util.ArrayList;
import java.util.HashMap;

public class Database
{
    public static ArrayList<Location> GVlocations;
    public static HashMap<String, String> stateByRace;
    
    public static void main(String[] args)
    {
     
        ArrayList<String> locationData = FileIO.readFile("datasets//GunViolence.csv");
        ArrayList<String> stateByRaceData = FileIO.readFile("datasets//Race.csv");
        
        Parser translator = new Parser();
        GVlocations = new ArrayList<Location>();
        stateByRace= new HashMap<String, String>();

        for (int i = 1; i < locationData.size(); i++)
        {
            Location m = translator.parseGVLocations(locationData.get(i));
            GVlocations.add(m);
            System.out.println(m);
        }
        
        for (int i = 2; i < stateByRaceData.size(); i++)
        {
            String[] result = translator.parseRaceData(stateByRaceData.get(i));
            stateByRace.put(result[0], result[1]);
            
        }
        
        
        
        
        
        
    }
}
