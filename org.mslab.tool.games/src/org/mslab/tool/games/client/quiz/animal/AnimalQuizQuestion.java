package org.mslab.tool.games.client.quiz.animal;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.client.quiz.abstracts.AbstractQuizQuestion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;

public class AnimalQuizQuestion extends AbstractQuizQuestion {
	
	public static AnimalQuizQuestion nextQuestion() {
		AnimalQuizQuestion question = new AnimalQuizQuestion(); 
		return question;
	}
	
	private AnimalQuizQuestion() {
		super("Quel_animal_je_suis"); 
	}

}
