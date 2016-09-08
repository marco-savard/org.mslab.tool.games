package org.mslab.tool.games.client.quiz.history;

import org.mslab.tool.games.shared.text.SafeString;

public class HistoryEvent {
	private int _year;
	private SafeString _name; 


	public HistoryEvent(int year, SafeString name) {
		_year = year;
		_name = name;
	}


	public String getHtml() {
		String html = _name.toHtml(); 
		return html;
	}


	public int getYear() {
		return _year;
	}

}
