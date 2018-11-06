package liyu.test.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
/**
 * 
 * @Description 此类描述的是：本地图片
 * @author: you@rthdtax.com
 * @version: 2018年11月5日 下午1:51:12
 */
public class ResultImg {
	public static void main(String[] args) {
		///1.生产背景图片
		BufferedImage img = new BufferedImage(400, 600,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("宋体", Font.BOLD, 20));
		graphics.drawString("这是一张背景图片", 10, 25);
		///2.加载前景图片
		graphics.drawImage(getFrontImg(), 100, 100, null);
		
		try {
			ImageIO.write(img, "jpg", new FileOutputStream(new File("result.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Image getFrontImg() {
		BufferedImage image = null;
		try {
			URL url = Object.class.getResource("/5.png");
			image = ImageIO.read(url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return image;
	}
}
