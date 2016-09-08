package org.mslab.tool.games.client.quiz.bundles;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.client.quiz.geo.Latitude;
import org.mslab.tool.games.client.quiz.geo.Location;
import org.mslab.tool.games.client.quiz.geo.Longitude;
import org.mslab.tool.games.client.quiz.geo.Region;
import org.mslab.tool.games.client.quiz.io.BufferedReader;
import org.mslab.tool.games.shared.text.SafeString;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.Random;

public class GeoFactory {
	private static GeoBundle _geoBundle;
	private static List<Region> _regions = null;
	
	public static Region getRegion() {
		if (_regions == null) {
			_regions = new ArrayList<Region>();
			GeoBundle bundle = getBundle();
			addRegion(bundle.canada());
			addRegion(bundle.europe());
			addRegion(bundle.quebec());
			addRegion(bundle.usa());
		}
		
		int idx = Random.nextInt(_regions.size()); 
		Region region = _regions.get(idx);
		return region;
	}
	
	private static void addRegion(TextResource res) {
		Region region = readRessource(res); 
		_regions.add(region); 
	}

	private static Region readRessource(TextResource res) {
		String text = res.getText();
		BufferedReader reader = new BufferedReader(text); 
		Region region = new Region();
		
		do {
			String line = reader.getLine(); 
			
			if (line == null) {
				break;
			} else {
				int start = 0, end = line.indexOf(':'); 
				if (end != -1) {
					SafeString city = new SafeString(line.substring(start, end)); 
					start = end + 1;
					end = line.indexOf(' ', start); 
					Latitude lat = Latitude.fromString(line.substring(start, end));
					start = end + 1;
					end = line.length() - 1;
					Longitude lon = Longitude.fromString(line.substring(start, end));
					Location location = new Location(city, lat, lon);
					region.add(location);
				}
			}

		} while (true); 
		
		
		return region;
	}

	private static GeoBundle getBundle() {
		if (_geoBundle == null) {
			_geoBundle = (GeoBundle) GWT.create(GeoBundle.class);
		}

		return _geoBundle;
	}

}
