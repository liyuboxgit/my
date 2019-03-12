package liyu.test.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class Example_fas {
	public static void main(String[] args) {
		//加载背景图片
		BufferedImage back = null;
		try {
			URL url = Object.class.getResource("/fspy_back.png");
			back = ImageIO.read(url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		//加字
		Graphics2D graphics = back.createGraphics();
		///去除毛刺
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("黑体", Font.BOLD, 20));
		graphics.drawString("北风@你，一起加入贸税帮", 30, 45);
		
		//输出
		try {
			ImageIO.write(back, "png", new FileOutputStream(new File("back_py.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
