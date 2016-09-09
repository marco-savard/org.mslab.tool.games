package org.mslab.tool.games.client.strategy.queens;

import org.mslab.tool.games.client.core.ui.theme.AbstractTheme;
import org.mslab.tool.games.shared.types.Color;

public class QueensTheme extends AbstractTheme {
	public static final Color FG_COLOR = new Color("493D26");
	public static final Color BG_COLOR = new Color("F3E5AB");
	public static final Color CHECK_COLOR = Color.GREEN_DARK; 
	public static final Color DELETE_COLOR = Color.RED_FIRE_BRICK;
	
	public QueensTheme() {
		_primaryColor = FG_COLOR; 
	}
	
}
