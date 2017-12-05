package liyu.test.activity.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.util.ReflectUtil;

public class MyDefaultProcessDiagramCanvas extends DefaultProcessDiagramCanvas {
	/**
	 * 高亮颜色
	 */
	protected static Color HIGHLIGHT_COLOR_GREEN = Color.GREEN;

	public MyDefaultProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType) {
		super(width, height, minX, minY, imageType);
	}
	public MyDefaultProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType,
			String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
		super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName,
				customClassLoader);
	}
	@Override
	public void initialize(String imageType) {
		if ("png".equalsIgnoreCase(imageType)) {
			this.processDiagram = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
		} else {
			this.processDiagram = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
		}
		this.g = processDiagram.createGraphics();
		if ("png".equalsIgnoreCase(imageType) == false) {
			this.g.setBackground(new Color(255, 255, 255, 0));
			this.g.clearRect(0, 0, canvasWidth, canvasHeight);
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(Color.black);
		Font font = new Font(activityFontName, Font.BOLD, FONT_SIZE);
		g.setFont(font);
		this.fontMetrics = g.getFontMetrics();
		LABEL_FONT = new Font(labelFontName, Font.ITALIC, 12);
		ANNOTATION_FONT = new Font(annotationFontName, Font.PLAIN, FONT_SIZE);
		try {
			USERTASK_IMAGE = ImageIO
					//.read(ReflectUtil.getResource("org/activiti/icons/userTask.png", customClassLoader));
					.read(ReflectUtil.getResource("liyu/test/activity/util/apple.png", customClassLoader));
			SCRIPTTASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/scriptTask.png", customClassLoader));
			SERVICETASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/serviceTask.png", customClassLoader));
			RECEIVETASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/receiveTask.png", customClassLoader));
			SENDTASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/sendTask.png", customClassLoader));
			MANUALTASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/manualTask.png", customClassLoader));
			BUSINESS_RULE_TASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/businessRuleTask.png", customClassLoader));
			SHELL_TASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/shellTask.png", customClassLoader));
			CAMEL_TASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/camelTask.png", customClassLoader));
			MULE_TASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/muleTask.png", customClassLoader));
			TIMER_IMAGE = ImageIO.read(ReflectUtil.getResource("org/activiti/icons/timer.png", customClassLoader));
			COMPENSATE_THROW_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/compensate-throw.png", customClassLoader));
			COMPENSATE_CATCH_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/compensate.png", customClassLoader));
			ERROR_THROW_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/error-throw.png", customClassLoader));
			ERROR_CATCH_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/error.png", customClassLoader));
			MESSAGE_THROW_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/message-throw.png", customClassLoader));
			MESSAGE_CATCH_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/message.png", customClassLoader));
			SIGNAL_THROW_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/signal-throw.png", customClassLoader));
			SIGNAL_CATCH_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/icons/signal.png", customClassLoader));
		} catch (IOException e) {
			LOGGER.warn("Could not load image for process diagram creation: {}", e.getMessage());
		}
	}
	@Override
	public void drawHighLight(int x, int y, int width, int height) {
		Paint originalPaint = g.getPaint();
		Stroke originalStroke = g.getStroke();
		g.setPaint(HIGHLIGHT_COLOR_GREEN);
		g.setStroke(THICK_TASK_BORDER_STROKE);
		RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
		g.draw(rect);
		g.setPaint(originalPaint);
		g.setStroke(originalStroke);
	}
	@Override
	public void drawConnection(int[] xPoints, int[] yPoints, boolean conditional, boolean isDefault,
			String connectionType, AssociationDirection associationDirection, boolean highLighted, double scaleFactor) {
		Paint originalPaint = g.getPaint();
		Stroke originalStroke = g.getStroke();
		g.setPaint(CONNECTION_COLOR);
		if (connectionType.equals("association")) {
			g.setStroke(ASSOCIATION_STROKE);
		} else if (highLighted) {
			g.setPaint(HIGHLIGHT_COLOR_GREEN);
			g.setStroke(HIGHLIGHT_FLOW_STROKE);
		}
		for (int i = 1; i < xPoints.length; i++) {
			Integer sourceX = xPoints[i - 1];
			Integer sourceY = yPoints[i - 1];
			Integer targetX = xPoints[i];
			Integer targetY = yPoints[i];
			Line2D.Double line = new Line2D.Double(sourceX, sourceY, targetX, targetY);
			g.draw(line);
		}
		if (isDefault) {
			Line2D.Double line = new Line2D.Double(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
			drawDefaultSequenceFlowIndicator(line, scaleFactor);
		}
		if (conditional) {
			Line2D.Double line = new Line2D.Double(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
			drawConditionalSequenceFlowIndicator(line, scaleFactor);
		}
		if (associationDirection.equals(AssociationDirection.ONE)
				|| associationDirection.equals(AssociationDirection.BOTH)) {
			Line2D.Double line = new Line2D.Double(xPoints[xPoints.length - 2], yPoints[xPoints.length - 2],
					xPoints[xPoints.length - 1], yPoints[xPoints.length - 1]);
			drawArrowHead(line, scaleFactor);
		}
		if (associationDirection.equals(AssociationDirection.BOTH)) {
			Line2D.Double line = new Line2D.Double(xPoints[1], yPoints[1], xPoints[0], yPoints[0]);
			drawArrowHead(line, scaleFactor);
		}
		g.setPaint(originalPaint);
		g.setStroke(originalStroke);
	}
}
