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

public class KillDesign extends JFrame {

	private JPanel contentPane;
	
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter out;
	public JTextField txtKill;
	
	public JButton buttonKill;
	

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
	public KillDesign(Socket socket, BufferedReader reader, PrintWriter out) {
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
		setTitle("Kill");
		ActionListener action = new Kill(this, socket, reader, out);
		
		txtKill = new JTextField();
		txtKill.setText("Nhập ID");
		txtKill.setBounds(6, 18, 178, 29);
		contentPane.add(txtKill);
		txtKill.setColumns(10);
		
		buttonKill = new JButton("Kill");
		buttonKill.setBounds(196, 18, 98, 29);
		buttonKill.addActionListener(action);
		contentPane.add(buttonKill);
	}
}
