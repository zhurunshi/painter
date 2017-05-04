package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public abstract class Shape {
    public int x1, y1, x2, y2;
    public Color color; // 画笔颜色
    public int width; // 画笔粗细

    public Shape() {}

    public Shape(int x1, int y1, int x2, int y2, Color color, int width) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.width = width;
    }

    public abstract void Draw(Graphics2D g);
}
