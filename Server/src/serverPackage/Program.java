package serverPackage;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import serverPackage.ServerDesign;

public class Program {
	Socket socket;
	BufferedReader reader;
	PrintWriter out;
		
	public static void main(String[] args) {
		Program program = new Program();
	}

	public Program() {
		try {
			
			ServerDesign frame = new ServerDesign();
			frame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
