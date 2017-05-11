/**
* @author RushChu(朱润石)
*
*/

import java.awt.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.undo.UndoManager;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;

public class J201326810327_4 extends JFrame implements ActionListener,DocumentListener
{
	//定义变量************************************************************************
	
	//1 菜单栏
	JMenuBar mb=new JMenuBar();
	
		//1.1 “文件”菜单
		RCMenu mFile=new RCMenu("文件(F)",KeyEvent.VK_F);
	
			//1.1.1 新建
			JMenuItem miNew=new JMenuItem("新建(N)",KeyEvent.VK_N);
			//1.1.2 打开
			JMenuItem miOpen=new JMenuItem("打开(O)...",KeyEvent.VK_O);
			//1.1.3 保存
			JMenuItem miSave=new JMenuItem("保存(S)",KeyEvent.VK_S);
			//1.1.4 字体
			JMenuItem miFont=new JMenuItem("字体(F)...",KeyEvent.VK_F);
			//1.1.5 背景颜色
			JMenuItem miBackgroundColor=new JMenuItem("背景颜色(B)...",KeyEvent.VK_C);
			//1.1.6 退出
			JMenuItem miQuit=new JMenuItem("退出(X)",KeyEvent.VK_X);
		
		//1.2 “Java上机题目”菜单
		RCMenu mJava=new RCMenu("Java上机题目",KeyEvent.VK_J);
		
			//1.2.1 回文数
			JMenuItem miPalindrome=new JMenuItem("回文数(P)...",KeyEvent.VK_P);
			//1.2.2 数字与英文互译
			JMenuItem miTranslate=new JMenuItem("数字与英文互译(T)...",KeyEvent.VK_T);
			//1.2.3 统计英文数据
			JMenuItem miCount=new JMenuItem("统计英文数据(C)...",KeyEvent.VK_C);
			//1.2.4 手机号码合法性判断
			JMenuItem miJudge=new JMenuItem("手机号码合法性判断(J)...",KeyEvent.VK_J);
			//1.2.5 文本文件求和
			JMenuItem miSum=new JMenuItem("文本文件求和(S)...",KeyEvent.VK_S);
			
		//1.3 “通讯录”菜单
		RCMenu mContacts=new RCMenu("通讯录(C)",KeyEvent.VK_C);
		
			//1.3.1 维护
			JMenuItem miMaintain=new JMenuItem("维护(M)...",KeyEvent.VK_M);
			//1.3.2 存储文件设置
			JMenuItem miSetup=new JMenuItem("存储文件设置(S)...",KeyEvent.VK_S);
			
	//2 文本框
	JTextPane tp=new JTextPane();
	
	
	
	//3 滚动条
	JScrollPane sp=new JScrollPane(tp);
	
	//4 右键菜单
	JPopupMenu pm=new JPopupMenu();;
	
		//4.1 撤销
		JMenuItem miUndo=new JMenuItem("撤销键入(U)",KeyEvent.VK_U);
		//恢复
		JMenuItem miRedo=new JMenuItem("恢复键入(Y)",KeyEvent.VK_Y);
		//4.2 剪切
		JMenuItem miCut=new JMenuItem("剪切(T)",KeyEvent.VK_T);
		//4.3 复制
		JMenuItem miCopy=new JMenuItem("复制(C)",KeyEvent.VK_C);
		//4.4 粘贴
		JMenuItem miPaste=new JMenuItem("粘贴(P)",KeyEvent.VK_P);
		//4.6全选
		JMenuItem miSelectAll=new JMenuItem("全选(A)",KeyEvent.VK_A);
		
	//5 工具栏
	JToolBar mtb=new JToolBar();
	
