package pers.rush.widget;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;

public class PainterButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5933591301876521160L;
	public PainterButton(){
        super();
    }
    public PainterButton(Icon icon){
        super(icon);
    }
    public PainterButton(Icon icon, String toolTipText){
        super(icon);
        setToolTipText(toolTipText);
        setContentAreaFilled(false);
        setFocusPainted(false); // 去掉按钮文字周围的焦点框
    }
    public PainterButton(String text){
        super(text);
        setContentAreaFilled(false);
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
//        setFocusPainted(false); // 去掉按钮文字周围的焦点框
//        setBorder(null);
    }
}
