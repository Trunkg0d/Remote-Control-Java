package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import clientPackage.ClientDesign;

public class Program {	
	
	Socket socket;
	BufferedReader reader;
	PrintWriter out;
	ClientDesign clientDesign;
	
	public static void main(String[] args) {
		Program program = new Program();
	}
	
	public Program() {
		try {
			
			ClientDesign frame = new ClientDesign(socket, reader, out);
			frame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
