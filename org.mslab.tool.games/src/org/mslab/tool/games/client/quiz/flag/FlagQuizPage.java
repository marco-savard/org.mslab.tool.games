package org.mslab.tool.games.client.quiz.flag;

import org.mslab.tool.games.client.quiz.QuizShell;
import org.mslab.tool.games.client.quiz.abstracts.AbstractPictureQuizPage;
import org.mslab.tool.games.client.quiz.abstracts.AbstractQuizQuestion;
import org.mslab.tool.games.client.quiz.bundles.ImageFactory;

import com.google.gwt.user.client.ui.Image;

public class FlagQuizPage extends AbstractPictureQuizPage {
	private Image _iconImage = ImageFactory.getImage(ImageFactory.IMAGE.FLAG_RED); 
	
	public FlagQuizPage(QuizShell shell) {
		super(shell, "Quel drapeau je suis?"); 
	}

	@Override
	public Image getIconImage() {
		return _iconImage;
	}
	
	@Override
	public String getName() {
		return "Drapeaux";
	}

	@Override
	protected AbstractQuizQuestion getNextQuestion() {
		AbstractQuizQuestion question = FlagQuizQuestion.nextQuestion();
		return question;
	}    
    
}