	RCButton newB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("new.png")),"新建");
	RCButton openB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("open.png")),"打开");
	RCButton saveB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("save.png")),"保存");
	RCButton cutB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("cut.png")),"剪切");
	RCButton copyB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("copy.png")),"复制");
	RCButton pasteB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("paste.png")),"粘贴");
	RCButton undoB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("undo.png")),"撤销键入");
	RCButton redoB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("redo.png")),"恢复键入");
	RCButton fontB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("font.png")),"字体");
	
	//定义临时文件
	File tmpFile;

	//文本是否改动过的标识
	boolean textChanged = false;
	//临时标识
	boolean flag=false;
	//撤销管理器
	private UndoManager undoMGR=new UndoManager();
	//文件路径
	File filepath;
	//判断文件有没有路径
	boolean textpath=false;
	//通讯录
	private String fName = null; 
	private String cfName = new String("Comunity.dat");
	//变量定义完**********************************************************************

	//字体值
	private Font initFont;
	
	//背景颜色
	private Color c;

	//配置文件
	private File configFile;
	
	//定义方法************************************************************************
	
	//文本区
	private void initText()
	{
		tp.getDocument().addDocumentListener(this);
		tp.getDocument().addUndoableEditListener(undoMGR);
	}
	
	//5 工具栏
	private void initToolBar()
	{
		Container c=getContentPane();
		c.add(BorderLayout.NORTH, mtb);
		//mtb.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		newB.setBorder(BorderFactory.createEmptyBorder());
		openB.setBorder(BorderFactory.createEmptyBorder());
		saveB.setBorder(BorderFactory.createEmptyBorder());
		//
		cutB.setBorder(BorderFactory.createEmptyBorder());
		copyB.setBorder(BorderFactory.createEmptyBorder());
		pasteB.setBorder(BorderFactory.createEmptyBorder());
		undoB.setBorder(BorderFactory.createEmptyBorder());
		redoB.setBorder(BorderFactory.createEmptyBorder());
		//
		fontB.setBorder(BorderFactory.createEmptyBorder());
		
		mtb.add(newB);
		mtb.add(openB);
		mtb.add(saveB);
		mtb.addSeparator();
		mtb.add(cutB);
		mtb.add(copyB);
		mtb.add(pasteB);
		mtb.addSeparator();
		mtb.add(undoB);
		mtb.add(redoB);
		mtb.addSeparator();
		mtb.add(fontB);
		
		newB.addActionListener(this);
		openB.addActionListener(this);
		saveB.addActionListener(this);
		cutB.addActionListener(this);
		copyB.addActionListener(this);
		pasteB.addActionListener(this);
		undoB.addActionListener(this);
		redoB.addActionListener(this);
		fontB.addActionListener(this);
		
		//设置可以浮动
		mtb.setFloatable(true);
	}
	
	//1 菜单栏
	private void initMenus()
	{	
		//将菜单栏添加到窗口上
		setJMenuBar(mb);
		
		//依次添加“文件”菜单上的项
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.addSeparator();
		mFile.add(miFont);
		mFile.add(miBackgroundColor);
		mFile.addSeparator();
		mFile.add(miQuit);
		
		//将“文件”菜单添加到菜单栏上
		mb.add(mFile);
		
		//注册监听者
		miNew.addActionListener(this);
		miOpen.addActionListener(this);
		miSave.addActionListener(this);
		miFont.addActionListener(this);
		miBackgroundColor.addActionListener(this);
		miQuit.addActionListener(this);
		
		//添加图标
		ImageIcon newIcon=new ImageIcon(J201326810327_4.class.getResource("new.png"));
		miNew.setIcon(newIcon);
		ImageIcon openIcon=new ImageIcon(J201326810327_4.class.getResource("open.png"));
		miOpen.setIcon(openIcon);
		ImageIcon saveIcon=new ImageIcon(J201326810327_4.class.getResource("save.png"));
		miSave.setIcon(saveIcon);
		ImageIcon fontIcon=new ImageIcon(J201326810327_4.class.getResource("font.png"));
		miFont.setIcon(fontIcon);
		
		//为“新建”设置快捷键
		miNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
		//为“打开”设置快捷键
		miOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));
		//为“保存”设置快捷键
		miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
		//为“退出”设置快捷键
		miQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_DOWN_MASK));
		
		//依次添加“Java上机题目”菜单上的项
		mJava.add(miPalindrome);
		mJava.add(miTranslate);
		mJava.add(miCount);
		mJava.add(miJudge);
		mJava.add(miSum);
		
		//将“Java上机题目”菜单添加到菜单栏上
		mb.add(mJava);
		
		//注册监听者
		miPalindrome.addActionListener(this);
		miTranslate.addActionListener(this);
		miCount.addActionListener(this);
		miJudge.addActionListener(this);
		miSum.addActionListener(this);
		
		//依次添加“通讯录”菜单上的项
		mContacts.add(miMaintain);
		mContacts.add(miSetup);
		
		//将“通讯录”菜单添加到菜单栏上
		mb.add(mContacts);
		
		//注册监听者
		miMaintain.addActionListener(this);
		miSetup.addActionListener(this);
	}
	
	//2 文本框(已经在之前定义过了)

	//3 滚动条
	private void initScrollPane()
	{
		//给文本框添加滚动条
		add(sp);
	}
	
	//4 右键菜单
	private void initRightClick()
	{	
		//依次添加右键菜单上的项
		pm.add(miUndo);
		pm.add(miRedo);
		pm.addSeparator();
		pm.add(miCut);
		pm.add(miCopy);
		pm.add(miPaste);
		pm.addSeparator();
		pm.add(miSelectAll);
		
		miUndo.addActionListener(this);
		miRedo.addActionListener(this);
		miCut.addActionListener(this);
		miCopy.addActionListener(this);
		miPaste.addActionListener(this);
		miSelectAll.addActionListener(this);
		
		miUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
		miRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK));
		
		//添加右键菜单
		add(pm);
		
		//先加入鼠标的单击事件
		tp.addMouseListener(new java.awt.event.MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				int mods=e.getModifiers();
				
				//鼠标右键
				if((mods&InputEvent.BUTTON3_MASK)!=0)
				{
					//弹出菜单
					pm.show(tp,e.getX(),e.getY());
				}
			}
		});
	}
	
	//界面风格
	private void initUI()
	{
		try
		{
		String lfClassName="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			
		//Windows风格
		UIManager.setLookAndFeel(lfClassName);
				
		//更新UI
		SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e){}
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
		setLocation((int)(dm.getWidth()-getWidth())/2,(int)(dm.getHeight()-getHeight()-taskBarHeight)/2);
	}
	
	//程序的全部方法
	private void initProcedure()
	{
		//载入配置
		initConfig();
		
		//菜单
		initMenus();
		
		//工具栏
		initToolBar();
					
		//右键菜单
		initRightClick();
		
		//文本区
		initText();
					
		//滚动条
		initScrollPane();
					
		//设置窗口大小
		setSize(700,500);
					
		//窗口居中函数
		centerWindow();
					
		//界面风格
		initUI();
				
		//设置close按钮
		setDefaultCloseOperation(0);	
		
		//写入配置
		writeConfig();
	}
	

	//监听
	public void actionPerformed(ActionEvent e)
	{
		//1.1 “文件”菜单
		if(e.getSource()==miNew||e.getSource()==newB)
			New();
		else if(e.getSource()==miOpen||e.getSource()==openB)
			Open();
		else if(e.getSource()==miSave||e.getSource()==saveB)
			Save();
		else if(e.getSource()==miFont||e.getSource()==fontB)
			Font();
		else if(e.getSource()==miBackgroundColor)
			BackgroundColor();
		else if(e.getSource()==miQuit)
			Quit();
		
		//1.2 “Java上机题目”菜单
		if(e.getSource()==miPalindrome)
			Palindrome();
		else if(e.getSource()==miTranslate)
			Translate();
		else if(e.getSource()==miCount)
			try {
				Count();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		else if(e.getSource()==miJudge)
			Judge();
		else if(e.getSource()==miSum)
			try {
				Sum();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		//1.3 “通讯录”菜单
		if(e.getSource()==miMaintain)
			Maintain();
		else if(e.getSource()==miSetup)
			Setup();
		
		//4 右键菜单
		if(e.getSource()==miUndo||e.getSource()==undoB)
			Undo();
		else if(e.getSource()==miRedo||e.getSource()==redoB)
			Redo();
		else if(e.getSource()==miCut||e.getSource()==cutB)
			Cut();
		else if(e.getSource()==miCopy||e.getSource()==copyB)
			Copy();
		else if(e.getSource()==miPaste||e.getSource()==pasteB)
			Paste();
		else if(e.getSource()==miSelectAll)
			SelectAll();
	}

	private void Maintain() {
	    try {
	        new MyComunity(this, this.cfName);
	      } catch (MyException localMyException) {
	        JOptionPane.showMessageDialog(this, localMyException.getMessage(), "Java程序设计综合实验", 0);
	      }
		
	}

	private void Setup() {
	    JFileChooser localJFileChooser = new JFileChooser();
	    localJFileChooser.setSelectedFile(new File(this.cfName));
	    if (localJFileChooser.showDialog(this, "选择文件") == 0)
	      this.cfName = localJFileChooser.getSelectedFile().getPath();
		
	}

	//监听方法************************************************************************	
	private void New()
	{
		int returnValue;
	
		while(textChanged)
		{
			returnValue = JOptionPane.showConfirmDialog(this, "是否将更改保存到 文本 中？", "Java程序设计综合实验", 1);
			if(returnValue==JOptionPane.YES_OPTION)
				Save();
			else if(returnValue==JOptionPane.NO_OPTION)
			{
				tp.setText("");
				setTitle("201326810327-朱润石-Java 程序设计综合实验");
				textChanged=false;
				return;
			}
			else
				return;
			if(flag)
				break;
		}
		if(!textChanged)
		{
			tp.setText("");
			textChanged=false;
		}
	}
	
	private void Open()
	{
		int returnValue;
		
		//括号里面是打开的默认路径
		JFileChooser openFC= new JFileChooser();
		returnValue=openFC.showOpenDialog(this);
		if(returnValue==JFileChooser.APPROVE_OPTION)
		{
			//得到选择的文件
			File f=openFC.getSelectedFile();
			//记录路径
			filepath=f;
			textpath=true;
			
			setTitle("201326810327-朱润石-Java 程序设计综合实验"+"-"+filepath.getName());
			try
			{
				//将文件放在输入流中
				InputStream is=new FileInputStream(f);
				//读入流
				tp.read(is,"tmp");
				tp.getDocument().addDocumentListener(this);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void Save()
	{
		//文件是原本就存在的
		if(textpath)
		{
			try
			{
				FileOutputStream os=new FileOutputStream(filepath);
				os.write(tp.getText().getBytes());
				os.close();
				//保存成功
				textChanged=false;
				return;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		//括号里面是保存的默认路径
		JFileChooser saveFC= new JFileChooser();
		int returnValue=saveFC.showSaveDialog(this);
		if(returnValue==JFileChooser.APPROVE_OPTION)
		{
			File f=saveFC.getSelectedFile();
			try
			{
				FileOutputStream os=new FileOutputStream(f);
				os.write(tp.getText().getBytes());
				os.close();
				//保存成功
				textChanged=false;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
			flag=true;
	}
	
	private void Font()
	{
		RCFont fontchooser=new RCFont();
		initFont=fontchooser.showDialog(this,"请选择你喜欢的字体");
		tp.setFont(initFont);
	}
	
	private void BackgroundColor()
	{
		c=JColorChooser.showDialog(this,"背景颜色",tp.getBackground());
		//点击取消
		if(c==null) 
			c=tp.getBackground();
		tp.setBackground(c);
	}
	
	private void Quit()
	{
		int returnValue;

		if(!textChanged)
		{
			writeConfig();
			System.exit(0);
		}
		while(textChanged)
		{
			if(textpath)
			{
				returnValue = JOptionPane.showConfirmDialog(this, "是否将更改保存到 "+filepath.getName()+" 中？", "Java程序设计综合实验", 1);
				if(returnValue==JOptionPane.YES_OPTION)
				{
					writeConfig();
					Save();
					System.exit(0);
				}
				else if(returnValue==JOptionPane.NO_OPTION)
				{
					writeConfig();
					System.exit(0);
				}
				else
					return;
				if(flag)
					break;
			}
			returnValue = JOptionPane.showConfirmDialog(this, "是否将更改保存到 文本 中？", "Java程序设计综合实验", 1);
			if(returnValue==JOptionPane.YES_OPTION)
			{
				Save();
				writeConfig();
				System.exit(0);
			}
			else if(returnValue==JOptionPane.NO_OPTION)
			{
				writeConfig();
				System.exit(0);
			}
			else
				return;
			if(flag)
				break;
		}
	}
	
	private void Palindrome()
	{
		String str;
			str=JOptionPane.showInputDialog(this,"请输入1-99999之间的整数", "回文数",1);
			//空字符串
			if(str.equals(""))
			{
				JOptionPane.showMessageDialog(this,"请输入内容", "回文数",1);
				return;
			}
			else
			{
				//输入不全为数字
				if(!RCPalindrome.isNumeric(str))
					JOptionPane.showMessageDialog(this,"请输入数字", "回文数",1);
				else
				{
					int i=Integer.parseInt(str);
					str=Integer.toString(i);
					//去掉字符串的空格
					str=str.trim();
					//数值越界判断
					if(RCPalindrome.trans(str)==-1)
						JOptionPane.showMessageDialog(this,"请输入1-99999之间的整数", "回文数",1);
				}
			}
		if(RCPalindrome.isNumeric(str)&&str.length()<6)
		{
			if(RCPalindrome.tell(str))
				JOptionPane.showMessageDialog(this,new StringBuilder().append(str).append("是回文数"),"回文数",1);
			else
				JOptionPane.showMessageDialog(this,new StringBuilder().append(str).append("不是回文数"),"回文数",1);
		}
	}
	
	private void Translate()
	{
		String str;
		str=JOptionPane.showInputDialog(this,"请输入一个小于100的正整数（中英文均可）", "数字与英文互译",1);
		if(str.equals(""))
		{
			JOptionPane.showMessageDialog(this,"请输入内容", "数字与英文互译",1);
			return;
		}
		if(RCTranslate.isNumeric(str))
		{
			if(RCTranslate.NtE(str)=="error")
				JOptionPane.showMessageDialog(this,"输入的内容有误，请重新输入。", "数字与英文互译",1);
			else
				JOptionPane.showMessageDialog(this,new StringBuilder().append(RCTranslate.NtE(str)),"数字与英文互译",1);
		}
		else
		{
			if(RCTranslate.EtN(str)==-1)
				JOptionPane.showMessageDialog(this,"输入的内容有误，请重新输入。", "数字与英文互译",1);
			else
				JOptionPane.showMessageDialog(this,new StringBuilder().append(RCTranslate.EtN(str)),"数字与英文互译",1);
		}
	}
	
	private void Count() throws IOException
	{
		//创建临时文件
		tmpFile=new File("D:/data.tmp");
		tmpFile.createNewFile();   
		String sets = "attrib +H \"" + tmpFile.getAbsolutePath() + "\"";  
		//输出命令串   
		Runtime.getRuntime().exec(sets);
		if(tmpFile.exists()) 
		{
			try {
				FileOutputStream out = new FileOutputStream(tmpFile);  //得到文件输出流
				out.write(tp.getText().getBytes()); //写出文件  
				out.close();
		    	} catch (Exception ex) {
		    		ex.printStackTrace(); //输出出错信息
		    	}
			if(tmpFile!=null)
			{
				RCCount_threeNumber tmp=RCCount.statis(tmpFile);
				int j = tmp.a; int k = tmp.b; int m = tmp.c;
			      JOptionPane.showMessageDialog(this, new StringBuilder().append("有").append(j).append("个以字母w开头的单词；\n").append("有").append(k).append("个含“or”字符串的单词；\n").append("有").append(m).append("个长度为3的单词。").toString(), "单词统计", 1);
			}
		}
		tmpFile.delete();
	}
	
	private void Judge()
	{
		String str;
		str=JOptionPane.showInputDialog(this,"请输入需要判断合法性的手机号码", "手机号码合法性判断",1);
		//下面是根据优先级顺序做出的设计
		if(RCJudge.draw(str)!=13)
			JOptionPane.showMessageDialog(this,"手机号码长度不合法", "手机号码合法性判断",1);
		else
		{
			if(RCJudge.charJudge(str)!=1)
				JOptionPane.showMessageDialog(this,"手机号码中包含非数字的字符","手机号码合法性判断",1);
			else
			{
				if(RCJudge.headJudge(str)!=1)
					JOptionPane.showMessageDialog(this,"手机号码不是以86打头","手机号码合法性判断",1);
				else
				{
					if(str.length()==13)
						JOptionPane.showMessageDialog(this,"手机号码合法","手机号码合法性判断",1);
					else
					{
						if(RCJudge.posJudge(str)==1)
							JOptionPane.showMessageDialog(this,"手机号码合法","手机号码合法性判断",1);
						else
							JOptionPane.showMessageDialog(this,"其他情况","手机号码合法性判断",1);
					}
				}
			}
		}
	}
	
	private void Sum() throws IOException
	{
		if(tp.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "请输入需要计算的变量","提示",1);
			return;
		}
		//创建临时文件
		tmpFile=new File("D:/data.tmp");
		tmpFile.createNewFile();   
		String sets = "attrib +H \"" + tmpFile.getAbsolutePath() + "\"";  
		//输出命令串   
		Runtime.getRuntime().exec(sets);
		if(tmpFile.exists()) 
		{
			try
			{
				FileOutputStream out = new FileOutputStream(tmpFile);  //得到文件输出流
				out.write(tp.getText().getBytes()); //写出文件  
				out.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace(); //输出出错信息
			}
			if(tmpFile!=null)
			{
				RCSum.calculateSum(this.tmpFile, this);
			}
		}
		tmpFile.delete();
	}
	
	private void Undo()
	{
		if(undoMGR.canUndo())
		{
			undoMGR.undo();
		}
		else
			JOptionPane.showMessageDialog(null, "无法撤销键入","提示",1);
	}
	
	private void Redo()
	{
		if(undoMGR.canRedo())
		{
			undoMGR.redo();
		}
		else
			JOptionPane.showMessageDialog(null, "无法恢复键入","提示",1);
	}
	
	private void Cut()
	{
		tp.cut();
	}
	
	private void Copy()
	{
		tp.copy();
	}
	
	private void Paste()
	{
		tp.paste();
	}
	
	private void SelectAll()
	{
		tp.selectAll();
	}
	
	
	//写入配置文件
	private void writeConfig(){
		configFile.delete();
		c=tp.getBackground();
		try {
		configFile.createNewFile();
		ObjectOutputStream objOS = new ObjectOutputStream(new FileOutputStream(configFile));
         objOS.writeObject(c);
         objOS.writeObject(initFont);
         objOS.close();
	} catch (IOException e) {
		JOptionPane.showMessageDialog(this,"写入配置文件失败","错误",0);
	}
	}
	
	//载入配置
	private void initConfig(){
		configFile=new File("./config.cfg");
		c=new Color(255,255,255);
		if(!configFile.exists()){
			try {
				configFile.createNewFile();
				ObjectOutputStream objOS = new ObjectOutputStream(new FileOutputStream(configFile));
		         objOS.writeObject(c);
		         objOS.writeObject(initFont);
		         objOS.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this,"写入配置文件失败","错误",0);
			}
	   }
		else{
				ObjectInputStream objIS;
				try {
					objIS = new ObjectInputStream(new FileInputStream(configFile));
					try {
						c=(Color)objIS.readObject();
						initFont=(Font)objIS.readObject();
						objIS.close();
					} catch (ClassNotFoundException e) {
						JOptionPane.showMessageDialog(this,"读取配置文件失败","错误",0);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this,"读取配置文件失败","错误",0);
				}
				}
		tp.setBackground(c);
		tp.setFont(initFont);
	}
	
	//监听方法定义完*******************************************************************
	
	//构造方法
	J201326810327_4(String sTitle)
	{
		super(sTitle);
		
		initProcedure();
		
		addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
		    	  Quit();
		      }
		});
	}
	
	//方法定义完**********************************************************************
	
	//自定义菜单
	class RCMenu extends JMenu
	{
		public RCMenu(String label)
		{
			super(label);
		}
		public RCMenu(String label,int nAccelerator)
		{
			super(label);
			setMnemonic(nAccelerator);
		}
	}
	
	//自定义工具栏
	class RCButton extends JButton
	{
		public RCButton()
		{
			super();
		}
		public RCButton(Icon icon)
		{
			super(icon);
		}
		public RCButton(Icon icon,String strToolTipText)
		{
			super(icon);
			setToolTipText(strToolTipText);
		}
		public RCButton(String text)
		{
			super(text);
		}
		public RCButton(String text,Icon icon,String strToolTipText)
		{
			super(text,icon);
			setToolTipText(strToolTipText);
		}
	}

	public void changedUpdate(DocumentEvent e) {
		
	}

	public void insertUpdate(DocumentEvent e) {
		textChanged=true;
		
	}

	public void removeUpdate(DocumentEvent e) {
		textChanged=true;
		
	}

	//主函数
	public static void main(String args[])
	{
		//创建窗口对象
		J201326810327_4 frm=new J201326810327_4("201326810327-朱润石-Java 程序设计综合实验");
		
		Toolkit tool=frm.getToolkit();
		Image im=tool.getImage(J201326810327_4.class.getResource("icon.png"));
		frm.setIconImage(im);
		
		//显示窗口
		frm.setVisible(true);
	}

}

//字体
class RCFont extends JPanel implements ActionListener,ListSelectionListener
{   
	private static final long serialVersionUID = 1L;
	private JDialog jd;//用于显示模态的窗体   
	private JComboBox fonts;//用于选择字体的下拉框   
	private JList face,size;//用于选择字形和字号的列表   
    private JTextField sizeJT;//用于显示选中的字形和字号   
    private JButton ok,cancel;//表示选中和取消的按钮   
    private Font current;//表示当前选中的字体   
    private GraphicsEnvironment ge;//表示当前的图形环境   
    private JLabel demo;//表示预览的label   
    private String fontName="宋体";   
    private int fontStyle=Font.PLAIN,fontSize=20;   
    private Frame owner;//表示父类的组件窗体   
    private Hashtable<String,Integer> ht;//名字到大小的映射  
    RCFont()
    {
    	UIManager.put("Button.font", new Font("宋体",Font.PLAIN,12));
		UIManager.put("Label.font", new Font("宋体", Font.PLAIN, 14));
		UIManager.put("ComboBox.font", new Font("宋体", Font.PLAIN, 14));
		UIManager.put("List.font", new Font("宋体", Font.PLAIN, 14));
		
        initOther();   
    }
	private void initOther()
	{   
        current=new Font(fontName,fontStyle,fontSize);   
        ht=new Hashtable<String,Integer>();   
        sizeJT=new JTextField("20");   
        sizeJT.setEditable(false);   
        sizeJT.setBounds(260,40,50,20);   
        ge=GraphicsEnvironment.getLocalGraphicsEnvironment();   
        String[] family=ge.getAvailableFontFamilyNames();   
        fonts=new JComboBox(family);   
        fonts.setEditable(false);   
        fonts.setMaximumRowCount(5);   
        demo=new JLabel("字体样式  AaBbCcDd",JLabel.CENTER);   
        demo.setFont(current);   
        String[] faceString={"正常","粗体","斜体","粗体+斜体"};   
        String[] sizeString={"初号","小初","一号","小一","二号","小二",   
        "三号","小三","四号","小四","五号","小五","六号","小六","七号",   
        "八号","5","8","9","10","11","12","14","16","18","20","22","24",   
        "26","28","36","48","72"};   
        int[] sizeValue={42,36,26,24,22,18,16,15,14,12,11,9,7,6,5,4,5,8,9,10,11,12,14,16,18,20,   
        22,24,26,28,36,48,72};   
        for(int i=0;i<sizeString.length;i++) 
            ht.put(sizeString[i],sizeValue[i]);   
        face=new JList(faceString);   
        size=new JList(sizeString);   
        face.setSelectedIndex(0);   
        size.setSelectedIndex(25);   
        fonts.setSelectedItem("宋体");   
        face.setVisibleRowCount(4);   
        size.setVisibleRowCount(4);   
        ok=new JButton("确定");   
        cancel=new JButton("取消");   
        ok.addActionListener(this);   
        cancel.addActionListener(this);   
        fonts.addActionListener(this);   
        face.addListSelectionListener(this);   
        size.addListSelectionListener(this);   
    }   
    private void initWindow(String title)
    {   
        this.setLayout(new BorderLayout());   
        JLabel fontLabel=new JLabel("字体");   
        JLabel faceLabel=new JLabel("字形");   
        JLabel sizeLabel=new JLabel("字号");   
        fontLabel.setForeground(Color.BLACK);   
        faceLabel.setForeground(Color.BLACK);   
        sizeLabel.setForeground(Color.BLACK);   
        fontLabel.setBounds(20,20,100,20);   
        faceLabel.setBounds(180,20,80,20);   
        sizeLabel.setBounds(260,20,50,20);   
        fonts.setBounds(10,40,127,21);   
        JScrollPane faceScroll=new JScrollPane(face);   
        JScrollPane sizeScroll=new JScrollPane(size);   
        faceScroll.setBounds(180,40,65,100);   
        sizeScroll.setBounds(260,60,50,80);   
        JPanel up=new JPanel(null);   
        JPanel center=new JPanel(new BorderLayout());   
        JPanel bottom=new JPanel();   
        up.setPreferredSize(new Dimension(345,160));   
        up.add(fontLabel);   
        up.add(faceLabel);   
        up.add(sizeLabel);   
        up.add(fonts);   
        up.add(faceScroll);   
        up.add(sizeScroll);   
        up.add(sizeJT);   
        up.setBorder(BorderFactory.createTitledBorder("选择区"));   
        center.add(demo,BorderLayout.CENTER);   
        center.setBorder(BorderFactory.createTitledBorder("预览区"));   
        bottom.add(ok);   
        bottom.add(cancel);   
        this.add(up,BorderLayout.NORTH);   
        this.add(center,BorderLayout.CENTER);   
        this.add(bottom,BorderLayout.SOUTH);   
        jd=new JDialog(owner,title,true);   
        jd.getContentPane().add(this,BorderLayout.CENTER);   
        jd.setSize(360,360);   
        jd.setResizable(false);   
        jd.setLocationRelativeTo(owner);   
        jd.addWindowListener(new WindowAdapter()
        {   
            public void windowClosing(WindowEvent we)
            {   
                current=null;   
                jd.dispose();   
            }   
        });   
        jd.setVisible(true);   
    }   
    public void actionPerformed(ActionEvent ae)
    {   
        if(ae.getSource()==fonts)
        {   
            fontName=(String)fonts.getSelectedItem();   
            current=new Font(fontName,fontStyle,fontSize);   
            demo.setFont(current);   
            this.repaint();   
        }
        else if(ae.getSource()==ok) 
            jd.dispose();   
        else if(ae.getSource()==cancel)
        {   
            current=null;   
            jd.dispose();   
        }   
    }   
    public void valueChanged(ListSelectionEvent le){   
        if(le.getSource()==face){   
            String value=(String)face.getSelectedValue();   
            if(value.equals("正常")){   
                fontStyle=Font.PLAIN;   
            }else if(value.equals("粗体")){   
                fontStyle=Font.BOLD;   
            }else if(value.equals("斜体")){   
                fontStyle=Font.ITALIC;   
            }else if(value.equals("粗体+斜体")){   
                fontStyle=Font.ITALIC|Font.BOLD;   
            }   
            current=new Font(fontName,fontStyle,fontSize);   
            demo.setFont(current);   
            this.repaint();   
        }else if(le.getSource()==size){   
            String sizeName=(String)size.getSelectedValue();   
            sizeJT.setText(sizeName);   
            fontSize=ht.get(sizeName);   
            current=new Font(fontName,fontStyle,fontSize);   
            demo.setFont(current);   
            this.repaint();   
        }   
    }   
    public Font showDialog(Frame owner,String title)
    {   
    	RCFont jf=new RCFont();   
        jf.initWindow(title);   
        return jf.current;   
    }
}   

//回文数
class RCPalindrome
{
	public static boolean isNumeric(String str)
	{
		  for(int i=0;i< str.length();i++)
		  {
			  if(!Character.isDigit(str.charAt(i)))
				  return false;
		  }
		  return true;
	}
	
	public static int trans(String paramString)
	{
		if(isNumeric(paramString))
		{
			int i=Integer.parseInt(paramString);
			if(0<=i&&i<100000)
				return i;
			return -1;
		}
		else
		{
			return 2;
		}
	}
	
	public static boolean tell(String tmp)
	{
		for(int i=0;i<tmp.length();i++)
			if(tmp.charAt(i)!=tmp.charAt(tmp.length()-1-i))
				return false;
		return true;
	}
}

//数字与英文互译
class RCTranslate
{
	static String x[]={"zero","one","two","three","four","five","six","seven","eight","nine"} ; 
	static String y[]={"ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"}; 
	static String z[]={"twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"," "," "};
	//数字->英文
	public static String NtE(String temp)
	{
		int b=Integer.parseInt(temp);
		if(b<10)
			return (x[b]);
		else if(b<20)
			return (y[b%10]);
		else if(b<100)
		{
			if(b%10==0)
				return (z[b/10-2]);
			else
				return (z[b/10-2]+' '+x[b%10]);
		}
		else
			return "error";
	}
	//英文->数字
	public static int EtN(String temp)
	{
		int n1=0,n2=0;
		boolean flag=false;
		boolean flag1=false;
		if(temp.contains(" "))
		{
			int t=temp.indexOf(' ');
			String s1=temp.substring(0,t);
			String s2=temp.substring(t+1,temp.length());
			for(int k=0;k<=9;k++)
			{
				if(z[k].equalsIgnoreCase(s1))
				{
					flag1=true;
					n1=k;
				}
			}
			for(int k=0;k<=9;k++)
			{
				if(x[k].equalsIgnoreCase(s2)&&flag1)
				{
					flag=true;
					n2=k;
				}
			}
			if(flag1&&flag)
			{
				int tmp=(n1+2)*10+(n2);
				return tmp;
			}
		}
		else
		{
			for(int i=0;i<=9;i++)
			{
				if(x[i].equalsIgnoreCase(temp))
				{
					flag=true;
					return (i);
				}
				else if(y[i].equalsIgnoreCase(temp))
				{
					flag=true;
					return (i+10);
				}
				else if(z[i].equalsIgnoreCase(temp))
				{
					flag=true;
					return ((i+2)*10);
				}
			}
		}
		if(!flag)
		{
			return -1;
		}
		return -2;
	}
	//判断字符串是否为数字
	public static boolean isNumeric(String str)
	{
		for(int i=str.length();--i>=0;)
		{
		    int chr=str.charAt(i);
		    if(chr<48||chr>57)
		       return false;
		}
		return true;
	}
}

//统计英文数据
class RCCount
{
	public static RCCount_threeNumber statis(File tmpFile)
			throws FileNotFoundException
	{
		FileInputStream localFileInputStream = new FileInputStream(tmpFile);
	    Scanner localScanner = new Scanner(localFileInputStream);
			int headW=0;
			int contOr=0;
			int length3=0;
			while (localScanner.hasNext())
			{
				String str = localScanner.next();
				if(str.charAt(0)== 'w'||str.charAt(0)== 'w')
					headW++;
				if(str.indexOf("or")!=-1||str.indexOf("OR")!=-1)
					contOr++;
				if(str.length()==3)
					length3++;
			}
			localScanner.close();
		return new RCCount_threeNumber(headW,contOr,length3);
	}
}

class RCCount_threeNumber
{
	  int a;
	  int b;
	  int c;
	  
	  public RCCount_threeNumber(int paramInt1,int paramInt2,int paramInt3)
	  {
	    this.a=paramInt1;
	    this.b=paramInt2;
	    this.c=paramInt3;
	  }
}

//手机号码合法性判断
class RCJudge
{
	//提取数字并返回长度
	public static int draw(String str)
	{
		int[] ch=new int[str.length()];
		int flag=0;
		for(int i=0;i<str.length();i++)
		{
			int chr=str.charAt(i);
			if(chr>47&&chr<58)
			{
				ch[flag]=chr-48;
				flag++;
			}
		}
		return flag;
	}
	//如果手机号码不是以86打头的，返回3
	public static int headJudge(String str)
	{
		int[] ch=new int[16];
		int flag=0;
		for(int i=0;i<str.length();i++)
		{
			int chr=str.charAt(i);
			if(chr>47&&chr<58)
			{
				ch[flag]=chr-48;
				flag++;
			}
		}
		for(int i=0;i<str.length();i++)
			if(ch[0]==8&&ch[1]==6)
				return 1;
		return 0;
	}
	//除了+/-以外的符号判断，若有错误信息输入则返回0，反之亦然
	public static int charJudge(String str)
	{
		char[] s=str.toCharArray();
		for(int i=0;i<str.length();i++)
		{
			if(s[i]>47&&s[i]<58)
				continue;
			else
			{
				if(s[i]=='+'||s[i]=='-')
					continue;
				else
					return 0;
			}
		}
		return 1;
	}
	//+/-位置判断，错误则返回0，正确则返回1
	public static int posJudge(String str)
	{
		int [] plus=new int [20];
		int j=0;
		int [] subtract=new int [20];
		int k=0;
		char[] s=str.toCharArray();
		for(int i=0;i<str.length();i++)
		{
			if(s[i]=='+')
			{
				plus[j]=i;
				j++;
			}
			else if(s[i]=='-')
			{
				subtract[k]=i;
				k++;
			}
		}
		if(j>1)
			return 0;
		if(j==1&&k==0)
		{
			if(plus[0]==0)
				return 1;
			else
				return 0;
		}
		if(k>3)
			return 0;
		if(k==1)
		{
			if(j==1&&subtract[0]==3)
				return 1;
			else if(j==0&&subtract[0]==2)
				return 1;
			else
				return 0;
		}
		if(k==3)
		{
			if(j==0&&subtract[0]==2&&subtract[1]==6&&subtract[2]==11)
				return 1;
			if(j==1&&subtract[0]==3&&subtract[1]==7&&subtract[2]==12)
				return 1;
			else
				return 0;
		}
		return 0;
	}
}

//文本文件求和
class RCSum extends Thread
{
	  private static JDialog jd;
	  private static JLabel jl;
	  private static JLabel jl1;
	  private static JLabel jl2;
	  private static JButton jb;
	  private static JProgressBar jpb;
	  private static ArrayList<String> sa;
	  private static ArrayList<Double> va;
	  private static double sum;
	  static boolean cancel;

	  private static void initializeDialog(JFrame paramJFrame, int paramInt)
	  {
	    jd = new JDialog(paramJFrame, "文本文件求和");
	    jd.setResizable(false);
	    jd.setBounds(100, 100, 550, 170);
	    JPanel localJPanel = new JPanel();
	    localJPanel.setLayout(null);
	    jd.getContentPane().add(localJPanel, "Center");
	    jl = new JLabel("开始计算...");
	    jl.setBounds(0, 0, 400, 25);
	    jl1 = new JLabel();
	    jl1.setBounds(400, 0, 150, 25);
	    jl2 = new JLabel("正在计算...");
	    jl2.setBounds(0, 110, 100, 25);
	    jpb = new JProgressBar();
	    jpb.setMinimum(0);
	    jpb.setMaximum(paramInt);
	    jpb.setValue(0);
	    jpb.setStringPainted(true);
	    jpb.setBorderPainted(true);
	    jpb.setBounds(0, 25, 550, 80);
	    jb = new JButton("取消");
	    jb.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
	    	  RCSum.cancel = true;
	      }
	    });
	    jb.setBounds(450, 110, 70, 25);
	    localJPanel.add(jl);
	    localJPanel.add(jl1);
	    localJPanel.add(jl2);
	    localJPanel.add(jpb);
	    localJPanel.add(jb);
	    jd.setLocationRelativeTo(paramJFrame);

	    jd.setVisible(true);
	  }

	  public static void calculateSum(File paramString, JFrame paramJFrame)
	  {
	    try
	    {
		Scanner localScanner = new Scanner(new FileInputStream(paramString));
	      sa = new ArrayList<String>();
	      va = new ArrayList<Double>();
	      int i = 0;
	      while (localScanner.hasNextLine())
	      {
	        String str = localScanner.nextLine().trim();
	        i++;
	        if (!str.equals(""))
	        {
	        	int j = str.indexOf(61);
	        	if ((j == -1) || (j == 0))
	        	{
	    			JOptionPane.showMessageDialog(null, "第" + i + "行格式错误。","文本文件求和",1);
	    			localScanner.close();
	        		return;
	        	}
	        	double d;
	        	try
	        	{
	        		d = Double.parseDouble(str.substring(j + 1));
	        	}
	        	 catch (NumberFormatException localNumberFormatException)
	        	{
		    			JOptionPane.showMessageDialog(null, "第" + i + "行数字格式错误。","文本文件求和",1);
		    			localScanner.close();
		        		return;
	        	}
	          va.add(new Double(d));
	          str = str.substring(0, j);
	          sa.add(str);
	        }
	        if(!localScanner.hasNextLine())
	        {
	        	localScanner.close();
	        	break;
	        }
	      }
	      initializeDialog(paramJFrame, sa.size());
	      sum = 0.0D;
	      cancel = false;
	      new RCSum().start();
	    }
	    catch (FileNotFoundException localFileNotFoundException) {
	      JOptionPane.showMessageDialog(paramJFrame, "文件未找到！", "文本文件求和", 1);
	    }
	    catch (Exception localException)
	    {
	      JOptionPane.showMessageDialog(paramJFrame, localException.getMessage(), "文本文件求和", 1);
	    }
	  }

	  public void run()
	  {
	    jl.setText(String.format("当前和为" + sum + "，正在计算变量(%d/%d)，", new Object[] { Integer.valueOf(0), Integer.valueOf(sa.size()) }));

	    jl1.setText("大约剩余...");
	    long l1 = System.nanoTime();
	    for (int i = 0; (i < sa.size()) && 
	      (!cancel); i++)
	    {
	      sum += ((Double)va.get(i)).doubleValue();
	      long l2 = System.nanoTime() - l1;
	      jl.setText(String.format("当前和为%.3f，正在计算变量 %10s(%d/%d)", new Object[] { Double.valueOf(sum), ((String)sa.get(i)).trim(), Integer.valueOf(i + 1), Integer.valueOf(sa.size()) }));

	      jl1.setText(String.format("大约剩余%.4f秒...", new Object[] { Double.valueOf(l2 / 1000000000.0D / (i + 1) * (sa.size() - i - 1)) }));

	      jpb.setValue(i + 1);
	    }
	    if (!cancel) {
	      jl2.setText("计算完毕！");
	      JOptionPane.showMessageDialog(jd, String.format("变量之和为 %.3f。", new Object[] { Double.valueOf(sum) }), jd.getTitle(), 1);
	    }
	    else
	    {
	      jl2.setText("已取消计算！");
	      JOptionPane.showMessageDialog(jd, "计算已取消！", jd.getTitle(), 1);
	    }

	    jd.dispose();
	  }
}

