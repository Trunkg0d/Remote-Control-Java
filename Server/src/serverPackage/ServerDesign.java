package serverPackage;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import serverPackage.Server;

import javax.swing.JButton;
import java.awt.BorderLayout;

public class ServerDesign extends JFrame 
{

	private JPanel contentPane;
	public Server server;
	JButton button1;

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
					ServerDesign frame = new ServerDesign();
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
	public ServerDesign() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//
		setSize(200,200);
		setTitle("Server");
		ActionListener action = new Server(this);
		//

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		button1 = new JButton("Mở server");
		contentPane.add(button1);
		button1.addActionListener(action);
	}
}
