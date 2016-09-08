package org.mslab.tool.games.client.quiz.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.RadioButton;

public class QuizRadioButton extends RadioButton {

	public QuizRadioButton(String name, String label) {
		super(name, label);
		this.getElement().getStyle().setFontSize(160, Unit.PCT);
	}

}
