package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public class Rectangle extends Shape {
    public Rectangle(){
    	super();
    }

    public Rectangle(int x1, int y1, int width, int height, Color color, int strokeSize){
        super(x1, y1, color, strokeSize);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(strokeSize));
        g.drawRect(x1, y1, width, height);
    }
    
	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
        if(x >= x1 && x <= (x1 + height) && y >= y1 && y <= (y1 + height)){
            return true;
        }
        // 因为规定矩形只从左上往右下画所以不成立
//        if(x >= (x1 - height) && x <= x1 && y >= (y1 - height) && y <= y1){
//            return true;
//        }
        return false;
	}
}
