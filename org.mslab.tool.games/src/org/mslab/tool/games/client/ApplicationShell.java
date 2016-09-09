package org.mslab.tool.games.client;

import org.mslab.tool.games.client.quiz.QuizShell;
import org.mslab.tool.games.client.strategy.GameShell;

import com.google.gwt.user.client.ui.DeckPanel;

public class ApplicationShell extends DeckPanel {
	
	ApplicationShell() {
		ApplicationHome home = new ApplicationHome(this);
		add(home);
		showWidget(0);
		
		QuizShell quiz = new QuizShell(this); 
		add(quiz);
		
		GameShell game = new GameShell(this); 
		add(game);
	}
	
	public void showHome() {
		showWidget(0);
	}

	public void showQuiz() {
		showWidget(1);
	}

	public void showStrategy() {
		showWidget(2);
	}

}
