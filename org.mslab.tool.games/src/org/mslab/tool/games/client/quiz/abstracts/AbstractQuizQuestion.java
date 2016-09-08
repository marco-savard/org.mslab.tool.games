package org.mslab.tool.games.client.quiz.abstracts;

import java.util.ArrayList;
import java.util.List;

import org.mslab.tool.games.client.quiz.bundles.PictureRepository;
import org.mslab.tool.games.shared.text.MessageFormat;
import org.mslab.tool.games.shared.text.SafeString;
import org.mslab.tool.games.shared.util.Collections;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;

public class AbstractQuizQuestion {
	private String _category;
	private String _chosenGroup; 
	private List<SafeString> _chosenPictures; 

	public AbstractQuizQuestion(String category) {
		_category = category;
		PictureRepository repository = PictureRepository.getInstance();
		List<String> groups = repository.getGroups(category); 
		int idx = Random.nextInt(groups.size());
		_chosenGroup = groups.get(idx);
		
		_chosenPictures = repository.getPictures(_chosenGroup); 
		Collections.shuffle(_chosenPictures);
	}

	public String getImageUrl() {
		SafeString choice = _chosenPictures.get(0);
		
		String url = MessageFormat.format("{0}images/{1}/{2}/{3}",
				GWT.getModuleBaseURL(), 
				_category,
				_chosenGroup, 
				choice);

		return url;
	}

	public List<SafeString> getChoices() {
		List<SafeString> choices = new ArrayList<SafeString>();
		choices.add(_chosenPictures.get(0));
		choices.add(_chosenPictures.get(1));
		choices.add(_chosenPictures.get(2));
		choices.add(_chosenPictures.get(3));

		return choices;
	}

}
