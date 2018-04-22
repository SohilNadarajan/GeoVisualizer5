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
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import maxim.shapes.Rectangle;
import processing.core.PApplet;

public class MyManSurface extends PApplet
{

    UnfoldingMap map;
    List<Marker> GVmarkers;
    List<Marker> SMarkers;
    List<Marker> statesMarkers;
    List<Marker> povertyMarkers;
    Database db;
    private boolean loadDB = false, toggleRaceData = false;
    private Button gunviolenceButton, raceButton, SButton, PovButton, USViewButton, alaskaViewButton, hawaiiViewButton;
    private boolean toggleGVData = false, toggleSData = false, togglePovData = false;
    private boolean added = false;
    private Location theLoc;

    public void settings()
    {

    }

    public void setup()
    {

        int width = 1200, height = 750;
        size(width, height);
        map = new UnfoldingMap(this, new Microsoft.RoadProvider());

        theLoc = new Location(30.576576, -76.85618);
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
        gunviolenceButton.setColor(new Color(255, 0, 0));
        raceButton = new Button("Racial Majorities", Button.RECTANGLE, 1000,
                gunviolenceButton.getY() + buttonHeight + 5, 150, buttonHeight);
        raceButton.setColor(new Color(34, 139, 34));
        SButton = new Button("Suicides", Button.RECTANGLE, 1000, raceButton.getY() + buttonHeight + 5, 150,
                buttonHeight);
        SButton.setColor(new Color(67, 211, 227));
        PovButton = new Button("Poverty Percentage", Button.RECTANGLE, 1000, SButton.getY() + buttonHeight + 5, 150,
                buttonHeight);
        PovButton.setColor(new Color(255, 150, 0));

        int buttonWidth = 25;
        USViewButton = new Button("US", Button.CIRCLE, 20, 15, buttonWidth, buttonWidth);
        USViewButton.setColor(new Color(255, 69, 0));
        alaskaViewButton = new Button("AL", Button.CIRCLE, USViewButton.getX() + buttonWidth + 10, 15, buttonWidth,
                buttonWidth);
        alaskaViewButton.setColor(new Color(255, 69, 0));
        hawaiiViewButton = new Button("HI", Button.CIRCLE, alaskaViewButton.getX() + buttonWidth + 10, 15, buttonWidth,
                buttonWidth);
        hawaiiViewButton.setColor(new Color(255, 69, 0));
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

        if (toggle)
        {
            for (Marker m : statesMarkers)
            {
                if (i > 50)
                    break;
                if (race.get(i).equals("White"))
                {
                    m.setColor(color(0, 255, 0, 100));
                }
                else if (race.get(i).equals("Asian"))
                {
                    m.setColor(color(0, 250, 154, 100));
                }
                else if (race.get(i).equals("Hispanic"))
                {
                    m.setColor(color(85, 107, 47, 100));
                }
                else if (race.get(i).equals("Black"))
                {
                    m.setColor(color(0, 100, 0, 100));
                }

                i++;
            }
        }
        else
        {
            for (Marker m : statesMarkers)
            {
                m.setColor(color(255, 255, 255, 0));

            }
        }

        // loadDB = false;
    }

    public void displayGVData(boolean toggle)
    {

        if (toggle)
        {

            for (Marker m : GVmarkers)
            {
                m.setSelected(true);
                m.setHidden(false);

            }
        }
        else
        {
            for (Marker m : GVmarkers)
            {
                m.setHidden(true);
                m.setSelected(false);

            }
        }

    }

    public void displaySData(boolean toggle, PApplet marker)
    {
        // marker.pushStyle();

        if (toggle)
        {

            for (Marker m : SMarkers)
            {
                m.setSelected(true);
                m.setHidden(false);

                ScreenPosition screenPos = map.getScreenPosition(m.getLocation());
                // ((SimplePointMarker) m).getScreenPosition(map);
                // marker.strokeWeight(16);
                // marker.stroke(67, 211, 227, 100);
                double radius = (Integer) (m.getProperties().get("Num Deaths: ")) / 2.;
                marker.fill(67, 211, 227, 100);
                marker.ellipse(screenPos.x, screenPos.y, (float) radius, (float) radius);

            }
        }
        else
        {
            for (Marker m : SMarkers)
            {
                m.setHidden(true);
                m.setSelected(false);

            }
        }

        // marker.popStyle();
    }

    private void displayPovData(boolean toggle)
    {
        Collection<Double> povVal = db.stateByPoverty.values();
        ArrayList<Double> pov = new ArrayList<Double>();
        int i = 0;

        for (Double value : povVal)
        {
            pov.add(value);
        }

        if (toggle)
        {
            for (Marker m : statesMarkers)
            {
                if (i > 50)
                    break;
                
                double percent = pov.get(i);
                System.out.println(percent);
                m.setColor(color(255, (int)(percent * 11), 0, 100));

                i++;
            }
        }
        else
        {
            for (Marker m : statesMarkers)
            {
                m.setColor(color(255, 255, 255, 0));

            }
        }
        
    }

