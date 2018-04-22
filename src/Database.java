import java.util.ArrayList;
import java.util.HashMap;

public class Database
{
    public static ArrayList<Location> GVlocations;
    public static HashMap<String, String> stateByRace;
    public static HashMap<String, Double> stateByPoverty;
    public static HashMap<Location, String> stateBySuicide;

    
    public static void main(String[] args)
    {
     
        ArrayList<String> locationData = FileIO.readFile("datasets//GunViolence.csv");
        ArrayList<String> stateByRaceData = FileIO.readFile("datasets//Race.csv");
        ArrayList<String> stateByPovertyData = FileIO.readFile("datasets//Poverty.csv");
        ArrayList<String> stateBySuicideData = FileIO.readFile("datasets//Suicide.csv");

        
        Parser translator = new Parser();
        GVlocations = new ArrayList<Location>();
        stateByPoverty = new HashMap<String, Double>();
        stateByRace= new HashMap<String, String>();
        stateBySuicide = new HashMap<Location, String>();


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
        
        for (int i = 1; i < stateByPovertyData.size(); i++)
        {
            ArrayList<String> data = translator.getLinePieces(stateByPovertyData.get(i));
            stateByPoverty.put(data.get(0).replace(".", ""), Double.parseDouble(data.get(1)));
        }
        
//        for (int i = 1; i < stateBySuicideData.size(); i++)
//        {
//            Location loc = translator.
//            stateByPoverty.put(data.get(0).replace(".", ""), Double.parseDouble(data.get(1)));
//        }
     
        System.out.println(stateByPoverty);
        
        
        
    }
}
