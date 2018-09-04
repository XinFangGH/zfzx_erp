package com.zhiwei.core.jbpm.jpdl;

import java.awt.*;   
import java.awt.font.FontRenderContext;   
import java.awt.geom.Rectangle2D;   
import java.awt.image.BufferedImage;   
import java.io.File;
import java.io.IOException;   
import java.util.ArrayList;
import java.util.List;   
import java.util.Map;   
import java.util.Set;
  
import javax.imageio.ImageIO;   
 
public class JpdlModelDrawer {   
  public static final int RECT_OFFSET_X = JpdlModel.RECT_OFFSET_X;   
  public static final int RECT_OFFSET_Y = JpdlModel.RECT_OFFSET_Y;   
  public static final int RECT_ROUND = 25;   
  
  public static final int DEFAULT_FONT_SIZE = 12;   
  
  public static final Color DEFAULT_STROKE_COLOR = Color.decode("#03689A");   
  public static final Stroke DEFAULT_STROKE = new BasicStroke(2);   
  
  public static final Color DEFAULT_LINE_STROKE_COLOR = Color.decode("#808080");   
  public static final Stroke DEFAULT_LINE_STROKE = new BasicStroke(1);   
  
  public static final Color DEFAULT_FILL_COLOR = Color.decode("#F6F7FF");   
  
  private final static Map<String, Object> nodeInfos = JpdlModel.getNodeInfos();   
  /**
   * 
   */
  private List<AnchorArea> links=new ArrayList<AnchorArea>();
  
  public BufferedImage draw(JpdlModel jpdlModel) throws IOException {   
    Rectangle dimension = getCanvasDimension(jpdlModel);   
    BufferedImage bi = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);   
    Graphics2D g2 = bi.createGraphics();   
    g2.setColor(Color.WHITE);   
    g2.fillRect(0, 0, dimension.width, dimension.height);   
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
    Font font = new Font("宋体", Font.PLAIN, DEFAULT_FONT_SIZE);   
    
