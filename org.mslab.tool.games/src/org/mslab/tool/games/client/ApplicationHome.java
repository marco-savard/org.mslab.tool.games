package org.mslab.tool.games.client;

import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.quiz.bundles.ImageFactory;
import org.mslab.tool.games.shared.util.MathUtil;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;

public class ApplicationHome extends GridPanel {
	private ApplicationShell _owner;
	private ApplicationShellContent _content;

	public ApplicationHome(ApplicationShell owner) {
		_owner = owner;
		_grid.setSize("100%", "100%");
		int row = 0;
		
		_content = new ApplicationShellContent(this); 
		_grid.setWidget(row, 0, _content);
		_grid.getFlexCellFormatter().setHeight(row, 0, "95%");
		_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
		row++;
	}
	
	private class ApplicationShellContent extends GridPanel {

		public ApplicationShellContent(ApplicationHome applicationShell) {
			_grid.setSize("100%", "100%");
			int row = 0;
			
			HTML title = new HTML("<i class=\"fa fa-home fa-2x\" aria-hidden=\"true\"></i> Accueil"); 
			title.getElement().getStyle().setFontSize(200, Unit.PCT);
			title.getElement().getStyle().setMarginBottom(50, Unit.PX);
			_grid.setWidget(row, 0, title); 
			_grid.getFlexCellFormatter().setColSpan(row, 0, 2);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++; 
			
			QuizPageIcon quizPageIcon = new QuizPageIcon(); 
			_grid.setWidget(row, 0, quizPageIcon);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
			
			StrategyPageIcon strategyPageIcon = new StrategyPageIcon(); 
			_grid.setWidget(row, 1, strategyPageIcon);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
		}
	}
	
	private abstract class PageIcon extends GridPanel implements LoadHandler, MouseOverHandler, MouseOutHandler {
		private Image _image;
		
		protected PageIcon(Image image, String titleText) {
			int row = 0;
			getElement().getStyle().setMargin(40, Unit.PX);
			
			_image = image;
			_image.addLoadHandler(this); 
			_grid.setWidget(row, 0, _image);
			row++;
			
			HTML title = new HTML(titleText); 
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
			_grid.setWidget(row, 0, title); 
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++; 
			
			_image.addMouseOverHandler(this); 
			_image.addMouseOutHandler(this);
			refresh();
		}
		
		@Override
		public void onMouseOver(MouseOverEvent event) {
			getElement().getStyle().setCursor(Cursor.POINTER);
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			refresh();
		}

		@Override
		public void onLoad(LoadEvent event) {
			resize();
		}
		
		private void refresh() {
			getElement().getStyle().setCursor(Cursor.DEFAULT);
		}

		private void resize() {
			int w = Window.getClientWidth();
			int h = Window.getClientHeight();
			boolean landscape = w > h;
			
			int imageWidth = _image.getWidth();
			int imageHeight = _image.getHeight();
			
			int maxWidth = landscape ? (w/2) - 360 : w - 60;
			int maxHeight = landscape ? h - 60 : (h/2) - 360;
			maxWidth = MathUtil.compute(150, maxWidth, 500); 
			maxHeight= MathUtil.compute(100, maxHeight, 400); 
			
			double ratio = calculateAspectRatioFit(imageWidth, imageHeight, maxWidth, maxHeight); 
			w = (int)(imageWidth * ratio);
			h = (int)(imageHeight * ratio);
			_image.setPixelSize(w, h);
			
		}
	}
	
	private class QuizPageIcon extends PageIcon implements ClickHandler {
		QuizPageIcon() {
			super(ImageFactory.getImage(ImageFactory.IMAGE.ANIMAL__PANDA_256PX), "Quiz"); 
			addClickHandler(this); 
		}

		@Override
		public void onClick(ClickEvent event) {
			_owner.showQuiz();
		}
	}
	
	private class StrategyPageIcon extends PageIcon implements ClickHandler {
		StrategyPageIcon() {
			super(ImageFactory.getImage(ImageFactory.IMAGE.CHESS), "Strat&eacute;gie"); 
			addClickHandler(this); 
		}
		
		@Override
		public void onClick(ClickEvent event) {
			_owner.showStrategy();
		}
	}
	
	private double calculateAspectRatioFit(double srcWidth, double srcHeight, int maxWidth, int maxHeight) {
	    double ratio = Math.min(maxWidth / srcWidth, maxHeight / srcHeight);
	    return ratio;
	 }
}
