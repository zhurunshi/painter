package pers.rush.graph;

import pers.rush.model.*;
import pers.rush.model.Rectangle;
import pers.rush.model.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ZhuRunShi on 2017/5/4.
 */
public class DrawListener implements MouseListener, MouseMotionListener{

    public Graphics2D g;
    public int x1, y1, x2, y2, ox, oy, x3, y3;
    public ButtonGroup bg;
    public String command;
    public Color color; // 画笔颜色
    public GraphFrame gf;
    public ArrayList list;
    public boolean flag = true;

    public static final Stroke s1 = new BasicStroke(1);
    public static final Stroke s2 = new BasicStroke(10);
    public static final Stroke s3 = new BasicStroke(15);

    public Random random = new Random();

    public DrawListener(Graphics g){
        this.g = (Graphics2D)g;
    }

    public DrawListener(Graphics g, ButtonGroup bg){
        this.g = (Graphics2D)g;
        this.bg = bg;
    }

    public DrawListener(Graphics g, ButtonGroup bg, GraphFrame gf, ArrayList list){
        this.g = (Graphics2D)g;
        this.bg = bg;
        this.gf = gf;
        this.list = list;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
        ButtonModel bm = bg.getSelection();
        command = bm.getActionCommand();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        System.out.println("command: " + command);
        if("cut".equals(command)){ }
        else if("copy".equals(command)){ }
        else if("paste".equals(command)){ }
        else if("line".equals(command)){
            Shape line = new Line(x1, y1, x2, y2, g.getColor(), 1);
            line.Draw(g);
            list.add(line);
        }
        else if("rectangle".equals(command)){
            Shape rect = new Rectangle(
                    Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2), g.getColor(), 1);
            rect.Draw(g);
            list.add(rect);
        }
        else if("oval".equals(command)){
            Shape oval = new Oval(
                    Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2), g.getColor(), 1);
            oval.Draw(g);
            list.add(oval);
        }
        else if("pencil".equals(command)){}
        else if("eraser".equals(command)){}
        else if("font".equals(command)){}
        else if("width".equals(command)){}
        else if("more".equals(command)){}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        color = gf.currentColor;
        g.setColor(color);
        g.setStroke(s1);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if("pencil".equals(command)){
            Shape line = new Line(x1, y1, x, y,g.getColor(),1);
            line.Draw(g);
            list.add(line);
            x1 = x;
            y1 = y;
        }
        else if("eraser".equals(command)){
            g.setColor(Color.WHITE);
            g.setStroke(s3);

            Shape line = new Line(x1, y1, x, y,g.getColor(),15);
            line.Draw(g);
            list.add(line);

            x1=x;
            y1=y;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
