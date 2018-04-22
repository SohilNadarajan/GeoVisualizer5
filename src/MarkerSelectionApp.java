
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * Loads country markers, and highlights a polygon when the user hovers over it.
 * 
 * This example starts in Southeast Asia to demonstrate hovering multi-marker
 * polygons such as Indonesia, Phillipines, etc.
 */
public class MarkerSelectionApp extends PApplet
{

    UnfoldingMap map;
    List<Marker> countryMarkers = new ArrayList<Marker>();
    Location indonesiaLocation = new Location(-6.175, 106.82);

    public void setup()
    {
        size(800, 600);// , OPENGL);

        map = new UnfoldingMap(this, new Microsoft.HybridProvider());
        // map.zoomAndPanTo(indonesiaLocation, 3);
        MapUtils.createDefaultEventDispatcher(this, map);

        
    }

    public void draw()
    {
        background(240);
        map.draw();
    }

    public void mouseMoved()
    {
        for (Marker marker : map.getMarkers())
        {
            marker.setSelected(false);
        }
        Marker marker = map.getFirstHitMarker(mouseX, mouseY);
        if (marker != null)
        {
            marker.setSelected(true);
        }
    }

    public static void main(String[] args)
    {
        String[] processingArgs = { "MarkerSelectionApp" };
        MarkerSelectionApp mySketch = new MarkerSelectionApp();
        PApplet.runSketch(processingArgs, mySketch);
    }

}