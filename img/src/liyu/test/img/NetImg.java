package liyu.test.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 * @Description 此类描述的是：网络图片,并进行按固定尺寸压缩
 * @author: you@rthdtax.com
 * @version: 2018年11月5日 下午1:51:56
 */
public class NetImg {
	public static void main(String[] args) {
		try {
			URI uri = new URI("https://rthdrtaxhelp.oss-cn-beijing.aliyuncs.com/片头图片.jpg");
			URL url = uri.toURL();
			BufferedImage read = ImageIO.read(url);
			
			BufferedImage image = new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB);
		    image.getGraphics().drawImage(read, 0, 0, image.getWidth(), image.getHeight(), null); 
			ImageIO.write(image, "png", new FileOutputStream(new File("net.png")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
