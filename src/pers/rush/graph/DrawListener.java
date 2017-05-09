package pers.rush.graph;

import pers.rush.model.*;
import pers.rush.model.Font;
import pers.rush.model.Rectangle;
import pers.rush.model.Shape;
import pers.rush.tool.Text;

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
    public Stroke s = new BasicStroke(1); // 画笔宽度
    public GraphFrame gf;
    public ArrayList<Shape> graphicsList;
    public ArrayList<Text> textList;
    public boolean flag = true;
    public static final int eraserWidth = 5;

    public Random random = new Random();

    public DrawListener(Graphics g){
        this.g = (Graphics2D)g;
    }

    public DrawListener(Graphics g, ButtonGroup bg){
        this.g = (Graphics2D)g;
        this.bg = bg;
    }

    public DrawListener(Graphics g, ButtonGroup bg, GraphFrame gf, ArrayList graphicsList, ArrayList textList){
        this.g = (Graphics2D)g;
        this.bg = bg;
        this.gf = gf;
        this.graphicsList = graphicsList;
        this.textList = textList;
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
          if("font".equals(command)){
          	  String str;
          	  str = JOptionPane.showInputDialog(gf, "请输入要插入的文本", "文本", 1);
          	  if(str != null){
                  Shape font = new Font(str, x1, y1, g.getColor(), s);
                  font.draw(g);
                  graphicsList.add(font);
          	  }
          }
          else {
//        	  for(int i = 0; i < graphicsList.size(); ++i){
        		  System.out.println("press: " + x1 + "," + y1);
//        		  Shape s = (Shape)graphicsList.get(i);
//        		  System.out.print(s + ": ");
//        		  System.out.println(s.contains(x1, y1));
//        	  }
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
                Shape line = new Line(x1, y1, x2, y2, g.getColor(), s);
                line.draw(g);
                graphicsList.add(line);
            }
            else if("rectangle".equals(command)){
                Shape rect = new Rectangle(
                        Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2), g.getColor(), s);
                rect.draw(g);
                graphicsList.add(rect);
            }
            else if("oval".equals(command)){
                Shape oval = new Oval(
                        Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2), g.getColor(), s);
                oval.draw(g);
                graphicsList.add(oval);
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
    	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
