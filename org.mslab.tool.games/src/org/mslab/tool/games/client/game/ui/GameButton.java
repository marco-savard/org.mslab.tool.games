package org.mslab.tool.games.client.game.ui;

import org.mslab.tool.games.client.core.ui.AquaButton3;
import org.mslab.tool.games.client.core.ui.theme.AbstractTheme;
import org.mslab.tool.games.client.core.ui.theme.ThemeChangeEvent;
import org.mslab.tool.games.client.core.ui.theme.ThemeChangeHandler;
import org.mslab.tool.games.shared.types.Color;

public class GameButton extends AquaButton3 implements ThemeChangeHandler {

	public GameButton(String icon, String html) {
		super.setHTML(concatenate(icon, html));
		
		AbstractTheme theme = AbstractTheme.getTheme(); 
		theme.addThemeChangeHandler(this); 
		refresh(); 
	}
	
	@Override
	public void onThemeChange(ThemeChangeEvent event) {
		refresh(); 
	}
	
	private void refresh() {
		AbstractTheme theme = AbstractTheme.getTheme(); 
		Color color = theme.getPrimaryColor(); 
		super.setPrimaryColor(color); 
	}

	private static String concatenate(String icon, String html) {
		html = icon + " " + html;
		return html;
	}

	


}
