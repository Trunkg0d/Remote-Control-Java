package clientPackage;
import java.awt.EventQueue;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class ClientDesign extends JFrame 
{

	private JPanel contentPane;
	public Client client;
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter out;
	
	JButton butApp;
	JButton butConnect;
	JTextField txtIP;
	JButton butTat;
	JButton butExit;
	JButton butPic;
	JButton butKeyLock;
	JButton butProcess;
	JButton butChat;
	private JButton btnChat;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					ClientDesign frame = new ClientDesign(socket, reader, out);
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientDesign(Socket socket, BufferedReader reader, PrintWriter out) 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//
		setTitle("Client");
		ActionListener action = new Client(this, socket, reader, out);
		
		//
		// butApp
		//
		butApp = new JButton("App");
		butApp.setBounds(128, 47, 185, 64);
		contentPane.add(butApp);
		butApp.addActionListener(action);
		//
		// butConnect
		//
		butConnect = new JButton("Kết nối");
		butConnect.setBounds(325, 6, 96, 29);
		contentPane.add(butConnect);
		butConnect.addActionListener(action);
		//
		// txtIP
		//
		txtIP = new JTextField();
		txtIP.setText("Nhập IP");
		txtIP.setBounds(20, 6, 283, 29);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		//
		// butTat
		//
		butTat = new JButton("<html><center> Tắt <br /> máy </html>");
		butTat.setBounds(128, 123, 84, 54);
		contentPane.add(butTat);
		butTat.addActionListener(action);
		//
		// butExit
		//
		butExit = new JButton("Thoát");
		butExit.setBounds(325, 189, 96, 64);
		contentPane.add(butExit);
		butExit.addActionListener(action);
		//
		// butPic
		//
		butPic = new JButton("Chụp màn hình");
		butPic.setBounds(128, 189, 185, 64);
		contentPane.add(butPic);
		butPic.addActionListener(action);
		//
		// butKeyLock
		//
		butKeyLock = new JButton("Keystroke");
		butKeyLock.setBounds(325, 47, 96, 130);
		contentPane.add(butKeyLock);
		butKeyLock.addActionListener(action);
		//
		// butProcess
		//
		butProcess = new JButton("");
		butProcess.setText("Process");
		butProcess.setBounds(20, 47, 96, 206);
		contentPane.add(butProcess);
		butProcess.addActionListener(action);
		//
		// butChat
		//
		butChat = new JButton("Chat");
		butChat.setBounds(224, 123, 89, 54);
		contentPane.add(butChat);
		butChat.addActionListener(action);
	}
		
}
