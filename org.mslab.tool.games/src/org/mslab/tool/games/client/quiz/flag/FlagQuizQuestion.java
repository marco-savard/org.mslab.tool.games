package org.mslab.tool.games.client.quiz.flag;

import org.mslab.tool.games.client.quiz.abstracts.AbstractQuizQuestion;

public class FlagQuizQuestion extends AbstractQuizQuestion {
	
	public static FlagQuizQuestion nextQuestion() {
		FlagQuizQuestion question = new FlagQuizQuestion(); 
		return question;
	}
	
	private FlagQuizQuestion() {
		super("Je_suis_le_drapeau_de_quel_Etat"); 
	}

}
