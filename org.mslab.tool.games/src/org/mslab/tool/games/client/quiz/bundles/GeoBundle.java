package org.mslab.tool.games.client.quiz.bundles;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.ClientBundle.Source;

public interface GeoBundle extends ClientBundle {
	
    @Source("resources/geo/canada.txt")
    TextResource canada();
    
    @Source("resources/geo/europe.txt")
    TextResource europe();
    
    @Source("resources/geo/quebec.txt")
    TextResource quebec();
    
    @Source("resources/geo/usa.txt")
    TextResource usa();
    
  

}
