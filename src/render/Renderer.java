package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Vector;

import creatures.BasicCreature;
import edibles.Food;
import enums.EntityType;
import screen.Screen;
import utility.Input;
import utility.Vector2D;

public class Renderer {
	
	
	Screen screen;
	Input input = new Input();
	
	Vector<BasicCreature> creatures = new Vector<BasicCreature>();
	int creatureCap = 25;
	Food[] foods = new Food[25];
	
	public Renderer(Screen screen){
		this.screen = screen;
		screen.addMouseMotionListener(input);
		screen.addKeyListener(input);
		screen.addMouseListener(input);
		setup();
		for(int i = 0; i < foods.length; i++)
			foods[i] = new Food();
	}
	
	private void setup(){
		//c = new BasicCreature(new Vector2D(10, 10));
		for(int i = 0; i < 5; i++)
			if(creatures.size() < creatureCap)
				creatures.add(new BasicCreature(Vector2D.getRandomVector2D(Screen.WIDTH, Screen.HEIGHT)));
		/*for(int i = 0; i < foods.length; i++)
			foods[i] = new Food();*/
	}
	
	public void update(){
		
		while(creatures.size() < 5){
			creatures.add(new BasicCreature(Vector2D.getRandomVector2D(Screen.WIDTH, Screen.HEIGHT)));
			//System.out.println("Added Creature to boost population");
		}
		
		
		input.update();
		if(input.reset == 1){
			setup();
			input.reset = 0;
		}
		if(input.up)
			GameLoop.changefps(1);
		else if(input.down)
			GameLoop.changefps(-1);
		else if(input.resetFPS)
			GameLoop.resetFPS();
		
		
		debug();
		input.position = null;
		input.shiftPosition = null;
		
		
		//Vector2D mouse = new Vector2D(input.mx, input.my);
		
		for(int i = creatures.size()-1; i >= 0; i--){
			creatures.get(i).update();
			creatures.get(i).eat(foods);
			creatures.get(i).mate(creatures);
			if(creatures.get(i).health <= 0){
				creatures.remove(i);
				continue;
			}
			if(creatures.get(i).child != null){
				if(creatures.size() < creatureCap){
					creatures.add(creatures.get(i).child);
					if(creatures.size() < creatureCap * 1.2 && Math.random() < .0001*60){
						creatures.add(new BasicCreature(Vector2D.getRandomVector2D(Screen.WIDTH, Screen.HEIGHT)));
						//System.out.println("Adding Variety");
					}
				}
				creatures.get(i).child = null;
			}			
		}
		
		for(int i = 0; i < foods.length; i++){
			foods[i].update();
			if(foods[i].rotten)
				foods[i] = new Food();
		}
	}
	
	
	private void debug(){
		BasicCreature healthiest = null;
		boolean first = true;
		boolean off = false;
		String family = "";
		BasicCreature oldest = null;
		for(BasicCreature c : creatures)
		{
			//c.debug = false;
			c.healthiest = false;
			if(healthiest == null || c.timeAlive > healthiest.timeAlive){
				healthiest = c;
			}
			/*if(healthiest == null || c.health > healthiest.health){
				healthiest = c;
			}*/
			if(input.position != null && first){
				if(input.position.dist(c.position) < 50 && first){
					c.debug = true;
					first = false;
					System.out.println(c.toString());
				}else{
					c.debug = false;
				}
			}else if(input.shiftPosition != null && family.length() < 1){
				if(input.shiftPosition.dist(c.position) < 50){
					if(c.debug)
						off = true;
					c.debugSwitch(off);
					family = c.dna.familyName;
					
				}
			}else if(family == c.dna.familyName){
				c.debugSwitch(off);
			}
		}
		if(healthiest != null)
			healthiest.healthiest = true;
		
		
	}
	
	private void debug(Graphics2D g){
		int xOff = 10;
		int yOff = 22;
		int newYOffset = yOff;
		//EntityType types[] = EntityType.getAllTypes();
		for(int i = 0; i < creatures.size(); i++)
		{
			if(creatures.get(i).debug || creatures.get(i).healthiest){
				String data[] = creatures.get(i).debugData;
				for(int j = 0; j < data.length; j++){
					if(data[j] != null){
						switch(j){
							case 0:
								g.setColor(creatures.get(i).perceptionColor[0]);
								break;
							case 1:
								g.setColor(creatures.get(i).perceptionColor[1]);
								break;
							case 2:
								g.setColor(creatures.get(i).perceptionColor[2]);
								break;
							default:
								g.setColor(Color.WHITE);
								break;
						}
						g.drawString(data[j], xOff, newYOffset + j * yOff);	
						
					}
				}
				newYOffset += yOff * data.length + yOff;				
			}
			
		}
		
	}
	
	public void render(){
		BufferStrategy bs = screen.getBufferStrategy();
		if(bs==null){
			screen.createBufferStrategy(3);
			return;
		}
		bs.show();
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		screen.clear(g);
		
		for(Food food : foods)
			food.show(g);
		
		for(BasicCreature c : creatures){
			c.show(g);
		}
		debug(g);
		
		g.dispose();
	}

}
