package serverPackage;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatPanel extends JPanel {
	
	Socket socket;
	BufferedReader reader;
	PrintWriter out;
	ChatOutputThread t;
	String sender;
	String receiver;
	JTextArea txtMessages;

	/**
	 * Create the panel.
	 */
	public ChatPanel(Socket socket, String sender, String receiver) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTextArea txtMessage = new JTextArea();
		scrollPane.setViewportView(txtMessage);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtMessage.getText().trim().length() == 0)
					return;
				try {
					out.println(txtMessage.getText());
					out.flush();
					
					if (txtMessage.getText().equals("QUIT")) {
						System.exit(0);
					}
					
					txtMessages.append(sender + ": " + txtMessage.getText() + "\n");
					txtMessage.setText("");
					
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		panel.add(btnSend);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, BorderLayout.CENTER);
		
		txtMessages = new JTextArea();
		scrollPane_1.setViewportView(txtMessages);
		
		this.socket = socket;
		this.sender = sender;
		this.receiver = receiver;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			t = new ChatOutputThread(socket, txtMessages, sender, receiver);
			t.start();
		}
		catch (Exception e) {
			
		}
	}
	
	public JTextArea getTxtMessages() {
		return this.txtMessages;
	}
	
	

}
