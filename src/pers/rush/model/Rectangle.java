package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public class Rectangle extends Shape{
    public Rectangle(){}

    public Rectangle(int x1, int y1, int x2, int y2, Color color, Stroke stroke){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.stroke = stroke;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(stroke);
        g.drawRect(x1, y1, x2, y2);
    }

    private int getWidth(){
    	return Math.abs(x2 - x1) + 1;
    }
    
    private int getHeight(){
    	return Math.abs(y2 - y1) + 1;
    }
    
	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
        return (x >= x1 &&
                y >= y1 &&
                x < x1 + getWidth() &&
                y < y1 + getHeight());
	}
}
