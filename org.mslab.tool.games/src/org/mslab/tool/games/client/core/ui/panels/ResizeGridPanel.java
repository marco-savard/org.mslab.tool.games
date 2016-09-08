package org.mslab.tool.games.client.core.ui.panels;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public abstract class ResizeGridPanel extends GridPanel implements ResizeHandler {
	private static final int RESIZE_DELAY = 300; //300 ms 
	private ResizeTimer _resizeTimer;
	
	protected ResizeGridPanel() {
		_resizeTimer = new ResizeTimer();
		_resizeTimer.schedule(RESIZE_DELAY);
		Window.addResizeHandler(this);
	}
	
	@Override
	public void onResize(ResizeEvent event) {
		_resizeTimer.cancel();
		_resizeTimer.schedule(300);
	}

	class ResizeTimer extends Timer {
		@Override
		public void run() {
			refresh(); 
		}
	}

	protected void refresh() {
		int width = Window.getClientWidth(); 
		int height = Window.getClientHeight(); 
		
		if (width > height) {
			layoutLandscape(); 
		} else {
			layoutPortrait(); 
		}
	}

	protected void layoutPortrait() {
		clearGrid(); 
	}
	

	protected void layoutLandscape() {
		clearGrid();
	}
	
	private void clearGrid() {
		_grid.clear(true);
		
		int rows = _grid.getRowCount(); 
		for (int i=0; i<rows; i++) {
			int cols = _grid.getCellCount(i); 
			for (int j=0; j<cols; j++) {
				_grid.getFlexCellFormatter().setColSpan(i, j, 1);
			}
		}
	} //end clearGrid()

}
