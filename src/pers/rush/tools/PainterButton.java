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
        setOpaque(true);
        setBorderPainted(false);
        setBackground(color);
    }
}
