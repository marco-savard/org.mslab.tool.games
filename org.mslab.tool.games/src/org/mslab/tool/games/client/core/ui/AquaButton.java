package org.mslab.tool.games.client.core.ui;

import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.SimplePanel;

//based on http://www.girliemac.com/blog/2009/04/30/css3-gradients-no-image-aqua-button/
public class AquaButton extends FocusPanel implements MouseOverHandler, MouseOutHandler {
	private static final int GLARE_MARGIN_TOP = 2; 
	private static final int GLARE_MARGIN_SIDE = 6; 
	private AbsolutePanel _absolutePanel; 
	protected Button _btn; 
	private SimplePanel _glarePanel;
	private Color _primaryColor; 
	
	public AquaButton() {		
		
		_absolutePanel = new AbsolutePanel(); 
		_absolutePanel.getElement().getStyle().setPaddingBottom(12, Unit.PX);
		_absolutePanel.getElement().getStyle().setPaddingRight(12, Unit.PX);
		this.add(_absolutePanel);
		
		_btn = new Button(); 
		_btn.removeStyleName("gwt-Button");
		Style style = _btn.getElement().getStyle(); 
		style.setPadding(6, Unit.PX);
		style.setPaddingLeft(12, Unit.PX);
		style.setPaddingRight(12, Unit.PX);
		style.setWhiteSpace(WhiteSpace.NOWRAP);
		StyleUtil.setBorderRadius(_btn, "12px");
		_absolutePanel.add(_btn);
		
		_glarePanel = new GlarePanel(); 
		_absolutePanel.add(_glarePanel, 0, 0);
		
		addMouseOverHandler(this);
		addMouseOutHandler(this);
		refresh();
	}
	
	public void setHTML(String html) {
		_btn.setHTML(html);
	}
	
	public void setEnabled(boolean enabled) {
		_btn.setEnabled(enabled);
		refresh(); 
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		if (_btn.isEnabled()) {
			getElement().getStyle().setCursor(Cursor.POINTER);
			int width = _btn.getOffsetWidth() - (GLARE_MARGIN_SIDE * 2);
			int height = _btn.getOffsetWidth() - (GLARE_MARGIN_TOP * 2);
			_glarePanel.setWidth(width + "px");
			_glarePanel.setHeight("50%");
			
			//set gradient BG
			if (_primaryColor != null) {
				String topColor = _primaryColor.toString();
				Color brighterColor = _primaryColor.brighter(3.0); 
		 		String bottomColor = brighterColor.toString(); 
		 		String patt = "top, {0}, {1}";
				String gradient = MessageFormat.format(patt, new Object[] {topColor, bottomColor});
		 		StyleUtil.setLinearGradient(_btn, gradient);
		 		
		 		setBoxShadow(brighterColor, true);
			}
		}
	}
	
	@Override
	public void onMouseOut(MouseOutEvent event) {
		refresh();
	}

	private void refresh() {
		getElement().getStyle().setCursor(Cursor.DEFAULT);
		_glarePanel.setWidth("0px");
		
		//set gradient BG
		if (_primaryColor != null) {
			//set gradient
			Color color = _btn.isEnabled() ? _primaryColor : _primaryColor.getGrayscale();
			String topColor = color.brighter(2.0).toString();
	 		String bottomColor = color.toString(); 
	 		String patt = "top, {0}, {1}";
			String gradient = MessageFormat.format(patt, new Object[] {topColor, bottomColor});
	 		StyleUtil.setLinearGradient(_btn, gradient);
	 		
			//set border
			patt = "{0} {1} {2} {3}";
			Color brighterColor = color.brighter(3.0); 
			String border = MessageFormat.format(patt, new Object[] {
					brighterColor.toString(), 
					brighterColor.toString(), 
					color.toString(), 
					color.toString(), //"#8ba2c1 #5890bf #4f93ca #768fa5"
			});
			_btn.getElement().getStyle().setBorderColor(border);
			
			//set box shadow
			setBoxShadow(brighterColor, false);
		}
	}

	//set box shadow
	private void setBoxShadow(Color brighterColor, boolean focused) {
		String patt = "{0} 0px 10px 10px 0px";
		int percentBlend = focused ? 40 : 70;
		Color shadowColor = brighterColor.blendWith(Color.WHITE, percentBlend);
		shadowColor.setAlpha(0.9);
		String shadow = MessageFormat.format("6px 6px 6px {0}", shadowColor.toString());
		StyleUtil.setBoxShadow(_btn, shadow);
	}

	public void setPrimaryColor(Color color) {
		//set color
		_primaryColor = color;
		Color brighterColor = color.brighter(3.0); 
		
		//set BG (fallback if gradient not supported)
		Style style = _btn.getElement().getStyle(); 
		style.setBackgroundColor(color.toString());
		 		
 		//set FG
		int brightness = color.getBrightness();
		Color fgColor = (brightness > 60) ? Color.BLACK : Color.WHITE; 
		style.setColor(fgColor.toString());
		
		//set border
		style.setBorderWidth(1, Unit.PX);
		
		//set text shadow
		style.setFontWeight(FontWeight.BOLD);
		style.setProperty("textShadow", "2px 2px 4px #000000");
		
		refresh();
	}

	
	private static class GlarePanel extends SimplePanel {
		GlarePanel() {
			getElement().getStyle().setMargin(2, Unit.PX);
			getElement().getStyle().setMarginLeft(6, Unit.PX);
			getElement().getStyle().setMarginRight(6, Unit.PX);
			
			String value = "top, rgba(255, 255, 255, 0.7), rgba(255, 255, 255, 0.1), rgba(255, 255, 255, 0)"; 
			StyleUtil.setLinearGradient(this, value);
			StyleUtil.setBorderRadius(this, "12px");
		}
	}


	



}