    g2.setFont(font);   
    Map<String, Node> nodes = jpdlModel.getNodes(); 
    Set<String> activityNames=jpdlModel.getActivityNames();
    drawNode(nodes,activityNames,g2, font);   
    drawTransition(nodes, g2);   
    return bi;   
  } 
  
  public List<AnchorArea> getMaps(JpdlModel jpdlModel){
	  Map<String, Node> nodes = jpdlModel.getNodes();
	  	for (Node node : nodes.values()) {   
		      String name = node.getName();   
//		      if (nodeInfos.get(node.getType())== null 
//		    		  || "decision".equals(node.getType())) {   
		    	int x = node.getX();   
		        int y = node.getY();   
		        int w = node.getWidth();   
		        int h = node.getHeight();   
		        String type=node.getType();
		        links.add(new AnchorArea(x, y, x+w, y+h, name,type));
//		      }   
	    } 
	  return links;
  }
  
  /**  
   * 获得图片的矩形大小  
   *   
   * @return  
   */  
  public Rectangle getCanvasDimension(JpdlModel jpdlModel) {   
    Rectangle rectangle = new Rectangle();   
    Rectangle rect = new Rectangle();   
    for (Node node : jpdlModel.getNodes().values()) {   
      rect = node.getRectangle();   
      if (rect.getMaxX() > rectangle.getMaxX()) {   
        rectangle.width = (int)Math.round(rect.getMaxX());   
      }   
      if (rect.getMaxY() > rectangle.getMaxY()) {   
        rectangle.height = (int)Math.round(rect.getMaxY());   
      }   
      for (Transition transition : node.getTransitions()) {   
        List<Point> trace = transition.getLineTrace();   
        for (Point point : trace) {   
          if (rectangle.getMaxX() < point.x) {   
            rectangle.width = point.x;   
          }   
          if (rectangle.getMaxY() < point.y) {   
            rectangle.height = point.y;   
          }   
        }   
      }   
    }   
    rectangle.width += 60;   
    rectangle.height += 20;   
    return rectangle;   
  }   
     
  /**  
   * @param g2  
   * @throws IOException  
   */  
  private void drawTransition(Map<String, Node> nodes, Graphics2D g2) throws IOException {   
    g2.setStroke(DEFAULT_LINE_STROKE);   
    g2.setColor(DEFAULT_LINE_STROKE_COLOR);   
    for (Node node : nodes.values()) {   
      for (Transition transition : node.getTransitions()) {   
        String to = transition.getTo();   
        Node toNode = nodes.get(to);   
        List<Point> trace = transition.getLineTrace();
        
        //加上首尾两个节点
        int len = trace.size() + 2;   
        trace.add(0, new Point(node.getCenterX(), node.getCenterY()));   
        trace.add(new Point(toNode.getCenterX(), toNode.getCenterY())); 
        
        int[] xPoints = new int[len];   
        int[] yPoints = new int[len];   
        
        for (int i = 0; i < len; i++) {   
          xPoints[i] = trace.get(i).x;   
          yPoints[i] = trace.get(i).y;   
        }   
        
        final int taskGrow = 4;   
        final int smallGrow = -2;   
        int grow = 0;   
        if (nodeInfos.get(node.getType()) != null) {   
          grow = smallGrow;   
        } else {   
          grow = taskGrow;   
        }
        
        Point p = GeometryUtils.getRectangleLineCrossPoint(node.getRectangle(), new Point(xPoints[1],yPoints[1]), grow);   
        if (p != null) {   
          xPoints[0] = p.x;   
          yPoints[0] = p.y;   
        }   
        if (nodeInfos.get(toNode.getType()) != null) {   
          grow = smallGrow;   
        } else {   
          grow = taskGrow;   
        }   
        p = GeometryUtils.getRectangleLineCrossPoint(toNode.getRectangle(), new Point(xPoints[len - 2],yPoints[len - 2]), grow);   
        if (p != null) {   
          xPoints[len - 1] = p.x;   
          yPoints[len - 1] = p.y;   
        }
        
        g2.drawPolyline(xPoints, yPoints, len);   
        drawArrow(g2, xPoints[len - 2], yPoints[len - 2], xPoints[len - 1], yPoints[len - 1]);   
        String label = transition.getLabel();  
        
        if (label != null && label.length() > 0) {   
          int cx, cy;   
          if (len % 2 == 0) {   
            cx = (xPoints[len / 2 - 1] + xPoints[len / 2]) / 2;   
            cy = (yPoints[len / 2 - 1] + yPoints[len / 2]) / 2;   
          } else {   
            cx = xPoints[len / 2];   
            cy = yPoints[len / 2];   
          }   
          Point labelPoint = transition.getLabelPosition();   
          if (labelPoint != null) {   
            cx += labelPoint.x;   
            cy += labelPoint.y;   
          }   
          cy -= RECT_OFFSET_Y + RECT_OFFSET_Y / 2; 
          g2.setColor(Color.BLUE);
          g2.drawString(label, cx, cy);
          g2.setColor(DEFAULT_LINE_STROKE_COLOR);  
        }   
      }   
    }   
  }   
  
  private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {   
	  final double len = 8.0;
	    final double exLen=2;
	    double xp3,yp3;

	    double slopy = Math.atan2(y2 - y1, x2 - x1);  
	    //加2个像数以延长箭头第三个点，以盖住画线的最后一点
	    xp3=(double)x2+exLen*Math.cos(slopy);
	    yp3=(double)y2+exLen*Math.sin(slopy);
	    
	    int x3=(int)Math.round(xp3);
	    int y3=(int)Math.round(yp3);
	    
	    double cosy = Math.cos(slopy);   
	    double siny = Math.sin(slopy);   
	    int[] xPoints = { 0, x3, 0 };   
	    int[] yPoints = { 0, y3, 0 };   
	    
	    double a = len * siny, b = len * cosy;   
	    double c = len / 2.0 * siny, d = len / 2.0 * cosy;   
	    
	    xPoints[0] = (int)Math.round((double)x2 -  (b + c));   
	    yPoints[0] = (int)Math.round((double)y2 -  (a - d));   
	    xPoints[2] = (int)Math.round((double)x2 - (b - c));   
	    yPoints[2] = (int)Math.round((double)y2 - (d + a));   
	       
	    g2.fillPolygon(xPoints, yPoints, 3);   
  }   
  
  /**  
   * @param g2  
   * @throws IOException  
   */  
  private void drawNode(Map<String, Node> nodes, Set activityNames,Graphics2D g2, Font font) throws IOException {   
    for (Node node : nodes.values()) {   
      String name = node.getName();   
      if (nodeInfos.get(node.getType()) != null) {   
        BufferedImage bi2 = ImageIO.read(getClass().getResourceAsStream("/icons/48/" + nodeInfos.get(node.getType())));   
        g2.drawImage(bi2, node.getX(), node.getY(), null);
      } else {
    	
    	int x = node.getX();   
        int y = node.getY();   
        int w = node.getWidth();   
        int h = node.getHeight();   
        
        links.add(new AnchorArea(x, y, x+w, y+h, name,node.getType()));
        
        g2.setColor(DEFAULT_FILL_COLOR);   
        g2.fillRoundRect(x, y, w, h, RECT_ROUND, RECT_ROUND); 
        
        if(activityNames.contains(name)){
        	g2.setColor(Color.RED);
        }else{
        	g2.setColor(DEFAULT_STROKE_COLOR);   
        }
        
        g2.setStroke(DEFAULT_STROKE); 
        
        g2.drawRoundRect(x, y, w, h, RECT_ROUND, RECT_ROUND);   
  
        FontRenderContext frc = g2.getFontRenderContext();   
        Rectangle2D r2 = font.getStringBounds(name, frc);   
        int xLabel = (int) (node.getX() + ((node.getWidth() - r2.getWidth()) / 2));   
        int yLabel = (int) ((node.getY() + ((node.getHeight() - r2.getHeight()) / 2)) - r2.getY());   
        g2.setStroke(DEFAULT_LINE_STROKE);   
        g2.setColor(Color.black);   
        g2.drawString(name, xLabel, yLabel);   
      }   
    } 
    
    
    
  }   
  
  public List<AnchorArea> getLinks() {
	return links;
  }


public static void main(String[] args) throws Exception {     
	  JpdlModel jpdlModel = new JpdlModel(Thread.currentThread().getContextClassLoader().getResource("jpdl/buyCar.jpdl.xml").openStream());
	  jpdlModel.getActivityNames().add("经理审批");
	  //jpdlModel.getActivityNames().add("总经理审批");
	  ImageIO.write(new JpdlModelDrawer().draw(jpdlModel), "png", new File("d:/buyCar.png"));     
  }
}  
