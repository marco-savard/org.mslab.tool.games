package org.mslab.tool.games.shared.util;

public class MathUtil {

	public static int compute(int min, int value, int max) {
		value = (value < min) ? min : value;
		value = (value > max) ? max : value;
		return value;
	}

}
