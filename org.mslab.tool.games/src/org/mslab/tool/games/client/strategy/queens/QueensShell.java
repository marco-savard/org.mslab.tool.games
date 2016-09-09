package org.mslab.tool.games.client.strategy.queens;

import org.mslab.tool.games.client.strategy.GameShell;

import com.google.gwt.user.client.ui.ScrollPanel;

public class QueensShell extends ScrollPanel {
	
	public QueensShell(GameShell owner) {
		QueensPage page = new QueensPage(owner);
		setWidget(page);
	}

}
