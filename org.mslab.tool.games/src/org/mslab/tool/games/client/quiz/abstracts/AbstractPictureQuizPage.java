package org.mslab.tool.games.client.quiz.abstracts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.quiz.QuizShell;
import org.mslab.tool.games.client.quiz.ui.ChoiceQuizButton;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.text.SafeString;
import org.mslab.tool.games.shared.types.Color;
import org.mslab.tool.games.shared.util.MathUtil;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public abstract class AbstractPictureQuizPage extends AbstractQuizPage implements LoadHandler, ResizeHandler {
	protected QuizShell _shell;
	private Label _titleLbl;
	private Image _image;
	private MainPanel _mainPanel;
	private ChoicePanel _choicePanel;
	private AnswerPanel _answerPanel;
	private ButtonPanel _buttonPanel;
	
	private AbstractQuizQuestion _currentQuestion;
	private SafeString _rightChoice; 
	private int _nbQuestions = 0, nbRightAnswers = 0;
	private int _imageWidth, _imageHeight;
	private boolean _anwsered = false;
	
	protected AbstractPictureQuizPage(QuizShell shell, String title) {
		super(shell);
		
		_grid.setWidth("100%");
		
		_titleLbl = new HTML(title);

		_image = new Image();
		_image.addLoadHandler(this);
		_image.getElement().getStyle().setBorderColor(Color.BLACK.toString());
		_image.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		_image.getElement().getStyle().setBorderWidth(1, Unit.PX);
		_image.getElement().getStyle().setProperty("transition", "all 2s ease-out");
		
		_mainPanel = new MainPanel(this);

		Window.addResizeHandler(this);
		update();
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
		
		int padding = MathUtil.compute(1, (w / 200), 6); 
		int spacing = MathUtil.compute(1, (w / 200), 6); 
		_grid.setCellPadding(padding);
		_grid.setCellSpacing(spacing);
		
		int min = _anwsered ? 110 : 140;
		int fontSize = MathUtil.compute(min, (w / 8), 250); 
		_titleLbl.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
		
		_grid.clear(true);
		int row = 0; 
		
		if (_mainPanel != null) {
			_mainPanel.refresh();
		}
		
		if (landscape) {
			_grid.setWidget(row, 0, _titleLbl);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setColSpan(row, 0, 2);
			row++;
			
			_grid.setWidget(row, 0, _image);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
			
			_grid.setWidget(row, 1, _mainPanel);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_TOP);
			row++;
		} else {
			_grid.setWidget(row, 0, _titleLbl);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setColSpan(row, 0, 1);
			row++;
			
			_grid.setWidget(row, 0, _image);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setRowSpan(row, 0, 1);
			row++;
			
			_grid.setWidget(row, 0, _mainPanel);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
			row++;
		}
		
		if (_imageWidth > 0) {
			int maxWidth, maxHeight; 
			
			if (! _anwsered) {
				maxWidth = landscape ? w - 360 : w - 60;
				maxHeight = landscape ? h - 60 : h - 360;
				maxWidth = MathUtil.compute(150, maxWidth, 500); 
				maxHeight= MathUtil.compute(100, maxHeight, 400); 
			} else {
				maxWidth = landscape ? w - 400 : w - 150;
				maxHeight = landscape ? h - 150 : h - 400;
				maxWidth = MathUtil.compute(100, maxWidth, 500); 
				maxHeight= MathUtil.compute(50, maxHeight, 400); 
			}
			
			double ratio = calculateAspectRatioFit(_imageWidth, _imageHeight, maxWidth, maxHeight); 
			w = (int)(_imageWidth * ratio);
			h = (int)(_imageHeight * ratio);
			_image.setPixelSize(w, h);
		}
	}
	
	@Override
	public void update() {
		_anwsered = false;
		_currentQuestion = getNextQuestion();
		String url = _currentQuestion.getImageUrl();
		List<SafeString> choices = _currentQuestion.getChoices();
		_image.setUrl(url);
		_rightChoice = choices.get(0);
		_choicePanel.setChoices(choices);
		_answerPanel.setVisible(false);
		_buttonPanel.setVisible(false);	
	}
	
	protected abstract AbstractQuizQuestion getNextQuestion();

	@Override
	public void onLoad(LoadEvent event) {
		_imageWidth = _image.getWidth();
		_imageHeight = _image.getHeight();
		refresh();
	}
	
	

	private double calculateAspectRatioFit(double srcWidth, double srcHeight, int maxWidth, int maxHeight) {
	    double ratio = Math.min(maxWidth / srcWidth, maxHeight / srcHeight);
	    return ratio;
	 }
	    
    private class MainPanel extends GridPanel {
    	MainPanel(AbstractPictureQuizPage owner) {
    		_choicePanel = new ChoicePanel(owner); 
    		_answerPanel = new AnswerPanel(); 		
    		_buttonPanel = new ButtonPanel(); 
    		
    		int row = 0;
    		_grid.setWidget(row, 0, _choicePanel);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++;
			
			_grid.setWidget(row, 0, _answerPanel);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++;
			
			_grid.setWidget(row, 0, _buttonPanel);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++;
    	}

		public void refresh() {
			_choicePanel.refresh();
			_answerPanel.refresh();
			_buttonPanel.refresh();
		}
    }
	
	private class ChoicePanel extends GridPanel implements ClickHandler {
		private AbstractPictureQuizPage _owner;
    	private List<ChoiceQuizButton> _choices = new ArrayList<ChoiceQuizButton>();
    	
    	ChoicePanel(AbstractPictureQuizPage owner) {
    		_owner = owner;
    		int row = 0, col = 0;
    		ChoiceQuizButton btn;
    		
    		btn = new ChoiceQuizButton();
    		btn.setWidth("100%");
    		btn.addClickHandler(this);
    		_grid.setWidget(row, col, btn);
    		_choices.add(btn);
    		row++;
    		
    		btn = new ChoiceQuizButton();
    		btn.setWidth("100%");
    		btn.addClickHandler(this);
    		_grid.setWidget(row, col, btn);
    		_choices.add(btn);
    		row = 0; col++;
    		
    		btn = new ChoiceQuizButton(); 
    		btn.setWidth("100%");
    		btn.addClickHandler(this);
    		_grid.setWidget(row, col, btn);
    		_choices.add(btn);
    		row++;
    		
    		btn = new ChoiceQuizButton();
    		btn.setWidth("100%");
    		btn.addClickHandler(this);
    		_grid.setWidget(row, col, btn);
    		_choices.add(btn);
    		row = 0; col++;
    		refresh();
    	}

		public void refresh() {
			int w = Window.getClientWidth();
			int h = Window.getClientHeight();
			boolean landscade = w > h;
			int padding = landscade ? MathUtil.compute(0, w/300, 4) : MathUtil.compute(0, w/150, 8);
			int spacing = landscade ? MathUtil.compute(1, w/300, 4) : MathUtil.compute(1, w/150, 8);
    		_grid.setCellPadding(padding);
    		_grid.setCellSpacing(spacing);
    		//_owner.refresh();
		}

		public void setChoices(List<SafeString> choices) {
			Collections.sort(choices);
			int idx = 0; 
			
			for (ChoiceQuizButton btn : _choices) {
				btn.setLabelAndPicture(choices.get(idx++));
				btn.setEnabled(true);
				btn.getElement().getStyle().setFontSize(100, Unit.PCT);
			}
		}

		@Override
		public void onClick(ClickEvent event) {
			Object src = event.getSource(); 
			if (src instanceof ChoiceQuizButton) {
				ChoiceQuizButton btn = (ChoiceQuizButton)src;
				disableAll();
				btn.setEnabled(true);
				btn.getElement().getStyle().setFontSize(100, Unit.PCT);
				verify(btn);
			}
			
			_playAgainBtn.setEnabled(true);
		}

		private void verify(ChoiceQuizButton btn) {
			String pictureName = btn.getPictureName();
			boolean isRight = pictureName.equals(_rightChoice.toString()); 
			 _nbQuestions++;
			 nbRightAnswers += isRight ? 1 : 0;

			_answerPanel.setAnswer(isRight, _rightChoice);
			_answerPanel.setVisible(true);
			_buttonPanel.setVisible(true);	
			_anwsered = true; 
			//refresh();
			_owner.refresh();
		}

		private void disableAll() {
			int w = Window.getClientWidth();
			int h = Window.getClientHeight();
			boolean landscade = w > h;
			int fontSize = MathUtil.compute(30, (w/12), 60);
			int padding = MathUtil.compute(0, (w/300), 4);
			_grid.setCellPadding(padding);
			
			for (ChoiceQuizButton btn : _choices) {
				btn.setEnabled(false);
				btn.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
			}
		}
    }
	
	private class AnswerPanel extends GridPanel implements ResizeHandler {
		private HTML _answer;
		private SimplePanel _smileyPanel;
		private Image _smileyImage;
		
		AnswerPanel() {
			int row = 0;
			
			_answer = new HTML();
			_grid.setWidget(row, 0, _answer);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++;
			
			_smileyPanel = new SimplePanel();
			_grid.setWidget(row, 0, _smileyPanel);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			
			Window.addResizeHandler(this);
			refresh();
		}
		
		@Override
		public void onResize(ResizeEvent event) {
			refresh();
		}

		public void refresh() {
			int w = Window.getClientWidth();
			int h = Window.getClientHeight();
			boolean landscade = w > h;
			int fontSize = landscade ? 110 : 140;
			_answer.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
			
			if (_smileyImage != null) {
				int len = Math.min(w, h);
				int ps = MathUtil.compute(30, len/10, 60); 
				_smileyImage.setPixelSize(ps, ps);
			}
			
		}

		public void setAnswer(boolean isRight, SafeString rightChoice) {
			String html = isRight ? 
				getSuccessMessage() : 
				MessageFormat.format("Mauvaise r&eacute;ponse, c'&eacute;tait: {0}", rightChoice.toHtml());
			_answer.setHTML(html);
			
			_smileyImage = SmileyImage.getInstance().getRandomSmiley(isRight);
			_smileyPanel.setWidget(_smileyImage);
		}

		private String getSuccessMessage() {
			String msg; 
			
			if (_nbQuestions == 1) {
				msg = "Bonne r&eacute;ponse";
			} else if (_nbQuestions <= 10) {
				 msg = MessageFormat.format("{0} bonnes r&eacute;ponses sur {1}", nbRightAnswers, _nbQuestions);
			} else {
				int percent = (nbRightAnswers * 100) / _nbQuestions;
				 msg = MessageFormat.format("{0} % de bonnes r&eacute;ponses", percent);
			}

			return msg;
		}


	}

 }
