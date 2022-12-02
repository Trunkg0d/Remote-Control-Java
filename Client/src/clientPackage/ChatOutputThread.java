package clientPackage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChatOutputThread extends Thread {
	Socket socket;
	JTextArea txt;
	BufferedReader reader;
	String sender;
	private static DataInputStream dataInputStream;
	String receiver;
	static JButton button;
	
	private static DataOutputStream dataOutputStream = null;
	
	public ChatOutputThread(Socket socket, JTextArea txt, String sender, String receiver) {
		super();
		this.socket = socket;
		this.txt = txt;
		this.sender = sender;
		this.receiver = receiver;
		
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				if (socket != null) {
					dataInputStream = new DataInputStream(socket.getInputStream());
		            dataOutputStream = new DataOutputStream(socket.getOutputStream());
					String msg = "";
					if ((msg = reader.readLine()) != null && msg.length() > 0) {
						txt.append(receiver + ": " + msg + "\n");
						if(msg.equals("NULL") || msg.equals("KILLED")) {
							txt.setText("");
						}
						if (msg.equals("QUIT")) {
							JOptionPane.showMessageDialog(null, "Server đã rời khỏi đoạn chat");
							reader.close();
							socket.close();
							
						}
						
					}
					sleep(1000);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
