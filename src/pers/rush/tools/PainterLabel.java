package pers.rush.tools;

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
}
