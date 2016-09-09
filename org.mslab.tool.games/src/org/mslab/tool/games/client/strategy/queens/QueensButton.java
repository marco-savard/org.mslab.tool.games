package org.mslab.tool.games.client.strategy.queens;

import org.mslab.tool.games.client.core.ui.theme.ThematicButton;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;

public class QueensButton extends ThematicButton {
	
	private QueensButton() {
		Style style = getElement().getStyle();
		//style.setColor(GameTheme.FG_COLOR.toString());
		//style.setBorderColor(GameTheme.FG_COLOR.toString());
		style.setBorderStyle(BorderStyle.SOLID);
		//style.setBackgroundColor(GameTheme.BG_COLOR.toString());
		style.setProperty("borderWidth", "1px 2px 2px 1px");
		//String shadow = MessageFormat.format("10px 10px 5px {0}", GameTheme.FG_COLOR.toString());
		//StyleUtil.setBoxShadow(this, shadow);
	}

}
