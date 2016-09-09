package org.mslab.tool.games.client.quiz.abstracts;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.game.ui.GameButton;
import org.mslab.tool.games.client.quiz.QuizShell;
import org.mslab.tool.games.client.quiz.bundles.ImageFactory;
import org.mslab.tool.games.client.quiz.ui.QuizButton;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.util.MathUtil;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;

public abstract class AbstractQuizPage extends GridPanel {
	protected QuizShell _shell; 
	protected GameButton _homeButton, _playAgainBtn;
	protected List<Boolean> _rightAnswers = new ArrayList<Boolean>();
	protected List<Boolean> _totalAnswers = new ArrayList<Boolean>();
	
	protected AbstractQuizPage(QuizShell shell) {
		_shell = shell;
	}

	public abstract Image getIconImage();
	
	public abstract String getName();
	
	public abstract void update();
	
	protected void goHome() {
		_shell.goHome();
	}
	
	protected void playAgain() {
		update();
	}
	
	protected class ButtonPanel extends GridPanel implements ClickHandler {
    	public ButtonPanel() {
    		int col = 0;
    		
    		_homeButton = new GameButton("<i class=\"fa fa-arrow-circle-left\" aria-hidden=\"true\"></i>", "Retour page principale");
    		_homeButton.addClickHandler(this); 
    		_grid.setWidget(0, col, _homeButton);
    		col++;
    		
    		_playAgainBtn = new GameButton("<i class=\"fa fa-repeat\" aria-hidden=\"true\"></i>", "Jouer encore"); 
    		_playAgainBtn.addClickHandler(this); 
    		_grid.setWidget(0, col, _playAgainBtn);
    		refresh();
    	}

		public void refresh() {
			int w = Window.getClientWidth();
			int h = Window.getClientHeight();
			boolean landscade = w > h;
			
			int margin = MathUtil.compute(0, h/200, 4);
    		getElement().getStyle().setMarginTop(margin, Unit.PX);
			
			int fontSize = landscade ? 
				MathUtil.compute(90, w/4, 110) : 
				MathUtil.compute(90, h/3, 130);
			
			_homeButton.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
			_playAgainBtn.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
		}

		@Override
		public void onClick(ClickEvent event) {
			Object src = event.getSource(); 
			
			if (_playAgainBtn.equals(src)) {
				playAgain();
			} else if (_homeButton.equals(src)) {
				goHome(); 
			}
		}
    }
	
	protected class SmileyPanel extends GridPanel implements ResizeHandler {
		private HTML _result = null;
		private Image _image;
		
		public SmileyPanel() {
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
			boolean landscape = w > h;
			
			if (_image != null) {
				int len = Math.min(w, h);
				int ps = MathUtil.compute(20, len/8, 90); 
				_image.setPixelSize(ps, ps);
			}
			
			if (_result != null) {
				int fontSize = landscape ? 120 : 150;
				int margin = landscape ? 0 : 20;
				
				if (Math.max(w, h) < 500) {
					fontSize -= 15;
				}
				
				_result.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
				_result.getElement().getStyle().setMarginTop(margin, Unit.PX);
			}
		}

		public void update(boolean valid) {
			_grid.clear(true);
			int row = 0; 
			
			String msg = valid ? 
					getGoodAnswers() : 
					"Mauvaise r&eacute;ponse";
			_result = new HTML(msg);
			_grid.setWidget(row, 0, _result);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++;
			
			_image = SmileyImage.getInstance().getRandomSmiley(valid);
			_grid.setWidget(row, 0, _image);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
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


	}
	
	protected static class SmileyImage {
    	List<Image> _happyImages = new ArrayList<Image>(); 
    	List<Image> _sadImages = new ArrayList<Image>(); 
    	
		public static SmileyImage getInstance() {
			if (_instance == null) {
				_instance = new SmileyImage();
			}
			
			return _instance;
		}
		private static SmileyImage _instance;
		
		SmileyImage() {
			_happyImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_HAPPY_01));
			_happyImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_HAPPY_02));
			_happyImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_HAPPY_03));
			_happyImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_HAPPY_04));
			_happyImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_HAPPY_05));
			_happyImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_HAPPY_06));
			
			_sadImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_SAD_01));
			_sadImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_SAD_02));
			_sadImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_SAD_03));
			_sadImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_SAD_04));
			_sadImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_SAD_05));
			_sadImages.add(ImageFactory.getImage(ImageFactory.IMAGE.SMILEY_SAD_06));
			
			for (Image image : _happyImages) {
				image.setPixelSize(60, 60);
			}
			
			for (Image image : _sadImages) {
				image.setPixelSize(60, 60);
			}
		}

		public Image getRandomSmiley(boolean isRight) {
			List<Image> images = isRight ? _happyImages : _sadImages;
			int idx = Random.nextInt(images.size()); 
			Image image = images.get(idx);
			return image;
		}
    }

}
