package org.mslab.tool.games.batch;

import java.net.MalformedURLException;
import java.net.URL;

public class FileSystem {

	public static FileSystem getInstance() {
		if (_fileSystem == null) {
			_fileSystem = new FileSystem();
		}
		
		return _fileSystem;
	}
	
	private static FileSystem _fileSystem;
	private FileSystem() {}
	
	public URL getAncestorUrl(URL childUrl, int depth) {
		URL ancestorUrl = childUrl;
		if (depth > 0) {
			String path = childUrl.getPath(); 
			int len = path.length();
			path = path.substring(0, len-1); //remove the ending /
			
			int idx = path.lastIndexOf('/'); 
			path = path.substring(0, idx) + "/";
			URL parentUrl;
			
			try {
				String protocol = childUrl.getProtocol();
				String host = childUrl.getHost();
				parentUrl = new URL(protocol, host, path);
			} catch (MalformedURLException e) {
				parentUrl = null;
			}
			ancestorUrl = getAncestorUrl(parentUrl, depth-1);
		}

		return ancestorUrl;
	}

}
