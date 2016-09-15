package org.mslab.tool.games.client.game.ui;

import org.mslab.tool.games.client.core.ui.AquaButton;
import org.mslab.tool.games.client.core.ui.theme.AbstractTheme;
import org.mslab.tool.games.client.core.ui.theme.ThemeChangeEvent;
import org.mslab.tool.games.client.core.ui.theme.ThemeChangeHandler;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

public class GameButton extends AquaButton implements ThemeChangeHandler, ResizeHandler {

	public GameButton(String icon, String html) {
		super.setHTML(concatenate(icon, html));		
		
		AbstractTheme theme = AbstractTheme.getTheme(); 
		theme.addThemeChangeHandler(this); 
		
		Window.addResizeHandler(this); 
		refresh(); 
	}
	
	@Override
	public void onThemeChange(ThemeChangeEvent event) {
		refresh(); 
	}

	protected void refresh() {
		AbstractTheme theme = AbstractTheme.getTheme(); 
		Color color = theme.getPrimaryColor(); 
		super.setPrimaryColor(color); 
		
		int w = Window.getClientWidth();
		int h = Window.getClientHeight();
		boolean landscape = w > h; 
		int fontSize = landscape ? 200 : 300;
		_btn.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
	}

	private static String concatenate(String icon, String html) {
		html = icon + " " + html;
		return html;
	}

	@Override
	public void onResize(ResizeEvent event) {
		refresh();
	}
	
	public com.google.gwt.user.client.Element getButtonElement() {
		return _btn.getElement();
	}
	


}
