package org.mslab.tool.games.client.core.ui.theme;

import org.mslab.tool.games.client.core.ui.StyleUtil;
import org.mslab.tool.games.shared.text.MessageFormat;
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

public class ThematicButton extends Button implements MouseOverHandler, MouseOutHandler, ThemeChangeHandler {
	private Color _color;
	
	public ThematicButton() {
		this("");
	}
	
	public ThematicButton(String html) {
		super(html);
		removeStyleName("gwt-Button");
		
		Style style = getElement().getStyle();
		style.setBorderStyle(BorderStyle.NONE);
		style.setPadding(6, Unit.PX);
		style.setWhiteSpace(WhiteSpace.NOWRAP);
		style.setTextOverflow(TextOverflow.ELLIPSIS);
		style.setFontWeight(FontWeight.BOLD);
		StyleUtil.setBorderRadius(this, "5px");
		
		addMouseOverHandler(this);
		addMouseOutHandler(this);
		AbstractTheme theme = AbstractTheme.getTheme(); 
		theme.addThemeChangeHandler(this); 
		
		updateColor(); 
		refresh();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		Color bg = _color;
		bg = enabled ? bg : bg.getGrayscale(); //.brighter(2.0); 
		setGradientBackgroud(bg); 
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		getElement().getStyle().setCursor(Cursor.POINTER);
		Color primaryColor = AbstractTheme.getTheme().getPrimaryColor(); 
		Color bgColor = primaryColor;
		setGradientBackgroud(isEnabled() ? bgColor : bgColor.getGrayscale()); 
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		getElement().getStyle().setCursor(Cursor.DEFAULT);
		Color bgColor = AbstractTheme.getTheme().getPrimaryColor();
		setGradientBackgroud(isEnabled() ? bgColor : bgColor.getGrayscale()); 
	}
	
	@Override
	public void onThemeChange(ThemeChangeEvent event) {
		updateColor();
		refresh();
	}
	
	private void updateColor() {
		AbstractTheme theme = AbstractTheme.getTheme(); 
		Color primaryColor = theme.getPrimaryColor();
		_color = primaryColor; 
	}
	
	public void setColor(Color color) {
		_color = color;
		refresh();
	}
	
	private void refresh() {
		Color bgColor = Color.createFromHsl(_color.getHue(), _color.getSaturation(), 60);
		setGradientBackgroud(isEnabled() ? bgColor : bgColor.getGrayscale()); 
		Color fgColor = bgColor.getContrastColor();
		getElement().getStyle().setColor(fgColor.toString());
	}

	private void setGradientBackgroud(Color primaryColor) {
		Color topColor = Color.createFromHsl(primaryColor.getHue(), primaryColor.getSaturation(), 100); //primaryColor.blendWith(Color.WHITE); 
		Color bottomColor = Color.createFromHsl(primaryColor.getHue(), primaryColor.getSaturation(), 60); //.darker(1.5);
		String gradient = MessageFormat.format("{0}deg, {1}, {2}", new Object[] {
				90, bottomColor.toString(), topColor.toString()});
		StyleUtil.setLinearGradient(this, gradient);
	}


	
}