class MyComunity extends JDialog
{
  private static final long serialVersionUID = 1L;
  public static final String[][] orgData = { { "1", "朱润石", "男", "天津市红桥区子牙河南路水竹花园", "浙江工业大学", "300133", "", "", "", "15700085842", "rushzhu95@hotmail.com", "496642712", "1995-02-21", "" }, { "2", "陈志强", "男", "浙江省宁波市鄞州区石碶街道石泉路358号阳光丽园", "浙江工业大学", "315153", "", "", "", "15700084905", "345494073@qq.com", "345494073", "1994-12-28", "学习委员" }, { "3", "陈灵强", "男", "浙江省台州市椒江区章安街道长汇村", "浙江工业大学", "318017", "", "", "", "15700082022", "784741054@qq.com", "784741054", "1995-09-11", "" }, { "4", "董国库", "男", "陕西省咸阳市乾县王村镇淡头村", "浙江工业大学", "713300", "", "", "", "15700083607", "971183504@qq.com", "971183504", "1993-08-07", "" }, { "5", "翟天亨", "男", "内蒙古巴彦淖尔市临河区乌兰图克镇东济村", "浙江工业大学", "", "0478-8930418", "", "", "15700084025", "1775885046@qq.com", "1775885046", "1994-02-25", "" }, { "6", "许开", "男", "吉林省梨树县向阳委1组", "浙江工业大学", "316500", "", "", "", "15700084552", "1784021414@qq.com", "1784021414", "1995-03-20", "文体委员" }, { "7", "高澍阳", "男", "天津市南开区天大北五村", "浙江工业大学", "300072", "022—27890872", "", "", "15700085815", "1025039750@qq.com", "1025039750", "1995-02-19", "班长" }, { "8", "陈雪", "女", "浙江省兰溪市丹溪大道46号恒誉大厦", "浙江工业大学", "321100", "0579-88828858", "", "", "15700084560", "562384116@qq.com", "562384116", "1995-01-17", "团支书" }, { "9", "程乾阳", "男", "浙江嘉兴南湖区大桥镇建国村东塘浜", "浙江工业大学", "314004", "", "", "", "15700082065", "1790805044@qq.com", "1790805044", "1995-07-15", "" }, { "10", "胡莎莎", "女", "浙江省金华市东阳市歌山镇象溪滩村", "浙江工业大学", "322105", "", "", "", "15700083029", "1015369963@qq.com", "1015369963", "1994-11-03", "" } };

