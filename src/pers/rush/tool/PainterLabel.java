package pers.rush.tool;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ZhuRunShi on 2017/5/3.
 */
public class PainterLabel extends JLabel {
    public PainterLabel(String text, int horizontalAlignment){
        super(text, horizontalAlignment);
        setForeground(Color.GRAY);
    }
    public PainterLabel(String text, Icon icon){
        super(text);
        setIcon(icon);
    }
}
