package org.mslab.tool.games.client.quiz.geo;

import java.util.ArrayList;
import java.util.List;

public class Region {
	private List<Location> _locations = new ArrayList<Location>();

	public void add(Location location) {
		_locations.add(location); 
	}

	public List<Location> getLocations() {
		return _locations;
	}

}
