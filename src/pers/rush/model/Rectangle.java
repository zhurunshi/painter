package pers.rush.model;

import pers.rush.model.impl.Shape;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public class Rectangle extends Shape{
    public Rectangle(){}

    public Rectangle(int x1, int y1, int x2, int y2, Color color, int width){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.width = width;
    }

    @Override
    public void Draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(width));
        g.drawRect(x1, y1, x2, y2);
    }
}
