package org.mslab.tool.games.client.quiz.bundles; 

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.user.client.ui.Image;

//
// Generated on 16-May-2013 1:04:21 PM
//

public class ImageFactory {

    public enum IMAGE {
		ANIMAL__PANDA_256PX,
		CHESS,
		FLAG_RED,
		GEOGRAPHY,
		HISTORY,
		SMILEY_HAPPY_01,
		SMILEY_HAPPY_02,
		SMILEY_HAPPY_03,
		SMILEY_HAPPY_04,
		SMILEY_HAPPY_05,
		SMILEY_HAPPY_06,
		SMILEY_SAD_01,
		SMILEY_SAD_02,
		SMILEY_SAD_03,
		SMILEY_SAD_04,
		SMILEY_SAD_05,
		SMILEY_SAD_06,
	}
	
	public static Image getImage(IMAGE literal) {
		DataResource res = findResource(literal);
		Image image = new Image(res.getSafeUri());
		return image;
	}
	
	private static DataResource findResource(IMAGE image) {
		DataResource res;
		ImageBundle bundle = getApplicationImages();

		switch (image) {
		
		    case ANIMAL__PANDA_256PX:
		        res = bundle.animal_Panda_256px();
		        break;
		    case CHESS:
		        res = bundle.chess();
		        break;
		    case FLAG_RED:
		        res = bundle.flag_red();
		        break;
		    case GEOGRAPHY:
		        res = bundle.geography();
		        break;
		    case HISTORY:
		        res = bundle.history();
		        break;
		    case SMILEY_HAPPY_01:
		        res = bundle.happy01();
		        break;
		    case SMILEY_HAPPY_02:
		        res = bundle.happy02();
		        break;
		    case SMILEY_HAPPY_03:
		        res = bundle.happy03();
		        break;
		    case SMILEY_HAPPY_04:
		        res = bundle.happy04();
		        break;
		    case SMILEY_HAPPY_05:
		        res = bundle.happy05();
		        break;
		    case SMILEY_HAPPY_06:
		        res = bundle.happy06();
		        break;
		    case SMILEY_SAD_01:
		        res = bundle.sad01();
		        break;
		    case SMILEY_SAD_02:
		        res = bundle.sad02();
		        break;
		    case SMILEY_SAD_03:
		        res = bundle.sad03();
		        break;
		    case SMILEY_SAD_04:
		        res = bundle.sad04();
		        break;
		    case SMILEY_SAD_05:
		        res = bundle.sad05();
		        break;
		    case SMILEY_SAD_06:
		        res = bundle.sad06();
		        break;

            default:
			    res = null; //bundle.empty();
		} // end switch
		return res;
	} //end findResource()

    private static ImageBundle _images = null;
	
	private static ImageBundle getApplicationImages() {
		if (_images == null) {
			_images = (ImageBundle) GWT.create(ImageBundle.class);
		}

		return _images;
	}

} //end ImageFactory
