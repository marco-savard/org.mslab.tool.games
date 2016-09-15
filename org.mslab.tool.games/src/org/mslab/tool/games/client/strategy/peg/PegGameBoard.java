package org.mslab.tool.games.client.strategy.peg;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.client.core.ui.StyleUtil;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class PegGameBoard extends AbsolutePanel implements ResizeHandler {
    static final int MARGIN = 5;
    
    private PegGameShell _shell; 
    private PegGameScore _score;
	private List<PegGameCell> _cells = new ArrayList<PegGameCell>(); 
	private List<PegGameSlot> _slots = new ArrayList<PegGameSlot>();
    
	public PegGameBoard(PegGameShell shell, PegGameScore score) {
		_shell = shell;
		_score = score;
		
		Style style = getElement().getStyle();
		style.setBorderColor(PegGameTheme.FG_COLOR.toString());
		style.setBorderStyle(BorderStyle.SOLID);
		setGradientBackgroud(PegGameTheme.FG_COLOR); 
		style.setProperty("borderWidth", "1px 2px 2px 1px");
		StyleUtil.setBorderRadius(this, "25px");
		String shadow = MessageFormat.format("10px 10px 5px {0}", PegGameTheme.FG_COLOR.toString());
		StyleUtil.setBoxShadow(this, shadow);
		
		Window.addResizeHandler(this); 
		initBoard();
		refresh();
	}
	
	@Override
	public void onResize(ResizeEvent event) {
		refresh();
	}
	
	private void setGradientBackgroud(Color primaryColor) {
		Color brighterColor = primaryColor.brighter(3.0); 
		Color topColor = brighterColor.blendWith(Color.WHITE, 70); 
		Color bottomColor = brighterColor;
		String gradient = MessageFormat.format("{0}deg, {1}, {2}", new Object[] {
				90, bottomColor.toString(), topColor.toString()});
		StyleUtil.setLinearGradient(this, gradient);
	}

	void initBoard() {
		clear();
		_cells.clear();
		
		for (int row = 0; row < 7; row++) {
			initRow(row); 
		}
	}

	private void initRow(int row) {
		for (int col = 0; col < 7; col++) {
			boolean hasSlot = true; 
			hasSlot &= (row >= 2) || (col >= 2);
			hasSlot &= (row >= 2) || (col <= 4);
			hasSlot &= (row <= 4) || (col >= 2);
			hasSlot &= (row <= 4) || (col <= 4);
			
			boolean hasCell = hasSlot; 
			hasCell &= (row != 3) || (col != 3);
			
			if (hasSlot) {
				PegGameSlot slot = new PegGameSlot(this, col, row);
				add(slot);
				//add(slot, slot.getX(), slot.getY());
				_slots.add(slot);
			}

			if (hasCell) {
				PegGameCell cell = new PegGameCell(this, col, row, PegGameCell.State.BLOCKED);
				add(cell);
				//add(cell, cell.getX(), cell.getY());
				_cells.add(cell);
			}
		}
	}
	
	public void removeCell(PegGameCell cell) {
		_cells.remove(cell);
	}
	
	private void refresh() {
		int w = Window.getClientHeight(); 
		int h = Window.getClientHeight(); 
		boolean landscape = h > w; 
		
		int boardSize = landscape ? (int)(w * 0.3) : (int)(w * 0.6);
		//int boardSize = (PegGameCell.SIZE + 2 * MARGIN) * 7;
		setWidth(boardSize + "px"); 
		setHeight(boardSize + "px"); 
		
		int cellSize = (boardSize / 7) - (2 * MARGIN); 
		for (PegGameCell cell : _cells) {
			cell.setSize(cellSize);
			setWidgetPosition(cell, cell.getX(), cell.getY());
		}
		for (PegGameSlot slot : _slots) {
			slot.setSize(cellSize);
			setWidgetPosition(slot, slot.getX(), slot.getY());
		}
	}

	private PegGameCell _selectedCell = null;
	public PegGameCell getSelectedCell() { return _selectedCell; }
	public void setSelected(PegGameCell cell) {
		_selectedCell = cell; 
		_shell.setSelected(cell);
	}

	public void move(PegGameCell source, PegGameSlot target) {
		_score.decrement();
		PegGameCellAnimation animation = new PegGameCellAnimation(this, source, target); 
		animation.run(750);
	}

	public PegGameCell getMidCell(PegGameCell source, PegGameSlot target) {
		
		int r0 = source.getRow(), r1 = target.getRow(); 
		int c0 = source.getCol(), c1 = target.getCol();
		boolean hasMidCell = (Math.abs(r0 - r1) == 2) && (Math.abs(c0 - c1) == 0);
		hasMidCell |= (Math.abs(r0 - r1) == 0) && (Math.abs(c0 - c1) == 2);
		PegGameCell midCell = null;
		
		if (hasMidCell) {
			int row = (r0 + r1) / 2;
			int col = (c0 + c1) / 2;
			midCell = findCellAt(row, col);
		}
		
		return midCell;
	}

	private PegGameCell findCellAt(int row, int col) {
		PegGameCell foundCell = null;
		for (PegGameCell cell : _cells) {
			if ((cell.getRow() == row) && (cell.getCol() == col)) {
				foundCell = cell;
				break;
			}
		}
		
		return foundCell;
	}






}
