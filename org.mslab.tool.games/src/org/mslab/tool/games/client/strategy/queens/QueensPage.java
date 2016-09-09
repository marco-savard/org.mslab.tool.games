package org.mslab.tool.games.client.strategy.queens;

import org.mslab.tool.games.client.core.ui.panels.ResizeGridPanel;
import org.mslab.tool.games.client.core.ui.theme.AbstractTheme;
import org.mslab.tool.games.client.strategy.GameShell;
import org.mslab.tool.games.shared.text.MessageFormat;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;

public class QueensPage extends ResizeGridPanel {
	private GameShell _owner; 
	private SimplePanel _left, _right;
	private HTML _title;
	private QueensLeftMenu _leftMenu;
	private QueensMainPanel _mainPanel; 
	private QueensRightMenu _rightMenu;
	private int _nbQueensTotal = 4, _nbQueensPlaced = 0;
	
	public QueensPage(GameShell owner) {
		_owner = owner;
		AbstractTheme.setTheme(new QueensTheme());

		_left = new SimplePanel(); 
		_title = new HTML();
		_title.getElement().getStyle().setFontSize(250, Unit.PCT);
		_title.getElement().getStyle().setMarginBottom(30, Unit.PX);
		_right = new SimplePanel(); 
		
		_leftMenu = new QueensLeftMenu(this); 		
		_mainPanel = new QueensMainPanel(this); 
		_rightMenu = new QueensRightMenu(this);
		
		_leftMenu.setNbQueensTotal(_nbQueensTotal); 
		_rightMenu.setNbQueensTotal(_nbQueensTotal); 
	}
	
	public void changeLevel(int inc) {
		_nbQueensTotal += inc;
		
		_leftMenu.setNbQueensTotal(_nbQueensTotal); 
		_rightMenu.setNbQueensTotal(_nbQueensTotal); 
		reset();
		refresh();
	}
	
	protected void refresh() {
		super.refresh();
		
		String title = MessageFormat.format("{0} reines &agrave; placer", _nbQueensTotal); 
		_title.setHTML(title);
		_mainPanel.setNbQueensTotal(_nbQueensTotal);
	}

	public int addQueens() {
		_nbQueensPlaced++;
		int queensRemaining = _nbQueensTotal - _nbQueensPlaced;
		_mainPanel.setNbQueensRemaining(queensRemaining);
		return queensRemaining; 
	}

	public void reset() {
		_mainPanel.reset();
		_mainPanel.setNbQueensTotal(_nbQueensTotal);
		_nbQueensPlaced = 0;
		_mainPanel.setNbQueensRemaining(_nbQueensTotal);
	}

	public void goHome() {
		_owner.showStrategy();
	}
	
	@Override
	protected void layoutLandscape() {
		super.layoutLandscape();
		int row = 0; 
		
		_grid.setWidget(row, 0, _left);
		_grid.getFlexCellFormatter().setWidth(row, 0, "45%");
		
		_grid.setWidget(row, 1, _title);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getFlexCellFormatter().setColSpan(row, 1, 1);

		_grid.setWidget(row, 2, _right);
		_grid.getFlexCellFormatter().setWidth(row, 2, "45%");
		row++; 
		
		_grid.setWidget(row, 0, _leftMenu);
		_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);;

		_grid.setWidget(row, 1, _mainPanel);
		_grid.getFlexCellFormatter().setColSpan(row, 1, 1);
		
		_grid.setWidget(row, 2, _rightMenu);
		_grid.getFlexCellFormatter().setVerticalAlignment(row, 2, HasVerticalAlignment.ALIGN_BOTTOM);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_LEFT);;
		row++; 
	}

	@Override
	protected void layoutPortrait() {
		super.layoutPortrait();
		int row = 0; 
		
		_grid.setWidget(row, 0, _left);
		_grid.getFlexCellFormatter().setWidth(row, 0, "45%");
		
		_grid.setWidget(row, 1, _title);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getFlexCellFormatter().setColSpan(row, 1, 2);

		_grid.setWidget(row, 2, _right);
		_grid.getFlexCellFormatter().setWidth(row, 2, "45%");
		row++; 
		
		_grid.setWidget(row, 1, _mainPanel);
		_grid.getFlexCellFormatter().setColSpan(row, 1, 2);
		row++; 
		
		_grid.setWidget(row, 1, _leftMenu);
		_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);;
		
		_grid.setWidget(row, 2, _rightMenu);
		_grid.getFlexCellFormatter().setVerticalAlignment(row, 2, HasVerticalAlignment.ALIGN_BOTTOM);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_LEFT);;
		row++; 
		
	}






}
