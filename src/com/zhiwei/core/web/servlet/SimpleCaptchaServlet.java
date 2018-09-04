package com.zhiwei.core.web.servlet;

import static nl.captcha.Captcha.NAME;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;


/**
 * Generates, displays, and stores in session a 200x50 CAPTCHA image with sheared black text, 
 * a solid dark grey background, and a straight, slanted red line through the text.
 * 
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public class SimpleCaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String PARAM_HEIGHT = "height";
    private static final String PARAM_WIDTH = "width";

    protected int _width = 200;
    protected int _height = 50;

    @Override
    public void init() throws ServletException {
    	if (getInitParameter(PARAM_HEIGHT) != null) {
    		_height = Integer.valueOf(getInitParameter(PARAM_HEIGHT));
    	}
    	
    	if (getInitParameter(PARAM_WIDTH) != null) {
    		_width = Integer.valueOf(getInitParameter(PARAM_WIDTH));
    	}
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	 List<Font> fontList = new ArrayList<Font>();  
//       fontList.add(new Font("Arial", Font.HANGING_BASELINE, 40));//可以设置斜体之类的  
         fontList.add(new Font("Courier", Font.BOLD, 40));      
         DefaultWordRenderer dwr=new DefaultWordRenderer(Color.black,fontList);  
           
         //加入多种颜色后会随机显示 字体空心  
//       List<Color> colorList=new ArrayList<Color>();  
//       colorList.add(Color.green);  
//       colorList.add(Color.white);  
//       colorList.add(Color.blue);  
//       ColoredEdgesWordRenderer cwr= new ColoredEdgesWordRenderer(colorList,fontList);  
           
         WordRenderer wr=dwr;  
        //这里没有0和1是为了避免歧义 和字母I和O  
        char[] numberChar = new char[] { '2', '3', '4', '5', '6', '7', '8' ,'a','A','b','B','c','C','d','D','e','E','f','F','h','H','j','J','k','K','l','L','m','M','n','N','p','P','q','Q','r','R','s','S','t','T'};

        Captcha captcha = new Captcha.Builder(_width, _height)
        	.addText(new DefaultTextProducer(4,numberChar),wr)
        	.addBackground()
            //.gimp()//文字扭曲
            .addNoise()//干扰
            //.addBorder()//边框
            .build();

        CaptchaServletUtil.writeImage(resp, captcha.getImage());
        req.getSession().setAttribute(NAME, captcha);
    }
}
