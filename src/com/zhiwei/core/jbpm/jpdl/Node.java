package com.zhiwei.core.jbpm.jpdl;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private String name; // 节点名称
	private String type; // 节点类型
	private Rectangle rectangle; // 节点的位置
	private List<Transition> transitions = new ArrayList<Transition>(); // 跳转线
	
	private int x;
	private int y;
	private int width;
	private int height;

	public Node(String name,String type){
		this.name=name;
		this.type=type;
	}
	
	public Node(String name, String type,int x, int y, int w, int h){
		this.name=name;
		this.type=type;
		this.x=x;
		this.y=y;
		this.width=w;
		this.height=h;
		
		rectangle=new Rectangle();
		rectangle.setBounds(x, y, w, h);
	}

	
	public int getCenterX(){
		return x+width/2;
	}
	
	public int getCenterY(){
		return y+height/2;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void addTransition(Transition tran){
		transitions.add(tran);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}
}