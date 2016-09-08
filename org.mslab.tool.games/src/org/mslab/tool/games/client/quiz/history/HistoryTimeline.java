package org.mslab.tool.games.client.quiz.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryTimeline {
	private List<HistoryEvent> _events = new ArrayList<HistoryEvent>(); 

	public void add(HistoryEvent event) {
		_events.add(event);
	}

	public List<HistoryEvent> getEvents() {
		return _events;
	}

}
