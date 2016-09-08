package org.mslab.tool.games.client.quiz.geo;

import org.mslab.tool.games.shared.text.SafeString;

public class Location {
	private SafeString _name;
	private Latitude _lat;
	private Longitude _lon;
	
	public Location(SafeString name, Latitude lat, Longitude lon) {
		_name = name;
		_lat = lat;
		_lon = lon;
	}

	public SafeString getName() {
		return _name;
	}
	
	private Longitude getLongitude() {
		return _lon;
	}

	private Latitude getLatitude() {
		return _lat;
	}
	
	public double computeDistanceFrom(Location thatLocation) {
		double lat0 = thatLocation.getLatitude().toRadians(); 
		double lon0 = thatLocation.getLongitude().toRadians();
		double lat1 = _lat.toRadians(); 
		double lon1 = _lon.toRadians(); 
		
		double deltaLon = Math.abs(lon0 - lon1); 
		double greatCircle = (Math.sin(lat0) * Math.sin(lat1)) + (Math.cos(lat0) * Math.cos(lat1) * Math.cos(deltaLon)); 
		double deltaAngle = Math.acos(greatCircle); 
		double distance = EARTH_RADIUS * deltaAngle;
		
		//0.1 km of precision
		distance = ((int)(distance * 10) / 10.0); 
		return distance;
	}
	

	
	private static final double EARTH_RADIUS = 6371.01; //mean radius in km

}
