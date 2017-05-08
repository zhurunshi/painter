package pers.rush.tool;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/2.
 */
public class PainterRadioButton extends JRadioButton {
    public PainterRadioButton(){
        super();
    }
    public PainterRadioButton(Icon icon){
        super(icon);
    }
    public PainterRadioButton(Icon icon, String toolTipText){
        super(icon);
        setToolTipText(toolTipText);
        setContentAreaFilled(false);
        setFocusPainted(false); // 去掉按钮文字周围的焦点框
    }
    public PainterRadioButton(String text){
        super(text);
        setContentAreaFilled(false);
    }
    public PainterRadioButton(String text, Icon icon, String toolTipText){
        super(text, icon);
        setToolTipText(toolTipText);
    }
    public PainterRadioButton(String text, Color color){
        super(text);
        setContentAreaFilled(false); // 透明
        setOpaque(true);
//        setBorderPainted(false);
        setBackground(color);
//        setPreferredSize(new Dimension(20, 20)); // 由于是GridLayout所以不能用setSize方法设置大小
//        setFocusPainted(false); // 去掉按钮文字周围的焦点框
//        setBorder(null);
    }
}
