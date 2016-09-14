package org.mslab.tool.games.client.quiz.geo;

import org.mslab.tool.games.client.game.ui.GameButton;

import com.google.gwt.dom.client.Style.Unit;

public class GeoButton extends GameButton {
	private Location _location; 

	public GeoButton(String label) {
		super("", label);
		getElement().getStyle().setPaddingLeft(2, Unit.EM);
		getElement().getStyle().setPaddingRight(2, Unit.EM);
		getElement().getStyle().setMarginBottom(12, Unit.PX);
	}

	public void setLocation(Location location) {
		_location = location;
		setHTML(location.getName().toHtml());
	}

	public Location getLocation() {
		return _location;
	}

}