  private static final String[] s = { "序号", "姓名", "性别", "通讯地址", "工作单位", "邮政编码", "家庭电话号码", "办公室电话号码", "传真号码", "移动电话号码", "Email", "QQ号或MSN", "出生日期", "备注" };

  private static final Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
  private static final int[] sizes = { 40, 80, 40, 120, 100, 70, 100, 100, 100, 100, 150, 100, 80, 100 };

  private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private Vector<String> colName = new Vector();
  private Vector<Vector<String>> info = new Vector();
  private Vector<ComunityItem> vit = new Vector();
  private String fName;
  private JTable table;
  private DefaultTableModel dtm;
  private JButton btnAdd;
  private JButton btnModify;
  private JButton btnDel;
  private JButton btnFind;
  private JButton btnClose;
  private JLabel rlab;
  private Vector<MyPair> rvec;

  static Vector<String> trans(ComunityItem paramComunityItem)
  {
    Vector localVector = new Vector();
    localVector.add(new Integer(paramComunityItem.nId).toString());
    localVector.add(paramComunityItem.sName);
    localVector.add(paramComunityItem.byteSex == 1 ? "男" : "女");
    localVector.add(paramComunityItem.sAddress);
    localVector.add(paramComunityItem.sCompany);
    localVector.add(paramComunityItem.sPostalCode);
    localVector.add(paramComunityItem.sHomeTele);
    localVector.add(paramComunityItem.sOfficeTele);
    localVector.add(paramComunityItem.sFax);
    localVector.add(paramComunityItem.sCellPhone);
    localVector.add(paramComunityItem.sEmail);
    localVector.add(paramComunityItem.sInstantMessager);
    if (paramComunityItem.dateBirthday != null)
      localVector.add(df.format(paramComunityItem.dateBirthday));
    else
      localVector.add("");
    localVector.add(paramComunityItem.sMemo);
    return localVector;
  }

