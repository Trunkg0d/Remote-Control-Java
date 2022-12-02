package serverPackage;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class Chat extends JFrame {//

	private JPanel contentPane;
	
	Socket socket;
	BufferedReader reader;
	Thread t;
	private JTabbedPane tabbedPane;


	public Chat(Socket socket) {
		this.socket = socket;
		setTitle("Server");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String S = reader.readLine();
			String staffName = S;
			
			ChatPanel p = new ChatPanel(socket, "Server", staffName);
			tabbedPane.add(staffName, p);
			p.updateUI();
			Thread.sleep(100);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	

}
