package org.mslab.tool.games.client.quiz.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.quiz.QuizShell;
import org.mslab.tool.games.client.quiz.abstracts.AbstractQuizPage;
import org.mslab.tool.games.client.quiz.bundles.ChronoFactory;
import org.mslab.tool.games.client.quiz.bundles.ImageFactory;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.util.Collections;
import org.mslab.tool.games.shared.util.MathUtil;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;

public class HistoryQuizPage extends AbstractQuizPage implements ResizeHandler {
	private Image _iconImage = ImageFactory.getImage(ImageFactory.IMAGE.HISTORY); 
	private HTML _titleLbl; 
	private ChoicePanel _choicePanel;
	private AnswerPanel _answerPanel;
	private FooterPanel _footerPanel;
	
	public HistoryQuizPage(QuizShell quizShell) {
		super(quizShell);
		
		_grid.setWidth("100%");
		
		_titleLbl = new HTML("Classer par ordre chronologique");
		
		_choicePanel = new ChoicePanel();
		_answerPanel = new AnswerPanel();
		_footerPanel = new FooterPanel();
		Window.addResizeHandler(this);
		
		update();
		refresh();
	}

	@Override
	public Image getIconImage() {
		return _iconImage;
	}

	@Override
	public String getName() {
		return "Histoire";
	}
	
	@Override
	public void onResize(ResizeEvent event) {
		refresh();
	}
	
	@Override
	public void update() {
		HistoryTimeline timeline = ChronoFactory.getChronologie(); 
		List<HistoryEvent> events = timeline.getEvents();
		Collections.shuffle(events);
		_choicePanel.update(events); 
		_answerPanel.clearGrid();
		_footerPanel.setVisible(false);
	}
	
	private void refresh() {
		int w = Window.getClientWidth(); 
		int h = Window.getClientHeight(); 
		boolean landscape = w > h;
		
		int fontSize = landscape ? 
			MathUtil.compute(140, w/4, 250) : 
			MathUtil.compute(140, w/3, 250);
		_titleLbl.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
		
		int row = 0;
		_grid.clear(true);
		
		_grid.setWidget(row, 0, _titleLbl);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++; 
		
		_choicePanel.refresh();
		_grid.setWidget(row, 0, _choicePanel);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++; 

		_answerPanel.refresh();
		_grid.setWidget(row, 0, _answerPanel);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++; 
		
		_footerPanel.refresh();
		_grid.setWidget(row, 0, _footerPanel);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++;
	
	}
	
	private class ChoicePanel extends GridPanel implements ClickHandler {
		List<HistoryButton> _buttons = new ArrayList<HistoryButton>();
		
		ChoicePanel() {
			_grid.setCellPadding(0);
			_grid.setCellSpacing(0);
			
			for (int i=0; i<4; i++) {
				addButton();
			}
		}
		
		public void addButton() {
			HistoryButton btn = new HistoryButton();
			btn.addClickHandler(this);
			_buttons.add(btn);
		}
		
		public void update(List<HistoryEvent> events) {
			int idx = 0;
			_choicePanel.setVisible(true);
			
			for (HistoryButton btn : _buttons) {
				btn.setHistoryEvent(events.get(idx++));
				btn.setWidth("80%");
				btn.setVisible(true);
			}
			refresh();
		}

		public void refresh() {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			boolean landscape = w > h;
			int fontSize = landscape ? 
				MathUtil.compute(95, (w/5), 120) : 
				MathUtil.compute(95, (w/3), 110);
			int margin = landscape ? 
				6 : 
				MathUtil.compute(3, (h/100), 12);
			
			_grid.clear(true);
			int row = 0; 
			
			for (HistoryButton btn : _buttons) {
				Style style = btn.getElement().getStyle();
				style.setFontSize(fontSize, Unit.PCT);
				style.setMargin(margin, Unit.PX);
				style.setMarginLeft(24, Unit.PX);
				style.setMarginRight(24, Unit.PX);
				_grid.setWidget(row, 0, btn);
				_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
				row++;
			}
		}

		@Override
		public void onClick(ClickEvent event) {
			Object src = event.getSource(); 
			
			if (src instanceof HistoryButton) {
				HistoryButton btn = (HistoryButton)src;
				btn.setVisible(false);
				
				HistoryEvent he = btn.getHistoryEvent(); 
				_answerPanel.addAnser(he);
			}
		}
	}
		
	private class AnswerPanel extends GridPanel {
		private List<HistoryEvent> events = new ArrayList<HistoryEvent>(); 
		private List<HTML> _answers = new ArrayList<HTML>(); 
		int _nbAnswer = 0;
		
		AnswerPanel() {
		}

		public void addAnser(HistoryEvent he) {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			boolean landscape = w > h;
			int fontSize = landscape ? 130 : 140;
			
			events.add(he);
			String msg = MessageFormat.format("#{0} : {1}", (_nbAnswer+1), he.getHtml());
			HTML html = new HTML(msg);
			html.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
			_grid.setWidget(_nbAnswer, 0, html);
			_answers.add(html);
			_nbAnswer++;
			
			if (_nbAnswer == 4) {
				showAnswers();
			}
		}

		private void showAnswers() {
			int row = 0;
			int previousYear = 0;
			boolean valid = true;
			_choicePanel.setVisible(false);
			_answers.clear();
			
			for (HistoryEvent he : events) {
				int year = he.getYear();
				valid &= year >= previousYear;
				previousYear = year;
						
				String msg = MessageFormat.format("{0} : {1}", year, he.getHtml());
				HTML html = new HTML(msg);
				_grid.setWidget(row++, 0, html);
				_answers.add(html);
			}	
			
			_rightAnswers.add(valid);
			_totalAnswers.add(true); 
			_footerPanel.update(valid);

			refresh();
		}
		
		public void refresh() {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			boolean landscape = w > h;
			int fontSize = landscape ? 
				MathUtil.compute(110, w/5, 140) : 
				MathUtil.compute(100, h/3, 140);

			for (HTML html : _answers) {
				html.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
			}
			
			_footerPanel.refresh();
		}

		public void clearGrid() {
			events.clear();
			_nbAnswer = 0;
			_grid.clear(true);
		}
	}
	
	private class FooterPanel extends GridPanel {
		private SmileyPanel _smileyPanel;
		private ButtonPanel _buttonPanel;
		
		FooterPanel() {		
			_smileyPanel = new SmileyPanel();
			_buttonPanel = new ButtonPanel();
			refresh();
		}

		public void update(boolean valid) {
			_smileyPanel.update(valid);
			setVisible(true);
		}

		public void refresh() { 
			_smileyPanel.refresh();
			_buttonPanel.refresh();
			_grid.clear(true);
			
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			boolean landscape = w > h;
			
			int row = 0, col = 0;
			_grid.setWidget(row, col, _smileyPanel);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, col, HasHorizontalAlignment.ALIGN_CENTER);
			
			row = landscape ? 0 : 1;
			col = landscape ? 1 : 0;
			
			_grid.setWidget(row, col, _buttonPanel);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, col, HasHorizontalAlignment.ALIGN_CENTER);
		}
	}


	




}
