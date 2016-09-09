package org.mslab.tool.games.client.core.ui.theme;

import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Anchor;

public class ThematicAnchor extends Anchor implements MouseOverHandler, MouseOutHandler, ThemeChangeHandler {

	public ThematicAnchor() {
		addMouseOverHandler(this);
		addMouseOutHandler(this);
		AbstractTheme.getTheme().addThemeChangeHandler(this); 
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		getElement().getStyle().setCursor(Cursor.POINTER);
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		getElement().getStyle().setCursor(Cursor.DEFAULT);
	}

	@Override
	public void onThemeChange(ThemeChangeEvent event) {
		Color color = AbstractTheme.getTheme().getPrimaryColor();
		getElement().getStyle().setColor(color.toString());
	}
}
