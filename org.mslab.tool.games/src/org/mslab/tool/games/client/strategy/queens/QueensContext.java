package org.mslab.tool.games.client.strategy.queens;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

public class QueensContext {
	
    //singleton
	public static QueensContext getInstance() {
		if (_instance == null) {
			_instance = new QueensContext();
		}
		
		return _instance;
	}
	
	private static QueensContext _instance;
	private QueensContext() {}
	
	private int _highScore = 0;
	public int getHighScore() { return _highScore; }
	public void setHighScore(int score) {
		_highScore = Math.max(_highScore, score);
		fireChangeEvent(_highScore);
	}
	
	private void fireChangeEvent(int highScore) {
		ScoreChangeEvent event = new ScoreChangeEvent(highScore); 
		
		for (ChangeHandler handler : _changeHandlers) {
			handler.onChange(event);
		}
	}
	
	public void addChangeHandler(ChangeHandler handler) {
		_changeHandlers.add(handler);
	}
	
	private List<ChangeHandler> _changeHandlers = new ArrayList<ChangeHandler>(); 
	
	public class ScoreChangeEvent extends ChangeEvent {
		private int _highScore; 
		
		public ScoreChangeEvent(int highScore) {
			_highScore = highScore;
		}	
		
		public int getHighScore() { return _highScore; }
	}
}
