package org.mslab.tool.games.client.strategy.peg;

import org.mslab.tool.games.client.core.ui.StyleUtil;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class PegGameSlot extends AbstractGameCell implements MouseOverHandler, MouseOutHandler, ClickHandler {
	
	public PegGameSlot(PegGameBoard board, int col, int row) {
		super(board, col, row); 
		
		StyleUtil.setBorderRadius(this, "50%");
		Style style = getElement().getStyle(); 
		style.setBackgroundColor(PegGameTheme.FG_COLOR.toString());
		style.setZIndex(0);
		
		Color darkBG = PegGameTheme.FG_COLOR.blendWith(Color.BLACK);
		String gradient = MessageFormat.format("{0}deg, {1}, {2}", new Object[] {
			180, PegGameTheme.FG_COLOR.toString(), darkBG});
		StyleUtil.setLinearGradient(this, gradient);
		
	    addMouseOverHandler(this);
	    addMouseOutHandler(this);
		addClickHandler(this);
		refresh();
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		PegGameCell selected = _board.getSelectedCell();
		if (selected != null) {
			PegGameCell midCell = _board.getMidCell(selected, this);
			if (midCell != null) {
				midCell.setToBeDeleted(true);
				Style style = getElement().getStyle(); 
				style.setCursor(Cursor.POINTER);
			}
		}
	}
	
	@Override
	public void onMouseOut(MouseOutEvent event) {
		PegGameCell selected = _board.getSelectedCell();
		if (selected != null) {
			PegGameCell midCell = _board.getMidCell(selected, this);
			if (midCell != null) {
				midCell.setToBeDeleted(false);
			}
		}
		
		refresh();
	}

	private void refresh() {
		Style style = getElement().getStyle(); 
		style.setCursor(Cursor.DEFAULT);
	}

	@Override
	public void onClick(ClickEvent event) {
		PegGameCell selected = _board.getSelectedCell();
		if (selected != null) {
			PegGameCell midCell = _board.getMidCell(selected, this);
			if (midCell != null) {
				_board.move(selected, this); 
			}
		}
	}




}
