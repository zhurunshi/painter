package pers.rush.widget;

import javax.swing.JMenu;

public class PainterMenu extends JMenu {
	public PainterMenu(String label){
		super(label);
	}
	public PainterMenu(String label, int mnemonic){
		super(label);
		setMnemonic(mnemonic);
	}

}
