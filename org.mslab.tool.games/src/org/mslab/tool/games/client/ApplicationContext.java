package org.mslab.tool.games.client;

import com.google.gwt.user.client.Window;

public class ApplicationContext {
	public static final String ADMIN_PARAM = "admin";

    private static ApplicationContext _instance;
	
	public static ApplicationContext getInstance() {
		if (_instance == null) {
			_instance = new ApplicationContext(); 
		}
		return _instance;
	}
	
	public static String getParameters(String parameterName) {
		return Window.Location.getParameter(parameterName);
	}
}
