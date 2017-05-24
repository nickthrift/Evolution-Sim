package screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import render.GameLoop;

public class Screen extends Canvas implements WindowListener{	
	
	private static final long serialVersionUID = 3354833010440458955L;
	
	public static int WIDTH, HEIGHT;
	private static Color background = Color.BLACK;
	public boolean closing;
	private JFrame frame;
	private GameLoop loop;
	
	public Screen(int width, int height, GameLoop loop){
		WIDTH = width;
		HEIGHT = height;
		this.loop = loop;
		setup();
	}
	
	public void setup(){

		this.setSize(WIDTH, HEIGHT);
		this.setBackground(background);
		this.setSize(WIDTH, HEIGHT);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(this);
		frame.pack();		
		frame.setMinimumSize(getSize());
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);
		frame.addWindowListener(this);
	}
	
	public void clear(Graphics2D g){
		g.setColor(background);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void setTitle(String title){
		frame.setTitle(title);
	}
	
	public String getTitle(){
		return frame.getTitle();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {		
		loop.stop();
		System.out.println("Closing Window");
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}	
}
