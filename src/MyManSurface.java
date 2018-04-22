import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class MyManSurface extends PApplet
{

    UnfoldingMap map;
    ArrayList<Marker> GVmarkers;
    List<Marker> statesMarkers;
    Database db;
    private boolean loadDB = false, toggleRaceData = false;
    private Button gunviolenceButton, raceButton;

    public void settings()
    {

    }

    public void setup()
    {

        int width = 1200, height = 750;
        size(width, height);
        map = new UnfoldingMap(this, new Microsoft.RoadProvider());
        
        Location theLoc = new Location(30.576576, -76.85618);
        map.zoomAndPanTo(4, theLoc);
        map.setPanningRestriction(theLoc, 0f);
        map.setZoomRange(4f, 25f);
        // map.setTweening(true);
        
        MapUtils.createDefaultEventDispatcher(this, map);
        List<Feature> states = GeoJSONReader.loadData(this, "us-states.json");
        statesMarkers = MapUtils.createSimpleMarkers(states);
        
        for (Marker m : statesMarkers)
            m.setColor(color(255, 255, 255, 0));
            
        
        
        map.addMarkers(statesMarkers);
        
        loadDB();
        
        int buttonHeight = 35;
        gunviolenceButton = new Button("Gun Violence", Button.RECTANGLE, 1000, 20, 150, buttonHeight);
        raceButton = new Button("Racial Majorities", Button.RECTANGLE, 1000, gunviolenceButton.getY() + buttonHeight + 5, 150, buttonHeight);

    }
    
    public void displayRaceData(boolean toggle)
    {
        Collection<String> raceVal = db.stateByRace.values();
        ArrayList<String> race = new ArrayList<String>();
        int i = 0;
        
        for (String value : raceVal)
        {
            race.add(value);
        }
        
        System.out.println(race);
        
        if (toggle) {
        		for (Marker m : statesMarkers)
        		{
        			if (i > 50)
        				break;
        			if (race.get(i).equals("White")) {
        				m.setColor(color(0, 255, 0, 100));
        			} else if (race.get(i).equals("Asian")) {
        				m.setColor(color(0, 250, 154, 100));
        			} else if (race.get(i).equals("Hispanic"))	{
        				m.setColor(color(85, 107, 47, 100));
        			} else if (race.get(i).equals("Black")) {
        				m.setColor(color(0, 100, 0, 100));
        			}


        			i++;
        		}
        } else {
        		for (Marker m : statesMarkers)
        		{
        			m.setColor(color(255, 255, 255, 0));
        			
        		}
        }
        
//        loadDB = false;
    }

    public void draw()
    {

        Location theLoc = new Location(30.576576, -76.85618);
        map.draw();
        
        int zoomLevel = map.getZoomLevel();

        if (zoomLevel == 4)
            map.setPanningRestriction(theLoc, 0f);
        else
            map.setPanningRestriction(theLoc, (float) (1200 * (Math.pow(2, (zoomLevel - 4)))));
       

        Location mouseCoordinates = map.getLocation(mouseX, mouseY);
        fill(100);
        text(mouseCoordinates.getLat() + ", " + mouseCoordinates.getLon(), mouseX, mouseY);
        
        if (loadDB)
        {
            displayRaceData(toggleRaceData);
        }
        
        gunviolenceButton.draw(this);
        raceButton.draw(this);
    }
    
    public void loadDB()
    {
        new Thread()
        {
            

            public void run()
            {
                db = new Database();
                GVmarkers = new ArrayList<Marker>();
                
                for (LatLongLocation i : Database.stateByGV)
                {
                    Location loc = new Location(i.getLatitude(), i.getLongitude());
                    Feature GVmarkersEq = new PointFeature(loc);
                    GVmarkersEq.addProperty("City: ", i.getCityName());
                    Marker m = new SimplePointMarker(loc, GVmarkersEq.getProperties());
                    m.setColor(color(255, 0, 0));
                    m.setSelected(true);
                    GVmarkers.add(m);
                    map.addMarker(m);
                }
                
                loadDB = true;
            }
        }.start();
        
    }
    
    public void toggleRaceData() {
    		toggleRaceData = !toggleRaceData;
    }
    
    public void mousePressed() {
        if (gunviolenceButton.isPointInside(mouseX, mouseY)) {
        //displayGunViolenceData();
        }
        if (raceButton.isPointInside(mouseX, mouseY)) {
//            displayRaceData();
        		toggleRaceData();
        }
    }
    
    public static void main(String[] args)
    {

        String[] processingArgs = { "MyManSurface" };
        MyManSurface mySketch = new MyManSurface();
        PApplet.runSketch(processingArgs, mySketch);

    }

}