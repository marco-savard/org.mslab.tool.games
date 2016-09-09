package org.mslab.tool.games.client.quiz;

import java.util.HashMap;
import java.util.Map;

import org.mslab.tool.games.client.ApplicationContext;
import org.mslab.tool.games.client.ApplicationShell;
import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.game.ui.GameButton;
import org.mslab.tool.games.client.quiz.abstracts.AbstractQuizPage;
import org.mslab.tool.games.client.quiz.animal.AnimalQuizPage;
import org.mslab.tool.games.client.quiz.flag.FlagQuizPage;
import org.mslab.tool.games.client.quiz.geo.GeoQuizPage;
import org.mslab.tool.games.client.quiz.history.HistoryQuizPage;
import org.mslab.tool.games.client.quiz.ui.QuizButton;
import org.mslab.tool.games.shared.text.MessageFormat;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;

public class QuizShell extends GridPanel implements ClickHandler {
	private ApplicationShell _shell; 
	private Map<AbstractQuizPage, Integer> _indexes = new HashMap<AbstractQuizPage, Integer>();
	private QuizHome _home; 
	private QuizShellContent _content;
	private GameButton _homeBtn;
	
	public QuizShell(ApplicationShell shell) {
		_shell = shell;
		_grid.setSize("100%", "100%");
		int row = 0;
		
		String admin = ApplicationContext.getInstance().getParameters(ApplicationContext.ADMIN_PARAM); 
		if (admin != null) {
			StatusBar status = new StatusBar();
			_grid.setWidget(row, 0, status);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
			row++;
		}
		
		_homeBtn = new GameButton("<i class=\"fa fa-home\" aria-hidden=\"true\"></i>", "Accueil");
		_homeBtn.getElement().getStyle().setMargin(12, Unit.PX);
		_homeBtn.addClickHandler(this); 
		_grid.setWidget(row, 0, _homeBtn);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
		row++;
		
		_content = new QuizShellContent(this); 
		_grid.setWidget(row, 0, _content);
		_grid.getFlexCellFormatter().setHeight(row, 0, "95%");
		_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++;
		
	}

	public void display(AbstractQuizPage category) {
		int idx = _indexes.get(category); 
		_content.showWidget(idx);
	}

	public void goHome() {
		_home.refresh(); 
		_content.showWidget(0);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		_shell.showHome();
	}
	
	private class QuizShellContent extends DeckPanel {
		private QuizShell _owner;
		
		QuizShellContent(QuizShell owner) {
			_owner = owner;
			
			_home = new QuizHome(_owner); 
			add(_home);
			int idx = 1; 
			
			AbstractQuizPage animal = new AnimalQuizPage(_owner); 
			add(animal);
			_home.addCategory(animal); 
			_indexes.put(animal, idx++); 
			
			AbstractQuizPage flag = new FlagQuizPage(_owner); 
			add(flag);
			_home.addCategory(flag);
			_indexes.put(flag, idx++); 
			
			AbstractQuizPage history = new HistoryQuizPage(_owner); 
			add(history);
			_home.addCategory(history);
			_indexes.put(history, idx++); 
			
			GeoQuizPage geo = new GeoQuizPage(_owner); 
			add(geo);
			_home.addCategory(geo);
			_indexes.put(geo, idx++); 
			
			//QuizScore score = new QuizScore(this); 
			//add(score);
			
			_home.refresh(); 
			showWidget(0);
		}
	}
	
	private class StatusBar extends GridPanel implements ResizeHandler {
		private HTML _statusLbl;
		
		StatusBar() {
			int row = 0;
			_statusLbl = new HTML();
			_grid.setWidget(row, 0, _statusLbl);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_BOTTOM); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
			row++; 
			
			Window.addResizeHandler(this);
			refresh();
		}

		@Override
		public void onResize(ResizeEvent event) {
			refresh();
		}

		private void refresh() {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight();
			boolean landscape = w > h;
			String orientation = landscape ? "Paysage" : "Portrait";
			String status = MessageFormat.format("{0} {1} x {2}", orientation, w, h);
			_statusLbl.setHTML(status);
		}
	}



}
