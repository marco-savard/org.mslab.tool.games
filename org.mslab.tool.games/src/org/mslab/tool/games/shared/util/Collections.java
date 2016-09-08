package org.mslab.tool.games.shared.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Random;

public class Collections {

	public static void shuffle(List elements) {
		List temp = new ArrayList();
		temp.addAll(elements);
		elements.clear();
		
		while (! temp.isEmpty()) {
			int nb = temp.size();
			int idx = Random.nextInt(nb);
			Object element = temp.remove(idx); 
			elements.add(element);
		}
	}

	public static void sort(List elements) {
		java.util.Collections.sort(elements);
	}

}
