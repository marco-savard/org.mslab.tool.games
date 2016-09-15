package org.mslab.tool.games.client.strategy.peg;

import org.mslab.tool.games.client.core.ui.StyleUtil;
import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.types.Color;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;

public class PegGameCell extends AbstractGameCell implements MouseOverHandler, MouseOutHandler, ClickHandler {
	public enum State {BLOCKED, MOVEABLE, SELECTED, EMPTY}; 
	private State _state;
	private Color _hoverColor; 
	private GameCellContent _cellContent;
	
	public PegGameCell(PegGameBoard board, int col, int row, State state) {
		super(board, col, row); 
		_state = state;
		_hoverColor = Color.BLUE_CADET.blendWith(Color.WHITE); 
		
		StyleUtil.setBorderRadius(this, "50%");
		Style style = getElement().getStyle(); 
		style.setBorderColor(PegGameTheme.FG_COLOR.toString());
		style.setBorderWidth(1, Unit.PX);
		style.setBorderStyle(BorderStyle.SOLID);
		style.setZIndex(1);
		
		String shadow = MessageFormat.format("6px 6px 3px {0}", PegGameTheme.FG_COLOR.toString());
		StyleUtil.setBoxShadow(this, shadow);
		
		addMouseOverHandler(this); 
		addMouseOutHandler(this); 
		addClickHandler(this);
		
		_cellContent = new GameCellContent();
		add(_cellContent);
		
		refresh();
	}

	public void refresh() {
		
		Color bgColor; 
		
		if (_state == State.EMPTY) {
			bgColor = Color.GREY_SLATE;
		} else if (_state == State.SELECTED) {
			bgColor = Color.BLUE_CADET;
		} else {
			bgColor = Color.WHITE;
		}
		
		Style style = getElement().getStyle(); 
		style.setBackgroundColor(bgColor.toString());
		style.setCursor(Cursor.DEFAULT);
	}
	
	public GameCellContent getCellContent() {
		return _cellContent; 
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		PegGameCell selected = _board.getSelectedCell();
		if (selected == null) {
			Style style = getElement().getStyle(); 
			style.setCursor(Cursor.POINTER);
		}
		
		/*
		GameBoardOld.State state = _board.getState();
		Style style = getElement().getStyle(); 
		Color bgColor; 
		
		if (state == GameBoardOld.State.SELECT_SRC) { 
			if (_state != State.EMPTY) {
				bgColor = _hoverColor; 
				style.setBackgroundColor(bgColor.toString());
				style.setCursor(Cursor.POINTER);
			}
		} else {
			if (_state == State.EMPTY) {
				bgColor = _hoverColor; 
				style.setBackgroundColor(bgColor.toString());
				style.setCursor(Cursor.POINTER);
			}
		}*/
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		refresh();
	}

	@Override
	public void onClick(ClickEvent event) {
		PegGameCell selected = _board.getSelectedCell();
		
		if (selected == null) {
			setSelected(true);
			_board.setSelected(this); 
		} else {
			if (selected.equals(this)) {
				setSelected(false);
				_board.setSelected(null); 
			}
		}
		
		/*
		GameBoardOld.State state = _board.getState();
		
		if (state == GameBoardOld.State.SELECT_SRC) {
			selectSource();
			_board.setState(GameBoardOld.State.SELECT_TARGET); 
		} else {
			selectTarget();
			_board.setState(GameBoardOld.State.SELECT_SRC); 
		}
		*/
	}
	
	public void setSelected(boolean selected) {
		_cellContent.setSelected(selected); 
	}
	
	public void setToBeDeleted(boolean toBeDeleted) {
		_cellContent.setToBeDeleted(toBeDeleted);
	}
	
	public void setDeleted(boolean deleted) {
		_cellContent.setDeleted(deleted);
	}

	@Override
	public String toString() {
		String text = MessageFormat.format("{0},{1}", new Object[] {_col, _row});
		return text;
	}

	private void selectSource() {
		//_state = State.SELECTED;
		//_board.setSelection(this);
		refresh(); 
	}
	
	private void selectTarget() {
		//GameCell srcCell = _board.getSelectedCell(); 
		
		//GameCellAnimation animation = new GameCellAnimation(srcCell); 
		//animation.run(4000);
		
		
		
		//_state = State.BLOCKED;
		//_board.setTarget(this);
		refresh(); 
	}

	public void setState(State state) {
		_state = state;
	}
	
	@Override
	public boolean equals(Object that) {
		boolean equal = (that instanceof PegGameCell);
		if (equal) {
			PegGameCell gc = (PegGameCell)that;
			equal &= gc._row == _row;
			equal &= gc._col == _col;
		}
		
		return equal;
	}
	
	@Override
	public int hashCode() {
		return _row * 7 + _col;
	}
	
	public void setPosition(int row, int col) {
		_row = row;
		_col = col;
	}
	
	//
	// inner class
	// 
	class GameCellContent extends GridPanel {
		private HTML _html;
		
		GameCellContent() {
			setHeight("100%"); 
			_grid.setWidth("100%");
			_grid.setHeight("100%");
			//_grid.getElement().getStyle().setBackgroundColor(Color.GREEN.toString());
			
			_html = new HTML(); 
			_grid.setWidget(0, 0, _html);
			_grid.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		}

		public void setSelected(boolean selected) {
			String html = selected ? "<i class=\"fa fa-check fa-3x\"></i>" : "";
			_html.setHTML(html);
			_html.getElement().getStyle().setColor(PegGameTheme.CHECK_COLOR.toString());
		}
		
		public void setToBeDeleted(boolean toBeDeleted) {
			Color paleDelete = PegGameTheme.DELETE_COLOR.blendWith(Color.WHITE); 
			String html = toBeDeleted ? "<i class=\"fa fa-times fa-2x\"></i>" : "";
			_html.setHTML(html);
			_html.getElement().getStyle().setColor(paleDelete.toString());
		}
		
		public void setDeleted(boolean deleted) {
			String html = deleted ? "<i class=\"fa fa-times fa-3x\"></i>" : "";
			_html.setHTML(html);
			_html.getElement().getStyle().setColor(PegGameTheme.DELETE_COLOR.toString());
		}
		
		public HTML getHTML() {
			return _html;
		}
	}












	



}
