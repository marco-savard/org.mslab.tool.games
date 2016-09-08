package org.mslab.tool.games.shared.text;

public class SafeString implements Comparable<SafeString> {
	private String _value;

	public SafeString(String value) {
		_value = value;
	}

	public String toHtml() {
		int idx = _value.lastIndexOf('.');
		String html = (idx == -1) ? _value : _value.substring(0, idx);
		html = html.replace("--", "~");
		
		html = html.replace("a-0", "&agrave;");
		html = html.replace("a-2", "&acirc;");
		html = html.replace("a-3", "&aacute;");
		html = html.replace("a-",  "&agrave;");
		
		html = html.replace("c-",  "&ccedil;");
		html = html.replace("n-",  "&ntilde;");
		
		html = html.replace("e-0", "&egrave;");
		html = html.replace("e-2", "&ecirc;");
		html = html.replace("e-",  "&eacute;");
		
		html = html.replace("i-1", "&icirc;");
		html = html.replace("i-2", "&icirc;");
		html = html.replace("i-3", "&iuml;");
		
		html = html.replace("o-1", "&ocirc;");
		html = html.replace("o-2", "&ocirc;");
		html = html.replace("o_2", "&ocirc;");
		html = html.replace("o-3", "&ouml;");
		
		html = html.replace("u-3", "&uacute;");
		
		html = html.replace("~", "-");
		html = html.replace("_", " ");
		html = Character.toUpperCase(html.charAt(0)) + html.substring(1);
	
		return html;
	}
	
	public String toString() {
		return _value;
	}

	@Override
	public int compareTo(SafeString that) {
		return _value.compareTo(that._value); 
	}

}