  static ComunityItem detrans(Vector<String> paramVector) throws MyException {
    ComunityItem localComunityItem = new ComunityItem();
    localComunityItem.nId = Integer.parseInt((String)paramVector.elementAt(0));
    String str = (String)paramVector.elementAt(1);
    if ((str != null) && (!str.trim().equals("")))
      localComunityItem.sName = str;
    else
      throw new MyException("姓名不能为空！", 1);
    if (((String)paramVector.elementAt(2)).equals("男"))
      localComunityItem.byteSex = 1;
    else if (((String)paramVector.elementAt(2)).equals("女"))
      localComunityItem.byteSex = 0;
    else
      throw new MyException("性别字段只能含有\"男\"或\"女\"！", 2);
    localComunityItem.sAddress = ((String)paramVector.elementAt(3));
    localComunityItem.sCompany = ((String)paramVector.elementAt(4));
    localComunityItem.sPostalCode = ((String)paramVector.elementAt(5));
    localComunityItem.sHomeTele = ((String)paramVector.elementAt(6));
    localComunityItem.sOfficeTele = ((String)paramVector.elementAt(7));
    localComunityItem.sFax = ((String)paramVector.elementAt(8));
    localComunityItem.sCellPhone = ((String)paramVector.elementAt(9));
    localComunityItem.sEmail = ((String)paramVector.elementAt(10));
    if ((localComunityItem.sEmail != null) && (!localComunityItem.sEmail.trim().equals("")) && (!p.matcher(localComunityItem.sEmail).matches()))
      throw new MyException("Email地址非法！", 10);
    localComunityItem.sInstantMessager = ((String)paramVector.elementAt(11));
    if ((paramVector.elementAt(12) != null) && (!((String)paramVector.elementAt(12)).trim().equals("")))
      try {
        localComunityItem.dateBirthday = df.parse((String)paramVector.elementAt(12));
      } catch (ParseException localParseException) {
        throw new MyException("出生日期字段格式错误！\n格式必须为 yyyy-MM-dd。", 12);
      }
    else
      localComunityItem.dateBirthday = null;
    localComunityItem.sMemo = ((String)paramVector.elementAt(13));
    return localComunityItem;
  }

