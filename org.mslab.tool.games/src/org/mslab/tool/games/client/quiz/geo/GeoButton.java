package org.mslab.tool.games.client.quiz.geo;

import org.mslab.tool.games.client.quiz.ui.QuizButton;

import com.google.gwt.dom.client.Style.Unit;

public class GeoButton extends QuizButton {
	private Location _location; 

	public GeoButton(String label) {
		super(label);
		getElement().getStyle().setPaddingLeft(2, Unit.EM);
		getElement().getStyle().setPaddingRight(2, Unit.EM);
		getElement().getStyle().setMarginBottom(12, Unit.PX);
	}

	public void setLocation(Location location) {
		_location = location;
		this.setHTML(location.getName().toHtml());
	}

	public Location getLocation() {
		return _location;
	}

}
