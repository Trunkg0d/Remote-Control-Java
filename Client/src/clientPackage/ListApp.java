package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.AbstractButton;
import javax.swing.table.DefaultTableModel;

public class ListApp implements ActionListener{
	
	private ListAppDesign listAppDesign;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;
	
	String unq;
	String pid;
	String handles;

	public ListApp(ListAppDesign listAppDesign, Socket socket, BufferedReader reader, PrintWriter out) {
		this.listAppDesign = listAppDesign;
		this.socket = socket;
		this.reader = reader;
		this.out = out;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == listAppDesign.btnXem)
		{
			String request = "APPXEM";
			
			out.println(request);
			out.flush();
			
			while (true) {
				try
                {
					String line = reader.readLine();
					
                	unq = line.substring(0, line.indexOf(' '));
            		
                	line = line.substring(line.indexOf(' '));
                	line = line.trim();
                	pid = line.substring(0, line.indexOf(' '));
                	
                	line = line.substring(line.indexOf(' '));
                	handles = line.trim();
                	
                	String row[] = {unq, pid, handles};
                	listAppDesign.model.addRow(row);
                	
                }
                catch (Exception ex)
                {
                	break;
                }
				
			}
		}
		
		if (e.getSource() == listAppDesign.btnKill)
		{
			KillDesign killDesign = new KillDesign(socket, reader, out);
			killDesign.setVisible(true);
		}
		
		if (e.getSource() == listAppDesign.btnStart)
		{
			StartDesign startDesign = new StartDesign(socket, reader, out);
			startDesign.setVisible(true);
		}
		
		if (e.getSource() == listAppDesign.btnXoa)
		{
			listAppDesign.table.setModel(new DefaultTableModel());
			DefaultTableModel model = (DefaultTableModel) listAppDesign.table.getModel();
			String colname[] = {"Name Application", "ID Application", "Handles"};
			model.setColumnIdentifiers(colname);
			
		}
	}

}
