package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public class Line extends Shape {
    public Line(){
        super();
    }

    public Line(int x1, int y1, int x2, int y2, Color color, int width){
        super(x1, y1, x2, y2, color, width);
    }

    @Override
    public void Draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(width));
        g.drawLine(x1, y1, x2, y2);
    }
}
