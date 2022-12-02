package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Kill implements ActionListener {

	private KillDesign killDesign;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;
		
	public Kill(KillDesign killDesign, Socket socket, BufferedReader reader, PrintWriter out) 
	{
		this.killDesign = killDesign;
		this.socket = socket;
		this.reader = reader;
		this.out = out;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == killDesign.buttonKill)
		{
			String request = "KILL";
			out.println(request);
			out.flush();
			
			String ID = killDesign.txtKill.getText();
			out.println(ID);
			out.flush();
			
			try {
				String response = reader.readLine();
				if (response.equals("KILLED")) {
					JOptionPane.showMessageDialog(null, "Đã diệt");
				} else {
					if(response.equals("")) {
						JOptionPane.showMessageDialog(null, "Đã diệt");
						
					} else {
						JOptionPane.showMessageDialog(null, "Không tìm thấy chương trình", "Lỗi", JOptionPane.ERROR_MESSAGE);
					
					}					
				}
			} catch (IOException e1) {
				return;
			}
			
		}
		
	}

}
