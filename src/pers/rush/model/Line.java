package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public class Line extends Shape {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2260339564467857659L;

	public Line(){
        super();
    }

    public Line(int x1, int y1, int x2, int y2, Color color, int strokeSize){
        super(x1, y1, color, strokeSize);
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(strokeSize));
        g.drawLine(x1, y1, x2, y2);
    }

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return Math.abs( (y - y1) * (x2 - x) - (x - x1) * (y2 - y) ) <= 1500;
	}
}
