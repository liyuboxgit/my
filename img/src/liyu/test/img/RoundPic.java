package liyu.test.img;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RoundPic {
	public static void main(String[] args) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File("net.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage circularBufferImage = roundImage(bufferedImage,bufferedImage.getWidth(),bufferedImage.getHeight(),50,50);
		try {
			ImageIO.write(circularBufferImage, "png", new File("tmp.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    private static BufferedImage roundImage(BufferedImage image, int w,int h, int wRadius,int hRadius) {
        BufferedImage outputImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, wRadius, hRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return outputImage;
    }
}

