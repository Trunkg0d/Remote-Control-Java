package clientPackage;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

public class StartDesign extends JFrame {

	private JPanel contentPane;
	
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter out;
	public JTextField txtStart;
	
	public JButton buttonStart;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KillDesign frame = new KillDesign(socket, reader, out);
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
	public StartDesign(Socket socket, BufferedReader reader, PrintWriter out) {
		this.socket = socket;
		this.reader = reader;
		this.out = out;
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setSize(300, 100);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//
		setTitle("Start");
		ActionListener action = new Start(this, socket, reader, out);
		
		txtStart = new JTextField();
		txtStart.setText("Nhập tên");
		txtStart.setBounds(6, 18, 178, 29);
		contentPane.add(txtStart);
		txtStart.setColumns(10);
		
		buttonStart = new JButton("Start");
		buttonStart.setBounds(196, 18, 98, 29);
		buttonStart.addActionListener(action);
		contentPane.add(buttonStart);
	}
}
