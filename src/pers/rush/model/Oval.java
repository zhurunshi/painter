package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public class Oval extends Shape{
    public Oval(){
        super();
    }

    public Oval(int x1, int y1, int x2, int y2, Color color, Stroke stroke){
        super(x1, y1, x2, y2, color, stroke);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(stroke);
        g.drawOval(x1, y1, x2, y2);
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
		double ellw = getWidth();
        if (ellw <= 0.0) {
            return false;
        }
        double normx = (x - x1) / ellw - 0.5;
        double ellh = getHeight();
        if (ellh <= 0.0) {
            return false;
        }
        double normy = (y - y1) / ellh - 0.5;
        return (normx * normx + normy * normy) < 0.25;
	}
}
