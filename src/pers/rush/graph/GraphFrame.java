package pers.rush.graph;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import pers.rush.main.Main;
import pers.rush.model.Shape;
import pers.rush.util.StreamUtils;
import pers.rush.widget.PainterRadioButton;
import pers.rush.widget.PainterButton;
import pers.rush.widget.PainterLabel;
import pers.rush.widget.PainterMenu;

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
//		ImageIcon newIcon = new ImageIcon("resources//images//new.png");
		ImageIcon newIcon = new ImageIcon(Main.class.getResource("../../../resources/images/new.png"));
		pNew.setIcon(newIcon);
		ImageIcon openIcon = new ImageIcon(Main.class.getResource("../../../resources/images/open.png"));
		pOpen.setIcon(openIcon);
		ImageIcon saveIcon = new ImageIcon(Main.class.getResource("../../../resources/images/save.png"));
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
        pHelp.addActionListener(this);
        pAbout.addActionListener(this);
	}
	// 放图形的容器
    ArrayList<Shape> graphicsList = new ArrayList<>();
    // 放恢复图形的容器
    Stack<Shape> redoStack = new Stack<>();
    // 是否全选变量
    boolean selectAll = false;
    // 文件
    File file;
    // 文件名
    String fileName = "无标题";
    // 用JPanel作画布
    JPanel pPanel = new JPanel(){
        public void paint(Graphics g){
            Graphics2D g2d = (Graphics2D)g;
            super.paint(g);
            for(Shape s : graphicsList){
                s.draw(g2d);
            }
            repaint();
        }
    };

    private void initFrame(){
        setLayout(new BorderLayout());
        setVisible(true);
    }
    // 按钮组
    ButtonGroup bg = new ButtonGroup();
    // 画笔粗细组
    ButtonGroup wg = new ButtonGroup();
    // 颜色组
    ButtonGroup cg = new ButtonGroup();
    // 声明监听器
    DrawListener dl;

    private void initpPanel(){
        add(pPanel, BorderLayout.CENTER); // 将画布添加到JFrame
        pPanel.setBackground(Color.WHITE);
        Graphics g = pPanel.getGraphics();
        dl = new DrawListener(g, bg, wg, cg, this, graphicsList);
        pencilButton.setSelected(true);
        pPanel.addMouseListener(dl);
        pPanel.addMouseMotionListener(dl);
        EventQueue.invokeLater( () -> addKeyListener(dl) );
    }

	// 右键菜单
    JPopupMenu pRightClickMenu = new JPopupMenu();
	JMenuItem pUndo = new JMenuItem("撤销(U)", KeyEvent.VK_U);
	JMenuItem pRedo = new JMenuItem("恢复(Y)", KeyEvent.VK_Y);
	JMenuItem pCut = new JMenuItem("剪切(T)", KeyEvent.VK_T);
	JMenuItem pCopy = new JMenuItem("复制(C)", KeyEvent.VK_C);
	JMenuItem pPaste = new JMenuItem("粘贴(P)", KeyEvent.VK_P);
	JMenuItem pSelectAll = new JMenuItem("全选(A)", KeyEvent.VK_A);
		
	private void initRightClickMenu(){
        pRightClickMenu.add(pUndo);
        pRightClickMenu.add(pRedo);
        pRightClickMenu.add(pCut);
        pRightClickMenu.add(pCopy);
        pRightClickMenu.add(pPaste);
        pRightClickMenu.add(pSelectAll);
		// 快捷键
		pUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
		pRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK));
        pCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK));
        pCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
        pPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK));
        pSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK));
        // 添加图标
        ImageIcon undoIcon = new ImageIcon(Main.class.getResource("../../../resources/images/undo.png"));
        pUndo.setIcon(undoIcon);
        ImageIcon redoIcon = new ImageIcon(Main.class.getResource("../../../resources/images/redo.png"));
        pRedo.setIcon(redoIcon);
        ImageIcon cutIcon = new ImageIcon(Main.class.getResource("../../../resources/images/cut.png"));
        pCut.setIcon(cutIcon);
        ImageIcon copyIcon = new ImageIcon(Main.class.getResource("../../../resources/images/copy.png"));
        pCopy.setIcon(copyIcon);
        ImageIcon pasteIcon = new ImageIcon(Main.class.getResource("../../../resources/images/paste.png"));
        pPaste.setIcon(pasteIcon);
        // 注册监听
        pUndo.addActionListener(this);
        pRedo.addActionListener(this);
        pCut.addActionListener(this);
        pCopy.addActionListener(this);
        pPaste.addActionListener(this);
        pSelectAll.addActionListener(this);

        pPanel.add(pRightClickMenu); // 将右键菜单添加到画布
        pPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mods = e.getModifiers();
                if((mods & InputEvent.BUTTON3_MASK) != 0){ // 点击右键
                    pRightClickMenu.show(pPanel,e.getX(),e.getY()); // 弹出菜单
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
    JPanel pToolButtons = new JPanel(new GridLayout(1, 4));
    JPanel pWidthBoard = new JPanel(new BorderLayout());
    JPanel pWidthButtons = new JPanel(new GridLayout(3, 1));
    JPanel pColorBoard = new JPanel(new BorderLayout());
    JPanel pCurrentColor = new JPanel();
    JPanel pColorButtons = new JPanel(new GridLayout(2, 10, 3, 3));

    // 工具栏
    PainterRadioButton cutButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/cut.png")), "剪切");
    PainterRadioButton copyButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/copy.png")), "复制");
    PainterRadioButton pasteButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/paste.png")), "粘贴");
    PainterRadioButton lineButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/line.png")), "直线");
    PainterRadioButton rectangleButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/rectangle.png")), "矩形");
    PainterRadioButton ovalButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/oval.png")), "椭圆形");
    ImageIcon pencilIcon = new ImageIcon(Main.class.getResource("../../../resources/images/pencil.png"));
    PainterRadioButton pencilButton = new PainterRadioButton(
            pencilIcon, "铅笔");
    ImageIcon eraserIcon = new ImageIcon(Main.class.getResource("../../../resources/images/eraser.png"));
    PainterRadioButton eraserButton = new PainterRadioButton(
            eraserIcon, "橡皮擦");
    PainterRadioButton fontButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/font.png")), "文本");
    PainterRadioButton pointerButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/pointer.png")), "选取");
    PainterRadioButton smallWidthButton = new PainterRadioButton(
    		new ImageIcon(Main.class.getResource("../../../resources/images/small.png")), "小");
    PainterRadioButton medianWidthButton = new PainterRadioButton(
    		new ImageIcon(Main.class.getResource("../../../resources/images/median.png")), "中");
    PainterRadioButton largeWidthButton = new PainterRadioButton(
    		new ImageIcon(Main.class.getResource("../../../resources/images/large.png")), "大");
//    PainterRadioButton widthButton = new PainterRadioButton(
//            new ImageIcon("resources//images//width.png"), "粗细");
    PainterButton currentColorButton = new PainterButton("", Color.BLACK);
    PainterRadioButton colorButton = new PainterRadioButton(
            new ImageIcon(Main.class.getResource("../../../resources/images/color_32px.png")), "更多颜色");

    private void initWidthMenu(){
        pWidthButtons.add(smallWidthButton);
        pWidthButtons.add(medianWidthButton);
        pWidthButtons.add(largeWidthButton);

        wg.add(smallWidthButton);
        wg.add(medianWidthButton);
        wg.add(largeWidthButton);
        smallWidthButton.setSelected(true);

        smallWidthButton.setActionCommand("small");
        medianWidthButton.setActionCommand("median");
        largeWidthButton.setActionCommand("large");

        // 快捷键
//        pSmall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
//        pMedian.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK));
//        pLarge.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK));
    }

    // 工具栏背景色
    Color ToolPanelBgColor = new Color(223, 223, 245);
    // 当前画笔颜色
    Color currentColor = Color.BLACK;
    // 当前画笔粗细
    int strokeSize = 1;
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
        pointerButton.addActionListener(this);
        smallWidthButton.addActionListener(this);
        medianWidthButton.addActionListener(this);
        largeWidthButton.addActionListener(this);
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
        bg.add(pointerButton);

        cutButton.setActionCommand("cut");
        copyButton.setActionCommand("copy");
        pasteButton.setActionCommand("paste");
        lineButton.setActionCommand("line");
        rectangleButton.setActionCommand("rectangle");
        ovalButton.setActionCommand("oval");
        pencilButton.setActionCommand("pencil");
        eraserButton.setActionCommand("eraser");
        fontButton.setActionCommand("font");
        pointerButton.setActionCommand("pointer");
        colorButton.setActionCommand("color");

        add(pToolPanel, BorderLayout.NORTH);
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
        pToolButtons.add(pointerButton);
        pToolButtons.setBackground(ToolPanelBgColor);
        pToolBoard.add(pToolButtons, BorderLayout.NORTH);
        pToolBoard.add(new PainterLabel("　", JLabel.CENTER), BorderLayout.CENTER);
        pToolBoard.add(new PainterLabel("工具", JLabel.CENTER), BorderLayout.SOUTH);
        pToolBoard.setBorder(BorderFactory.createEtchedBorder ());
        pToolBoard.setBackground(ToolPanelBgColor);
        pToolPanel.add(pToolBoard);

        pWidthButtons.add(smallWidthButton);
        pWidthButtons.add(medianWidthButton);
        pWidthButtons.add(largeWidthButton);
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

        for(int i = 0; i < colors.length; ++i){
        	Color c = colors[i];
        	PainterButton colorButton = new PainterButton("", c);
        	colorButton.setActionCommand(String.valueOf(i));
            colorButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {
                	if(selectAll){
                        for(Shape s : graphicsList){
        	    			s.color = c;
        	    		}
        			}
                	currentColor = c;
                	currentColorButton.setBackground(currentColor);
                	colorButton.setSelected(true);
                    if(dl.currentShape != null){
                        dl.currentShape.color = colorButton.getBackground();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });
            pColorButtons.add(colorButton);
            cg.add(colorButton);
        }
        pColorButtons.setBackground(ToolPanelBgColor);
        pCurrentColor.add(currentColorButton);
        pCurrentColor.setBackground(ToolPanelBgColor);
        pColorBoard.add(pCurrentColor, BorderLayout.WEST);
        pColorBoard.add(pColorButtons, BorderLayout.CENTER);
        pColorBoard.add(colorButton, BorderLayout.EAST);
        pColorBoard.add(new PainterLabel("颜色", JLabel.CENTER), BorderLayout.SOUTH);
        pColorBoard.setBorder(BorderFactory.createEtchedBorder ());
        pColorBoard.setBackground(ToolPanelBgColor);
        pToolPanel.add(pColorBoard);

        cutButton.addActionListener(this);
        copyButton.addActionListener(this);
        pasteButton.addActionListener(this);
        fontButton.addActionListener(this);
        pointerButton.addActionListener(this);
    }

    // 显示坐标栏
    JToolBar pToolBar = new JToolBar("坐标栏");
    JLabel pPositionIcon = new JLabel(new ImageIcon(Main.class.getResource("../../../resources/images/coord.png")));
    JLabel pPosition = new JLabel("                      ");

    JLabel pDimensionIcon = new JLabel(new ImageIcon(Main.class.getResource("../../../resources/images/size.png")));
    JLabel pDimension = new JLabel();
    JLabel pAction = new JLabel("动作：铅笔         ");
    JLabel pWidth = new JLabel("画笔宽度：1px         ");
    JLabel pShape = new JLabel("选中：");
    private void initToolBar(){
        pPanel.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
                pPosition.setText("  " + e.getX() + ", " + e.getY() + "像素        ");
            }

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				pPosition.setText("  " + e.getX() + ", " + e.getY() + "像素         ");
			}
    	});
        pPanel.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                pDimension.setText("  " + pPanel.getWidth() + " × " + pPanel.getHeight() + "像素         ");
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        add(pToolBar, BorderLayout.SOUTH);
        pToolBar.add(pPositionIcon);
        pToolBar.add(pPosition);
        pToolBar.addSeparator(new Dimension(16, 16));
        pToolBar.add(pDimensionIcon);
        pToolBar.add(pDimension);
        pToolBar.addSeparator(new Dimension(16, 16));
        pToolBar.add(pAction);
        pToolBar.addSeparator(new Dimension(16, 16));
        pToolBar.add(pWidth);
        pToolBar.addSeparator(new Dimension(16, 16));
        pToolBar.add(pShape);
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

    private void initWindowClose(){
    	/**
    	 * 使用下面的方法，仅触发windowClosing方法；
    	 * 若不使用下面的方法，还会调用dispose()方法会释放屏幕资源，无法做出取消按钮的效果。
    	 */
    	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
                exit();
            }
        });
    }
	
	// 程序初始化
	private void initProcedure(){
        initFrame(); // 初始化布局
		initMenus(); // 初始化菜单栏
        initpPanel(); // 初始化画布
		initRightClickMenu(); // 右键弹出菜单
		initWidthMenu(); // 粗细选择菜单
        initToolPanel(); // 初始化工具栏
        initToolBar(); // 初始化坐标栏
		setSize(1100,600); // 设置窗口大小
        centerWindow(); // 窗口居中函数
		initStyle(); // 初始化风格
		// 报错导致无法关闭程序，故注释掉
//		setIconImage(
//				Toolkit.getDefaultToolkit().getImage(
//						Main.class.getResource(
//								"../../../resources/images/icon_2g.png")));
        initWindowClose();
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
            dl.cut();
        }
        else if(e.getSource() == pCopy || e.getSource() == copyButton){
            dl.copy();
        }
        else if(e.getSource() == pPaste || e.getSource() == pasteButton){
            dl.paste();
        }
        else if(e.getSource() == pSelectAll){
            selectAll();
            pAction.setText("动作：全选         ");
        }
        else if(e.getSource() == lineButton){
            line();
            pAction.setText("动作：直线         ");
        }
        else if(e.getSource() == rectangleButton){
            rectangle();
            pAction.setText("动作：矩形         ");
        }
        else if(e.getSource() == ovalButton){
            oval();
            pAction.setText("动作：椭圆形         ");
        }
        else if(e.getSource() == pencilButton){
            pencil();
            pAction.setText("动作：铅笔         ");
        }
        else if(e.getSource() == fontButton){
            font();
            pAction.setText("动作：添加文本         ");
        }
        else if(e.getSource() == pointerButton){
            pointer();
            pAction.setText("动作：选取         ");
        }
        else if(e.getSource() == eraserButton){
            eraser();
            pAction.setText("动作：橡皮擦         ");
        }
        else if(e.getSource() == largeWidthButton){
        	strokeSize = 10;
        	if(selectAll){
                for(Shape s : graphicsList){
	    			s.strokeSize = strokeSize;
	    		}
			}
        	else{
                if(dl.currentShape != null){
                    dl.currentShape.strokeSize = strokeSize;
                }
        	}
        	pWidth.setText("画笔宽度：" + strokeSize + "px         ");
        }
        else if(e.getSource() == medianWidthButton){
        	strokeSize = 5;
        	if(selectAll){
                for(Shape s : graphicsList){
	    			s.strokeSize = strokeSize;
	    		}
			}
        	else{
                if(dl.currentShape != null){
                    dl.currentShape.strokeSize = strokeSize;
                }
        	}
            pWidth.setText("画笔宽度：" + strokeSize + "px         ");
        }
        else if(e.getSource() == smallWidthButton){
        	strokeSize = 1;
        	if(selectAll){
                for(Shape s : graphicsList){
	    			s.strokeSize = strokeSize;
	    		}
			}
        	else{
                if(dl.currentShape != null){
                    dl.currentShape.strokeSize = strokeSize;
                }
        	}
            pWidth.setText("画笔宽度：" + strokeSize + "px         ");
        }
        else if(e.getSource() == colorButton){
            setColor();
        }
    }

    private void setColor() {
		Color tmpColor = JColorChooser.showDialog(this, "编辑颜色", pPanel.getBackground());
		if(tmpColor != null){
			if(selectAll){
                for(Shape s : graphicsList){
	    			s.color = tmpColor;
	    		}
			}
			currentColor = tmpColor;
			currentColorButton.setBackground(currentColor);
            if(dl.currentShape != null){
                dl.currentShape.color = currentColor;
            }
        }
    }

    private void pointer(){
        System.out.println("dl.currentShape = " + dl.currentShape);
    }

    private void eraser() {
//    	Cursor eraserCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//    			eraserIcon.getImage(), new Point(0, 0), "eraser");
//    	setCursor(eraserCursor);
    }

    private void font() {
//    	Cursor inputCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//    			new ImageIcon("resources//images//input.png").getImage(), new Point(0, 0), "eraser");
//    	setCursor(inputCursor);
    }

    private void pencil() {
//    	Cursor pencilCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//    			pencilIcon.getImage(), new Point(10, 10), "pencil");
//    	setCursor(pencilCursor);
    }

    private void oval() {
        bg.clearSelection();
        ovalButton.setSelected(true);
    }

    private void rectangle() {
        bg.clearSelection();
        rectangleButton.setSelected(true);
    }

    private void line() {
        bg.clearSelection();
        lineButton.setSelected(true);
    }

    private void selectAll() {
    	selectAll = !selectAll;
    }

    private void paste() {
    }

    private void copy() {
    }

    private void cut() {
    }

    private void redo() {
    	if(!redoStack.empty()){
    		Shape s = redoStack.pop(); // 删除栈顶元素并返回栈顶元素
        	if(s != null){
        		graphicsList.add(s);
        	}
    	}
    }

    private void undo() {
    	int index = graphicsList.size() - 1;
    	if(index >= 0){
    		Shape s = graphicsList.get(index);
    		graphicsList.remove(index); 
    		redoStack.push(s);
    	}
    }

    private void about() {
        JOptionPane.showMessageDialog(this, "" +
                "画图\n" +
                "版本 1.0\n" +
                "版权所有 © 2017 Rush Chuh。保留所有权利。\n" +
                "E-mail: rushzhu95@hotmail.com\n" +
                "Website: http://zhurunshi.com/", "关于“画图”", JOptionPane.PLAIN_MESSAGE);
    }

    private void help() {
    	JOptionPane.showMessageDialog(this, "" +
                "==菜单==\n" +
                "新建(Ctrl+N)：若画板是空白的，则直接新建；否则会询问您是否保存当前画板，然后新建。\n" +
                "打开(Ctrl+O)：打开文件，若画板为空，直接打开文件；否则会询问您是否保存当前画板，然后打开文件。\n" +
                "保存(Ctro+S)：保存当前画板。\n" +
                "背景颜色：选择画板的背景颜色。注意：该背景是不能被保存的，只能保存当前画布上面的图形。\n" +
                "退出(Alt+F4)：。\n" +
                "帮助：查看帮助文档。\n" +
                "关于：本软件的版权信息。\n" +
                "==工具==\n" +
                "剪切、复制(Ctrl+X/C)：用法不多说。注意：要先选择“选取”按钮，然后点击图形，再进行剪切和复制操作。\n" +
                "粘贴(Ctrl+V)：先点击想要粘贴的位置，然后使用快捷键，或者使用右键菜单中的功能。\n" +
                "直线：先选择“直线”按钮，然后点击鼠标不放开，拖拽形成一条直线。\n" +
                "矩形：先选择“矩形”按钮，然后点击鼠标不放开，向右下侧拖拽形成一个矩形。注意：不能向别的方向拖拽。\n" +
                "椭圆形：先选择“椭圆形”按钮，然后点击鼠标不放开，向右下侧拖拽形成一个椭圆形。注意：不能向别的方向拖拽。\n" +
                "铅笔：先选择“铅笔”按钮，跟随鼠标路径划线。\n" + 
                "文本：先选择“文本”按钮，然后点击指定位置插入文本。注意：文本的放大缩小功能使用快捷键(+/-)控制。\n" + 
                "橡皮擦：先选择“橡皮擦”按钮，擦掉画板上的痕迹。\n" + 
                "选取：先选择“选取”按钮，然后点击画板上的图形获得选中状态，可以参考下方的提示栏中的提示。\n" + 
                "粗细：先选择其中一个“粗细”按钮，然后进行图形的绘制，可绘制出指定宽度的图形。于此同时，还可以根据快捷键(b/l)来递增/递减画笔的宽度，达到细微的调整。\n" + 
                "颜色：可以从给定的色板上选取颜色，也可以自定义颜色，通过右侧的颜色选取按钮进行自定义颜色的选择。\n" + 
                "撤销/恢复：通过右键菜单或快捷键(Ctrl+Z/Y)进行操作，是图形级的撤销，即不能撤销颜色以及粗细等参数的操作。\n" + 
                "全选：通过右键菜单或快捷键(Ctrl+A)进行操作，全选完成后，可以批量修改图形的宽度以及颜色等参数。\n" + 
                "==提示栏==\n" +
                "画板下方有鼠标的实时坐标、画板尺寸、当前动作、画笔宽度、以及选中图形提示。\n" + 
                "以上。", "帮助", JOptionPane.PLAIN_MESSAGE);
    }

    private void exit() {
        if( !( pPanel.getBackground() == Color.WHITE && graphicsList.isEmpty() ) ){
            /* 弹出是否保存，如果选是，保存文件之后退出；
    		 * 如果选否，不保存文件直接退出；
    		 * 如果选取消，关闭对话框
    		 */
            int returnValue = JOptionPane.showConfirmDialog(
                    this, "您想将更改保存到 " + fileName + " 吗？", "画图", JOptionPane.YES_NO_CANCEL_OPTION);
            if( returnValue == JOptionPane.YES_OPTION ){
                if( save() ){
                    System.exit(0);
                }
                else{
                    JOptionPane.showMessageDialog(this, "保存过程中出现异常，保存失败，请重新尝试保存该图片", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(returnValue == JOptionPane.NO_OPTION){
                System.exit(0);
            }
        }
        else{
            System.exit(0);
        }
    }

    private void setBgColor() {
        Color tmpColor = JColorChooser.showDialog(this, "背景颜色", pPanel.getBackground());
        if(tmpColor != null){
            bgColor = tmpColor;
            pPanel.setBackground(bgColor);
        }
    }

    private boolean save() {
        boolean res = false;
    	if(file != null){
//    		BufferedImage bufferedImage = createImage(pPanel);
//        	try {
//				res = ImageIO.write(bufferedImage, "png", file);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            res = StreamUtils.writeObject(graphicsList, file);
    	}
    	else{
    		JFileChooser fileChooser = new JFileChooser();
        	FileNameExtensionFilter filter = new FileNameExtensionFilter("CAD (*.cad)", "cad");
        	fileChooser.setSelectedFile(new File("无标题.cad"));
        	fileChooser.setFileFilter(filter);
    		int returnValue = fileChooser.showSaveDialog(this);
    		if(returnValue==JFileChooser.APPROVE_OPTION){
    			file = fileChooser.getSelectedFile();
    			String fileName = file.getName();
    			if(fileName.indexOf(".cad") < 0){
    				file = new File(fileChooser.getCurrentDirectory(), fileName + ".cad");
    			}
                res = StreamUtils.writeObject(graphicsList, file);
                if( !res ){
                    file = null;
                }
//    			BufferedImage bufferedImage = createImage(pPanel);
//    			try {
//					res = ImageIO.write(bufferedImage, "png", file);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					file = null;
//					e.printStackTrace();
//				}
    		}
    	}
        if( file != null ){
            fileName = file.getName();
        }
        else{
            fileName = "无标题";
        }
        this.setTitle(fileName + " - 画图");
        return res;
    }
    
//    private BufferedImage createImage(JPanel p) {
//		// TODO Auto-generated method stub
//    	int totalWidth = p.getWidth();
//    	int totalHeight = p.getHeight();
//        BufferedImage panelImage = new BufferedImage(
//    			totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
//    	Graphics2D g2d = panelImage.createGraphics();
//    	g2d.setColor(Color.WHITE);
//    	g2d.fillRect(0, 0, totalWidth, totalHeight);
//    	g2d.translate(0, 0);
//    	p.paint(g2d);
//    	g2d.dispose();
//    	return panelImage;
//	}

    private void openCore(JFileChooser fileChooser){
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("CAD (*.cad)", "cad");
    	fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(this);
        if( returnValue == JFileChooser.APPROVE_OPTION ){
            file = fileChooser.getSelectedFile();
            ArrayList<Shape> tmpList = new ArrayList<Shape>( StreamUtils.<Shape>readObjectForList(file) );
            if(!tmpList.isEmpty()){
                graphicsList.clear();
                for(Shape s : tmpList){
                    graphicsList.add(s);
                }
                for(Shape s : graphicsList){
                    s.draw(dl.g);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "文件打开过程中出现异常，文件打开失败", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

	private void open() {
        /* 如果当前画布上没有任何内容，直接打开文件；
         * 否则，先保存，再打开文件。
         */
        JFileChooser fileChooser = new JFileChooser();
		if( pPanel.getBackground() == Color.WHITE && graphicsList.isEmpty() ){
            openCore(fileChooser);
    	}
		else{
			int returnValue = JOptionPane.showConfirmDialog(this, "您想将更改保存到 " + fileName + " 吗？", "画图", JOptionPane.YES_NO_CANCEL_OPTION);
			if( returnValue == JOptionPane.YES_OPTION ){
                if( save() ){
                    openCore(fileChooser);
                }
                else{
                    JOptionPane.showMessageDialog(this, "保存过程中出现异常，保存失败，请重新尝试保存该图片", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(returnValue == JOptionPane.NO_OPTION){
            	pPanel.setBackground(Color.WHITE);
                graphicsList.clear();
                openCore(fileChooser);
            }
		}
        if( file != null ){
            fileName = file.getName();
        }
        else{
            fileName = "无标题";
        }
        this.setTitle(fileName + " - 画图");
    }

    private void newFile() {
    	if( !( pPanel.getBackground() == Color.WHITE && graphicsList.isEmpty() ) ){
            /* 弹出是否保存，如果选是，保存文件之后打开新画布；
    		 * 如果选否，不保存文件直接打开新画布；
    		 * 如果选取消，关闭对话框。
    		 */
            int returnValue = JOptionPane.showConfirmDialog(this, "您想将更改保存到 " + fileName + " 吗？", "画图", JOptionPane.YES_NO_CANCEL_OPTION);
            if( returnValue == JOptionPane.YES_OPTION ){
                if( save() ){
                    pPanel.setBackground(Color.WHITE);
                    graphicsList.clear();
                    file = null;
                }
                else{
                    JOptionPane.showMessageDialog(this, "保存过程中出现异常，保存失败，请重新尝试保存该图片", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(returnValue == JOptionPane.NO_OPTION){
                pPanel.setBackground(Color.WHITE);
                graphicsList.clear();
                file = null;
            }
    	}
        if( file != null ){
            fileName = file.getName();
        }
        else{
            fileName = "无标题";
        }
        this.setTitle( fileName + " - 画图" );
    }
}
