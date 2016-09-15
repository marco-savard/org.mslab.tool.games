package org.mslab.tool.games.client.quiz.geo;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.quiz.QuizShell;
import org.mslab.tool.games.client.quiz.abstracts.AbstractQuizPage;
import org.mslab.tool.games.client.quiz.bundles.GeoFactory;
import org.mslab.tool.games.client.quiz.bundles.ImageFactory;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.util.Collections;
import org.mslab.tool.games.shared.util.MathUtil;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;

public class GeoQuizPage extends AbstractQuizPage implements ResizeHandler {
	private Image _iconImage = ImageFactory.getImage(ImageFactory.IMAGE.GEOGRAPHY); 
	private HTML _titleLbl; 
	private ChoicePanel _choicePanel;
	private AnswerPanel _answerPanel;
	private FooterPanel _footerPanel;
	private Location _reference;
	
	public GeoQuizPage(QuizShell quizShell) {
		super(quizShell);
		_grid.setWidth("100%");
		
		_titleLbl = new HTML("Classer les villes selon la proximit&eacute;");
		_titleLbl.getElement().getStyle().setMarginBottom(24, Unit.PX);
		
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
		return "G&eacute;ographie";
	}
	
	@Override
	public void onResize(ResizeEvent event) {
		refresh();
	}
	
	@Override
	public void update() {
		Region region = GeoFactory.getRegion();
		List<Location> locations = region.getLocations(); 
		Collections.shuffle(locations);
		_choicePanel.update(locations); 
		_answerPanel.clearGrid();
		
		_shell.setMenuEnabled(false);
		_footerPanel.setVisible(false);
	}
	
	private void refresh() {
		_choicePanel.refresh();
		_answerPanel.refresh();
		_footerPanel.refresh();
		int w = Window.getClientWidth(); 
		int h = Window.getClientHeight(); 
		boolean landscape = w > h;
		
		int fontSize = landscape ? 
			MathUtil.compute(140, w/4, 250) : 
			MathUtil.compute(140, w/3, 250);
		_titleLbl.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
		
		_grid.clear(true);
		int row = 0;
		int colSpan = landscape ? 2 : 1;
		
		_grid.setWidget(row, 0, _titleLbl);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++;
		
		_grid.setWidget(row, 0, _choicePanel);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++;
		
		_grid.setWidget(row, 0, _answerPanel);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++;
		
		_grid.setWidget(row, 0, _footerPanel);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++;
		
	}
	
	private class ChoicePanel extends GridPanel implements ClickHandler {
		List<GeoButton> _buttons = new ArrayList<GeoButton>();
		
		ChoicePanel() {
			int row = 0;
			_buttons.add(new GeoButton("")); 
			_buttons.add(new GeoButton("")); 
			_buttons.add(new GeoButton("")); 
			_buttons.add(new GeoButton("")); 
			
			for (GeoButton btn : _buttons) {
				btn.setWidth("100%");
				btn.addClickHandler(this);
				_grid.setWidget(row, 0, btn);
				_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
				row++;
			}
			refresh();
		}

		public void refresh() {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			boolean landscape = w > h;
			int fontSize = landscape ? 130 : 150;
			
			for (GeoButton btn : _buttons) {
				btn.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
			}
		}

		public void update(List<Location> locations) {
			int idx = 0;
			_reference = locations.get(idx++);
			String html = MessageFormat.format("Classer les villes selon leur proximit&eacute; de {0}", 
				_reference.getName().toHtml());
			_titleLbl.setHTML(html);
			_choicePanel.setVisible(true);
			
			for (GeoButton btn : _buttons) {
				btn.setLocation(locations.get(idx++));
				btn.setVisible(true);
			}
		}

		@Override
		public void onClick(ClickEvent event) {
			Object src = event.getSource(); 
			
			if (src instanceof GeoButton) {
				GeoButton btn = (GeoButton)src;
				btn.setVisible(false);
				
				Location location = btn.getLocation(); 
				_answerPanel.addAnser(location);
			}
		}

	}
	
	private class AnswerPanel extends GridPanel {
		private List<Location> _locations = new ArrayList<Location>(); 
		private List<HTML> _answers = new ArrayList<HTML>(); 
		private HTML _result = null;
		int _nbAnswer = 0;
		
		AnswerPanel() {
		}

		public void addAnser(Location location) {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			boolean landscape = w > h;
				
			//int fontSize = landscape ? 
			//		MathUtil.compute(110, w/4, 140) : 
			//		MathUtil.compute(110, h/3, 140);
			int fontSize = landscape ? 175 : 300;
			
			_locations.add(location);
			String msg = MessageFormat.format("#{0} : {1}", (_nbAnswer+1), location.getName().toHtml());
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
			int previousDistance = 0;
			boolean valid = true;
			_choicePanel.setVisible(false);
			_answers.clear();
			
			for (Location location : _locations) {
				int distance = (int)location.computeDistanceFrom(_reference); 
				valid &= distance >= previousDistance;
				previousDistance = distance;
						
				String msg = MessageFormat.format("{0} : {1} km", 
					location.getName().toHtml(),
					distance);
				HTML html = new HTML(msg);
				_grid.setWidget(row++, 0, html);
				_answers.add(html);
			}	
			
			_rightAnswers.add(valid);
			_totalAnswers.add(true); 
			
			/*
			String msg = valid ? 
				getGoodAnswers() : 
				"Mauvaise r&eacute;ponse";
			_result = new HTML(msg);
			_grid.setWidget(row, 0, _result);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++;
			
			Image image = SmileyImage.getInstance().getRandomSmiley(valid);
			_grid.setWidget(row, 0, image);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			*/
			
			_footerPanel.update(valid);
			_footerPanel.setVisible(true);
			refresh();
		}
		
		private String getGoodAnswers() {
			String msg; 
			int rightAnswers = countRightAnswers();
			
			if (_totalAnswers.size() == 1) {
				msg = "Bonne r&eacute;ponse";
			} else if (_totalAnswers.size() < 10)  {
				String patt = (rightAnswers == 1) ? 
					"{0} bonne r&eacute;ponse sur {1}" :
					"{0} bonnes r&eacute;ponses sur {1}";
				msg = MessageFormat.format(patt, 
					rightAnswers, 
					_rightAnswers.size());
			} else {
				int percent = (rightAnswers * 100) / _rightAnswers.size();
				msg = MessageFormat.format("{0} % de bonnes r&eacute;ponses", percent);
			}
			
			return msg;
		}

		private int countRightAnswers() {
			int count = 0; 
			
			for (Boolean b : _rightAnswers) {
				count += b ? 1 : 0;
			}
			return count;
		}

		public void refresh() {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			boolean landscape = w > h;
			//int fontSize = landscape ? 
			//		MathUtil.compute(110, w/4, 140) : 
			//		MathUtil.compute(110, h/3, 140);
			int fontSize = landscape ? 175 : 300;
			
			for (HTML html : _answers) {
				html.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
			}

			if (_result != null) {
				fontSize = landscape ? 120 : 150;
				int margin = landscape ? 0 : 20;
				_result.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
				_result.getElement().getStyle().setMarginTop(margin, Unit.PX);
			}
		}

		public void clearGrid() {
			_locations.clear();
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
			_shell.setMenuEnabled(true);
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
