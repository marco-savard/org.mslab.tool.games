package org.mslab.tool.games.client.quiz.geo;

import java.io.Serializable;

import org.mslab.tool.games.shared.text.MessageFormat;

@SuppressWarnings("serial")
public class Longitude implements Serializable {
	private long _totalMinutes;
	
	//required by GWT
	public Longitude() {}

	public Longitude(int degrees, int minutes) {
		_totalMinutes = (degrees * 60) + minutes;
	}

	public static Longitude fromString(String text) {
		int a = text.indexOf('.'); 
		String degText = text.substring(0, a);
		int b = text.indexOf('\'', 2);
		String minText = (b == -1) ? "0" : text.substring(a+1, b); 
		boolean isWest = text.indexOf('W') != -1; 
		
		int deg = Integer.parseInt(degText); 
		deg = isWest ? -deg : deg;
		int min = Integer.parseInt(minText); 
		min = isWest ? -min : min;
		Longitude longitude = new Longitude(deg, min);
		return longitude;
	}
	
	@Override
	public String toString() {
		String hemis = (_totalMinutes >= 0) ? "E" : "W"; 
		int deg = (int)(_totalMinutes / 60); 
		int min = (int)(_totalMinutes % 60);
		
		String text = MessageFormat.format("{0}.{1}'{2}", new Object[] {deg, min, hemis}); 
		return text;
	}
	
	public double toRadians() {
		double degs = _totalMinutes / 60.0; 
		double rads = degs * Math.PI / 180.0;
		return rads;
	}

}
