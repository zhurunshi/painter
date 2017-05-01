package pers.rush.main;

import java.awt.Image;
import java.awt.Toolkit;

import pers.rush.graph.GraphFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphFrame gf = new GraphFrame("画图");
//		Toolkit toolkit = gf.getToolkit();
//		Image image = toolkit.getImage(GraphFrame.class.getResource("icon.png"));
//		gf.setIconImage(image);
		gf.setVisible(true);
	}

}
