package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Start implements ActionListener {

	private StartDesign startDesign;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;
		
	public Start(StartDesign startDesign, Socket socket, BufferedReader reader, PrintWriter out) 
	{
		this.startDesign = startDesign;
		this.socket = socket;
		this.reader = reader;
		this.out = out;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startDesign.buttonStart)
		{
			String request = "START";
			out.println(request);
			out.flush();
			
			String Name = startDesign.txtStart.getText();
			out.println(Name);
			out.flush();
			
			String response = "";
			try {
				response = reader.readLine();
				if (response.equals("STARTED")) {
					JOptionPane.showMessageDialog(null, "Đã bật");
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Không bật được", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}

		}
		
	}

}
