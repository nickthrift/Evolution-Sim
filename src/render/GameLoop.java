package render;

import screen.Screen;


public class GameLoop implements Runnable{
	public boolean running = false;
	static int goalFrames = 60;
	Thread render;
	Screen screen;
	String title;
	Renderer renderer;
	
	
	public static void main(String[] args) {
		new GameLoop();
	}
	
	public GameLoop(){
		screen = new Screen(800, 600, this);
		renderer = new Renderer(screen);
		title = "Testing";
		this.start();
	}
	
	
	public synchronized void start(){
		if(render == null){
			running = true;
			render = new Thread(this);
			render.start();
		}
	}
	
	public synchronized void stop(){
		running = false;
		System.out.println("Stopping Engine");
		try {
			if(render != null)
				render.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void changefps(int change){
		goalFrames += change;
	}
	public static void resetFPS() {
		goalFrames = 60;
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer  = System.currentTimeMillis();
		double FPS = 1000000000.0/goalFrames;	
		double delta = 0;
		int frames = 0;
		int updates = 0;		
		double runningTime = 0;
		
		while(running){
			long now = System.nanoTime();
			FPS = 1000000000.0/goalFrames;
			delta += (now - lastTime) / FPS;
			lastTime = now;
			if(delta >= 1){
				updates++;	
				renderer.update();
				frames++;
				renderer.render();
				delta--;
				runningTime++;
			}
			
			
			
			if(System.currentTimeMillis() - timer >= 1000){
				timer += 1000;
				int seconds = (int) (runningTime/60 % 60);
				String leadingZero = "";
				if(seconds < 10){
					leadingZero = "0";
				}
				int minutes =  (int) Math.round(runningTime/60/60);
				screen.setTitle(title + "              Running at: "+updates+" updates, "+frames+" frames for " + minutes + ":" + leadingZero + seconds + " Equivilant | Trying For : " + goalFrames + " Frames");				
				updates = 0;
				frames = 0;
			}			
		}		
	}

	
}
