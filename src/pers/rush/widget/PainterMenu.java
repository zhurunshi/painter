package pers.rush.widget;

import javax.swing.JMenu;

public class PainterMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5881342868973831743L;
	public PainterMenu(String label){
		super(label);
	}
	public PainterMenu(String label, int mnemonic){
		super(label);
		setMnemonic(mnemonic);
	}

}
