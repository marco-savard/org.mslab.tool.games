package org.mslab.tool.games.client.core.ui.theme;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.shared.types.Color;

public abstract class AbstractTheme {
	protected static AbstractTheme _currentTheme; 
	private List<ThemeChangeHandler> _handlers = new ArrayList<ThemeChangeHandler>();
	
	public static void setTheme(AbstractTheme theme) {
		_currentTheme = theme;
		_currentTheme.fireThemeChangeEvent(); 
	}
	
	private void fireThemeChangeEvent() {
		//fire event
		ThemeChangeEvent event = new ThemeChangeEvent();
		for (ThemeChangeHandler handler : _handlers) {
			handler.onThemeChange(event);
		}
	}

	public static AbstractTheme getTheme() {
		if (_currentTheme == null) {
			String msg = "Error: call AbstractTheme.setTheme() before calling AbstractTheme.getTheme()"; 
			RuntimeException ex = new RuntimeException(msg); 
			throw ex;
		}
		
		return _currentTheme;
	}

	public Color getPrimaryColor() { return _primaryColor; }
	public void setPrimaryColor(Color color) { 
		_primaryColor = color; 
		fireThemeChangeEvent();
	}
	protected Color _primaryColor = new Color(64, 153, 255);

	public void addThemeChangeHandler(ThemeChangeHandler handler) {
		_handlers.add(handler); 
	}

	

	
}
