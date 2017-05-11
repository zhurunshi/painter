/**
* @author RushChu(����ʯ)
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
	//�������************************************************************************
	
	//1 �˵���
	JMenuBar mb=new JMenuBar();
	
		//1.1 ���ļ����˵�
		RCMenu mFile=new RCMenu("�ļ�(F)",KeyEvent.VK_F);
	
			//1.1.1 �½�
			JMenuItem miNew=new JMenuItem("�½�(N)",KeyEvent.VK_N);
			//1.1.2 ��
			JMenuItem miOpen=new JMenuItem("��(O)...",KeyEvent.VK_O);
			//1.1.3 ����
			JMenuItem miSave=new JMenuItem("����(S)",KeyEvent.VK_S);
			//1.1.4 ����
			JMenuItem miFont=new JMenuItem("����(F)...",KeyEvent.VK_F);
			//1.1.5 ������ɫ
			JMenuItem miBackgroundColor=new JMenuItem("������ɫ(B)...",KeyEvent.VK_C);
			//1.1.6 �˳�
			JMenuItem miQuit=new JMenuItem("�˳�(X)",KeyEvent.VK_X);
		
		//1.2 ��Java�ϻ���Ŀ���˵�
		RCMenu mJava=new RCMenu("Java�ϻ���Ŀ",KeyEvent.VK_J);
		
			//1.2.1 ������
			JMenuItem miPalindrome=new JMenuItem("������(P)...",KeyEvent.VK_P);
			//1.2.2 ������Ӣ�Ļ���
			JMenuItem miTranslate=new JMenuItem("������Ӣ�Ļ���(T)...",KeyEvent.VK_T);
			//1.2.3 ͳ��Ӣ������
			JMenuItem miCount=new JMenuItem("ͳ��Ӣ������(C)...",KeyEvent.VK_C);
			//1.2.4 �ֻ�����Ϸ����ж�
			JMenuItem miJudge=new JMenuItem("�ֻ�����Ϸ����ж�(J)...",KeyEvent.VK_J);
			//1.2.5 �ı��ļ����
			JMenuItem miSum=new JMenuItem("�ı��ļ����(S)...",KeyEvent.VK_S);
			
		//1.3 ��ͨѶ¼���˵�
		RCMenu mContacts=new RCMenu("ͨѶ¼(C)",KeyEvent.VK_C);
		
			//1.3.1 ά��
			JMenuItem miMaintain=new JMenuItem("ά��(M)...",KeyEvent.VK_M);
			//1.3.2 �洢�ļ�����
			JMenuItem miSetup=new JMenuItem("�洢�ļ�����(S)...",KeyEvent.VK_S);
			
	//2 �ı���
	JTextPane tp=new JTextPane();
	
	
	
	//3 ������
	JScrollPane sp=new JScrollPane(tp);
	
	//4 �Ҽ��˵�
	JPopupMenu pm=new JPopupMenu();;
	
		//4.1 ����
		JMenuItem miUndo=new JMenuItem("��������(U)",KeyEvent.VK_U);
		//�ָ�
		JMenuItem miRedo=new JMenuItem("�ָ�����(Y)",KeyEvent.VK_Y);
		//4.2 ����
		JMenuItem miCut=new JMenuItem("����(T)",KeyEvent.VK_T);
		//4.3 ����
		JMenuItem miCopy=new JMenuItem("����(C)",KeyEvent.VK_C);
		//4.4 ճ��
		JMenuItem miPaste=new JMenuItem("ճ��(P)",KeyEvent.VK_P);
		//4.6ȫѡ
		JMenuItem miSelectAll=new JMenuItem("ȫѡ(A)",KeyEvent.VK_A);
		
	//5 ������
	JToolBar mtb=new JToolBar();
	
	RCButton newB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("new.png")),"�½�");
	RCButton openB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("open.png")),"��");
	RCButton saveB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("save.png")),"����");
	RCButton cutB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("cut.png")),"����");
	RCButton copyB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("copy.png")),"����");
	RCButton pasteB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("paste.png")),"ճ��");
	RCButton undoB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("undo.png")),"��������");
	RCButton redoB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("redo.png")),"�ָ�����");
	RCButton fontB=new RCButton(new ImageIcon(J201326810327_4.class.getResource("font.png")),"����");
	
	//������ʱ�ļ�
	File tmpFile;

	//�ı��Ƿ�Ķ����ı�ʶ
	boolean textChanged = false;
	//��ʱ��ʶ
	boolean flag=false;
	//����������
	private UndoManager undoMGR=new UndoManager();
	//�ļ�·��
	File filepath;
	//�ж��ļ���û��·��
	boolean textpath=false;
	//ͨѶ¼
	private String fName = null; 
	private String cfName = new String("Comunity.dat");
	//����������**********************************************************************

	//����ֵ
	private Font initFont;
	
	//������ɫ
	private Color c;

	//�����ļ�
	private File configFile;
	
	//���巽��************************************************************************
	
	//�ı���
	private void initText()
	{
		tp.getDocument().addDocumentListener(this);
		tp.getDocument().addUndoableEditListener(undoMGR);
	}
	
	//5 ������
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
		
		//���ÿ��Ը���
		mtb.setFloatable(true);
	}
	
	//1 �˵���
	private void initMenus()
	{	
		//���˵�����ӵ�������
		setJMenuBar(mb);
		
		//������ӡ��ļ����˵��ϵ���
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.addSeparator();
		mFile.add(miFont);
		mFile.add(miBackgroundColor);
		mFile.addSeparator();
		mFile.add(miQuit);
		
		//�����ļ����˵���ӵ��˵�����
		mb.add(mFile);
		
		//ע�������
		miNew.addActionListener(this);
		miOpen.addActionListener(this);
		miSave.addActionListener(this);
		miFont.addActionListener(this);
		miBackgroundColor.addActionListener(this);
		miQuit.addActionListener(this);
		
		//���ͼ��
		ImageIcon newIcon=new ImageIcon(J201326810327_4.class.getResource("new.png"));
		miNew.setIcon(newIcon);
		ImageIcon openIcon=new ImageIcon(J201326810327_4.class.getResource("open.png"));
		miOpen.setIcon(openIcon);
		ImageIcon saveIcon=new ImageIcon(J201326810327_4.class.getResource("save.png"));
		miSave.setIcon(saveIcon);
		ImageIcon fontIcon=new ImageIcon(J201326810327_4.class.getResource("font.png"));
		miFont.setIcon(fontIcon);
		
		//Ϊ���½������ÿ�ݼ�
		miNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK));
		//Ϊ���򿪡����ÿ�ݼ�
		miOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));
		//Ϊ�����桱���ÿ�ݼ�
		miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
		//Ϊ���˳������ÿ�ݼ�
		miQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_DOWN_MASK));
		
		//������ӡ�Java�ϻ���Ŀ���˵��ϵ���
		mJava.add(miPalindrome);
		mJava.add(miTranslate);
		mJava.add(miCount);
		mJava.add(miJudge);
		mJava.add(miSum);
		
		//����Java�ϻ���Ŀ���˵���ӵ��˵�����
		mb.add(mJava);
		
		//ע�������
		miPalindrome.addActionListener(this);
		miTranslate.addActionListener(this);
		miCount.addActionListener(this);
		miJudge.addActionListener(this);
		miSum.addActionListener(this);
		
		//������ӡ�ͨѶ¼���˵��ϵ���
		mContacts.add(miMaintain);
		mContacts.add(miSetup);
		
		//����ͨѶ¼���˵���ӵ��˵�����
		mb.add(mContacts);
		
		//ע�������
		miMaintain.addActionListener(this);
		miSetup.addActionListener(this);
	}
	
	//2 �ı���(�Ѿ���֮ǰ�������)

	//3 ������
	private void initScrollPane()
	{
		//���ı�����ӹ�����
		add(sp);
	}
	
	//4 �Ҽ��˵�
	private void initRightClick()
	{	
		//��������Ҽ��˵��ϵ���
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
		
		//����Ҽ��˵�
		add(pm);
		
		//�ȼ������ĵ����¼�
		tp.addMouseListener(new java.awt.event.MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				int mods=e.getModifiers();
				
				//����Ҽ�
				if((mods&InputEvent.BUTTON3_MASK)!=0)
				{
					//�����˵�
					pm.show(tp,e.getX(),e.getY());
				}
			}
		});
	}
	
	//������
	private void initUI()
	{
		try
		{
		String lfClassName="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			
		//Windows���
		UIManager.setLookAndFeel(lfClassName);
				
		//����UI
		SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e){}
	}
	
	//���ھ��к���
	private void centerWindow()
	{
		//�����ʾ�����洰�ڵĴ�С
		Toolkit tk=getToolkit();
		Dimension dm=tk.getScreenSize();
		
		//��ȡ��Ļ�ı߽�
		Insets screenInsets=Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		
		//��ȡ�ײ��������߶�
		int taskBarHeight=screenInsets.bottom;
		
		//�ô��ھ�����ʾ
		setLocation((int)(dm.getWidth()-getWidth())/2,(int)(dm.getHeight()-getHeight()-taskBarHeight)/2);
	}
	
	//�����ȫ������
	private void initProcedure()
	{
		//��������
		initConfig();
		
		//�˵�
		initMenus();
		
		//������
		initToolBar();
					
		//�Ҽ��˵�
		initRightClick();
		
		//�ı���
		initText();
					
		//������
		initScrollPane();
					
		//���ô��ڴ�С
		setSize(700,500);
					
		//���ھ��к���
		centerWindow();
					
		//������
		initUI();
				
		//����close��ť
		setDefaultCloseOperation(0);	
		
		//д������
		writeConfig();
	}
	

	//����
	public void actionPerformed(ActionEvent e)
	{
		//1.1 ���ļ����˵�
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
		
		//1.2 ��Java�ϻ���Ŀ���˵�
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
		
		//1.3 ��ͨѶ¼���˵�
		if(e.getSource()==miMaintain)
			Maintain();
		else if(e.getSource()==miSetup)
			Setup();
		
		//4 �Ҽ��˵�
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
	        JOptionPane.showMessageDialog(this, localMyException.getMessage(), "Java��������ۺ�ʵ��", 0);
	      }
		
	}

	private void Setup() {
	    JFileChooser localJFileChooser = new JFileChooser();
	    localJFileChooser.setSelectedFile(new File(this.cfName));
	    if (localJFileChooser.showDialog(this, "ѡ���ļ�") == 0)
	      this.cfName = localJFileChooser.getSelectedFile().getPath();
		
	}

	//��������************************************************************************	
	private void New()
	{
		int returnValue;
	
		while(textChanged)
		{
			returnValue = JOptionPane.showConfirmDialog(this, "�Ƿ񽫸��ı��浽 �ı� �У�", "Java��������ۺ�ʵ��", 1);
			if(returnValue==JOptionPane.YES_OPTION)
				Save();
			else if(returnValue==JOptionPane.NO_OPTION)
			{
				tp.setText("");
				setTitle("201326810327-����ʯ-Java ��������ۺ�ʵ��");
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
		
		//���������Ǵ򿪵�Ĭ��·��
		JFileChooser openFC= new JFileChooser();
		returnValue=openFC.showOpenDialog(this);
		if(returnValue==JFileChooser.APPROVE_OPTION)
		{
			//�õ�ѡ����ļ�
			File f=openFC.getSelectedFile();
			//��¼·��
			filepath=f;
			textpath=true;
			
			setTitle("201326810327-����ʯ-Java ��������ۺ�ʵ��"+"-"+filepath.getName());
			try
			{
				//���ļ�������������
				InputStream is=new FileInputStream(f);
				//������
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
		//�ļ���ԭ���ʹ��ڵ�
		if(textpath)
		{
			try
			{
				FileOutputStream os=new FileOutputStream(filepath);
				os.write(tp.getText().getBytes());
				os.close();
				//����ɹ�
				textChanged=false;
				return;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		//���������Ǳ����Ĭ��·��
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
				//����ɹ�
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
		initFont=fontchooser.showDialog(this,"��ѡ����ϲ��������");
		tp.setFont(initFont);
	}
	
	private void BackgroundColor()
	{
		c=JColorChooser.showDialog(this,"������ɫ",tp.getBackground());
		//���ȡ��
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
				returnValue = JOptionPane.showConfirmDialog(this, "�Ƿ񽫸��ı��浽 "+filepath.getName()+" �У�", "Java��������ۺ�ʵ��", 1);
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
			returnValue = JOptionPane.showConfirmDialog(this, "�Ƿ񽫸��ı��浽 �ı� �У�", "Java��������ۺ�ʵ��", 1);
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
			str=JOptionPane.showInputDialog(this,"������1-99999֮�������", "������",1);
			//���ַ���
			if(str.equals(""))
			{
				JOptionPane.showMessageDialog(this,"����������", "������",1);
				return;
			}
			else
			{
				//���벻ȫΪ����
				if(!RCPalindrome.isNumeric(str))
					JOptionPane.showMessageDialog(this,"����������", "������",1);
				else
				{
					int i=Integer.parseInt(str);
					str=Integer.toString(i);
					//ȥ���ַ����Ŀո�
					str=str.trim();
					//��ֵԽ���ж�
					if(RCPalindrome.trans(str)==-1)
						JOptionPane.showMessageDialog(this,"������1-99999֮�������", "������",1);
				}
			}
		if(RCPalindrome.isNumeric(str)&&str.length()<6)
		{
			if(RCPalindrome.tell(str))
				JOptionPane.showMessageDialog(this,new StringBuilder().append(str).append("�ǻ�����"),"������",1);
			else
				JOptionPane.showMessageDialog(this,new StringBuilder().append(str).append("���ǻ�����"),"������",1);
		}
	}
	
	private void Translate()
	{
		String str;
		str=JOptionPane.showInputDialog(this,"������һ��С��100������������Ӣ�ľ��ɣ�", "������Ӣ�Ļ���",1);
		if(str.equals(""))
		{
			JOptionPane.showMessageDialog(this,"����������", "������Ӣ�Ļ���",1);
			return;
		}
		if(RCTranslate.isNumeric(str))
		{
			if(RCTranslate.NtE(str)=="error")
				JOptionPane.showMessageDialog(this,"����������������������롣", "������Ӣ�Ļ���",1);
			else
				JOptionPane.showMessageDialog(this,new StringBuilder().append(RCTranslate.NtE(str)),"������Ӣ�Ļ���",1);
		}
		else
		{
			if(RCTranslate.EtN(str)==-1)
				JOptionPane.showMessageDialog(this,"����������������������롣", "������Ӣ�Ļ���",1);
			else
				JOptionPane.showMessageDialog(this,new StringBuilder().append(RCTranslate.EtN(str)),"������Ӣ�Ļ���",1);
		}
	}
	
	private void Count() throws IOException
	{
		//������ʱ�ļ�
		tmpFile=new File("D:/data.tmp");
		tmpFile.createNewFile();   
		String sets = "attrib +H \"" + tmpFile.getAbsolutePath() + "\"";  
		//������   
		Runtime.getRuntime().exec(sets);
		if(tmpFile.exists()) 
		{
			try {
				FileOutputStream out = new FileOutputStream(tmpFile);  //�õ��ļ������
				out.write(tp.getText().getBytes()); //д���ļ�  
				out.close();
		    	} catch (Exception ex) {
		    		ex.printStackTrace(); //���������Ϣ
		    	}
			if(tmpFile!=null)
			{
				RCCount_threeNumber tmp=RCCount.statis(tmpFile);
				int j = tmp.a; int k = tmp.b; int m = tmp.c;
			      JOptionPane.showMessageDialog(this, new StringBuilder().append("��").append(j).append("������ĸw��ͷ�ĵ��ʣ�\n").append("��").append(k).append("������or���ַ����ĵ��ʣ�\n").append("��").append(m).append("������Ϊ3�ĵ��ʡ�").toString(), "����ͳ��", 1);
			}
		}
		tmpFile.delete();
	}
	
	private void Judge()
	{
		String str;
		str=JOptionPane.showInputDialog(this,"��������Ҫ�жϺϷ��Ե��ֻ�����", "�ֻ�����Ϸ����ж�",1);
		//�����Ǹ������ȼ�˳�����������
		if(RCJudge.draw(str)!=13)
			JOptionPane.showMessageDialog(this,"�ֻ����볤�Ȳ��Ϸ�", "�ֻ�����Ϸ����ж�",1);
		else
		{
			if(RCJudge.charJudge(str)!=1)
				JOptionPane.showMessageDialog(this,"�ֻ������а��������ֵ��ַ�","�ֻ�����Ϸ����ж�",1);
			else
			{
				if(RCJudge.headJudge(str)!=1)
					JOptionPane.showMessageDialog(this,"�ֻ����벻����86��ͷ","�ֻ�����Ϸ����ж�",1);
				else
				{
					if(str.length()==13)
						JOptionPane.showMessageDialog(this,"�ֻ�����Ϸ�","�ֻ�����Ϸ����ж�",1);
					else
					{
						if(RCJudge.posJudge(str)==1)
							JOptionPane.showMessageDialog(this,"�ֻ�����Ϸ�","�ֻ�����Ϸ����ж�",1);
						else
							JOptionPane.showMessageDialog(this,"�������","�ֻ�����Ϸ����ж�",1);
					}
				}
			}
		}
	}
	
	private void Sum() throws IOException
	{
		if(tp.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "��������Ҫ����ı���","��ʾ",1);
			return;
		}
		//������ʱ�ļ�
		tmpFile=new File("D:/data.tmp");
		tmpFile.createNewFile();   
		String sets = "attrib +H \"" + tmpFile.getAbsolutePath() + "\"";  
		//������   
		Runtime.getRuntime().exec(sets);
		if(tmpFile.exists()) 
		{
			try
			{
				FileOutputStream out = new FileOutputStream(tmpFile);  //�õ��ļ������
				out.write(tp.getText().getBytes()); //д���ļ�  
				out.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace(); //���������Ϣ
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
			JOptionPane.showMessageDialog(null, "�޷���������","��ʾ",1);
	}
	
	private void Redo()
	{
		if(undoMGR.canRedo())
		{
			undoMGR.redo();
		}
		else
			JOptionPane.showMessageDialog(null, "�޷��ָ�����","��ʾ",1);
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
	
	
	//д�������ļ�
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
		JOptionPane.showMessageDialog(this,"д�������ļ�ʧ��","����",0);
	}
	}
	
	//��������
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
				JOptionPane.showMessageDialog(this,"д�������ļ�ʧ��","����",0);
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
						JOptionPane.showMessageDialog(this,"��ȡ�����ļ�ʧ��","����",0);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this,"��ȡ�����ļ�ʧ��","����",0);
				}
				}
		tp.setBackground(c);
		tp.setFont(initFont);
	}
	
	//��������������*******************************************************************
	
	//���췽��
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
	
	//����������**********************************************************************
	
	//�Զ���˵�
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
	
	//�Զ��幤����
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

	//������
	public static void main(String args[])
	{
		//�������ڶ���
		J201326810327_4 frm=new J201326810327_4("201326810327-����ʯ-Java ��������ۺ�ʵ��");
		
		Toolkit tool=frm.getToolkit();
		Image im=tool.getImage(J201326810327_4.class.getResource("icon.png"));
		frm.setIconImage(im);
		
		//��ʾ����
		frm.setVisible(true);
	}

}

//����
class RCFont extends JPanel implements ActionListener,ListSelectionListener
{   
	private static final long serialVersionUID = 1L;
	private JDialog jd;//������ʾģ̬�Ĵ���   
	private JComboBox fonts;//����ѡ�������������   
	private JList face,size;//����ѡ�����κ��ֺŵ��б�   
    private JTextField sizeJT;//������ʾѡ�е����κ��ֺ�   
    private JButton ok,cancel;//��ʾѡ�к�ȡ���İ�ť   
    private Font current;//��ʾ��ǰѡ�е�����   
    private GraphicsEnvironment ge;//��ʾ��ǰ��ͼ�λ���   
    private JLabel demo;//��ʾԤ����label   
    private String fontName="����";   
    private int fontStyle=Font.PLAIN,fontSize=20;   
    private Frame owner;//��ʾ������������   
    private Hashtable<String,Integer> ht;//���ֵ���С��ӳ��  
    RCFont()
    {
    	UIManager.put("Button.font", new Font("����",Font.PLAIN,12));
		UIManager.put("Label.font", new Font("����", Font.PLAIN, 14));
		UIManager.put("ComboBox.font", new Font("����", Font.PLAIN, 14));
		UIManager.put("List.font", new Font("����", Font.PLAIN, 14));
		
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
        demo=new JLabel("������ʽ  AaBbCcDd",JLabel.CENTER);   
        demo.setFont(current);   
        String[] faceString={"����","����","б��","����+б��"};   
        String[] sizeString={"����","С��","һ��","Сһ","����","С��",   
        "����","С��","�ĺ�","С��","���","С��","����","С��","�ߺ�",   
        "�˺�","5","8","9","10","11","12","14","16","18","20","22","24",   
        "26","28","36","48","72"};   
        int[] sizeValue={42,36,26,24,22,18,16,15,14,12,11,9,7,6,5,4,5,8,9,10,11,12,14,16,18,20,   
        22,24,26,28,36,48,72};   
        for(int i=0;i<sizeString.length;i++) 
            ht.put(sizeString[i],sizeValue[i]);   
        face=new JList(faceString);   
        size=new JList(sizeString);   
        face.setSelectedIndex(0);   
        size.setSelectedIndex(25);   
        fonts.setSelectedItem("����");   
        face.setVisibleRowCount(4);   
        size.setVisibleRowCount(4);   
        ok=new JButton("ȷ��");   
        cancel=new JButton("ȡ��");   
        ok.addActionListener(this);   
        cancel.addActionListener(this);   
        fonts.addActionListener(this);   
        face.addListSelectionListener(this);   
        size.addListSelectionListener(this);   
    }   
    private void initWindow(String title)
    {   
        this.setLayout(new BorderLayout());   
        JLabel fontLabel=new JLabel("����");   
        JLabel faceLabel=new JLabel("����");   
        JLabel sizeLabel=new JLabel("�ֺ�");   
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
        up.setBorder(BorderFactory.createTitledBorder("ѡ����"));   
        center.add(demo,BorderLayout.CENTER);   
        center.setBorder(BorderFactory.createTitledBorder("Ԥ����"));   
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
            if(value.equals("����")){   
                fontStyle=Font.PLAIN;   
            }else if(value.equals("����")){   
                fontStyle=Font.BOLD;   
            }else if(value.equals("б��")){   
                fontStyle=Font.ITALIC;   
            }else if(value.equals("����+б��")){   
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

//������
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

//������Ӣ�Ļ���
class RCTranslate
{
	static String x[]={"zero","one","two","three","four","five","six","seven","eight","nine"} ; 
	static String y[]={"ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"}; 
	static String z[]={"twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"," "," "};
	//����->Ӣ��
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
	//Ӣ��->����
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
	//�ж��ַ����Ƿ�Ϊ����
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

//ͳ��Ӣ������
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

//�ֻ�����Ϸ����ж�
class RCJudge
{
	//��ȡ���ֲ����س���
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
	//����ֻ����벻����86��ͷ�ģ�����3
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
	//����+/-����ķ����жϣ����д�����Ϣ�����򷵻�0����֮��Ȼ
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
	//+/-λ���жϣ������򷵻�0����ȷ�򷵻�1
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

//�ı��ļ����
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
	    jd = new JDialog(paramJFrame, "�ı��ļ����");
	    jd.setResizable(false);
	    jd.setBounds(100, 100, 550, 170);
	    JPanel localJPanel = new JPanel();
	    localJPanel.setLayout(null);
	    jd.getContentPane().add(localJPanel, "Center");
	    jl = new JLabel("��ʼ����...");
	    jl.setBounds(0, 0, 400, 25);
	    jl1 = new JLabel();
	    jl1.setBounds(400, 0, 150, 25);
	    jl2 = new JLabel("���ڼ���...");
	    jl2.setBounds(0, 110, 100, 25);
	    jpb = new JProgressBar();
	    jpb.setMinimum(0);
	    jpb.setMaximum(paramInt);
	    jpb.setValue(0);
	    jpb.setStringPainted(true);
	    jpb.setBorderPainted(true);
	    jpb.setBounds(0, 25, 550, 80);
	    jb = new JButton("ȡ��");
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
	    			JOptionPane.showMessageDialog(null, "��" + i + "�и�ʽ����","�ı��ļ����",1);
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
		    			JOptionPane.showMessageDialog(null, "��" + i + "�����ָ�ʽ����","�ı��ļ����",1);
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
	      JOptionPane.showMessageDialog(paramJFrame, "�ļ�δ�ҵ���", "�ı��ļ����", 1);
	    }
	    catch (Exception localException)
	    {
	      JOptionPane.showMessageDialog(paramJFrame, localException.getMessage(), "�ı��ļ����", 1);
	    }
	  }

	  public void run()
	  {
	    jl.setText(String.format("��ǰ��Ϊ" + sum + "�����ڼ������(%d/%d)��", new Object[] { Integer.valueOf(0), Integer.valueOf(sa.size()) }));

	    jl1.setText("��Լʣ��...");
	    long l1 = System.nanoTime();
	    for (int i = 0; (i < sa.size()) && 
	      (!cancel); i++)
	    {
	      sum += ((Double)va.get(i)).doubleValue();
	      long l2 = System.nanoTime() - l1;
	      jl.setText(String.format("��ǰ��Ϊ%.3f�����ڼ������ %10s(%d/%d)", new Object[] { Double.valueOf(sum), ((String)sa.get(i)).trim(), Integer.valueOf(i + 1), Integer.valueOf(sa.size()) }));

	      jl1.setText(String.format("��Լʣ��%.4f��...", new Object[] { Double.valueOf(l2 / 1000000000.0D / (i + 1) * (sa.size() - i - 1)) }));

	      jpb.setValue(i + 1);
	    }
	    if (!cancel) {
	      jl2.setText("������ϣ�");
	      JOptionPane.showMessageDialog(jd, String.format("����֮��Ϊ %.3f��", new Object[] { Double.valueOf(sum) }), jd.getTitle(), 1);
	    }
	    else
	    {
	      jl2.setText("��ȡ�����㣡");
	      JOptionPane.showMessageDialog(jd, "������ȡ����", jd.getTitle(), 1);
	    }

	    jd.dispose();
	  }
}

class MyComunity extends JDialog
{
  private static final long serialVersionUID = 1L;
  public static final String[][] orgData = { { "1", "����ʯ", "��", "����к�������������·ˮ��԰", "�㽭��ҵ��ѧ", "300133", "", "", "", "15700085842", "rushzhu95@hotmail.com", "496642712", "1995-02-21", "" }, { "2", "��־ǿ", "��", "�㽭ʡ������۴����ʯ�\�ֵ�ʯȪ·358��������԰", "�㽭��ҵ��ѧ", "315153", "", "", "", "15700084905", "345494073@qq.com", "345494073", "1994-12-28", "ѧϰίԱ" }, { "3", "����ǿ", "��", "�㽭ʡ̨���н������°��ֵ������", "�㽭��ҵ��ѧ", "318017", "", "", "", "15700082022", "784741054@qq.com", "784741054", "1995-09-11", "" }, { "4", "������", "��", "����ʡ������Ǭ��������ͷ��", "�㽭��ҵ��ѧ", "713300", "", "", "", "15700083607", "971183504@qq.com", "971183504", "1993-08-07", "" }, { "5", "�����", "��", "���ɹŰ����׶����ٺ�������ͼ���򶫼ô�", "�㽭��ҵ��ѧ", "", "0478-8930418", "", "", "15700084025", "1775885046@qq.com", "1775885046", "1994-02-25", "" }, { "6", "��", "��", "����ʡ����������ί1��", "�㽭��ҵ��ѧ", "316500", "", "", "", "15700084552", "1784021414@qq.com", "1784021414", "1995-03-20", "����ίԱ" }, { "7", "������", "��", "������Ͽ���������", "�㽭��ҵ��ѧ", "300072", "022��27890872", "", "", "15700085815", "1025039750@qq.com", "1025039750", "1995-02-19", "�೤" }, { "8", "��ѩ", "Ů", "�㽭ʡ��Ϫ�е�Ϫ���46�ź�������", "�㽭��ҵ��ѧ", "321100", "0579-88828858", "", "", "15700084560", "562384116@qq.com", "562384116", "1995-01-17", "��֧��" }, { "9", "��Ǭ��", "��", "�㽭�����Ϻ��������򽨹��嶫���", "�㽭��ҵ��ѧ", "314004", "", "", "", "15700082065", "1790805044@qq.com", "1790805044", "1995-07-15", "" }, { "10", "��ɯɯ", "Ů", "�㽭ʡ���ж����и�ɽ����Ϫ̲��", "�㽭��ҵ��ѧ", "322105", "", "", "", "15700083029", "1015369963@qq.com", "1015369963", "1994-11-03", "" } };

  private static final String[] s = { "���", "����", "�Ա�", "ͨѶ��ַ", "������λ", "��������", "��ͥ�绰����", "�칫�ҵ绰����", "�������", "�ƶ��绰����", "Email", "QQ�Ż�MSN", "��������", "��ע" };

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
    localVector.add(paramComunityItem.byteSex == 1 ? "��" : "Ů");
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
      throw new MyException("��������Ϊ�գ�", 1);
    if (((String)paramVector.elementAt(2)).equals("��"))
      localComunityItem.byteSex = 1;
    else if (((String)paramVector.elementAt(2)).equals("Ů"))
      localComunityItem.byteSex = 0;
    else
      throw new MyException("�Ա��ֶ�ֻ�ܺ���\"��\"��\"Ů\"��", 2);
    localComunityItem.sAddress = ((String)paramVector.elementAt(3));
    localComunityItem.sCompany = ((String)paramVector.elementAt(4));
    localComunityItem.sPostalCode = ((String)paramVector.elementAt(5));
    localComunityItem.sHomeTele = ((String)paramVector.elementAt(6));
    localComunityItem.sOfficeTele = ((String)paramVector.elementAt(7));
    localComunityItem.sFax = ((String)paramVector.elementAt(8));
    localComunityItem.sCellPhone = ((String)paramVector.elementAt(9));
    localComunityItem.sEmail = ((String)paramVector.elementAt(10));
    if ((localComunityItem.sEmail != null) && (!localComunityItem.sEmail.trim().equals("")) && (!p.matcher(localComunityItem.sEmail).matches()))
      throw new MyException("Email��ַ�Ƿ���", 10);
    localComunityItem.sInstantMessager = ((String)paramVector.elementAt(11));
    if ((paramVector.elementAt(12) != null) && (!((String)paramVector.elementAt(12)).trim().equals("")))
      try {
        localComunityItem.dateBirthday = df.parse((String)paramVector.elementAt(12));
      } catch (ParseException localParseException) {
        throw new MyException("���������ֶθ�ʽ����\n��ʽ����Ϊ yyyy-MM-dd��", 12);
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
        throw new MyException("д�ļ�����");
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
              throw new MyException("�ļ���ʽ����"); 
          }
        } catch (EOFException localEOFException) { ((ObjectInputStream)localObject1).close(); }

      }
      catch (IOException localIOException2)
      {
        throw new MyException("���ļ�����");
      } catch (ClassNotFoundException localClassNotFoundException) {
        throw new MyException("�ļ���ʽ����");
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
      JOptionPane.showMessageDialog(this, "�ļ���д����", "ͨѶ¼", 0);
    }
  }

  private Dimension testInfo() {
    this.vit.clear();
    for (int i = 0; i < this.info.size(); i++) {
      try {
        this.vit.add(detrans((Vector)this.info.elementAt(i)));
      } catch (MyException localMyException) {
        JOptionPane.showMessageDialog(this, "��" + i + "��" + localMyException.getMessage(), "ͨѶ¼", 0);

        return new Dimension(i, 2);
      }
    }
    return null;
  }

  private void performQuit() {
    int i = JOptionPane.showConfirmDialog(this, "�˳�ͨѶ¼�����棿", "ͨѶ¼", 1);

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
      localDlgAddModify = new DlgAddModify(this, (Vector)this.info.elementAt(paramInt), "�޸�ͨѶ¼");
      if (localDlgAddModify.getResult() != null) {
        this.info.setElementAt(localDlgAddModify.getResult(), paramInt);
        this.dtm.setDataVector(this.info, this.colName);
      }
    } else {
      ComunityItem localComunityItem = new ComunityItem();
      localComunityItem.nId = (this.info.size() + 1);
      Vector localVector = trans(localComunityItem);
      localVector.setElementAt("", 2);
      localDlgAddModify = new DlgAddModify(this, localVector, "���ͨѶ¼");
      if (localDlgAddModify.getResult() != null) {
        this.info.add(localDlgAddModify.getResult());
        this.dtm.setDataVector(this.info, this.colName);
      }
    }
    for (int j = 0; j < sizes.length; j++)
      this.table.getColumnModel().getColumn(j).setPreferredWidth(arrayOfInt[j]);
  }

  public MyComunity(JFrame paramJFrame, String paramString) throws MyException {
    super(paramJFrame, "ͨѶ¼");
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

    this.btnAdd = new JButton("���");
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

    this.btnModify = new JButton("�޸�");
    this.btnModify.setBounds(100, 240, 70, 25);
    this.btnModify.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        int i = MyComunity.this.table.getSelectedRow();
        if (i != -1)
          MyComunity.this.performAddModify(i);
      }
    });
    getContentPane().add(this.btnModify);

    this.btnDel = new JButton("ɾ��");
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

    this.btnFind = new JButton("��ѯ");
    this.btnFind.setBounds(280, 240, 70, 25);
    final MyComunity localMyComunity = this;
    this.btnFind.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        new DlgFind(localMyComunity);
      }
    });
    getContentPane().add(this.btnFind);

    this.btnClose = new JButton("�ر�");
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
        MyComunity.this.rlab.setText(((MyPair)MyComunity.this.rvec.elementAt(i)).val + "���ַ�ƥ��");
      }
    });
    JScrollPane localJScrollPane = new JScrollPane(this.table);
    getContentPane().add(localJScrollPane);
    this.rlab = new JLabel(((MyPair)paramVector1.elementAt(0)).val + "���ַ�ƥ�䡣");
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
  private static final String[] s = { "���", "����(����Ϊ��)", "�Ա�(����Ϊ��)", "ͨѶ��ַ", "������λ", "��������", "��ͥ�绰����", "�칫�ҵ绰����", "�������", "�ƶ��绰����", "Email", "QQ�Ż�MSN", "��������", "��ע" };

  private JLabel[] labels = new JLabel[14];
  private JTextField[] texts = new JTextField[14];
  private JButton btnOK = new JButton("ȷ��"); private JButton btnCancel = new JButton("ȡ��");
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
  private static final String[] s = { "����", "�Ա�", "ͨѶ��ַ", "������λ", "��������", "��ͥ�绰����", "�칫�ҵ绰����", "�������", "�ƶ��绰����", "Email", "QQ�Ż�MSN", "��������", "��ע" };

  private JLabel[] labels = new JLabel[13];
  private JTextField[] texts = new JTextField[13];
  private JButton btnFind = new JButton("����");
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
      JOptionPane.showMessageDialog(this, "������Ҫ���ҵ���Ϣ��", "ͨѶ¼", 1);

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
      new MyComunity(this, "���ҽ��", this.dvvs, this.vResult);
    else
      JOptionPane.showMessageDialog(this, "û���ҵ�ƥ��ļ�¼��", getTitle(), 1);
  }

  public DlgFind(MyComunity paramMyComunity)
  {
    super(paramMyComunity, "����");
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