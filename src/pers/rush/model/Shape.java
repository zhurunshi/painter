package pers.rush.model;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public abstract class Shape implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7288932686050758114L;
	public int x1;
    public int y1;
    public int x2;
    public int y2;
    public int width;
    public int height;
    public Color color; // 画笔颜色
    public int strokeSize; // 画笔粗细
    
    public Shape() {}

    public Shape(int x1, int y1, Color color, int strokeSize) {
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.strokeSize = strokeSize;
    }

    public abstract void draw(Graphics2D g);
    
    public abstract boolean contains(int x, int y);
}
