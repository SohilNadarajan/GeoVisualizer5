import java.awt.Color;

import de.fhpotsdam.unfolding.UnfoldingMap;

import de.fhpotsdam.unfolding.data.Feature;

import de.fhpotsdam.unfolding.data.PointFeature;

import de.fhpotsdam.unfolding.geo.Location;

import de.fhpotsdam.unfolding.marker.Marker;

import de.fhpotsdam.unfolding.marker.MarkerManager;

import de.fhpotsdam.unfolding.marker.SimplePointMarker;

import de.fhpotsdam.unfolding.providers.Google;

import de.fhpotsdam.unfolding.utils.MapUtils;

import maxim.shapes.*;

import processing.core.PApplet;

public class MyManSurface extends PApplet
{

    UnfoldingMap map;

    Marker val;

    public void settings()
    {

    }

    public void setup()
    {

        int width = 1200, height = 750;

        size(width, height);

        map = new UnfoldingMap(this, new Google.GoogleMapProvider());

        Location theLoc = new Location(30.576576, -76.85618);

        map.zoomAndPanTo(4, theLoc);

        map.setPanningRestriction(theLoc, 0f);

        map.setZoomRange(4f, 25f);

        // map.setTweening(true);

        MapUtils.createDefaultEventDispatcher(this, map);

        Location loc = new Location(37.404094, -121.790247);

        Feature valEq = new PointFeature(loc);

        valEq.addProperty("Number Of People Who Died", 50);

        valEq.addProperty("Category", "Super Murder");

        val = new SimplePointMarker(loc, valEq.getProperties());

        map.addMarker(val);

    }

    public void draw()
    {

        Location theLoc = new Location(30.576576, -76.85618);

        map.draw();

        int zoomLevel = map.getZoomLevel();

        System.out.println(map.getWidth() + "   level: " + zoomLevel);

        if (zoomLevel == 4)
        {

            map.setPanningRestriction(theLoc, 0f);

        }
        else
        {

            map.setPanningRestriction(theLoc, (float) (1200 * (Math.pow(2, (zoomLevel - 4)))));

        }

        Location mouseCoordinates = map.getLocation(mouseX, mouseY);

        fill(100);

        text(mouseCoordinates.getLat() + ", " + mouseCoordinates.getLon(), mouseX, mouseY);

        if (val.getDistanceTo(map.getLocation(mouseX, mouseY)) < 2)
        {

            Rectangle infoBox = new Rectangle(10, 10, 20, 20);

            infoBox.setFillColor(new Color(255, 0, 0));

            infoBox.draw(this);

            text(val.getProperties().toString(), 10f, 30f);

        }

        // /*

        val.setColor(color(255, 0, 0));

        val.setSelected(true);

        val.draw(map);

        // */

    }

    public static void main(String[] args)
    {

        String[] processingArgs = { "MyManSurface" };

        MyManSurface mySketch = new MyManSurface();

        PApplet.runSketch(processingArgs, mySketch);

    }

}