package org.mslab.tool.games.client.strategy.queens;


import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.strategy.queens.QueensContext.ScoreChangeEvent;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class QueensStatus extends DeckPanel implements ChangeHandler {
	private QueensRemainingPanel _queensRemainingPanel;
	private GridPanel _failPanel, _successPanel;
	
	QueensStatus() {
		_queensRemainingPanel = new QueensRemainingPanel(); 
		_failPanel = new FailPanel(); 
		_successPanel = new SuccessPanel(); 
		
		add(_queensRemainingPanel);
		add(_failPanel);
		add(_successPanel);
		showWidget(0);
		
		QueensContext.getInstance().addChangeHandler(this); 
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		if (event instanceof QueensContext.ScoreChangeEvent) {
			ScoreChangeEvent scoreChangeEvent = (ScoreChangeEvent)event;
			int highScore = scoreChangeEvent.getHighScore(); 
			showWidget(2);
		}
		
	}
	
	public void setNbQueens(int size) {
		showWidget(0);
		_queensRemainingPanel.setNbQueens(size); 
	}

	public void setNbQueensRemaining(int remaining) {
		_queensRemainingPanel.setNbQueens(remaining); 
	}
	
	public void setFailureStatus() {
		showWidget(1);
	}
	
	/*
	public void setSuccessStatus() {
		showWidget(2);
	}
	*/
	
	class QueensRemainingPanel extends GridPanel {
		private int _nbQueens; 
		private HTML _title;
		
		QueensRemainingPanel() {
			_title = new HTML("Reines &agrave; placer:"); 
			_title.setWordWrap(false);
			_title.getElement().getStyle().setFontSize(150, Unit.PCT);
			_title.getElement().getStyle().setMarginRight(24, Unit.PX);
		}
		
		public void setNbQueens(int nbQueens) {
			_nbQueens = nbQueens;
			_grid.clear(true);
			
			_grid.setWidget(0, 0, _title);
			
			for (int i=0; i<nbQueens; i++) {
				HTML html = new HTML("&#9813;");
				html.getElement().getStyle().setFontSize(300, Unit.PCT);
				_grid.setWidget(0, 1+i, html);
				_grid.getFlexCellFormatter().setWidth(0, 1+i, "5%"); 
			}
			
			SimplePanel filler = new SimplePanel(); 
			_grid.setWidget(0, 1+nbQueens, filler);
			_grid.getFlexCellFormatter().setWidth(0, 1+nbQueens, "80%"); 
		}
		
		public void setNbQueensRemaining(int nbQueens) {
			String title = (nbQueens == 0) ? 
				"Niveau r&eacute;ussi" : 
				"Reines &agrave; placer:"; 
			_title.setHTML(title);
			
			for (int i=0; i<_nbQueens; i++) {
				Widget w = _grid.getWidget(0, 1+i); 
				HTML queen = (HTML)w;
				boolean visible = (i < nbQueens); 
				queen.setVisible(visible);
			}
		}
	}
	
	class FailPanel extends GridPanel {
		FailPanel() {
			HTML icon = new HTML("<i class=\"fa fa-thumbs-down\"></i>"); 
			icon.getElement().getStyle().setFontSize(300, Unit.PCT);
			icon.getElement().getStyle().setColor(Color.RED_DARK.toString());
			_grid.setWidget(0, 0, icon);
			
			HTML title = new HTML("Vous n'avez pas r&eacute;ussi &agrave; placer toutes les reines");
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
			_grid.setWidget(0, 1, title);
		}
	}
	
	class SuccessPanel extends GridPanel {
		SuccessPanel() {
			SmilleyIcon smilley = new SmilleyIcon(); 
			_grid.setWidget(0, 0, smilley);
			
			HTML title = new HTML("Vous avez r&eacute;ussi &agrave; placer toutes les reines");
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
			_grid.setWidget(0, 1, title);
		}
	}
	
	class SmilleyIcon extends AbsolutePanel {
		SmilleyIcon() {
			HTML bg = new HTML("<i class=\"fa fa-circle\"></i>"); 
			bg.getElement().getStyle().setColor(Color.YELLOW.toString());
			bg.getElement().getStyle().setFontSize(300, Unit.PCT);
			add(bg);
			
			HTML fg = new HTML("<i class=\"fa fa-smile-o\"></i>"); 
			fg.getElement().getStyle().setColor(Color.BLACK.toString());
			fg.getElement().getStyle().setFontSize(300, Unit.PCT);
			add(fg, 0, 0);
		}
	}





	
}

/*
public class QueensStatus extends GridPanel {
	
	
	

	

	public void levelFailed() {
		_title.setHTML("Niveau ");
	}
	
}
*/
