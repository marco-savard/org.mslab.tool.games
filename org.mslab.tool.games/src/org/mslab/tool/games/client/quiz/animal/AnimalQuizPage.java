package org.mslab.tool.games.client.quiz.animal;

import org.mslab.tool.games.client.quiz.QuizShell;
import org.mslab.tool.games.client.quiz.abstracts.AbstractPictureQuizPage;
import org.mslab.tool.games.client.quiz.abstracts.AbstractQuizQuestion;
import org.mslab.tool.games.client.quiz.bundles.ImageFactory;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.user.client.ui.Image;

public class AnimalQuizPage extends AbstractPictureQuizPage {
	private Image _iconImage = ImageFactory.getImage(ImageFactory.IMAGE.ANIMAL__PANDA_256PX); 
	
	public AnimalQuizPage(QuizShell shell) {
		super(shell, "Quel animal je suis?"); 
	}

	@Override
	public Image getIconImage() {
		return _iconImage;
	}
	
	@Override
	public String getName() {
		return "Animaux";
	}

	@Override
	protected AbstractQuizQuestion getNextQuestion() {
		AbstractQuizQuestion question = AnimalQuizQuestion.nextQuestion();
		return question;
	}

  
    
}
