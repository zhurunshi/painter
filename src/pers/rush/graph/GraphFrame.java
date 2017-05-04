package pers.rush.graph;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import pers.rush.model.Shape;
import pers.rush.tools.PainterButton;
import pers.rush.tools.PainterLabel;
import pers.rush.tools.PainterMenu;

public class GraphFrame extends JFrame implements ActionListener{
	// 菜单栏
	JMenuBar pMenuBar = new JMenuBar();
	// 文件菜单
	PainterMenu pFileMenu = new PainterMenu("文件(F)", KeyEvent.VK_F);
	JMenuItem pNew = new JMenuItem("新建(N)", KeyEvent.VK_N);
	JMenuItem pOpen = new JMenuItem("打开(O)", KeyEvent.VK_O);
	JMenuItem pSave = new JMenuItem("保存(S)", KeyEvent.VK_S);
    JMenuItem pBgColor=new JMenuItem("背景颜色(B)...",KeyEvent.VK_C);
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
        pFileMenu.add(pBgColor);
		pFileMenu.addSeparator();
		pFileMenu.add(pExit);
		pMenuBar.add(pFileMenu); // 将文件菜单添加到菜单栏
        // 注册监听
        pNew.addActionListener(this);
        pOpen.addActionListener(this);
        pSave.addActionListener(this);
        pBgColor.addActionListener(this);
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
        pBgColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
		pExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_DOWN_MASK));

        // 添加帮助菜单
        pHelpMenu.add(pHelp);
        pHelpMenu.add(pAbout);
        pMenuBar.add(pHelpMenu); // 将帮助菜单添加到菜单栏

        // 注册监听
        pHelpMenu.addActionListener(this);
        pAbout.addActionListener(this);
	}

    ArrayList list = new ArrayList();
    // 用JPanel作画布
    JPanel pPanel = new JPanel(){
        public void paint(Graphics g){
            Graphics2D g2d = (Graphics2D)g;
            super.paint(g);
            for(int i = 0; i < list.size(); ++i){
                Shape shape = (Shape)list.get(i);
                shape.Draw(g2d);
            }
        }
    };

    private void initpPanel(){
        pPanel.setBackground(Color.WHITE);
        Graphics g = pPanel.getGraphics();
        DrawListener dl = new DrawListener(g, bg, this, list);
        add(pPanel); // 将画布添加到JFrame
        pPanel.addMouseListener(dl);
        pPanel.addMouseMotionListener(dl);
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
        // 注册监听
        pUndo.addActionListener(this);
        pRedo.addActionListener(this);
        pCut.addActionListener(this);
        pCopy.addActionListener(this);
        pPaste.addActionListener(this);
        pSelectAll.addActionListener(this);

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
    JPanel pClipBoard = new JPanel(new BorderLayout());
    JPanel pClipButtons = new JPanel(new GridLayout(1, 3));
    JPanel pShapeBoard = new JPanel(new BorderLayout());
    JPanel pShapeButtons = new JPanel(new GridLayout(1, 3));
    JPanel pToolBoard = new JPanel(new BorderLayout());
    JPanel pToolButtons = new JPanel(new GridLayout(1, 3));
    JPanel pWidthBoard = new JPanel(new BorderLayout());
    JPanel pWidthButtons = new JPanel(new GridLayout(1, 1));
    JPanel pColorBoard = new JPanel(new BorderLayout());
    JPanel pColorButtons = new JPanel(new GridLayout(2, 10, 3, 3));

    ButtonGroup bg = new ButtonGroup();

    // 工具栏
    PainterButton cutButton = new PainterButton(
            new ImageIcon("resources//images//cut.png"), "剪切", "cut");
    PainterButton copyButton = new PainterButton(
            new ImageIcon("resources//images//copy.png"), "复制", "copy");
    PainterButton pasteButton = new PainterButton(
            new ImageIcon("resources//images//paste.png"), "粘贴", "paste");
    PainterButton lineButton = new PainterButton(
            new ImageIcon("resources//images//line.png"), "直线", "line");
    PainterButton rectangleButton = new PainterButton(
            new ImageIcon("resources//images//rectangle.png"), "矩形", "rectangle");
    PainterButton ovalButton = new PainterButton(
            new ImageIcon("resources//images//oval.png"), "椭圆形", "oval");
    PainterButton pencilButton = new PainterButton(
            new ImageIcon("resources//images//pencil.png"), "铅笔", "pencil");
    PainterButton eraserButton = new PainterButton(
            new ImageIcon("resources//images//eraser.png"), "橡皮擦", "eraser");
    PainterButton fontButton = new PainterButton(
            new ImageIcon("resources//images//font.png"), "文本", "font");
    PainterButton widthButton = new PainterButton(
            new ImageIcon("resources//images//width.png"), "粗细", "width");
    PainterButton colorButton = new PainterButton(
            new ImageIcon("resources//images//color_32px.png"), "更多颜色", "more");

    // 工具栏背景色
    Color ToolPanelBgColor = new Color(223, 223, 245);
    // 当前画笔颜色
    Color currentColor;
    // 背景颜色
    Color bgColor;

    private void initToolPanel(){
        // 注册监听
        cutButton.addActionListener(this);
        copyButton.addActionListener(this);
        pasteButton.addActionListener(this);
        lineButton.addActionListener(this);
        rectangleButton.addActionListener(this);
        ovalButton.addActionListener(this);
        pencilButton.addActionListener(this);
        eraserButton.addActionListener(this);
        fontButton.addActionListener(this);
        widthButton.addActionListener(this);
        colorButton.addActionListener(this);

        bg.add(cutButton);
        bg.add(copyButton);
        bg.add(pasteButton);
        bg.add(lineButton);
        bg.add(rectangleButton);
        bg.add(ovalButton);
        bg.add(pencilButton);
        bg.add(eraserButton);
        bg.add(fontButton);
        bg.add(widthButton);
        bg.add(colorButton);

        Container container = getContentPane();
        container.add(BorderLayout.NORTH, pToolPanel);
        pToolPanel.setBackground(ToolPanelBgColor);
        pClipButtons.add(cutButton);
        pClipButtons.add(copyButton);
        pClipButtons.add(pasteButton);
        pClipButtons.setBackground(ToolPanelBgColor);
        pClipBoard.add(pClipButtons, BorderLayout.NORTH);
        pClipBoard.add(new PainterLabel("　", JLabel.CENTER), BorderLayout.CENTER);
        pClipBoard.add(new PainterLabel("剪贴板", JLabel.CENTER), BorderLayout.SOUTH);
        pClipBoard.setBorder(BorderFactory.createEtchedBorder ());
        pClipBoard.setBackground(ToolPanelBgColor);
        pToolPanel.add(pClipBoard);

        pShapeButtons.add(lineButton);
        pShapeButtons.add(rectangleButton);
        pShapeButtons.add(ovalButton);
        pShapeButtons.setBackground(ToolPanelBgColor);
        pShapeBoard.add(pShapeButtons, BorderLayout.NORTH);
        pShapeBoard.add(new PainterLabel("　", JLabel.CENTER), BorderLayout.CENTER);
        pShapeBoard.add(new PainterLabel("形状", JLabel.CENTER), BorderLayout.SOUTH);
        pShapeBoard.setBorder(BorderFactory.createEtchedBorder ());
        pShapeBoard.setBackground(ToolPanelBgColor);
        pToolPanel.add(pShapeBoard);

        pToolButtons.add(pencilButton);
        pToolButtons.add(fontButton);
        pToolButtons.add(eraserButton);
        pToolButtons.setBackground(ToolPanelBgColor);
        pToolBoard.add(pToolButtons, BorderLayout.NORTH);
        pToolBoard.add(new PainterLabel("　", JLabel.CENTER), BorderLayout.CENTER);
        pToolBoard.add(new PainterLabel("工具", JLabel.CENTER), BorderLayout.SOUTH);
        pToolBoard.setBorder(BorderFactory.createEtchedBorder ());
        pToolBoard.setBackground(ToolPanelBgColor);
        pToolPanel.add(pToolBoard);

        pWidthButtons.add(widthButton);
        pWidthButtons.setBackground(ToolPanelBgColor);
        pWidthBoard.add(pWidthButtons, BorderLayout.NORTH);
        pWidthBoard.add(new PainterLabel("粗细", JLabel.CENTER), BorderLayout.SOUTH);
        pWidthBoard.setBorder(BorderFactory.createEtchedBorder ());
        pWidthBoard.setBackground(ToolPanelBgColor);
        pToolPanel.add(pWidthBoard);

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
            pColorButtons.add(new PainterButton("", c));
        }
        pColorButtons.setBackground(ToolPanelBgColor);
        pColorBoard.add(pColorButtons, BorderLayout.WEST);
        pColorBoard.add(colorButton, BorderLayout.EAST);
        pColorBoard.add(new PainterLabel("颜色", JLabel.CENTER), BorderLayout.SOUTH);
        pColorBoard.setBorder(BorderFactory.createEtchedBorder ());
        pColorBoard.setBackground(ToolPanelBgColor);
        pToolPanel.add(pColorBoard);

        cutButton.addActionListener(this);
        copyButton.addActionListener(this);
        pasteButton.addActionListener(this);
        fontButton.addActionListener(this);
    }

    // 显示坐标栏
    JToolBar pToolBar = new JToolBar("坐标栏");
    JLabel pPosition = new JLabel("");
    JLabel pDimension = new JLabel("像素");
    private void initToolBar(){
        Container c = getContentPane();
        c.add(BorderLayout.SOUTH, pToolBar);
        pToolBar.add(pPosition);
        pToolBar.addSeparator();
        pToolBar.add(pDimension);
        pToolBar.setFloatable(false);
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
        initpPanel(); // 初始化画布
		initPopupMenu(); // 右键弹出菜单
        initToolPanel(); // 初始化工具栏
        initToolBar(); // 初始化坐标栏
		setSize(1100,600); // 设置窗口大小
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
            newFile();
        }
        else if(e.getSource() == pOpen){
            open();
        }
        else if(e.getSource() == pSave){
            save();
        }
        else if(e.getSource() == pBgColor){
            setBgColor();
        }
        else if(e.getSource() == pExit){
            exit();
        }
        else if(e.getSource() == pHelp){
            help();
        }
        else if(e.getSource() == pAbout){
            about();
        }
        else if(e.getSource() == pUndo){
            undo();
        }
        else if(e.getSource() == pRedo){
            redo();
        }
        else if(e.getSource() == pCut || e.getSource() == cutButton){
            cut();
        }
        else if(e.getSource() == pCopy || e.getSource() == copyButton){
            copy();
        }
        else if(e.getSource() == pPaste || e.getSource() == pasteButton){
            paste();
        }
        else if(e.getSource() == pSelectAll){
            selectAll();
        }
        else if(e.getSource() == lineButton){
            line();
        }
        else if(e.getSource() == rectangleButton){
            rectangle();
        }
        else if(e.getSource() == ovalButton){
            oval();
        }
        else if(e.getSource() == pencilButton){
            pencil();
        }
        else if(e.getSource() == fontButton){
            font();
        }
        else if(e.getSource() == eraserButton){
            eraser();
        }
        else if(e.getSource() == widthButton){
            setWidth();
        }
        else if(e.getSource() == colorButton){
            setColor();
        }
    }

    private void setColor() {
        Color tmpColor = JColorChooser.showDialog(this, "编辑颜色", pPanel.getBackground());
        if(tmpColor != null){
            currentColor = tmpColor;
        }
    }

    private void setWidth() {
    }

    private void eraser() {
    }

    private void font() {
    }

    private void pencil() {
    }

    private void oval() {
    }

    private void rectangle() {
    }

    private void line() {
    }

    private void selectAll() {
    }

    private void paste() {
    }

    private void copy() {
    }

    private void cut() {
    }

    private void redo() {
    }

    private void undo() {
    }

    private void about() {
    }

    private void help() {
    }

    private void exit() {
        System.exit(0);
    }

    private void setBgColor() {
        Color tmpColor = JColorChooser.showDialog(this, "背景颜色", pPanel.getBackground());
        if(tmpColor != null){
            bgColor = tmpColor;
            pPanel.setBackground(bgColor);
        }
    }

    private void save() {
    }

    private void open() {
    }

    private void newFile() {
    }

    public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
