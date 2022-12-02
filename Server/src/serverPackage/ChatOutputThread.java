package serverPackage;
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
						if (msg.equals("FILE")) {
							String fileName = reader.readLine();
							txt.append(receiver + ": " + fileName + "\n");
							int choose = JOptionPane.showConfirmDialog(null, "<html> Có file được gửi đến <br /> Bạn có muốn lưu không? </html>", "title", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (choose == JOptionPane.YES_OPTION) {
								try {
									JFileChooser saveFileChooser = new JFileChooser();
									saveFileChooser.setCurrentDirectory(new File("D:\\"));
									
									int returnvalue = saveFileChooser.showSaveDialog(this.button);
									if (returnvalue == JFileChooser.APPROVE_OPTION) {
										File file = saveFileChooser.getSelectedFile();
										
										receiveFile(saveFileChooser.getSelectedFile().toString());
									} 
									
									
									
									
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						} else {
							txt.append(receiver + ": " + msg + "\n");
							if (msg.equals("QUIT")) {
								JOptionPane.showMessageDialog(null, "Client đã rời khỏi đoạn chat");
								reader.close();
								socket.close();
								
							}
						}
						
					}
					sleep(1000);
				}
			}
			catch (Exception e) {
				
			}
		}
	}
	
	private static void receiveFile(String fileName) throws Exception {
			File file = new File(fileName);
	        int bytes = 0;
	        FileOutputStream fileOutputStream = new FileOutputStream(file);
	 
	        long size = dataInputStream.readLong(); // read file size

	        byte[] buffer = new byte[4 * 1024];
	        while (size > 0
	               && (bytes = dataInputStream.read(
	                       buffer, 0,
	                       (int)Math.min(buffer.length, size)))
	                      != -1) {
	            fileOutputStream.write(buffer, 0, bytes);
	            size -= bytes; // read upto file size
	        }	        
	        	        
	        fileOutputStream.close();
	    }
}
