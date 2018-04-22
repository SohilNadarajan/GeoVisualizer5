import java.awt.Color;
import java.util.ArrayList;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
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
    Database db;

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
        
        loadDB();
        

    }

    public void draw()
    {

        Location theLoc = new Location(30.576576, -76.85618);
        map.draw();
        
        int zoomLevel = map.getZoomLevel();

//        System.out.println(map.getWidth() + "   level: " + zoomLevel);
        if (zoomLevel == 4)
            map.setPanningRestriction(theLoc, 0f);
        else
            map.setPanningRestriction(theLoc, (float) (1200 * (Math.pow(2, (zoomLevel - 4)))));
       

        Location mouseCoordinates = map.getLocation(mouseX, mouseY);
        fill(100);
        text(mouseCoordinates.getLat() + ", " + mouseCoordinates.getLon(), mouseX, mouseY);

//        if (GVmarkers.getDistanceTo(map.getLocation(mouseX, mouseY)) < 2)
//        {
//
//            Rectangle infoBox = new Rectangle(10, 10, 20, 20);
//            infoBox.setFillColor(new Color(255, 0, 0));
//            infoBox.draw(this);
//            text(GVmarkers.getProperties().toString(), 10f, 30f);
//
//        }

        // /*

        


        // */

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
            }
        }.start();
        
    }

    public static void main(String[] args)
    {

        String[] processingArgs = { "MyManSurface" };
        MyManSurface mySketch = new MyManSurface();
        PApplet.runSketch(processingArgs, mySketch);

    }

}