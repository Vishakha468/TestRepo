package componentgroups;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import com.cognizant.framework.FrameworkException;

public class ImageComponents {
	
	public static String getImagePathLocation(String imageName) {
		return new File("").getAbsolutePath() + "\\images\\" + imageName;
	}
	
	public static boolean isImageMatched(String imageFileLocation, String imagePageURL) {
		try {
			BufferedImage original = ImageIO.read(new File(imageFileLocation));
			BufferedImage copy = ImageIO.read(new URL(imagePageURL));
			
			Raster ras1 = original.getData();
			Raster ras2 = copy.getData();
			if (ras1.getNumBands() != ras2.getNumBands() || ras1.getWidth() != ras2.getWidth() || ras1.getHeight() != ras2.getHeight()) {
				return false;
			}
			
			for (int i = 0; i < ras1.getNumBands(); ++i) {
				for (int x = 0; x < ras1.getWidth(); ++x) {
					for (int y = 0; y < ras1.getHeight(); ++y) {
						if (ras1.getSample(x, y, i) != ras2.getSample(x, y, i)) {
							return false;
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			throw new FrameworkException("Image Comparison Exception: " + e.getMessage());
		}
	}
}
