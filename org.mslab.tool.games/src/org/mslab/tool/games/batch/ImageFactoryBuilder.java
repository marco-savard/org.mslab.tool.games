package org.mslab.tool.games.batch;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.text.MessageFormat;

/**
 * Run this program each time images are added or deleted. 
 * This will generate resource bundle.
 * 
 * @author Marco
 *
 */
public class ImageFactoryBuilder {

	public static void main(String[] args) {
		ImageFactoryBuilder builder = new ImageFactoryBuilder(); 
		builder.build(); 
	}

	private void build() {
		//scan the original images
		FileSystem fileSystem = FileSystem.getInstance();
		Class claz = ImageFactoryBuilder.class; 
		URL classUrl = claz.getResource(".");
		int depth = 8;
		URL projectUrl = fileSystem.getAncestorUrl(classUrl, depth);
		String root = projectUrl.getPath().substring(1);
		String path = MessageFormat.format("{0}src/{1}", root, "org/mslab/tool/games/public/images/"); 
		File imageFolder = new File(path); 
		File[] folders = imageFolder.listFiles();
		
		//generate resource file
		String filename = MessageFormat.format("{0}src/{1}", root, "org/mslab/tool/games/client/quiz/bundles/PictureRepositoryResources.java"); 
		//FileWriter fw = new FileWriter(filename); 
		//PrintWriter pw = new PrintWriter(fw); 
		
		try {
			for (File folder : folders) {
				buildFolder(folder);
			}
			
			String msg = MessageFormat.format("Success", ""); 
			System.out.println(msg); 
		} catch (Exception ex) {
			System.out.println(ex.toString()); 
		}
		
		
		
		
		
		
		/*

		
		
		
		String path = "D:/Users/Marco_Home/My Workspaces/GitHubs/mslab-workspaces/org.mslab.games/src/org/mslab/games/public/images/";
		File imageFolder = new File(path); 
		
		try {
			File generatedFile = new File("D:/Users/Marco_Home/My Workspaces/GitHubs/mslab-workspaces/org.mslab.games/src/org/mslab/games/client/tool/quiz/bundles/PictureRepositoryExample.java"); 
			
			//printHeader(pw); 
			//scanCategories(pw, imageFolder); 
			//printFooter(pw); 
		} catch (Exception ex) {
			
		}
*/
	}

	private void buildFolder(File imageFolder) {
		String msg = MessageFormat.format("  ..scanning {0}", imageFolder.getPath()); 
		System.out.println(msg); 
		
		File[] files = imageFolder.listFiles();
		
		for (File file : files) {
			//buildImageEntry(pw, file); 
		}
	}

	private void printHeader(PrintWriter pw) {
		pw.println("package org.mslab.games.client.tool.quiz.bundles;");
		pw.println();
		pw.println("//Genrated by ImageFactoryBuilder");
		pw.println("public class PictureRepositoryExample {");
		pw.println("  public static final String[][] PICTURES = new String[][] {");	
	}

	private void scanCategories(PrintWriter pw, File imageFolder) {
		File[] files = imageFolder.listFiles();
		
		for (File file : files) {
			scanGroups(pw, file); 
		}
	}

	private void scanGroups(PrintWriter pw, File categoryFolder) {
		File[] files = categoryFolder.listFiles();
		
		if (files != null) {
			for (File file : files) {
				scanImages(pw, file); 
			}
		}
	}

	private void scanImages(PrintWriter pw, File groupFolder) {
		File[] files = groupFolder.listFiles();
		
		if (files != null) {
			for (File file : files) {
				scanImage(pw, file); 
			}
			pw.flush();
		}
	}

	private void scanImage(PrintWriter pw, File file) {
		String path = file.getPath();
		int idx = path.lastIndexOf("\\");
		String filename = path.substring(idx+1); 
		
		path = path.substring(0,  idx); 
		idx = path.lastIndexOf("\\");
		String group = path.substring(idx+1); 
		
		path = path.substring(0,  idx); 
		idx = path.lastIndexOf("\\");
		String category = path.substring(idx+1); 
		
		String msg = MessageFormat.format(PATT, category, group, filename); 
		pw.println(msg);
	}
	private static final String PATT = "    new String[] {\"{0}\", \"{1}\", \"{2}\"},"; 

	private void printFooter(PrintWriter pw) {
		pw.println("  };");
		pw.println("}");
	}
}
