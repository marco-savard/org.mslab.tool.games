package org.mslab.tool.games.client.core.ui;

import org.mslab.tool.games.client.core.ui.theme.ThemeChangeHandler;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.TextOverflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

//based on http://www.girliemac.com/blog/2009/04/30/css3-gradients-no-image-aqua-button/
public class AquaButton extends FocusPanel implements MouseOverHandler, MouseOutHandler {
	public static final int PX_PER_CHARACTRER = 12;
	private static final int BORDER_RADIUS = 8;
	private GlarePanel _glareBG; 
	private Color _color, _lighterColor; 
	
	private AquaButton(String html, int widthPx) {					
		removeStyleName("gwt-Button");
		Style style = getElement().getStyle(); 
		
		style.setWidth(widthPx, Unit.PX);
		style.setHeight(36, Unit.PX);
		StyleUtil.setBorderRadius(this, (BORDER_RADIUS * 2) + "px");
		style.setProperty("position", "relative");
		
		style.setMarginBottom(18, Unit.PX);		
		style.setBorderStyle(BorderStyle.SOLID);
		style.setBorderWidth(1, Unit.PX);
		style.setColor(Color.WHITE.toString());
		
		_glareBG = new GlarePanel(html, widthPx);
		setWidget(_glareBG);
		
		addMouseOverHandler(this);
		addMouseOutHandler(this);
	}
	
	public void setHTML(String html) {
		_glareBG.setHTML(html); 	
	}

	public void setColor(Color color) {
		_color = color;
		_lighterColor = color.brighter(2.0);
		
		//set BG (fallback if gradient not supported)
		Style style = getElement().getStyle(); 
		style.setBackgroundColor(color.toString());
		
		//set gradient BG
		String topColor = color.toRGBAString();
 		String bottomColor = _lighterColor.toRGBAString(); 
 		
 		String patt = "top, {0}, {1}";
		String gradient = MessageFormat.format(patt, new Object[] {topColor, bottomColor});
 		StyleUtil.setLinearGradient(this, gradient);
		
		//set shadow
		patt = "{0} 0px 10px 16px";
		Color shadowColor = color.brighter(3.0);
		shadowColor.setAlpha(0.5);
		String shadow = MessageFormat.format(patt, new Object[] {shadowColor.toRGBAString()} );
		style.setProperty("boxShadow", shadow);
		
		//set border
		patt = "{0} {1} {2} {3}";
		String border = MessageFormat.format(patt, new Object[] {
				_lighterColor.toString(), 
				_lighterColor.toString(), 
				_color.toString(), 
				_color.toString(), //"#8ba2c1 #5890bf #4f93ca #768fa5"
		});
		style.setBorderColor(border);
	}

	private class GlarePanel extends SimplePanel {
		private HTML _label; 
		
		GlarePanel(String html, int widthPx) {
			Style style = getElement().getStyle(); 
			String value = "top, rgba(255, 255, 255, 0.7), rgba(255, 255, 255, 0)"; 
			StyleUtil.setLinearGradient(this, value);

			style.setHeight(1, Unit.PX);
			style.setPadding(8, Unit.PX);
			style.setMarginTop(2, Unit.PX);
			StyleUtil.setBorderRadius(this, BORDER_RADIUS + "px");
			style.setPosition(Position.ABSOLUTE);
			
			style.setWidth(widthPx - BORDER_RADIUS*2 - 20, Unit.PX);
			style.setMarginLeft(10, Unit.PX);
			style.setMarginRight(10, Unit.PX);
			
			_label = new HTML(html); 
			style = _label.getElement().getStyle(); 
			style.setProperty("fontFamily", "Lucida Sans, Helvetica, sans-serif");
			style.setProperty("fontWeight", "800");
			style.setProperty("textShadow", "rgba(10, 10, 10, 0.5) 1px 2px 2px");
			style.setTextAlign(TextAlign.CENTER);
			style.setWhiteSpace(WhiteSpace.NOWRAP);
			style.setTextOverflow(TextOverflow.ELLIPSIS);
			style.setVerticalAlign(VerticalAlign.TOP);
			setWidget(_label);
		}

		public void setHTML(String html) {
			_label.setHTML(html);
		}
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Style style = getElement().getStyle();
		style.setCursor(Cursor.POINTER);
		style.setColor(Color.YELLOW.toString());	
		//style.setProperty("boxShadow", "rgba(255, 165, 0, 0.5) 0px 10px 16px");
		
		if (_color != null) {
			Color darker = _color.darker(1.5); 
			style.setBorderColor(darker.toString());
		}
		
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		Style style = getElement().getStyle();
		style.setCursor(Cursor.DEFAULT);
		style.setColor(Color.WHITE.toString());
		style.setBorderColor("#8ba2c1 #5890bf #4f93ca #768fa5");
		
		if (_color != null) {
			String patt = "{0} {1} {2} {3}";
			String border = MessageFormat.format(patt, new Object[] {
					_lighterColor.toString(), _lighterColor.toString(), _color.toString(), _color.toString(),
			});
			style.setBorderColor(border);
		}
	}
} //end AquaButton