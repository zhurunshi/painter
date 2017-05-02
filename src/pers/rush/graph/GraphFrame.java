package pers.rush.graph;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import pers.rush.tools.PainterButton;
import pers.rush.tools.PainterMenu;

public class GraphFrame extends JFrame implements ActionListener{
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
		// 添加文件菜单
		pFileMenu.add(pNew);
		pFileMenu.add(pOpen);
		pFileMenu.add(pSave);
		pFileMenu.addSeparator();
		pFileMenu.add(pExit);
		pMenuBar.add(pFileMenu); // 将文件菜单添加到菜单栏
        // 注册监听
        pNew.addActionListener(this);
        pOpen.addActionListener(this);
        pSave.addActionListener(this);
        pExit.addActionListener(this);
		// 添加图标
		ImageIcon newIcon = new ImageIcon("resources//images//new.png");
		pNew.setIcon(newIcon);
		ImageIcon openIcon = new ImageIcon("resources//images//open.png");
		pOpen.setIcon(openIcon);
		ImageIcon saveIcon = new ImageIcon("resources//images//save.png");
		pSave.setIcon(saveIcon);
		// 快捷键
		pNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
		pOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));
		pSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
		pExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_DOWN_MASK));

        // 添加帮助菜单
        pHelpMenu.add(pHelp);
        pHelpMenu.add(pAbout);
        pMenuBar.add(pHelpMenu); // 将帮助菜单添加到菜单栏
	}

    // 用JPanel作画布
    JPanel pPanel = new JPanel();

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
        pCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK));
        pCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
        pPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK));
        pSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK));
        // 添加图标
        ImageIcon undoIcon = new ImageIcon("resources//images//undo.png");
        pUndo.setIcon(undoIcon);
        ImageIcon redoIcon = new ImageIcon("resources//images//redo.png");
        pRedo.setIcon(redoIcon);
        ImageIcon cutIcon = new ImageIcon("resources//images//cut.png");
        pCut.setIcon(cutIcon);
        ImageIcon copyIcon = new ImageIcon("resources//images//copy.png");
        pCopy.setIcon(copyIcon);
        ImageIcon pasteIcon = new ImageIcon("resources//images//paste.png");
        pPaste.setIcon(pasteIcon);

        add(pPanel); // 将画布添加到JFrame
        pPanel.add(pPopupMenu); // 将右键菜单添加到画布
        pPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mods = e.getModifiers();
                if((mods & InputEvent.BUTTON3_MASK) != 0){ // 点击右键
                    pPopupMenu.show(pPanel,e.getX(),e.getY()); // 弹出菜单
                }
            }
        });
	}

    // 工具栏（不用ToolBar实现）
    JPanel pToolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel pClipBoard = new JPanel(new GridLayout(3, 3));
    JPanel pShapeBoard = new JPanel(new GridLayout(3, 3));
    JPanel pColorBoard = new JPanel(new GridLayout(2, 10));

    // 工具栏
