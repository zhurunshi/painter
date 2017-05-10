package pers.rush.graph;

import pers.rush.model.*;
import pers.rush.model.Font;
import pers.rush.model.Rectangle;
import pers.rush.model.Shape;
import pers.rush.tool.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ZhuRunShi on 2017/5/4.
 */
public class DrawListener implements MouseListener, MouseMotionListener, KeyListener{

    public Graphics2D g;
    public int x1, y1, x2, y2, ox, oy, x3, y3;
    public ButtonGroup bg; // 按钮组
    public ButtonGroup wg; // 画笔粗细组
    public ButtonGroup cg; // 颜色组
    public String command;
    public Color color; // 画笔颜色
    public Stroke s = new BasicStroke(1); // 画笔宽度
    public GraphFrame gf;
    public ArrayList<Shape> graphicsList;
    public ArrayList<Text> textList;
    public boolean first = true;
    public static final int eraserWidth = 10;
    public Shape currentShape;

    public DrawListener(Graphics g){
        this.g = (Graphics2D)g;
    }

    public DrawListener(Graphics g, ButtonGroup bg){
        this.g = (Graphics2D)g;
        this.bg = bg;
    }

    public DrawListener(Graphics g, ButtonGroup bg, ButtonGroup wg, ButtonGroup cg, GraphFrame gf, ArrayList<Shape> graphicsList){
        this.g = (Graphics2D)g;
        this.bg = bg;
        this.wg = wg;
        this.cg = cg;
        this.gf = gf;
        this.graphicsList = graphicsList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
       if(e.getButton() == MouseEvent.BUTTON1){ // 鼠标左键
           ButtonModel bm = bg.getSelection();
           command = bm.getActionCommand();
           x1 = e.getX();
           y1 = e.getY();
           if ("font".equals(command)) {
               String str;
               str = JOptionPane.showInputDialog(gf, "请输入要插入的文本", "文本", 1);
               if (str != null) {
                   Shape font = new Font(str, x1, y1, g.getColor(), s);
                   font.draw(g);
                   graphicsList.add(font);
               }
           }
           else if ("pointer".equals(command)){
               for (Shape s : graphicsList) {
                   if(s.contains(x1, y1)){ // 鼠标点击的坐标在图形内
                       System.out.print(s + ": ");
                       System.out.println(s.contains(x1, y1));
                       ButtonModel bmStroke = wg.getSelection();
                       String commandStroke = bmStroke.getActionCommand();
                       if("small".equals(commandStroke)){
                           s.stroke = new BasicStroke(1);
                       }
                       else if("median".equals(commandStroke)){
                           s.stroke = new BasicStroke(5);
                       }
                       else if("large".equals(commandStroke)){
                           s.stroke = new BasicStroke(10);
                       }
                       ButtonModel bmColor = cg.getSelection();
                       String commandColor = bmColor.getActionCommand();
                   }
               }
           }
           else {
               System.out.println("================================");
               for (Shape s : graphicsList) {
                   System.out.print(s + ": ");
                   System.out.println(s.contains(x1, y1));
               }
           }
       }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if(e.getButton() == MouseEvent.BUTTON1){ // 鼠标左键
    		x2 = e.getX();
            y2 = e.getY();
            System.out.println("released command: " + command);
            System.out.println("release: " + x2 + "," + y2);
            if("cut".equals(command)){ }
            else if("copy".equals(command)){ }
            else if("paste".equals(command)){ }
            else if("line".equals(command)){
                first = true;
            }
            else if("rectangle".equals(command)){
                first = true;
//                Shape rect = new Rectangle(
//                        Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2), g.getColor(), s);
//                rect.draw(g);
//                graphicsList.add(rect);
            }
            else if("oval".equals(command)){
                first = true;
//                Shape oval = new Oval(
//                        Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2), g.getColor(), s);
//                oval.draw(g);
//                graphicsList.add(oval);
            }
            else if("pencil".equals(command)){}
            else if("eraser".equals(command)){}
            else if("font".equals(command)){}
            else if("width".equals(command)){}
            else if("more".equals(command)){}
    	}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        color = gf.currentColor;
        s = gf.s;
        g.setColor(color);
        g.setStroke(s);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // 鼠标左键 BUTTON1 mac可用 NOBUTTON windows可用
        if(e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.NOBUTTON){
    		int x = e.getX();
            int y = e.getY();
            if("pencil".equals(command)){
                Shape line = new Line(x1, y1, x, y, g.getColor(), s);
                line.draw(g);
                graphicsList.add(line);
                x1 = x;
                y1 = y;
            }
            else if("eraser".equals(command)){
                g.setColor(Color.WHITE);
                g.setStroke(new BasicStroke(eraserWidth));
                Shape line = new Line(x1, y1, x, y, g.getColor(), new BasicStroke(eraserWidth));
                line.draw(g);
                graphicsList.add(line);

                x1=x;
                y1=y;
            }
            else if("line".equals(command)){
                if(first){
                    Shape line = new Line(x1, y1, x1, y1, g.getColor(), s);
                    line.draw(g);
                    graphicsList.add(line);
                    first = false;
                    currentShape = line;
                }
                else{
                    currentShape.x2 = x;
                    currentShape.y2 = y;
                }
            }
            else if("rectangle".equals(command)){
                if(first){
                    Shape rect = new Rectangle(
                            x1, y1, (x2 - x1), (y1 - y2), g.getColor(), s);
//                    Shape rect = new Rectangle(
//                            Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2), g.getColor(), s);
                    rect.draw(g);
                    graphicsList.add(rect);
                    first = false;
                    currentShape = rect;
                }
                else{
                    currentShape.width = x - x1;
                    currentShape.height = y - y1;
                }
            }
            else if("oval".equals(command)){
                if(first){
                    Shape oval = new Oval(
                            x1, y1, (x2 - x1), (y1 - y2), g.getColor(), s);
//                    Shape oval = new Oval(
//                            Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2), g.getColor(), s);
                    oval.draw(g);
                    graphicsList.add(oval);
                    first = false;
                    currentShape = oval;
                }
                else{
                    currentShape.width = x - x1;
                    currentShape.height = y - y1;
                }
            }
    	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
