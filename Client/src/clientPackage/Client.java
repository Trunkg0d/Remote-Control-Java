package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client implements ActionListener 
{
	
	private ClientDesign clientDesign;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;
	
	private String IP;
	
	public Client(ClientDesign clientDesign, Socket socket, BufferedReader reader, PrintWriter out) {
		this.clientDesign = clientDesign;
		this.socket = socket;
		this.reader = reader;
		this.out = out;
	}

	public void actionPerformed(ActionEvent e)
	{
		//
		// butApp
		//
		if (e.getSource() == clientDesign.butApp) 
		{
			if (socket == null) {
				JOptionPane.showMessageDialog(null, "Chưa kết nối đến Server", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else {
				ListAppDesign listAppDesign = new ListAppDesign(socket, reader, out);
				listAppDesign.setVisible(true);
			}
		}
		//
		// butConnect
		//
		if (e.getSource() == clientDesign.butConnect) 
		{
			boolean text = true;
			String serverIP = clientDesign.txtIP.getText();
			try {
				socket = new Socket(serverIP, 1234);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Chưa kết nối đến Server", "Lỗi", JOptionPane.ERROR_MESSAGE);
				text = false;
			}
			
			if (text) 
			{
				JOptionPane.showMessageDialog(null, "Kết nối đến Server thành công");
				try {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = new PrintWriter(socket.getOutputStream());
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		//
		// butTat
		//
		if (e.getSource() == clientDesign.butTat) 
		{
			if (socket == null) {
				JOptionPane.showMessageDialog(null, "Chưa kết nối đến Server", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else {
				String cmd = "SHUTDOWN";
				out.println(cmd);  
				out.flush();  
				out.close();  
				socket = null;
			}
		}
		//
		// butExit
		//
		if (e.getSource() == clientDesign.butExit) 
		{
			System.exit(0);
		}
		//
		// butPic
		//
		if (e.getSource() == clientDesign.butPic) 
		{
			if (socket == null) {
				JOptionPane.showMessageDialog(null, "Chưa kết nối đến Server", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else {
				PicDesign picDesign = new PicDesign(socket, reader, out);
				picDesign.setVisible(true);
			}
		}
		//
		// butKeyLock
		//
		if (e.getSource() == clientDesign.butKeyLock) 
		{
			if (socket == null) {
				JOptionPane.showMessageDialog(null, "Chưa kết nối đến Server", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else {
				KeyStrokeDesign keyStrokeDesign = new KeyStrokeDesign(socket, reader, out);
				keyStrokeDesign.setVisible(true);
			}
		}
		//
		// butProcess
		//
		if (e.getSource() == clientDesign.butProcess) 
		{
			if (socket == null) {
				JOptionPane.showMessageDialog(null, "Chưa kết nối đến Server", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else {
				ProcessDesign processDesign = new ProcessDesign(socket, reader, out);
				processDesign.setVisible(true);
			}
		}
		//
		// butChat
		//
		if (e.getSource() == clientDesign.butChat) 
		{
			if (socket == null) {
				JOptionPane.showMessageDialog(null, "Chưa kết nối đến Server", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else {
				out.println("CHAT");
				out.flush();
				
				reader = null;
				out = null;
				
				Chat chat = new Chat(socket);
				chat.setVisible(true);
			}
		}
		
	}

}
