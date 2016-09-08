package org.mslab.tool.games.client.quiz.geo;

import java.io.Serializable;

import org.mslab.tool.games.shared.text.MessageFormat;

@SuppressWarnings("serial")
public class Latitude implements Serializable {
	private long _totalMinutes;
	
	//required by GWT
	public Latitude() {}

	public Latitude(int degrees, int minutes) {
		_totalMinutes = (degrees * 60) + minutes;
	}

	public static Latitude fromString(String text) {
		int a = text.indexOf('.'); 
		int b = text.indexOf('\'', 2);
		
		String degText = text.substring(0, a);
		String minText = (b == -1) ? "0" : text.substring(a+1, b); 
		boolean isSouth = text.indexOf('S') != -1; 
		
		int deg = Integer.parseInt(degText); 
		deg = isSouth ? -deg : deg;
		int min = Integer.parseInt(minText); 
		min = isSouth ? -min : min;
		Latitude latitude = new Latitude(deg, min);
		return latitude;
	}
	
	@Override
	public String toString() {
		String hemis = (_totalMinutes >= 0) ? "N" : "S"; 
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
