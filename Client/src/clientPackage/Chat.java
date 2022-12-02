package clientPackage;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Chat extends JFrame {

	private JPanel contentPane;
	private JTextField txtStaff;
	
	Socket socket;
	String staffName = "";
	BufferedReader reader;
	PrintWriter out;
	ChatOutputThread t;
	ChatPanel p;
	private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;


	public Chat(final Socket socket) {
		this.socket = socket;
		setTitle("Client");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		final JFrame thisFrame = this;
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 3, 0, 0));
		
		
		JButton btnConnect = new JButton("Connect");
		panel.add(btnConnect);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				staffName = txtStaff.getText();
				try {
					dataInputStream = new DataInputStream(socket.getInputStream());
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
					if (socket != null) {
						p = new ChatPanel(socket, staffName, "Server");
						thisFrame.getContentPane().add(p);
						p.getTxtMessages().append("Connected!\n");
						p.updateUI();
				
						
						reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						out = new PrintWriter(socket.getOutputStream());
						
						
						
						out.println(staffName);
						out.flush();
					}
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		JButton btnFile = new JButton("File");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
					try {
						JFileChooser fileChooser = new JFileChooser();
						if (fileChooser.showOpenDialog(null) == fileChooser.APPROVE_OPTION) {
							File file = fileChooser.getSelectedFile();
							
							p.txtMessages.append(staffName + ": " + fileChooser.getName(file) + "\n");
							
							out.println("FILE");
							out.flush();
							
							out.println(fileChooser.getName(file));
							out.flush();
							
							String path = fileChooser.getCurrentDirectory() + "/" + fileChooser.getName(file);
							sendFile(path);
						}
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				
			}
		});
		panel.add(btnFile);
		
		JLabel lblNewLabel = new JLabel("Name: ");
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtStaff = new JTextField();
		txtStaff.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(txtStaff);
		txtStaff.setColumns(10);
	}
	
	private static void sendFile(String path) throws Exception {
	        int bytes = 0;
	        File file = new File(path);
	        FileInputStream fileInputStream = new FileInputStream(file);
	 
	        dataOutputStream.writeLong(file.length());
	        
	        byte[] buffer = new byte[4 * 1024];
	        while ((bytes = fileInputStream.read(buffer)) != -1) {
	        	dataOutputStream.write(buffer, 0, bytes);
	        	dataOutputStream.flush();
	        }
	        fileInputStream.close();
	    }
	
}
