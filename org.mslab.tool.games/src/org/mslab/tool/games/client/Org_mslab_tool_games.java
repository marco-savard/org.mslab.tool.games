package org.mslab.tool.games.client;

import org.mslab.tool.games.client.quiz.QuizShell;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is the entry point method.
 * 
 * How to create a splash screen while GWT loads
 *   http://turbomanage.wordpress.com/2009/10/13/how-to-create-a-splash-screen-while-gwt-loads/
 *   
 * How to create a splash (animated) gif
 *   http://www.ajaxload.info/
 */
public class Org_mslab_tool_games implements EntryPoint {
	private RootLayoutPanel _root; 

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Set GWT container invisible
		_root = RootLayoutPanel.get();
		_root.setVisible(false);
		
		//create the context
		ApplicationContext ctx = ApplicationContext.getInstance();
		init(ctx);		
	}
	
	private void init(ApplicationContext ctx) {
		//Load the application (can be long)
		QuizShell shell = new QuizShell();
		//GameShell shell = new GameShell();
		_root.add(shell);
		
		// Hide the splash & set GWT container visible
		DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("splashScreen"));
		_root.setVisible(true);
	}
}
