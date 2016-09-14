package org.mslab.tool.games.client.strategy.peg;

import org.mslab.tool.games.client.core.ui.panels.CommonDisclosurePanel;
import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.core.ui.panels.ResizeGridPanel;
import org.mslab.tool.games.client.core.ui.theme.AbstractTheme;
import org.mslab.tool.games.client.game.ui.GameButton;
import org.mslab.tool.games.client.strategy.GameShell;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PegGameShell extends ScrollPanel {
	private GameShell _owner;
	private PegGamePanel _panel;
	
	public PegGameShell(GameShell owner) {
		_owner = owner;
		_panel = new PegGamePanel(this);
		setWidget(_panel); 
	}
	
	public void goStrategy() {
		_owner.showStrategy();
	}
	
	public void goHome() {
		_owner.showHome();
	}
	
	public void setSelected(PegGameCell cell) {
		_panel.setSelected(cell);
	}
	
	class PegGamePanel extends ResizeGridPanel implements ClickHandler {
		private PegGameShell _owner;
		private NavigationPanel _navigationPanel;
		private SimplePanel _leftMargin, _rightMargin;
		private PegGameScore _score; 
		private GameButton _startBtn; 
		private PegGameBoard _board; 
		private HTML _highScore;
		private CommonDisclosurePanel _instructionPanel;
		private InstructionHTML _instruction;
		
		PegGamePanel(PegGameShell owner) {
			_owner = owner;
			_grid.setSize("100%", "100%");
			AbstractTheme.setTheme(new PegGameTheme());
			
			_leftMargin = new SimplePanel(); 
			_score = new PegGameScore(); 
			_rightMargin = new SimplePanel(); 
			
			_navigationPanel = new NavigationPanel(); 
			_navigationPanel.getElement().getStyle().setMarginLeft(12, Unit.PX);
			_navigationPanel.getElement().getStyle().setMarginTop(12, Unit.PX);
			
			_board = new PegGameBoard(_owner, _score); 

			_startBtn = new GameButton("<i class=\"fa fa-repeat\"></i>", "Recommencer");
			_startBtn.getElement().getStyle().setMarginRight(24, Unit.PX);
			_startBtn.getElement().getStyle().setMarginTop(24, Unit.PX);
			_startBtn.addClickHandler(this); 
			
			_highScore = new HTML();
			_highScore.setWordWrap(false);
			_highScore.getElement().getStyle().setMarginLeft(24, Unit.PX);
			_highScore.getElement().getStyle().setMarginTop(24, Unit.PX);
			_highScore.getElement().getStyle().setColor(PegGameTheme.FG_COLOR.toString());
			_score.setHighScoreLabel(_highScore); 
			
			_instruction = new InstructionHTML();
			_instructionPanel = new GameDisclosurePanel("Instruction", _instruction);
			_instructionPanel.getElement().getStyle().setMarginTop(24, Unit.PX);
		}
		
		@Override
		public void onClick(ClickEvent event) {
			Object src = event.getSource(); 
			
			if (src.equals(_startBtn)) {
				_score.init();
				_board.initBoard();
			} 
		}
		
		protected void refresh() {
			super.refresh();
		}
		
		protected void layoutLandscape() {
			super.layoutLandscape();
			int row = 0;
			
			_grid.setWidget(row, 0, _navigationPanel); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
			_grid.getFlexCellFormatter().setColSpan(row, 0, 3);	
			row++;
			
			_grid.setWidget(row, 0, _leftMargin);
			_grid.getFlexCellFormatter().setWidth(row, 0, "45%");
			
			_grid.setWidget(row, 1, _score); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setColSpan(row, 0, 1);
						
			_grid.setWidget(row, 2, _rightMargin);
			_grid.getFlexCellFormatter().setWidth(row, 2, "45%");
			row++; 
			
			_grid.setWidget(row, 0, _startBtn); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_BOTTOM);
			
			_grid.setWidget(row, 1, _board); 
			_grid.getFlexCellFormatter().setColSpan(row, 0, 1);
			
			_grid.setWidget(row, 2, _highScore); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_LEFT);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 2, HasVerticalAlignment.ALIGN_BOTTOM);
			row++;
			
			_grid.setWidget(row, 1, _instructionPanel); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_TOP);
			row++;
			
			SimplePanel filler = new SimplePanel(); 
			filler.setHeight("99%");
			_grid.setWidget(row, 0, filler);
			row++;
		}

		protected void layoutPortrait() {
			super.layoutPortrait();
			int row = 0;
			
			_grid.setWidget(row, 0, _navigationPanel); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
			//_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_BOTTOM);
			_grid.getFlexCellFormatter().setColSpan(row, 0, 3);
			row++;
			
			_grid.setWidget(row, 1, _score); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setColSpan(row, 1, 1);
			row++;
			
			_grid.setWidget(row, 0, _leftMargin);
			_grid.getFlexCellFormatter().setWidth(row, 0, "45%");
			
			_grid.setWidget(row, 1, _board); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			//_grid.getFlexCellFormatter().setColSpan(row, 1, 2);
						
			_grid.setWidget(row, 2, _rightMargin);
			_grid.getFlexCellFormatter().setWidth(row, 2, "45%");
			row++; 
			
			_grid.setWidget(row, 1, _highScore); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row,1, HasHorizontalAlignment.ALIGN_LEFT);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_BOTTOM);
			row++;
			
			_grid.setWidget(row, 1, _startBtn); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_BOTTOM);
			row++;
			
			_grid.setWidget(row, 1, _instructionPanel); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_TOP);
			//_grid.getFlexCellFormatter().setColSpan(row, 1, 2);
			_grid.getFlexCellFormatter().setHeight(row, 1, "90%");	
		}
		
		public void setSelected(PegGameCell cell) {
			_instruction.setSelected(cell);
		}
		
		private class NavigationPanel extends GridPanel implements ClickHandler {
			private GameButton _homeBtn, _strategyBtn; 
			
			NavigationPanel() {
				_homeBtn = new GameButton("<i class=\"fa fa-home\"></i>", "Accueil");
				_homeBtn.addClickHandler(this); 
				_grid.setWidget(0, 0, _homeBtn);
				
				_strategyBtn = new GameButton("<i class=\"fa fa-arrow-circle-left\"></i>", "Strat&eacute;gie");
				_strategyBtn.getElement().getStyle().setMarginLeft(12, Unit.PX);
				_strategyBtn.addClickHandler(this); 
				_grid.setWidget(0, 1, _strategyBtn);
				_grid.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
				_grid.getFlexCellFormatter().setWidth(0, 1, "99%");
			}

			@Override
			public void onClick(ClickEvent event) {
				Object src = event.getSource(); 
				
				if (src.equals(_homeBtn)) {
					_owner.goHome();
				} else if (src.equals(_strategyBtn)) {
					_owner.goStrategy();
				}
			}
		}
		
		private class GameDisclosurePanel extends CommonDisclosurePanel {
			public GameDisclosurePanel(String name, Widget childWidget) {
				super(name, childWidget);
				setExpanded(true); 
			}
			
			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				super.onOpen(event);
				HTML hideLbl = (HTML)_showHeader.getWidget(0); 
				hideLbl.setHTML("Cacher les instructions"); 
			}
			
			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				super.onClose(event);
				HTML showLbl = (HTML)_showHeader.getWidget(0); 
				showLbl.setHTML("Afficher les instructions"); 
			}
		}
		
		private class InstructionHTML extends HTML {
			private boolean _selected = false; 
			
			InstructionHTML() {
				_selected = false; 
				refreshHTML(); 
			}

			public void setSelected(PegGameCell cell) {
				_selected = (cell != null);
				refreshHTML(); 
			}

			private void refreshHTML() {
				
				String html = (! _selected) ? 
					"S&eacute;lectionner une pi&egrave;ce blanche &agrave; d&eacute;placer; vous pourrez d&eacute;s&eacute;lectionner cette pi&egrave;ce si vous &ecirc;tes bloqu&eacute;" : 
					"S&eacute;lectionner ensuite une case vide de fa&ccedil;on &agrave; sauter par-dessus une pi&egrave;ce &agrave; retirer";
				setHTML(html);
			}
		}
	}






}


