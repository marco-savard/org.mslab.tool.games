package org.mslab.tool.games.client.strategy;

import org.mslab.tool.games.client.ApplicationContext;
import org.mslab.tool.games.client.core.ui.StyleUtil;
import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.core.ui.theme.AbstractTheme;
import org.mslab.tool.games.client.core.ui.theme.ThemeChangeEvent;
import org.mslab.tool.games.client.core.ui.theme.ThemeChangeHandler;
import org.mslab.tool.games.client.game.ui.GameButton;
import org.mslab.tool.games.client.strategy.peg.PegGameTheme;
import org.mslab.tool.games.client.strategy.queens.QueensTheme;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GameHome extends GridPanel implements ClickHandler {
	private GameShell _owner; 
	private GameTile _queens, _solitaire; 
	private GameButton _home; 
	
	public GameHome(GameShell owner) {
		_owner = owner; 
		int row = 0, col = 0;
		
		_home = new GameButton("<i class=\"fa fa-home\" aria-hidden=\"true\"></i>","Accueil");
		_home.getElement().getStyle().setMargin(12, Unit.PX);
		_home.addClickHandler(this);
		_grid.setWidget(row, 0, _home);
		_grid.getFlexCellFormatter().setColSpan(row, 0, 4);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
		row++;
		
		Title title = new Title(); 
		title.getElement().getStyle().setMarginBottom(12, Unit.PX);
		_grid.setWidget(row, 1, title); 
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getFlexCellFormatter().setColSpan(row, 1, 2);
		row++; 
		
		SimplePanel left = new SimplePanel(); 
		_grid.setWidget(row, col, left);
		_grid.getFlexCellFormatter().setWidth(row, col, "45%");
		col++; 
		
		Widget icon = new SolitaireIcon(); 
		_solitaire = new GameTile(icon, "Solitaire"); 
		_solitaire.getElement().getStyle().setMargin(12, Unit.PX);
		_solitaire.addClickHandler(this);
		_grid.setWidget(row, col, _solitaire);
		col++;
		
	     icon = new HTML("&#9813;");
		_queens = new GameTile(icon, "8 reines"); 
		_queens.getElement().getStyle().setMargin(12, Unit.PX);
		_queens.addClickHandler(this);
		_grid.setWidget(row, col, _queens);
		col++;
		
		SimplePanel right = new SimplePanel(); 
		_grid.setWidget(row, col, right);
		_grid.getFlexCellFormatter().setWidth(row, col, "45%");
		row++;
		
		SimplePanel filler = new SimplePanel(); 
		_grid.setWidget(row, 0, filler);
		_grid.getFlexCellFormatter().setHeight(row, 0, "95%");
	}
	
	@Override
	public void onClick(ClickEvent event) {
		Object src = event.getSource(); 
		
		if (src.equals(_queens)) {
			_owner.showQueen();
		} else if (src.equals(_solitaire)) {
			_owner.showSolitaire();
		} else if (src.equals(_home)) {
			_owner.showHome();
		}
		
	}
	
	class Title extends GridPanel {
		Title() {
			HTML icon = new HTML("");
			icon.getElement().getStyle().setFontSize(500, Unit.PCT);
			_grid.setWidget(0, 0, icon);
			
			HTML text = new HTML("Strat&eacute;gie");
			text.getElement().getStyle().setFontSize(500, Unit.PCT);
			_grid.setWidget(0, 1, text);
		}
	}
	
	class SolitaireIcon extends GridPanel {
		SolitaireIcon() {
			int size = 200;
			_grid.setCellPadding(0);
			_grid.setCellSpacing(0);
			getElement().getStyle().setMarginTop(18, Unit.PX);
			
			HTML html = new HTML("<i class=\"fa fa-circle\"></i>");
			html.getElement().getStyle().setFontSize(size, Unit.PCT);
			html.getElement().getStyle().setMarginRight(6, Unit.PX);
			_grid.setWidget(0, 0, html);
			
			html = new HTML("<i class=\"fa fa-circle-o\"></i>");
			html.getElement().getStyle().setFontSize(size, Unit.PCT);
			_grid.setWidget(0, 1, html);
			
			html = new HTML("<i class=\"fa fa-circle-o\"></i>");
			html.getElement().getStyle().setFontSize(size, Unit.PCT);
			html.getElement().getStyle().setMarginRight(6, Unit.PX);
			_grid.setWidget(1, 0, html);
			
			html = new HTML("<i class=\"fa fa-circle\"></i>");
			html.getElement().getStyle().setFontSize(size, Unit.PCT);
			_grid.setWidget(1, 1, html);
		}
	}
	
	class GameTile extends GridPanel implements MouseOverHandler, MouseOutHandler, ThemeChangeHandler {
		GameTile(Widget icon, String titleText) {
			_grid.setSize("100%", "100%");
			//Color color = QueensTheme.BG_COLOR;
			setSize("110px", "150px");
			
			StyleUtil.setBorderRadius(this, "10px");
			getElement().getStyle().setBorderWidth(2, Unit.PX);
			getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			String shadow = MessageFormat.format("10px 10px 5px {0}", PegGameTheme.FG_COLOR.toString());
			StyleUtil.setBoxShadow(this, shadow);
			
			int row = 0;
			icon.getElement().getStyle().setFontSize(500, Unit.PCT);
			_grid.setWidget(row, 0, icon);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);;
			row++; 
			
			HTML title = new HTML(titleText);
			title.setWordWrap(false);
			title.getElement().getStyle().setFontSize(300, Unit.PCT);
			_grid.setWidget(row, 0, title);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);;
			row++;
			
			addMouseOverHandler(this);
			addMouseOutHandler(this);
			AbstractTheme.getTheme().addThemeChangeHandler(this);
			refresh();
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			getElement().getStyle().setCursor(Cursor.POINTER);
		}
		
		@Override
		public void onMouseOut(MouseOutEvent event) {
			getElement().getStyle().setCursor(Cursor.DEFAULT);
		}

		@Override
		public void onThemeChange(ThemeChangeEvent event) {
			refresh();
		}
		
		private void refresh() {
			Color primaryColor = AbstractTheme.getTheme().getPrimaryColor(); 
			Color top = primaryColor.blendWith(Color.WHITE, 90);
			Color bottom = primaryColor.blendWith(Color.WHITE, 70);
			Color border = primaryColor.darker();
			String gradient = MessageFormat.format("{0}deg, {1}, {2}", new Object[] {
				270, top.toString(), bottom.toString()});
			StyleUtil.setLinearGradient(this, gradient);
			getElement().getStyle().setBorderColor(border.toString());
		}


	}
}
