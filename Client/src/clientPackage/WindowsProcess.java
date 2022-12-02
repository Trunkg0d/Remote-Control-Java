package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class WindowsProcess implements ActionListener{
	
	private ProcessDesign showP;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;

	public WindowsProcess(ProcessDesign showP, Socket socket, BufferedReader reader, PrintWriter out) {
		this.showP = showP;
		this.socket = socket;
		this.reader = reader;
		this.out = out;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == showP.btnRun)
		{
			String request = "SHOW";
			
			out.println(request);
			out.flush();
			
			while (true) {
				try
                {
					String line = reader.readLine();
					
					String[] parts = line.split(",");
		            String unq = parts[0].substring(1).replaceFirst(".$", "");
		            String pid = parts[1].substring(1).replaceFirst(".$", "");
		            String session1 = parts[2].substring(1).replaceFirst(".$", "");
		            String session2 = parts[3].substring(1).replaceFirst(".$", "");
		            
		            String row[] = {unq, pid,session1, session2};
                	showP.model.addRow(row);
                	
                }
                catch (Exception ex)
                {
                	break;
                }
				
			}
		}
		
		if (e.getSource() == showP.btnKill)
		{
			KillDesign killDesign = new KillDesign(socket, reader, out);
			killDesign.setVisible(true);
		}
		
		if (e.getSource() == showP.btnRemove)
		{
			showP.table.setModel(new DefaultTableModel());
			DefaultTableModel model = (DefaultTableModel) showP.table.getModel();
			String colname[] = {"Process Name", "ID Process", "Session Name", "Session#"};
			model.setColumnIdentifiers(colname);
		}
		
		if (e.getSource() == showP.btnStart)
		{
			StartDesign startDesign = new StartDesign(socket, reader, out);
			startDesign.setVisible(true);
		}
	}

}
