package clientPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JTable;

public class ListAppDesign extends JFrame {

	private JPanel contentPane;
	public JTable table;
	
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter out;
	
	public JButton btnKill;
	public JButton btnXem;
	public JButton btnXoa;
	public JButton btnStart;
	public DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListAppDesign frame = new ListAppDesign(socket, reader, out);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListAppDesign(Socket socket, BufferedReader reader, PrintWriter out) {
		
		this.socket = socket;
		this.reader = reader;
		this.out = out;
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		//
		setTitle("listApp");
		ActionListener action = (ActionListener) new ListApp(this, socket, reader, out);
		
		btnKill = new JButton("Kill");
		btnKill.addActionListener(action);
		panel.add(btnKill);
		
		btnXem = new JButton("Xem");
		btnXem.addActionListener(action);
		panel.add(btnXem);
		
		btnXoa = new JButton("Xoá");
		btnXoa.addActionListener(action);
		panel.add(btnXoa);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(action);
		panel.add(btnStart);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 428, 221);
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		model = (DefaultTableModel) table.getModel();
		String colname[] = {"Name Application", "ID Application", "Handles"};
		model.setColumnIdentifiers(colname);
	}
}
