package org.mslab.tool.games.client.strategy.peg;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;

public class PegGameCellAnimation extends Animation {
	private PegGameBoard _board; 
	private PegGameCell _source; 
	private PegGameSlot _target; 
	private int _x0, _x1, _y0, _y1;
	private PegGameCell _midCell; 
	
	public PegGameCellAnimation(PegGameBoard board, PegGameCell source, PegGameSlot target) {
		_board = board;
		_source = source;
		_target = target;
		source.getElement().getStyle().setZIndex(2);
		_midCell = board.getMidCell(source, target);
		
		_x0 = source.getX();
		_x1 = target.getX();
		_y0 = source.getY(); 
		_y1 = target.getY(); 
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if (_midCell != null) {
			_midCell.setDeleted(true);
		}
	}
	
	@Override
	protected void onUpdate(double progress) {
		int x = _x0 + (int)(progress * (_x1-_x0)); 
		int y = _y0 + (int)(progress * (_y1-_y0)); 
		_board.setWidgetPosition(_source, x, y);
	}
	
	@Override
	protected void onComplete() {
		_source.setPosition(_target.getRow(), _target.getCol());
		_source.setSelected(false);
		_source.getElement().getStyle().setZIndex(1);
		
		if (_midCell != null) {
			_midCell.getElement().getStyle().setZIndex(-1);
			_midCell.setVisible(false);
			_board.removeCell(_midCell);
		}

		_board.setSelected(null);
	    super.onComplete();
	}
	

}
