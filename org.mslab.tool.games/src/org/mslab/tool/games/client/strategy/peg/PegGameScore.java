package org.mslab.tool.games.client.strategy.peg;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.shared.text.MessageFormat;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;

public class PegGameScore extends GridPanel {
	private static final int FONT_SIZE_PX = 50; 
	private int _score, _highScore = 32;
	private HTML _scoreLbl, _highScoreLbl; 
	private GameScoreAnimation _animation;
	
	PegGameScore() {
		_grid.setCellPadding(0);
		_grid.setCellSpacing(0);
		_grid.setHeight((FONT_SIZE_PX + 20) + "px"); 
		
		_scoreLbl = new HTML();
		Style style = _scoreLbl.getElement().getStyle(); 
		style.setFontSize(FONT_SIZE_PX, Unit.PX);
		style.setColor(PegGameTheme.FG_COLOR.toString());
		_grid.setWidget(0, 0, _scoreLbl);
		_grid.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		_animation = new GameScoreAnimation();
		init();
	}
	
	public void init() {
		_score = 32;
		refresh();
	}

	public void decrement() {
		_animation.run(750);
	}
	
	private void refresh() {
		String html = Integer.toString(_score);
		_scoreLbl.setHTML(html);
		
		if (_highScoreLbl != null) {
			html = MessageFormat.format("Le meilleur score est: {0}", new Object[] {_highScore}); 
			_highScoreLbl.setHTML(html);
		}
	}

	public void setHighScoreLabel(HTML highScore) {
		_highScoreLbl = highScore;
		refresh();
	}
	
	private class GameScoreAnimation extends Animation {
		private boolean _decremented; 
		
		protected void onStart() {
			_decremented = false;
		}
		  
		@Override
		protected void onUpdate(double progress) {
			double ratio = (progress < 0.5) ? (progress * 2) : (1.0 - progress) * 2; 
			double fontSize = FONT_SIZE_PX - (ratio * 200); 
			_scoreLbl.getElement().getStyle().setFontSize(fontSize, Unit.PX);
			
			if (! _decremented && progress >= 0.5) {
				decrementScore();
			}
		}
		
		private void decrementScore() {
			_score--;
			_highScore = Math.min(_highScore, _score); 
			_decremented = true;
			refresh();
		}

		@Override
		protected void onComplete() {
		}
		
	}


}
