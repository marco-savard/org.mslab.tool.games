package org.mslab.tool.games.client.quiz.bundles;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.client.quiz.history.HistoryEvent;
import org.mslab.tool.games.client.quiz.history.HistoryTimeline;
import org.mslab.tool.games.client.quiz.io.BufferedReader;
import org.mslab.tool.games.shared.text.SafeString;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.Random;

public class ChronoFactory {
	private static ChronoBundle _chronoBundle;
	private static List<HistoryTimeline> _timelines = null;
	
	public static HistoryTimeline getChronologie() {
		if (_timelines == null) {
			_timelines = new ArrayList<HistoryTimeline>();
			ChronoBundle bundle = getBundle();
			addTimeline(bundle.astronomie());
			addTimeline(bundle.chimie());
			addTimeline(bundle.medecine());
			addTimeline(bundle.inventions());
			addTimeline(bundle.decouvertes());
			addTimeline(bundle.quebec());
			addTimeline(bundle.fondations());
		}
		
		int idx = Random.nextInt(_timelines.size()); 
		HistoryTimeline timeline = _timelines.get(idx);
		return timeline;
	}
	
	private static void addTimeline(TextResource res) {
		HistoryTimeline timeline = readTimeline(res); 
		_timelines.add(timeline); 
	}

	private static HistoryTimeline readTimeline(TextResource res) {
		String text = res.getText();
		BufferedReader reader = new BufferedReader(text); 
		HistoryTimeline timeline = new HistoryTimeline();
		
		do {
			String line = reader.getLine(); 
			
			if (line == null) {
				break;
			} else {
				int idx = line.indexOf(':'); 
				int year = Integer.parseInt(line.substring(0, idx)); 
				SafeString name = new SafeString(line.substring(idx+1)); 
				HistoryEvent event = new HistoryEvent(year, name); 
				timeline.add(event);
			}

		} while (true); 
		
		
		return timeline;
	}

	private static ChronoBundle getBundle() {
		if (_chronoBundle == null) {
			_chronoBundle = (ChronoBundle) GWT.create(ChronoBundle.class);
		}

		return _chronoBundle;
	}

}
 