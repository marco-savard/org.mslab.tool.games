package org.mslab.tool.games.client.strategy.peg;

import com.google.gwt.user.client.ui.FocusPanel;

public class AbstractGameCell  extends FocusPanel  {
	//public static final int SIZE = 40;
	protected PegGameBoard _board; 
	protected int _col, _row; 
	private int _size;
	
	protected AbstractGameCell(PegGameBoard board, int col, int row) {
		_board = board;
		_col = col;
		_row = row;
	}
	
	public int getRow() { return _row; }
	public int getCol() { return _col; }
	
	public void setSize(int cellSize) {
		_size = cellSize;
		setSize(cellSize + "px", cellSize + "px");
	}
	
	public int getX() {
		int x = _col * (_size + 2 * PegGameBoard.MARGIN) + PegGameBoard.MARGIN; 
		return x;
	}
	
	public int getY() {
		int y = _row * (_size + 2 * PegGameBoard.MARGIN) + PegGameBoard.MARGIN; 
		return y;
	}
}
