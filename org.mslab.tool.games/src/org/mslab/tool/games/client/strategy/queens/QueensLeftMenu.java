package org.mslab.tool.games.client.strategy.queens;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.game.ui.GameButton;
import org.mslab.tool.games.client.strategy.GameButtonOld;
import org.mslab.tool.games.shared.text.MessageFormat;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class QueensLeftMenu extends GridPanel implements ClickHandler {
	private QueensPage _owner; 
	private GameButton _resetBtn, _previousBtn; 
	
	QueensLeftMenu(QueensPage owner) {
		_owner = owner;
		_grid.setCellSpacing(18);
		int row = 0;
		
		_resetBtn = new GameButton("<i class=\"fa fa-repeat\"></i>", "Recommencer"); 
		_grid.setWidget(row, 0, _resetBtn);
		_resetBtn.addClickHandler(this); 
		row++; 
		
		_previousBtn = new GameButton("", "Pr&eacute;c&eacute;dent"); 
		_grid.setWidget(row, 0, _previousBtn);
		_previousBtn.addClickHandler(this); 
		row++;
		
		_previousBtn.getElement().getStyle().setMarginBottom(60, Unit.PX);
	}

	@Override
	public void onClick(ClickEvent event) {
		Object src = event.getSource(); 
		
		if (src.equals(_resetBtn)) {
			_owner.reset(); 
		} else if (src.equals(_previousBtn)) {
			_owner.changeLevel(-1);
		}
	}

	public void setNbQueensTotal(int nbQueensTotal) {
		String html = MessageFormat.format("<i class=\"fa fa-arrow-left\"></i> {0} Reines", nbQueensTotal-1); 
		_previousBtn.setHTML(html);
		_previousBtn.setEnabled(nbQueensTotal > 4);
	}

}
