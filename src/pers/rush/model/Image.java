package pers.rush.model;

import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/12.
 */
public class Image extends Shape {
    private java.awt.Image image;

    public Image() {
        super();
    }

    public Image(int x1, int y1, java.awt.Image image) {
        this.x1 = x1;
        this.y1 = y1;
        this.image = image;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, x1, y1, null);
    }

    @Override
    public boolean contains(int x, int y) {
        return true;
    }
}
