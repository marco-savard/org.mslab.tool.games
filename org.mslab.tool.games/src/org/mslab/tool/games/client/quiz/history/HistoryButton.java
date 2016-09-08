package org.mslab.tool.games.client.quiz.history;

import org.mslab.tool.games.client.quiz.ui.QuizButton;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.WhiteSpace;

public class HistoryButton extends QuizButton {
	private HistoryEvent _historyEvent; 

	public HistoryButton(String label) {
		super(label);
		getElement().getStyle().setWhiteSpace(WhiteSpace.NORMAL);
	}
	
	public HistoryButton() {
		this("");
	}

	public void setHistoryEvent(HistoryEvent historyEvent) {
		_historyEvent = historyEvent;
		this.setHTML(historyEvent.getHtml());
	}

	public HistoryEvent getHistoryEvent() {
		return _historyEvent;
	}

}
