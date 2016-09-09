package org.mslab.tool.games.client.strategy;

import org.mslab.tool.games.client.core.ui.StyleUtil;
import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.core.ui.theme.AbstractTheme;
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
		AbstractTheme.setTheme(new PegGameTheme());
		_grid.setCellSpacing(24);
		int row = 0, col = 0;
		
		Title title = new Title(); 
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
		_solitaire.addClickHandler(this);
		_grid.setWidget(row, col, _solitaire);
		col++;
		
	     icon = new HTML("&#9813;");
		_queens = new GameTile(icon, "8 reines"); 
		_queens.addClickHandler(this);
		_grid.setWidget(row, col, _queens);
		col++;
		
		SimplePanel right = new SimplePanel(); 
		_grid.setWidget(row, col, right);
		_grid.getFlexCellFormatter().setWidth(row, col, "45%");
		row++;
		
		_home = new GameButton();
		_home.setText("Accueil");
		_home.addClickHandler(this);
		_grid.setWidget(row, 1, _home);
		_grid.getFlexCellFormatter().setColSpan(row, 1, 2);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
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
			HTML icon = new HTML("<i class=\"fa fa-home\"></i>");
			icon.getElement().getStyle().setFontSize(300, Unit.PCT);
			_grid.setWidget(0, 0, icon);
			
			HTML text = new HTML("Strat&eacute;gie");
			text.getElement().getStyle().setFontSize(300, Unit.PCT);
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
	
	class GameTile extends GridPanel implements MouseOverHandler, MouseOutHandler {
		GameTile(Widget icon, String titleText) {
			_grid.setSize("100%", "100%");
			Color color = QueensTheme.BG_COLOR;
			Color brighter = color.blendWith(Color.WHITE, 50);
			Color border = color.darker();
			String gradient = MessageFormat.format("{0}deg, {1}, {2}", new Object[] {
				90, color.toString(), brighter.toString()});
			setSize("110px", "150px");
			
			StyleUtil.setBorderRadius(this, "10px");
			StyleUtil.setLinearGradient(this, gradient);
			getElement().getStyle().setBorderColor(border.toString());
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
			title.getElement().getStyle().setFontSize(200, Unit.PCT);
			_grid.setWidget(row, 0, title);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);;
			row++;
			
			addMouseOverHandler(this);
			addMouseOutHandler(this);
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			getElement().getStyle().setCursor(Cursor.POINTER);
		}
		
		@Override
		public void onMouseOut(MouseOutEvent event) {
			getElement().getStyle().setCursor(Cursor.DEFAULT);
		}


	}
}
