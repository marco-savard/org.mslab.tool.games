package org.mslab.tool.games.client.strategy.queens;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.strategy.GameButton;
import org.mslab.tool.games.shared.text.MessageFormat;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class QueensLeftMenu extends GridPanel implements ClickHandler {
	private QueensPage _owner; 
	private GameButton _resetBtn, _previousBtn, _homeBtn; 
	
	QueensLeftMenu(QueensPage owner) {
		_owner = owner;
		_grid.setCellSpacing(18);
		int row = 0;
		
		_resetBtn = new GameButton(); 
		String html = "<i class=\"fa fa-repeat\"></i> Recommencer"; 
		_resetBtn.setHTML(html);
		_grid.setWidget(row, 0, _resetBtn);
		_resetBtn.addClickHandler(this); 
		row++; 
		
		_previousBtn = new GameButton(); 
		_grid.setWidget(row, 0, _previousBtn);
		_previousBtn.addClickHandler(this); 
		row++;
		
		_homeBtn = new GameButton(); 
		 html = "<i class=\"fa fa-home\"></i> Strat&eacute;gie"; 
		 _homeBtn.setHTML(html);
		_grid.setWidget(row, 0, _homeBtn);
		_homeBtn.addClickHandler(this); 
		
		_homeBtn.getElement().getStyle().setMarginBottom(60, Unit.PX);
	}

	@Override
	public void onClick(ClickEvent event) {
		Object src = event.getSource(); 
		
		if (src.equals(_resetBtn)) {
			_owner.reset(); 
		} else if (src.equals(_previousBtn)) {
			_owner.changeLevel(-1);
		} else if (src.equals(_homeBtn)) {
			goHome();
		}
	}

	private void goHome() {
		_owner.goHome();
	}

	public void setNbQueensTotal(int nbQueensTotal) {
		String html = MessageFormat.format("<i class=\"fa fa-arrow-left\"></i> {0} Reines", nbQueensTotal-1); 
		_previousBtn.setHTML(html);
		_previousBtn.setEnabled(nbQueensTotal > 4);
	}

}
