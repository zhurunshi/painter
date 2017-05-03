package pers.rush.tools;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/2.
 */
public class PainterButton extends JButton{
    public PainterButton(){
        super();
    }
    public PainterButton(Icon icon){
        super(icon);
    }
    public PainterButton(Icon icon, String toolTipText){
        super(icon);
        setToolTipText(toolTipText);
    }
    public PainterButton(String text){
        super(text);
    }
    public PainterButton(String text, Icon icon, String toolTipText){
        super(text, icon);
        setToolTipText(toolTipText);
    }
    public PainterButton(String text, Color color){
        super(text);
        setContentAreaFilled(false); // 透明
        setOpaque(true);
        setBorderPainted(false);
        setBackground(color);
        setPreferredSize(new Dimension(20, 20)); // 由于是GridLayout所以不能用setSize方法设置大小
        setFocusPainted(false); // 去掉按钮文字周围的焦点框
    }
}
