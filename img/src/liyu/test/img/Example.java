package liyu.test.img;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 * @Description 此类描述的是：比较复杂的图片
 * @author: you@rthdtax.com
 * @version: 2018年11月5日 下午2:08:57
 */
public class Example {
	public static void main(String[] args) {
		//加载背景图片
		BufferedImage back = null;
		try {
			URL url = Object.class.getResource("/xcx_back.png");
			back = ImageIO.read(url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//加载视频封面
		try {
			//加载元素
			URI uri = new URI("https://rthdrtaxhelp.oss-cn-beijing.aliyuncs.com/片头图片.jpg");
			URL url = uri.toURL();
			BufferedImage read = ImageIO.read(url);
			///压缩
			if(read.getWidth()>back.getWidth()) {
				BufferedImage ret = resize(back.getWidth(), 275, read);
				back.getGraphics().drawImage(ret, 0, 0, null);
			}else {
				back.getGraphics().drawImage(read, 0, 0, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//加载分享者头像
		try {
			//加载元素
			URI uri = new URI("https://rthdrtaxhelp.oss-cn-beijing.aliyuncs.com/headimg35.png");
			URL url = uri.toURL();
			BufferedImage read = ImageIO.read(url);
			
			BufferedImage resize = resize(80,80,read);
			
			BufferedImage outputImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = outputImage.createGraphics();
	        g2.setComposite(AlphaComposite.Src);
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(Color.WHITE);
	        g2.fill(new RoundRectangle2D.Float(0, 0, 80, 80, 80, 80));
	        g2.setComposite(AlphaComposite.SrcAtop);
	        g2.drawImage(resize, 0, 0, null);
	        g2.dispose();
	       
	        back.getGraphics().drawImage(outputImage, 30, 300, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//加载小程序二维码
		try {
			//加载元素
			URI uri = new URI("http://localhost:8080/rtaxhelp/webs/get2Dcode");
			URL url = uri.toURL();
			BufferedImage read = ImageIO.read(url);
			
			BufferedImage resize = resize(160,160,read);
			
			BufferedImage outputImage = new BufferedImage(160, 160, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = outputImage.createGraphics();
	        g2.setComposite(AlphaComposite.Src);
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(Color.WHITE);
	        g2.fill(new RoundRectangle2D.Float(0, 0, 160, 160, 160, 160));
	        g2.setComposite(AlphaComposite.SrcAtop);
	        g2.drawImage(resize, 0, 0, null);
	        g2.dispose();
	        
	        back.getGraphics().drawImage(outputImage, 30, 460, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//加字
		Graphics2D graphics = back.createGraphics();
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("宋体", Font.BOLD, 30));
		graphics.drawString("北风", 130, 325);
		
		graphics.setColor(Color.GRAY);
		graphics.setFont(new Font("宋体", Font.BOLD, 25));
		graphics.drawString("邀请您加入", 130, 365);
		
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("宋体", Font.BOLD, 25));
		graphics.drawString("贸税帮", 260, 365);
		
		graphics.setColor(Color.GRAY);
		graphics.setFont(new Font("宋体", Font.BOLD, 24));
		graphics.drawString("长按扫描小程序码", 260, 520);
		
		graphics.setColor(Color.GRAY);
		graphics.setFont(new Font("宋体", Font.BOLD, 24));
		graphics.drawString("与我一起学习", 260, 560);
		
		graphics.dispose();
		
		//输出
		try {
			ImageIO.write(back, "png", new FileOutputStream(new File("back.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage resize(int w, int h, BufferedImage src) throws IOException {
	    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    image.getGraphics().drawImage(src, 0, 0, w, h, null); 
	    return image;
	}
}
