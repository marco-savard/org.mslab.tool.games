package org.mslab.tool.games.client.strategy.queens;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.strategy.GameButton;
import org.mslab.tool.games.shared.text.MessageFormat;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;

public class QueensRightMenu extends GridPanel implements ClickHandler, ChangeHandler {
	private QueensPage _owner; 
	private GameButton _nextBtn;
	private HTML _highScoreLbl;
	private int _nbQueensTotal; 

    QueensRightMenu(QueensPage owner) {
    	_owner = owner;
    	int row = 0;
    	
    	_nextBtn = new GameButton(); 
		_grid.setWidget(row, 0, _nextBtn);
		_nextBtn.addClickHandler(this); 
		_nextBtn.getElement().getStyle().setMarginBottom(12, Unit.PX);
		_nextBtn.getElement().getStyle().setMarginLeft(12, Unit.PX);
		 row++; 
		 
		_highScoreLbl = new HTML();
		_grid.setWidget(row, 0, _highScoreLbl);
		_highScoreLbl.getElement().getStyle().setMarginBottom(60, Unit.PX);
		_highScoreLbl.getElement().getStyle().setMarginLeft(12, Unit.PX);
		row++;
		
		QueensContext.getInstance().addChangeHandler(this);
		refresh(); 
	}

	@Override
	public void onClick(ClickEvent event) {
		Object src = event.getSource(); 
		
		if (src.equals(_nextBtn)) {
			_owner.changeLevel(1);
		}
	}

	public void setNbQueensTotal(int nbQueensTotal) {
		_nbQueensTotal = nbQueensTotal;
		refresh();
	}

	@Override
	public void onChange(ChangeEvent event) {
		if (event instanceof QueensContext.ScoreChangeEvent) {
			refresh();
		}
	}
	
	private void refresh() {
		QueensContext ctx = QueensContext.getInstance();
		int highScore = ctx.getHighScore(); 
		boolean enabled = highScore >= _nbQueensTotal;
		String icon = enabled ? "fa-arrow-right" : "fa-lock";
		String html = MessageFormat.format("{0} Reines <i class=\"fa {1}\"></i>", 
			_nbQueensTotal+1, icon); 
		_nextBtn.setHTML(html);
		_nextBtn.setEnabled(enabled);
		
		 html = (highScore == 0) ? 
			"Pas de meilleur score" : 
			MessageFormat.format("Meilleur score: {0} reines", highScore); 
		_highScoreLbl.setHTML(html);
	}
	
	
}
