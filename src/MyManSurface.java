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

public class MyManSurface extends PApplet {
	
	UnfoldingMap map;
	Marker val;
	
	public void settings() {
		
	}
	
	
	public void setup() {
		size(1200, 700);
		map = new UnfoldingMap(this, new Google.GoogleMapProvider());
		
		map.zoomAndPanTo(10, new Location(37.404094, -121.790247));		
		MapUtils.createDefaultEventDispatcher(this, map);
		
		Location loc = new Location(37.404094, -121.790247);
		
		Feature valEq = new PointFeature(loc);
		valEq.addProperty("Number Of People Who Died", 50);
		valEq.addProperty("Category", "Super Murder");
		
		val = new SimplePointMarker(loc, valEq.getProperties());
		map.addMarker(val);
		
	}
	
	public void draw() {
		
		map.draw();
		
		Location mouseCoordinates = map.getLocation(mouseX, mouseY);
		fill(100);
		text(mouseCoordinates.getLat() + ", " + mouseCoordinates.getLon(), mouseX, mouseY);
		
		if (val.getDistanceTo(map.getLocation(mouseX, mouseY)) < 2) {
			Rectangle infoBox = new Rectangle(10, 10, 20, 20);
			infoBox.setFillColor(new Color(255, 0, 0));
			infoBox.draw(this);
			text(val.getProperties().toString(), 10f, 30f);
		}
		
//		/*
		val.setColor(color(255,0,0));
		val.setSelected(true);
		val.draw(map);
//		*/
	}
	
	public static void main(String[] args){
		String[] processingArgs = {"MyManSurface"};
		MyManSurface mySketch = new MyManSurface();
		PApplet.runSketch(processingArgs, mySketch);
		
	}
}