  Vector<Vector<String>> getData()
  {
    return this.info;
  }

  private void getInfo() throws MyException {
    File localFile = new File(this.fName);
    Object localObject1;
    if (!localFile.exists())
      try
      {
        localFile.createNewFile();
        localObject1 = new ObjectOutputStream(new FileOutputStream(localFile));

        for (int i = 0; i < orgData.length; i++) {
          Vector localVector = new Vector();
          for (int j = 0; j < orgData[i].length; j++)
            localVector.add(orgData[i][j]);
          ((ObjectOutputStream)localObject1).writeObject(detrans(localVector));
          this.info.add(localVector);
        }
        ((ObjectOutputStream)localObject1).close();
      } catch (IOException localIOException1) {
        throw new MyException("写文件错误！");
      }
    else
      try
      {
        localObject1 = new ObjectInputStream(new FileInputStream(localFile));
        try
        {
          while (true)
          {
            Object localObject2 = ((ObjectInputStream)localObject1).readObject();
            if ((localObject2 instanceof ComunityItem))
              this.info.add(trans((ComunityItem)localObject2));
            else
              throw new MyException("文件格式错误！"); 
          }
        } catch (EOFException localEOFException) { ((ObjectInputStream)localObject1).close(); }

      }
      catch (IOException localIOException2)
      {
        throw new MyException("读文件错误！");
      } catch (ClassNotFoundException localClassNotFoundException) {
        throw new MyException("文件格式错误！");
      }
  }

