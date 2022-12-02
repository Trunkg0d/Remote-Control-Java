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
import javax.swing.JTextArea;

public class KeyStrokeDesign extends JFrame {

	private JPanel contentPane;
	
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter out;
	
	public JButton btnHook;
	public JButton btnUnhook;
	public JButton btnPrint;
	public JButton btnRemove;
	public DefaultTableModel model;
	public JTextArea Box;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KeyStrokeDesign frame = new KeyStrokeDesign(socket, reader, out);
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
	public KeyStrokeDesign(Socket socket, BufferedReader reader, PrintWriter out) {
		
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
		setTitle("KeyStroke");
		ActionListener action = (ActionListener) new KeyStroke(this, socket, reader, out);
		
		btnHook = new JButton("Hook");
		btnHook.addActionListener(action);
		panel.add(btnHook);
		
		btnUnhook = new JButton("Unhook");
		btnUnhook.addActionListener(action);
		panel.add(btnUnhook);
		
		btnPrint = new JButton("In phím");
		btnPrint.addActionListener(action);
		panel.add(btnPrint);
		
		btnRemove = new JButton("Xoá");
		btnRemove.addActionListener(action);
		panel.add(btnRemove);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		Box = new JTextArea();
		panel_1.add(Box, BorderLayout.CENTER);
	}
}
