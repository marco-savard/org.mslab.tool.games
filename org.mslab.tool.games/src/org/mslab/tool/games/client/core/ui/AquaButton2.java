package org.mslab.tool.games.client.core.ui;

import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;

//based on http://www.girliemac.com/blog/2009/04/30/css3-gradients-no-image-aqua-button/
public class AquaButton2 extends Button implements MouseOverHandler, MouseOutHandler {
	private static final int BORDER_RADIUS = 8;
	
	private AquaButton2() {
		this("");
	}

	private AquaButton2(String html) {
		super(html); 
		removeStyleName("gwt-Button");
		Element element = getElement(); 
		Style style = element.getStyle(); 
		style.setPadding(5, Unit.PX);
		StyleUtil.setBorderRadius(this, "12px");
		
		addMouseOverHandler(this);
		addMouseOutHandler(this);
		refresh(); 
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		getElement().getStyle().setCursor(Cursor.POINTER);
	}
	
	@Override
	public void onMouseOut(MouseOutEvent event) {
		refresh(); 
	}

	private void refresh() {
		getElement().getStyle().setCursor(Cursor.DEFAULT);
	}

	public void setPrimaryColor(Color color) {
		//set BG (fallback if gradient not supported)
		Style style = getElement().getStyle(); 
		style.setBackgroundColor(color.toString());
		
		//set gradient BG
		String topColor = color.brighter(2.0).toString();
 		String bottomColor = color.toString(); 
 		String patt = "top, {0}, {1}";
		String gradient = MessageFormat.format(patt, new Object[] {topColor, bottomColor});
 		StyleUtil.setLinearGradient(this, gradient);
		
 		//set FG
		int brightness = color.getBrightness();
		Color fgColor = (brightness > 60) ? Color.BLACK : Color.WHITE; 
		style.setColor(fgColor.toString());
		
		style.setBorderStyle(BorderStyle.NONE);
		
		//set label style
		ButtonElement btn = getButtonElement();
		setLabelStyle2(btn); 
		//setLabelStyle(btn); 
	}

	private void setLabelStyle2(ButtonElement btn) {
		// TODO Auto-generated method stub
		
	}

	private void setLabelStyle(ButtonElement btn) {
		Style btnStyle = btn.getStyle();
		String value = "top, rgba(255, 255, 255, 0.7), rgba(255, 255, 255, 0)"; 
		StyleUtil.setLinearGradient(btn, value);
		
		btnStyle.setHeight(1, Unit.PX);
		btnStyle.setPadding(8, Unit.PX);
		btnStyle.setMarginTop(2, Unit.PX);
		btnStyle.setPosition(Position.ABSOLUTE);
		StyleUtil.setBorderRadius(this, BORDER_RADIUS + "px");
		
		int widthPx = 48;
		btnStyle.setWidth(widthPx - BORDER_RADIUS*2 - 20, Unit.PX);
		btnStyle.setMarginLeft(10, Unit.PX);
		btnStyle.setMarginRight(10, Unit.PX);
	}




}
