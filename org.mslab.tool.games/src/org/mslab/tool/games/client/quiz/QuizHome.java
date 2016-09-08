package org.mslab.tool.games.client.quiz;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.client.ApplicationContext;
import org.mslab.tool.games.client.core.ui.panels.GridPanel;
import org.mslab.tool.games.client.quiz.abstracts.AbstractQuizPage;
import org.mslab.tool.games.shared.util.MathUtil;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class QuizHome extends GridPanel implements ResizeHandler {
	private List<AbstractQuizPage> _categoryPages = new ArrayList<AbstractQuizPage>(); 
	private QuizShell _quizShell;
	private HTML _title;
	private CategoryIconTray _iconTray; 
	
	public QuizHome(QuizShell quizShell) {
		_quizShell = quizShell;
		_grid.setSize("100%", "100%");
		
		_title = new HTML("Choisissez une cat&eacute;gorie"); 
		_iconTray = new CategoryIconTray(); 
		
		Window.addResizeHandler(this);
		refresh();
	}

	public void addCategory(AbstractQuizPage category) {
		_categoryPages.add(category);
		CategoryIcon icon = new CategoryIcon(category); 
		_iconTray.add(icon);
	}
	
	@Override
	public void onResize(ResizeEvent event) {
		refresh();
	}

	public void refresh() {
		int row = 0;
		_grid.clear();
		
		int w = Window.getClientWidth(); 
		int h = Window.getClientHeight(); 
		boolean landscape = w > h;
		
		int fontSize = computeValue(100, landscape ? (w/3) : (w/2) , 200);
		_title.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
		_iconTray.getElement().getStyle().setMarginTop(12, Unit.PX);
		
		_grid.setWidget(row, 0, _title);
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++; 
		
		_iconTray.refresh();
		//_iconTray.getElement().getStyle().setBackgroundColor("yellow");
		_grid.setWidget(row, 0, _iconTray);
		_grid.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP); 
		_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
		row++; 
				
		String admin = ApplicationContext.getParameters(ApplicationContext.ADMIN_PARAM); 
		if (admin != null) {
			
		}
	}
	
	private int computeValue(int lowerBound, double value, int upperBound) {
		value = (value < lowerBound) ? lowerBound : value;
		value = (value > upperBound) ? upperBound : value;
		return (int)value;
	}

	private static class CategoryIconTray extends GridPanel implements ResizeHandler {
		private List<CategoryIcon> _widgets = new ArrayList<CategoryIcon>();
		
		CategoryIconTray() {
			Window.addResizeHandler(this);
			refresh();
		}

		@Override
		public void add(Widget child) {
			_widgets.add((CategoryIcon)child); 
		}

		@Override
		public void onResize(ResizeEvent event) {
			refresh();
		}
		
		private void refresh() {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			int padding = MathUtil.compute(4, (h/12), 24);
			_grid.setCellPadding(padding);
			
			boolean landscape = w > h;
			int colsPerRow = landscape ? 4 : 2;
			_grid.clear(true);
			_grid.removeAllRows();
			int row = 0, col = 0; 

			for (CategoryIcon icon : _widgets) {
				icon.refresh();
				//icon.getElement().getStyle().setBackgroundColor("orange");
				
				_grid.setWidget(row, col, icon);
				_grid.getFlexCellFormatter().setHorizontalAlignment(row, col, HasHorizontalAlignment.ALIGN_CENTER);
				col++;
				
				if (col >= colsPerRow) {
					col = 0; row++;
				}
			}
		}
	}
	
	private class CategoryIcon extends GridPanel implements MouseOverHandler, MouseOutHandler, ClickHandler {
		private AbstractQuizPage _category; 
		private Image _image;
		private HTML _nameLbl;

		public CategoryIcon(AbstractQuizPage category) {
			_category = category;
			_image = category.getIconImage(); 
			String name = category.getName(); 
			int row = 0;	
			
			_grid.setWidget(row, 0, _image);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++;
			
			_nameLbl = new HTML(name);
			_nameLbl.setWordWrap(false);
			_grid.setWidget(row, 0, _nameLbl);
			_grid.getFlexCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			row++;
			
			addMouseOverHandler(this);
			addMouseOutHandler(this);
			addClickHandler(this);
			refresh();
		}

		public void refresh() {
			if (_image != null) {
				int w = Window.getClientWidth(); 
				int h = Window.getClientHeight(); 
				
				int size = MathUtil.compute(36, computeSize(), 128);
				_image.setPixelSize(size, size);
				setWidth((10 + size) + "px");
				
				int fontSize = MathUtil.compute(90, w/4, 120);
				_nameLbl.getElement().getStyle().setFontSize(fontSize, Unit.PCT);
			}
		}

		private int computeSize() {
			int w = Window.getClientWidth(); 
			int h = Window.getClientHeight(); 
			boolean landscape = w > h;
			int size = landscape ? (int)(w/8.0) : (w/5);
			size = (size > 128) ? 128 : size;
			return size;
		}

		@Override
		public void onClick(ClickEvent event) {
			_category.update();
			_quizShell.display(_category); 
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			getElement().getStyle().setCursor(Cursor.POINTER);
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			getElement().getStyle().setCursor(Cursor.DEFAULT);
		}
	}



}
