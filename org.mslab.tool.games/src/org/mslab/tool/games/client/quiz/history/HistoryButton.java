package org.mslab.tool.games.client.quiz.history;

import org.mslab.tool.games.client.core.ui.theme.AbstractTheme;
import org.mslab.tool.games.client.game.ui.GameButton;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.user.client.Window;

public class HistoryButton extends GameButton {
	private HistoryEvent _historyEvent; 

	public HistoryButton(String label) {
		super("", label);
		_btn.getElement().getStyle().setWhiteSpace(WhiteSpace.NORMAL);
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
	
	@Override
	protected void refresh() {
		AbstractTheme theme = AbstractTheme.getTheme(); 
		Color color = theme.getPrimaryColor(); 
		super.setPrimaryColor(color); 
		
		int w = Window.getClientWidth();
		int h = Window.getClientHeight();
		boolean landscape = w > h; 
		int fontSize = landscape ? 130 : 150;
		_btn.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
	}

}
