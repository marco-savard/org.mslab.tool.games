package org.mslab.tool.games.client.quiz.bundles;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.ClientBundle.Source;

public interface ChronoBundle extends ClientBundle {
	
    @Source("resources/chrono/astronomie.txt")
    TextResource astronomie();
    
    @Source("resources/chrono/chimie.txt")
    TextResource chimie();
    
    @Source("resources/chrono/decouvertes.txt")
    TextResource decouvertes();
    
    @Source("resources/chrono/fondations.txt")
    TextResource fondations();
    
    @Source("resources/chrono/inventions.txt")
    TextResource inventions();
    
    @Source("resources/chrono/medecine.txt")
    TextResource medecine();
    
    @Source("resources/chrono/quebec.txt")
    TextResource quebec();

}
