package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/8.
 */
public class Font extends Shape{
    public String content; // 文本内容
    public int size = 15; // 初始字号为15

    public Font(){}

    public Font(String content, int x1, int y1, Color color, int strokeSize){
        this.content = content;
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.strokeSize = strokeSize;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(strokeSize));
        g.setFont(new java.awt.Font(null, java.awt.Font.PLAIN, size));
        g.drawString(content, x1, y1);
        height = g.getFontMetrics().getHeight();
        width = g.getFontMetrics().stringWidth(content);
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= x1 && x <= (x1 + width) && y <= y1 && y >= (y1 - height);
    }
}
