
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class ImageLoading {
    public static BufferedImage loadImg(String path) {
		
		try {
			return ImageIO.read(ImageLoading.class.getResource(path));
		} catch (IOException e) {
// TODO Auto-generated catch block
		}
		return null;
	}
	
}
	