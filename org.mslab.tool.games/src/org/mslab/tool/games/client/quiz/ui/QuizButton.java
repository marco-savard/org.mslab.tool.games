package org.mslab.tool.games.client.quiz.ui;

import org.mslab.tool.games.client.core.ui.StyleUtil;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextOverflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;

public class QuizButton extends Button implements MouseOverHandler, MouseOutHandler {
	Color _bgColor = new Color(255, 255, 240);
	
	public QuizButton(String label) {
		super(label);
		removeStyleName("gwt-Button");
		
		Style style = getElement().getStyle();
		style.setBorderStyle(BorderStyle.NONE);
		style.setPadding(6, Unit.PX);
		style.setWhiteSpace(WhiteSpace.NOWRAP);
		style.setTextOverflow(TextOverflow.ELLIPSIS);
		style.setFontWeight(FontWeight.BOLD);
		style.setFontSize(130, Unit.PCT);
		style.setBorderWidth(2, Unit.PX);
		style.setBorderStyle(BorderStyle.SOLID);
		StyleUtil.setBorderRadius(this, "10px");
		refresh();

		addMouseOverHandler(this);
		addMouseOutHandler(this);
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		getElement().getStyle().setCursor(Cursor.DEFAULT);
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		getElement().getStyle().setCursor(Cursor.POINTER);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		refresh(); 
	}

	private void refresh() {
		boolean enabled = super.isEnabled(); 
		Style style = getElement().getStyle();
		
		Color color = enabled ? Color.BLACK : Color.GREY_LIGHT;
		Color bgColor = enabled ? _bgColor : _bgColor.getGrayscale(); 
		Color borderColor = enabled ? Color.BROWN_CHOCOLATE : Color.BROWN_CHOCOLATE.getGrayscale();
		
		style.setBackgroundColor(bgColor.toString());
		style.setColor(color.toString());
		style.setBorderColor(borderColor.toString());
	}
}
