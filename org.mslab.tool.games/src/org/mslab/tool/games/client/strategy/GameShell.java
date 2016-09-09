package org.mslab.tool.games.client.strategy;

import org.mslab.tool.games.client.ApplicationShell;
import org.mslab.tool.games.client.strategy.peg.PegGameShell;
import org.mslab.tool.games.client.strategy.queens.QueensShell;

import com.google.gwt.user.client.ui.DeckPanel;

public class GameShell extends DeckPanel {
	private ApplicationShell _shell; 
	
	public GameShell(ApplicationShell shell) {
		_shell = shell;
		GameHome home = new GameHome(this); 
		add(home);
		
		QueensShell queens = new QueensShell(this); 
		add(queens);
		
		PegGameShell peg = new PegGameShell(this); 
		add(peg);
		
		showWidget(0);
	}

	public void showQueen() {
		showWidget(1);
	}
	
	public void showSolitaire() {
		showWidget(2);
	}

	public void showStrategy() {
		showWidget(0);
	}

	public void showHome() {
		_shell.showHome();
	}



}
