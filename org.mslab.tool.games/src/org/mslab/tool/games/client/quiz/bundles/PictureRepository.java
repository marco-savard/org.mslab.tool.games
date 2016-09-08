package org.mslab.tool.games.client.quiz.bundles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mslab.tool.games.shared.text.SafeString;

public class PictureRepository {

	public static PictureRepository getInstance() {
		if (_repository == null) {
			_repository = new PictureRepository(); 
		}
		return _repository;
	}
	private static PictureRepository _repository;
	private PictureRepository() {
		for (String[] triplet : PictureRepositoryExample.PICTURES) {
			String category = triplet[0]; 
			String group = triplet[1]; 
			SafeString picture = new SafeString(triplet[2]);
			
			if (! _categories.containsKey(category)) {
				List<String> groups = new ArrayList<String>(); 
				_categories.put(category, groups); 
			}
			
			List<String> groups = _categories.get(category); 
			if (! groups.contains(group)) {
				groups.add(group);
			}
			
			if (! _pictures.containsKey(group)) {
				List<SafeString> pictures = new ArrayList<SafeString>(); 
				_pictures.put(group, pictures); 
			}
			
			List<SafeString> pictures = _pictures.get(group); 
			if (! pictures.contains(picture)) {
				pictures.add(picture);
			}
		}
	}
	
	private Map<String, List<String>> _categories = new HashMap<String, List<String>>(); 
	private Map<String, List<SafeString>> _pictures = new HashMap<String, List<SafeString>>(); 
	
	public List<String> getGroups(String category) {
		List<String> groups = _categories.get(category); 
		return groups;
	}

	public List<SafeString> getPictures(String group) {
		List<SafeString> pictures = _pictures.get(group); 
		return pictures;
	}

}
