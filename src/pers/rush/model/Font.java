package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/8.
 */
public class Font extends Shape{
    String content; // 文本内容

    public Font(){}

    public Font(String content, int x1, int y1, Color color, Stroke stroke){
        this.content = content;
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.stroke = stroke;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(stroke);
        g.drawString(content, x1, y1);
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }
}