    public void draw()
    {

        map.draw();

        int zoomLevel = map.getZoomLevel();

        if (zoomLevel == 4)
            map.setPanningRestriction(theLoc, 0f);
        else
            map.setPanningRestriction(theLoc, (float) (1200 * (Math.pow(2, (zoomLevel - 4)))));

//        Location mouseCoordinates = map.getLocation(mouseX, mouseY);
//        fill(100);
//        text(mouseCoordinates.getLat() + ", " + mouseCoordinates.getLon(), mouseX, mouseY);

        if (loadDB)
        {
            
            if (!togglePovData)
                displayRaceData(toggleRaceData);

            displayGVData(toggleGVData);
            displaySData(toggleSData, this);
            
            if (!toggleRaceData)
                displayPovData(togglePovData);
            
            
            if (!added)
            {
                map.addMarkers(GVmarkers);
                //map.addMarkers(povertyMarkers);
                added = true;
            }
        }
        
        // for (Marker m : GVmarkers) {
        // if (m.getDistanceTo(map.getLocation(mouseX, mouseY)) < 10) {
        Marker m = map.getFirstHitMarker(mouseX, mouseY);
        if (m != null)
        {
            Rectangle infoBox = new Rectangle(200, 600, 400, 100);
            infoBox.setFillColor(new Color(245, 245, 220));
            infoBox.setStrokeColor(new Color(162, 82, 45));
            infoBox.draw(this);
            textSize(15);
            textAlign(LEFT, CENTER);
            fill(0);
            text(m.getProperties().toString(), 205, 625);
        }

        // }
        // }

        gunviolenceButton.draw(this);
        raceButton.draw(this);
        SButton.draw(this);
        PovButton.draw(this);
        USViewButton.draw(this);
        alaskaViewButton.draw(this);
        hawaiiViewButton.draw(this);
    }



    public void loadDB()
    {
        new Thread()
        {

            public void run()
            {
                db = new Database();
                GVmarkers = new ArrayList<Marker>();
                SMarkers = new ArrayList<Marker>();

                for (LatLongLocation i : Database.stateByGV)
                {
                    Location loc = new Location(i.getLatitude(), i.getLongitude());
                    Feature GVmarkersEq = new PointFeature(loc);
                    GVmarkersEq.addProperty("City: ", i.getCityName());
                    Marker m = new SimplePointMarker(loc, GVmarkersEq.getProperties());
                    m.setColor(color(255, 0, 0));
                    m.setSelected(false);
                    GVmarkers.add(m);

                }

                for (LatLongLocation i : Database.stateBySuicide.keySet())
                {
                    Location loc = new Location(i.getLatitude(), i.getLongitude());
                    Feature GVmarkersEq = new PointFeature(loc);
                    GVmarkersEq.addProperty("City: ", i.getCityName());
                    GVmarkersEq.addProperty("Num Deaths: ", Database.stateBySuicide.get(i));
                    SimplePointMarker m = new SimplePointMarker(loc, GVmarkersEq.getProperties());
                    m.setColor(color(0, 0, 255));
                    m.setSelected(false);
                    SMarkers.add(m);

                    
                }

                loadDB = true;
            }
        }.start();

    }

    public void mousePressed()
    {
        if (gunviolenceButton.isPointInside(mouseX, mouseY))
        {
            // displayGunViolenceData();
            toggleGVData();

        }
        if (raceButton.isPointInside(mouseX, mouseY))
        {
            // displayRaceData();
            toggleRaceData();
        }
        if (SButton.isPointInside(mouseX, mouseY))
        {
            // displayRaceData();'

            toggleSData();
        }
        if (PovButton.isPointInside(mouseX, mouseY))
        {
            // displayRaceData();'

            togglePovData();
        }
        if (USViewButton.isPointInside(mouseX, mouseY))
        {
            theLoc.set(30.576576f, -76.85618f);
            map.zoomAndPanTo(4, new Location(30.576576f, -76.85618f));
        }
        if (alaskaViewButton.isPointInside(mouseX, mouseY))
        {
            theLoc.set(60.22629f, -127.87768f);
            map.zoomAndPanTo(4, new Location(60.22629f, -127.87768f));
        }
        if (hawaiiViewButton.isPointInside(mouseX, mouseY))
        {
            theLoc.set(20.75624f, -157.38574f);
            map.zoomAndPanTo(8, new Location(20.75624f, -157.38574f));
        }
    }
    

    public void toggleRaceData()
    {
        if (!togglePovData)
            toggleRaceData = !toggleRaceData;
    }

    private void togglePovData()
    {
        if (!toggleRaceData)
            togglePovData = !togglePovData;

    }

    private void toggleSData()
    {
        toggleSData = !toggleSData;

    }

    private void toggleGVData()
    {
        toggleGVData = !toggleGVData;

    }

    public static void main(String[] args)
    {

        String[] processingArgs = { "MyManSurface" };
        MyManSurface mySketch = new MyManSurface();
        PApplet.runSketch(processingArgs, mySketch);

    }

}