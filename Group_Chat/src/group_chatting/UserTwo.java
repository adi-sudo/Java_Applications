package group_chatting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserTwo extends JFrame implements Runnable, ActionListener {
	JPanel p1;
	JTextField t1;
	JButton b1;
	static JTextArea a1;
	
	BufferedWriter writer;
	BufferedReader reader;
	UserTwo()	{
		
		p1=new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0,0,450,70);
		add(p1);
		
		ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("group_chatting/icons/3.png"));
		Image i2=i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3=new ImageIcon(i2);
		JLabel l1=new JLabel(i3);
		l1.setBounds(5,17,30,30);
		p1.add(l1);
		
		l1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae)	{
				System.exit(0);
			}
		});
		
		ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("group_chatting/icons/mirzapur.png"));
		Image i5=i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6=new ImageIcon(i5);
		JLabel l2=new JLabel(i6);
		l2.setBounds(40,5,60,60);
		p1.add(l2);
		
		ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("group_chatting/icons/video.png"));
		Image i8=i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9=new ImageIcon(i8);
		JLabel l5=new JLabel(i9);
		l5.setBounds(290,20,30,30);
		p1.add(l5);
		
		ImageIcon i11=new ImageIcon(ClassLoader.getSystemResource("group_chatting/icons/phone.png"));
		Image i12=i11.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i13=new ImageIcon(i12);
		JLabel l6=new JLabel(i13);
		l6.setBounds(350,20,35,30);
		p1.add(l6);
		
		ImageIcon i14=new ImageIcon(ClassLoader.getSystemResource("group_chatting/icons/3icon.png"));
		Image i15=i14.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
		ImageIcon i16=new ImageIcon(i15);
		JLabel l7=new JLabel(i16);
		l7.setBounds(410,20,13,25);
		p1.add(l7);
		
		JLabel l3=new JLabel("Mirzapur");
		l3.setFont(new Font("SAN SERIF",Font.BOLD,18));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110,15,100,20);
		p1.add(l3);
		
		JLabel l4=new JLabel("Guddu,Kaleen,Bablu");
		l4.setFont(new Font("SAN SERIF",Font.PLAIN,14));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110,35,100,20);
		p1.add(l4);
		
		a1=new JTextArea();
		a1.setBounds(5,75,440,570);
		a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		a1.setEditable(false);
		a1.setLineWrap(true);
		a1.setWrapStyleWord(true);
		a1.setBackground(Color.WHITE);
		add(a1);
		
		t1=new JTextField();
		t1.setBounds(5,655,300,40);
		t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		add(t1);
		
		b1=new JButton("Send");
		b1.setBounds(320,655,120,40);
		b1.setBackground(new Color(7,94,84));
		b1.setForeground(Color.WHITE);
		b1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		b1.addActionListener(this);
		add(b1);
		
		
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setSize(450,700);
		setLocation(300,20);
		setUndecorated(true);
		setVisible(true);
	
	try {
		Socket socketClient=new Socket("localhost",2001);
		writer=new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
		reader=new BufferedReader(new InputStreamReader(socketClient.getInputStream())); 
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void actionPerformed(ActionEvent ae)	{
		String str=" Guddu Bhaiya\n"+t1.getText();
		try {
			writer.write(str);
			writer.write("\r\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.setText("");
		
		
	}

	public static void main(String[] args) {
	UserTwo one=new UserTwo();	
	Thread t1=new Thread(one);
	t1.start();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String msg="";
		try {
			while((msg=reader.readLine())!=null)	{
			a1.append(msg+"\n");	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}



