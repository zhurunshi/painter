package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public class Oval extends Shape{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9137358095768620815L;

	public Oval(){
        super();
    }

    public Oval(int x1, int y1, int width, int height, Color color, int strokeSize){
        super(x1, y1, color, strokeSize);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(strokeSize));
        g.drawOval(x1, y1, width, height);
    }
    
	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		double ellw = width;
        if (ellw <= 0.0) {
            return false;
        }
        double normx = (x - x1) / ellw - 0.5;
        double ellh = height;
        if (ellh <= 0.0) {
            return false;
        }
        double normy = (y - y1) / ellh - 0.5;
        return (normx * normx + normy * normy) < 0.25;
	}
}