//    JToolBar pToolBar = new JToolBar("工具栏");
    PainterButton cutButton = new PainterButton(
            new ImageIcon("resources//images//cut.png"),"剪切");
    PainterButton copyButton = new PainterButton(
            new ImageIcon("resources//images//copy.png"),"复制");
    PainterButton pasteButton = new PainterButton(
            new ImageIcon("resources//images//paste.png"),"粘贴");
    PainterButton fontButton = new PainterButton(
            new ImageIcon("resources//images//font.png"),"字体");

    private void initToolPanel(){
        Container container = getContentPane();
        container.add(BorderLayout.NORTH, pToolPanel);
        pClipBoard.setBackground(Color.green);
        pClipBoard.add(cutButton);
        pClipBoard.add(copyButton);
        pClipBoard.add(pasteButton);
        pClipBoard.add(new JLabel(""));
        pClipBoard.add(new JLabel(""));
        pClipBoard.add(new JLabel(""));
        pClipBoard.add(new JLabel(""));
        pClipBoard.add(new JLabel("剪贴板"));
        pClipBoard.add(new JLabel(""));
        pToolPanel.add(pClipBoard);

        pShapeBoard.setBackground(Color.orange);
        pShapeBoard.add(new JButton("直线"));
        pShapeBoard.add(new JButton("矩形"));
        pShapeBoard.add(new JButton("圆形"));
        pShapeBoard.add(new JLabel(""));
        pShapeBoard.add(new JLabel(""));
        pShapeBoard.add(new JLabel(""));
        pShapeBoard.add(new JLabel(""));
//        pClipBoard.add(fontButton);
        pShapeBoard.add(new JLabel("形状"));
        pShapeBoard.add(new JLabel(""));
        pToolPanel.add(pShapeBoard);

//        pToolPanel.add(new JLabel("粗细"));

        Color colors[] = new Color[]{
            new Color(0, 0, 0),
            new Color(127, 127, 127),
            new Color(136, 0, 21),
            new Color(237, 28, 36),
            new Color(255, 127, 39),
            new Color(255, 242, 0),
            new Color(34, 177, 76),
            new Color(0, 162, 232),
            new Color(63, 72, 204),
            new Color(163, 73, 164),
            new Color(255, 255, 255),
            new Color(195, 195, 195),
            new Color(185, 122, 87),
            new Color(255, 174, 201),
            new Color(255, 201, 14),
            new Color(239, 228, 176),
            new Color(181, 230, 29),
            new Color(153, 217, 234),
            new Color(112, 146, 190),
            new Color(200, 191, 231),
        };

        for(Color c : colors){
            pColorBoard.add(new PainterButton("", c));
        }
        pToolPanel.add(pColorBoard);

        JPanel thick = new JPanel();
        JPanel color = new JPanel();

//        pToolBar.add(cutButton);
//        pToolBar.add(copyButton);
//        pToolBar.add(pasteButton);
//        pToolBar.addSeparator();
        JPanel shape = new JPanel();
        shape.setBackground(Color.green);
//        pToolBar.add(shape, new GridLayout(2,3));
        String str1[] = {"直线", "矩形", "圆", "1", "2", "3"};
        JButton btn1[] = new JButton[str1.length];
        for(int i = 0; i < str1.length; ++i){
            btn1[i] = new JButton(str1[i]);
            shape.add(btn1[i]);
        }
//        pToolBar.addSeparator();
        JPanel tool = new JPanel();
        tool.setBackground(Color.orange);
//        pToolBar.add(tool, new GridLayout(3,1,0,0));
        String str2[] = {"铅笔", "文字", "橡皮"};
        JButton btn2[] = new JButton[str2.length];
        for(int i = 0; i < str2.length; ++i){
            btn2[i] = new JButton(str2[i]);
            tool.add(btn2[i]);
        }
//        pToolBar.add(tool);
//        pToolBar.addSeparator();
//        pToolBar.add(thick);
//        pToolBar.add(color);
//        pToolBar.add(fontButton);

        cutButton.addActionListener(this);
        copyButton.addActionListener(this);
        pasteButton.addActionListener(this);
        fontButton.addActionListener(this);

//        pToolBar.setFloatable(false);
    }
	
	// 默认为Windows风格
	private void initStyle(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			SwingUtilities.updateComponentTreeUI(this);
		}
	}

    //窗口居中函数
    private void centerWindow()
    {
        //获得显示屏桌面窗口的大小
        Toolkit tk=getToolkit();
        Dimension dm=tk.getScreenSize();
        //获取屏幕的边界
        Insets screenInsets=Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        //获取底部任务栏高度
        int taskBarHeight=screenInsets.bottom;
        //让窗口居中显示
        setLocation((int)(dm.getWidth() - getWidth()) / 2,
                (int)(dm.getHeight() - getHeight() - taskBarHeight) / 2);
    }
	
	// 程序初始化
	private void initProcedure(){
		initMenus(); // 初始化菜单栏
		initPopupMenu(); // 右键弹出菜单
        initToolPanel(); // 初始化工具栏
		setSize(900,500); // 设置窗口大小
        centerWindow(); // 窗口居中函数
		initStyle(); // 初始化风格
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置点击X按钮关闭程序
    }
	
	public GraphFrame(String title){
		super(title);
		initProcedure();
	}

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == pNew){
            New();
        }
        else if(e.getSource() == pOpen){
            Open();
        }
        else if(e.getSource() == pSave){
            Save();
        }
        else if(e.getSource() == pExit){
            Exit();
        }
        else if(e.getSource() == pHelp){
            Help();
        }
        else if(e.getSource() == pAbout){
            About();
        }
        else if(e.getSource() == pUndo){
            Undo();
        }
        else if(e.getSource() == pRedo){
            Redo();
        }
        else if(e.getSource() == pCut){
            Cut();
        }
        else if(e.getSource() == pCopy){
            Copy();
        }
        else if(e.getSource() == pPaste){
            Paste();
        }
        else if(e.getSource() == pSelectAll){
            SelectAll();
        }
    }

    private void SelectAll() {
    }

    private void Paste() {
    }

    private void Copy() {
    }

    private void Cut() {
    }

    private void Redo() {
    }

    private void Undo() {
    }

    private void About() {
    }

    private void Help() {
    }

    private void Exit() {
        System.exit(0);
    }

    private void Save() {
    }

    private void Open() {
    }

    private void New() {
    }

    public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
