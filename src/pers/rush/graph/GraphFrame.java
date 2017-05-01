package pers.rush.graph;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pers.rush.tools.PainterMenu;

public class GraphFrame extends JFrame{
	// 菜单栏
	JMenuBar pMenuBar = new JMenuBar();
	// 文件菜单
	PainterMenu pFileMenu = new PainterMenu("文件(F)", KeyEvent.VK_F);
	JMenuItem pNew = new JMenuItem("新建(N)", KeyEvent.VK_N);
	JMenuItem pOpen = new JMenuItem("打开(O)", KeyEvent.VK_O);
	JMenuItem pSave = new JMenuItem("保存(S)", KeyEvent.VK_S);
	JMenuItem pExit = new JMenuItem("退出(X)", KeyEvent.VK_X);
	// 帮助菜单
	PainterMenu pHelpMenu = new PainterMenu("帮助(H)", KeyEvent.VK_H);
	JMenuItem pHelp = new JMenuItem("帮助(H)", KeyEvent.VK_H);
	JMenuItem pAbout = new JMenuItem("关于画图(A)", KeyEvent.VK_A);
	
	private void initMenus(){
		setJMenuBar(pMenuBar); // 将菜单栏添加到窗口
		// 添加文件菜单项
		pFileMenu.add(pNew);
		pFileMenu.add(pOpen);
		pFileMenu.add(pSave);
		pFileMenu.addSeparator();
		pFileMenu.add(pExit);
		pMenuBar.add(pFileMenu); // 将文件菜单添加到菜单栏
		// 添加图标
//		ImageIcon newIcon=new ImageIcon(GraphFrame.class.getResource("new.png"));
//		pNew.setIcon(newIcon);
//		ImageIcon openIcon=new ImageIcon(GraphFrame.class.getResource("open.png"));
//		pOpen.setIcon(openIcon);
//		ImageIcon saveIcon=new ImageIcon(GraphFrame.class.getResource("save.png"));
//		pSave.setIcon(saveIcon);
		// 快捷键
		pNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
		pOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));
		pSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
		pExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_DOWN_MASK));
	}
	
	// 右键菜单
	JPopupMenu pPopupMenu = new JPopupMenu();
	JMenuItem pUndo = new JMenuItem("撤销(U)", KeyEvent.VK_U);
	JMenuItem pRedo = new JMenuItem("恢复(Y)", KeyEvent.VK_Y);
	JMenuItem pCut = new JMenuItem("剪切(T)", KeyEvent.VK_T);
	JMenuItem pCopy = new JMenuItem("复制(C)", KeyEvent.VK_C);
	JMenuItem pPaste = new JMenuItem("粘贴(P)", KeyEvent.VK_P);
	JMenuItem pSelectAll = new JMenuItem("全选(A)", KeyEvent.VK_A);
		
	private void initPopupMenu(){
		pPopupMenu.add(pUndo);
		pPopupMenu.add(pRedo);
		pPopupMenu.add(pCut);
		pPopupMenu.add(pCopy);
		pPopupMenu.add(pPaste);
		pPopupMenu.add(pSelectAll);
		// 快捷键
		pUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
		pRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK));
	}
	
	// 默认为Windows风格
	private void initStyle(){
		try{
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	// 程序初始化
	private void initProcedure(){
		initMenus();
		initPopupMenu();
		setSize(700,500);
		initStyle();
	}
	
	public GraphFrame(String title){
		super(title);
		initProcedure();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