  private void saveInfo()
  {
    try {
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(new FileOutputStream(this.fName));

      for (int i = 0; i < this.vit.size(); i++)
        localObjectOutputStream.writeObject(this.vit.elementAt(i));
      localObjectOutputStream.close();
    } catch (IOException localIOException) {
      JOptionPane.showMessageDialog(this, "文件读写错误！", "通讯录", 0);
    }
  }

  private Dimension testInfo() {
    this.vit.clear();
    for (int i = 0; i < this.info.size(); i++) {
      try {
        this.vit.add(detrans((Vector)this.info.elementAt(i)));
      } catch (MyException localMyException) {
        JOptionPane.showMessageDialog(this, "第" + i + "行" + localMyException.getMessage(), "通讯录", 0);

        return new Dimension(i, 2);
      }
    }
    return null;
  }

  private void performQuit() {
    int i = JOptionPane.showConfirmDialog(this, "退出通讯录并保存？", "通讯录", 1);

    if (i == 1) {
      dispose();
    } else if (i == 0) {
      Dimension localDimension = testInfo();
      if (localDimension != null) {
        this.table.grabFocus();
        this.table.changeSelection(localDimension.width, localDimension.height, false, false);
      } else {
        saveInfo();
        dispose();
      }
    }
  }

  private void performAddModify(int paramInt)
  {
    int[] arrayOfInt = new int[14];
    for (int i = 0; i < sizes.length; i++)
      arrayOfInt[i] = this.table.getColumnModel().getColumn(i).getWidth();
    DlgAddModify localDlgAddModify;
    if (paramInt != -1) {
      localDlgAddModify = new DlgAddModify(this, (Vector)this.info.elementAt(paramInt), "修改通讯录");
      if (localDlgAddModify.getResult() != null) {
        this.info.setElementAt(localDlgAddModify.getResult(), paramInt);
        this.dtm.setDataVector(this.info, this.colName);
      }
    } else {
      ComunityItem localComunityItem = new ComunityItem();
      localComunityItem.nId = (this.info.size() + 1);
      Vector localVector = trans(localComunityItem);
      localVector.setElementAt("", 2);
      localDlgAddModify = new DlgAddModify(this, localVector, "添加通讯录");
      if (localDlgAddModify.getResult() != null) {
        this.info.add(localDlgAddModify.getResult());
        this.dtm.setDataVector(this.info, this.colName);
      }
    }
    for (int j = 0; j < sizes.length; j++)
      this.table.getColumnModel().getColumn(j).setPreferredWidth(arrayOfInt[j]);
  }

  public MyComunity(JFrame paramJFrame, String paramString) throws MyException {
    super(paramJFrame, "通讯录");
    setModal(true);
    setBounds(100, 100, 550, 300);
    setLayout(null);
    this.fName = paramString;
    getInfo();
    setDefaultCloseOperation(0);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
        MyComunity.this.performQuit();
      }
    });
    for (int i = 0; i < s.length; i++)
      this.colName.add(s[i]);
    this.dtm = new MyTableModel(this.info, this.colName);
    this.table = new JTable(this.dtm);
    for (int i = 0; i < sizes.length; i++)
      this.table.getColumnModel().getColumn(i).setPreferredWidth(sizes[i]);
    this.table.setAutoResizeMode(0);
    JScrollPane localJScrollPane = new JScrollPane(this.table);
    localJScrollPane.setBounds(0, 0, 545, 230);
    getContentPane().add(localJScrollPane);

    this.btnAdd = new JButton("添加");
    this.btnAdd.setBounds(10, 240, 70, 25);
    this.btnAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        MyComunity.this.performAddModify(-1);
        int i = MyComunity.this.table.getRowCount();
        MyComunity.this.table.grabFocus();
        MyComunity.this.table.changeSelection(i - 1, 0, false, false);
      }
    });
    getContentPane().add(this.btnAdd);

    this.btnModify = new JButton("修改");
    this.btnModify.setBounds(100, 240, 70, 25);
    this.btnModify.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        int i = MyComunity.this.table.getSelectedRow();
        if (i != -1)
          MyComunity.this.performAddModify(i);
      }
    });
    getContentPane().add(this.btnModify);

    this.btnDel = new JButton("删除");
    this.btnDel.setBounds(190, 240, 70, 25);
    this.btnDel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        int i = MyComunity.this.table.getSelectedRow();
        if (i != -1) {
          MyComunity.this.dtm.removeRow(i);
          if (MyComunity.this.table.getRowCount() > 0) {
            MyComunity.this.table.grabFocus();
            MyComunity.this.table.changeSelection(0, 0, false, false);
          }
          for (int j = 0; j < MyComunity.this.table.getRowCount(); j++)
            MyComunity.this.dtm.setValueAt(new Integer(j + 1).toString(), j, 0);
          MyComunity.this.info = MyComunity.this.dtm.getDataVector();
        }
      }
    });
    getContentPane().add(this.btnDel);

    this.btnFind = new JButton("查询");
    this.btnFind.setBounds(280, 240, 70, 25);
    final MyComunity localMyComunity = this;
    this.btnFind.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        new DlgFind(localMyComunity);
      }
    });
    getContentPane().add(this.btnFind);

    this.btnClose = new JButton("关闭");
    this.btnClose.setBounds(450, 240, 70, 25);
    this.btnClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        MyComunity.this.performQuit();
      }
    });
    getContentPane().add(this.btnClose);

    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public MyComunity(JDialog paramJDialog, String paramString, Vector<Vector<String>> paramVector, Vector<MyPair> paramVector1)
  {
    super(paramJDialog, paramString);
    setModal(true);
    setBounds(100, 100, 550, 300);

    for (int i = 0; i < s.length; i++)
      this.colName.add(s[i]);
    this.dtm = new MyTableModel(paramVector, this.colName);
    this.table = new JTable(this.dtm);
    for (int i = 0; i < sizes.length; i++) {
      this.table.getColumnModel().getColumn(i).setPreferredWidth(sizes[i]);
    }
    this.table.setAutoResizeMode(0);
    this.table.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent paramAnonymousMouseEvent) {
        int i = MyComunity.this.table.getSelectedRow();
        MyComunity.this.rlab.setText(((MyPair)MyComunity.this.rvec.elementAt(i)).val + "个字符匹配");
      }
    });
    JScrollPane localJScrollPane = new JScrollPane(this.table);
    getContentPane().add(localJScrollPane);
    this.rlab = new JLabel(((MyPair)paramVector1.elementAt(0)).val + "个字符匹配。");
    getContentPane().add(this.rlab, "South");
    this.rvec = paramVector1;
    setResizable(false);
    setLocationRelativeTo(paramJDialog);
    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent paramAnonymousWindowEvent) {
        MyComunity.this.table.grabFocus();
        MyComunity.this.table.changeSelection(0, 0, false, false);
      }
    });
    setVisible(true);
  }
}

class ComunityItem
implements Serializable
{
int nId;
String sName;
byte byteSex;
String sAddress;
String sCompany;
String sPostalCode;
String sHomeTele;
String sOfficeTele;
String sFax;
String sCellPhone;
String sEmail;
String sInstantMessager;
Date dateBirthday;
String sMemo;
}

class MyPair
implements Comparable<MyPair>
{
int rid;
int val;

public MyPair()
{
}

public MyPair(int paramInt1, int paramInt2)
{
  this.rid = paramInt1; this.val = paramInt2;
}
public int compareTo(MyPair paramMyPair) {
  return this.val != paramMyPair.val ? paramMyPair.val - this.val : this.rid - paramMyPair.rid;
}
}

class MyException extends Exception
{
  private static final long serialVersionUID = 1L;
  int wm;

  public MyException(String paramString)
  {
    super(paramString);
  }
  public MyException(String paramString, int paramInt) {
    super(paramString);
    this.wm = paramInt;
  }
}

