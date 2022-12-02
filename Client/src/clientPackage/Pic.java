package clientPackage;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Pic implements ActionListener 
{

	private PicDesign picDesign;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;
	
	BufferedImage img;
	
	public Pic(PicDesign picDesign, Socket socket, BufferedReader reader, PrintWriter out) 
	{
		this.picDesign = picDesign;
		this.socket = socket;
		this.reader = reader;
		this.out = out;
	}
	

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == picDesign.butTake)
		{
			String request = "TAKE";
			
			out.println(request);
			out.flush();
	
			try 
			{
				img = ImageIO.read(ImageIO.createImageInputStream(socket.getInputStream()));
				try
				{
					BufferedImage outputImage = new BufferedImage(300, 200, img.getType());
					
					Graphics2D g2d = outputImage.createGraphics();
					g2d.drawImage(img, 0, 0, 300, 200, null);
					g2d.dispose();
			            
			        ImageIcon imageIcon = new ImageIcon(outputImage);
			        picDesign.picture.setIcon(imageIcon);
				}
				catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Không nhận được ảnh từ Server");
				}
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}

		}
		
		if (e.getSource() == picDesign.butLuu) 
		{
			try 
			{
				JFileChooser saveFileChooser = new JFileChooser();
				saveFileChooser.setCurrentDirectory(new File("D:\\"));
				saveFileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
				
				int returnvalue = saveFileChooser.showSaveDialog(this.picDesign.butLuu);
				if (returnvalue == JFileChooser.APPROVE_OPTION) {
					ImageIO.write(img, "png", saveFileChooser.getSelectedFile());
				}
			}
			catch (Exception ex) 
			{
				JOptionPane.showMessageDialog(null, "Không có hình ảnh để lưu");
			}
		}
		
	}
	
}
