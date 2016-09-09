package org.mslab.tool.games.client.quiz.ui;

import org.mslab.tool.games.client.game.ui.GameButton;
import org.mslab.tool.games.shared.text.SafeString;

public class ChoiceQuizButton extends GameButton {
	private SafeString _value;

	public ChoiceQuizButton() {
		super("", "");
		getElement().getStyle().setProperty("transitionDuration", "1s");
	}
	
	public void setLabelAndPicture(SafeString value) {
		super.setHTML(value.toHtml());
		_value = value;
	}

	public String getPictureName() {
		return _value.toString();
	}

}