class DlgAddModify extends JDialog
{
  private static final long serialVersionUID = 1L;
  private static final String[] s = { "序号", "姓名(不能为空)", "性别(不能为空)", "通讯地址", "工作单位", "邮政编码", "家庭电话号码", "办公室电话号码", "传真号码", "移动电话号码", "Email", "QQ号或MSN", "出生日期", "备注" };

  private JLabel[] labels = new JLabel[14];
  private JTextField[] texts = new JTextField[14];
  private JButton btnOK = new JButton("确定"); private JButton btnCancel = new JButton("取消");
  Vector<String> result = null;

  public Vector<String> getResult() {
    return this.result;
  }

  private boolean performOK() {
    this.result = new Vector();
    for (int i = 0; i < s.length; i++)
      this.result.add(this.texts[i].getText());
    try {
      MyComunity.detrans(this.result);
      return true;
    } catch (MyException localMyException) {
      JOptionPane.showMessageDialog(this, localMyException.getMessage(), getTitle(), 0);
      this.result = null;
      this.texts[localMyException.wm].requestFocus();
    }return false;
  }

  public DlgAddModify(MyComunity paramMyComunity, Vector<String> paramVector, String paramString)
  {
    super(paramMyComunity, paramString);
    setModal(true);
    setSize(230, 500);
    setResizable(false);
    setLayout(new FlowLayout());
    setLocationRelativeTo(paramMyComunity);
    Container localContainer = getContentPane();
    for (int i = 0; i < s.length; i++) {
      this.labels[i] = new JLabel(s[i]);
      if (paramVector != null)
        this.texts[i] = new JTextField((String)paramVector.elementAt(i));
      else
        this.texts[i] = new JTextField();
      this.labels[i].setPreferredSize(new Dimension(100, 25));
      this.texts[i].setPreferredSize(new Dimension(100, 25));
      localContainer.add(this.labels[i]);
      localContainer.add(this.texts[i]);
    }
    for (int i = 1; i < s.length - 1; i++) {
      final int j = i;
      this.texts[j].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
          DlgAddModify.this.texts[(j + 1)].requestFocus();
        }
      });
    }
    this.texts[13].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        if (DlgAddModify.this.performOK())
          DlgAddModify.this.dispose();
      }
    });
    this.texts[5].addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent paramAnonymousKeyEvent) {
        if ((paramAnonymousKeyEvent.getKeyChar() < '0') || (paramAnonymousKeyEvent.getKeyChar() > '9'))
          paramAnonymousKeyEvent.consume();
      }
    });
    for (int i = 6; i < 9; i++)
      this.texts[i].addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent paramAnonymousKeyEvent) {
          if (((paramAnonymousKeyEvent.getKeyChar() < '0') || (paramAnonymousKeyEvent.getKeyChar() > '9')) && (paramAnonymousKeyEvent.getKeyChar() != '+') && (paramAnonymousKeyEvent.getKeyChar() != '-'))
          {
            paramAnonymousKeyEvent.consume();
          }
        } } );
    this.texts[9].addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent paramAnonymousKeyEvent) {
        if (((paramAnonymousKeyEvent.getKeyChar() < '0') || (paramAnonymousKeyEvent.getKeyChar() > '9')) && (paramAnonymousKeyEvent.getKeyChar() != '+'))
          paramAnonymousKeyEvent.consume();
      }
    });
    this.texts[0].setEditable(false);
    localContainer.add(this.btnOK);
    this.btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        if (DlgAddModify.this.performOK())
          DlgAddModify.this.dispose();
      }
    });
    localContainer.add(this.btnCancel);
    this.btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        DlgAddModify.this.dispose();
      }
    });
    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent paramAnonymousWindowEvent) {
        DlgAddModify.this.texts[1].requestFocus();
      }
    });
    setVisible(true);
  }
}

class MyTableModel extends DefaultTableModel
{
  private static final long serialVersionUID = 1L;

  public MyTableModel(Vector<Vector<String>> paramVector, Vector<String> paramVector1)
  {
    super(paramVector, paramVector1);
  }
  public boolean isCellEditable(int paramInt1, int paramInt2) {
    return false;
  }
}

class DlgFind extends JDialog
{
  private static final long serialVersionUID = 1L;
  private static final String[] s = { "姓名", "性别", "通讯地址", "工作单位", "邮政编码", "家庭电话号码", "办公室电话号码", "传真号码", "移动电话号码", "Email", "QQ号或MSN", "出生日期", "备注" };

  private JLabel[] labels = new JLabel[13];
  private JTextField[] texts = new JTextField[13];
  private JButton btnFind = new JButton("查找");
  private Vector<MyPair> vResult = new Vector();

  private int objCol = -1;
  private int ip = -1;
  private MyComunity p;
  private Vector<Vector<String>> vvs;
  private Vector<Vector<String>> dvvs = new Vector();

  private boolean tellEmpty(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      return true;
    }
    return false;
  }

  private int getMatchValue(String paramString, int paramInt1, int paramInt2)
  {
    String str = (String)((Vector)this.vvs.elementAt(paramInt1)).elementAt(paramInt2);
    int[][] arrayOfInt = new int[paramString.length() + 1][str.length() + 1];
    for (int i = 0; i < paramString.length(); i++)
      for (int j = 0; j < str.length(); j++) {
        arrayOfInt[(i + 1)][(j + 1)] = Math.max(arrayOfInt[i][(j + 1)], arrayOfInt[(i + 1)][j]);
        if (paramString.charAt(i) == str.charAt(j))
          arrayOfInt[(i + 1)][(j + 1)] = Math.max(arrayOfInt[(i + 1)][(j + 1)], arrayOfInt[i][j] + 1);
      }
    return arrayOfInt[paramString.length()][str.length()];
  }

  private void performFind() {
    this.objCol = -1;
    for (int i = 0; i < this.texts.length; i++)
      this.texts[i].setText(this.texts[i].getText().trim());
    for (int i = 0; i < this.texts.length; i++)
      if (!tellEmpty(this.texts[i].getText())) {
        this.objCol = (i + 1);
        break;
      }
    if (this.objCol == -1) {
      JOptionPane.showMessageDialog(this, "请输入要查找的信息！", "通讯录", 1);

      return;
    }
    this.vvs = this.p.getData();
    this.dvvs.clear();
    this.vResult.clear();
    for (int i = 0; i < this.vvs.size(); i++) {
      int j = 0;
      for (int k = 0; k < this.texts.length; k++)
        if (!tellEmpty(this.texts[k].getText()))
          j += getMatchValue(this.texts[k].getText(), i, k + 1);
      if (j > 0)
        this.vResult.add(new MyPair(i, j));
    }
    Collections.sort(this.vResult);
    for (int i = 0; i < this.vResult.size(); i++)
      this.dvvs.add(this.vvs.elementAt(((MyPair)this.vResult.elementAt(i)).rid));
    if (this.vResult.size() > 0)
      new MyComunity(this, "查找结果", this.dvvs, this.vResult);
    else
      JOptionPane.showMessageDialog(this, "没有找到匹配的记录。", getTitle(), 1);
  }

  public DlgFind(MyComunity paramMyComunity)
  {
    super(paramMyComunity, "查找");
    this.p = paramMyComunity;
    setModal(true);
    setSize(230, 460);
    setLocationRelativeTo(paramMyComunity);
    setResizable(false);
    setLayout(new FlowLayout());
    Container localContainer = getContentPane();
    for (int i = 0; i < this.labels.length; i++) {
      this.labels[i] = new JLabel(s[i]);
      this.texts[i] = new JTextField();
    }
    for (int i = 0; i < this.labels.length; i++) {
      this.labels[i].setPreferredSize(new Dimension(100, 25));
      this.texts[i].setPreferredSize(new Dimension(100, 25));
      localContainer.add(this.labels[i]);
      localContainer.add(this.texts[i]);
      if (i + 1 != this.texts.length) {
        final int j = i;
        this.texts[j].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
            DlgFind.this.texts[(j + 1)].requestFocus();
          }
        });
      }
    }
    this.texts[(this.texts.length - 1)].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        DlgFind.this.performFind();
      }
    });
    this.btnFind.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        DlgFind.this.performFind();
      }
    });
    localContainer.add(this.btnFind);
    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent paramAnonymousWindowEvent) {
        DlgFind.this.texts[0].requestFocus();
      }
    });
    setVisible(true);
  }
}