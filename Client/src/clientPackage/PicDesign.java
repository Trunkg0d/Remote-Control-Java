package clientPackage;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PicDesign extends JFrame 
{

	private JPanel contentPane;
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter out;
	
	JLabel picture;
	JButton butTake;
	JButton butLuu;

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
					PicDesign frame = new PicDesign(socket, reader, out);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PicDesign(Socket socket, BufferedReader reader, PrintWriter out) 
	{
		this.socket = socket;
		this.reader = reader;
		this.out = out;
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//
		setTitle("pic");
		ActionListener action = new Pic(this, socket, reader, out);
		
		picture = new JLabel("");
		picture.setBounds(16, 16, 299, 220);
		contentPane.add(picture);
		
		butTake = new JButton("Chụp");
		butTake.setBounds(327, 16, 98, 124);
		butTake.addActionListener(action);
		contentPane.add(butTake);
		
		butLuu = new JButton("Lưu");
		butLuu.setBounds(327, 152, 98, 80);
		butLuu.addActionListener(action);
		contentPane.add(butLuu);
	}
}
