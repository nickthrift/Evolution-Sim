package utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseMotionListener, MouseListener{
	
	public boolean keys[] = new boolean[120];
	public boolean w, s, up, down, resetFPS;
	public int mouseX, mouiseY, shiftMouseX, shiftMouseY, reset = 0;
	public Vector2D position = null, shiftPosition = null;
	public void update(){
		w = keys[KeyEvent.VK_W];
		s = keys[KeyEvent.VK_S];
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		resetFPS = keys[KeyEvent.VK_D];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < 120)
			keys[e.getKeyCode()] = true;
		if(e.getKeyChar() == KeyEvent.VK_SPACE)
			reset = 1;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < 120)
			keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() < 120)
			keys[e.getKeyCode()] = false;
		if(e.getKeyChar() == KeyEvent.VK_SPACE)
			reset = 1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.isShiftDown()){
			shiftPosition = new Vector2D(e.getX(), e.getY());
			//System.out.println(shiftPosition.toString());
		}
		else{
			position = new Vector2D(e.getX(), e.getY());
			//System.out.println(position.toString());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
