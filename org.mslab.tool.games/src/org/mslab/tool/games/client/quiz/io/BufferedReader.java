package org.mslab.tool.games.client.quiz.io;

public class BufferedReader {
	private static final String EOL = "\r\n";
	private String _text;
	private int _startIdx = 0; 
	
	public BufferedReader(String text) {
		_text = text;
	}

	public String getLine() {
		int end = _text.indexOf(EOL, _startIdx);
		String line = null; 
		
		if (end != -1) {
			line = _text.substring(_startIdx, end); 
			_startIdx = end + EOL.length();
		}
		return line;
	}

}
