package creatures;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import edibles.Food;
import enums.EntityType;
import render.Shapes;
import screen.Screen;
import utility.Maths;
import utility.Vector2D;


public class BasicCreature {
	
	/**
	 * The Vector Position of the creature.
	 * */
	public Vector2D position;
	/**
	 * The type of object on the screen.
	 * */
	public EntityType entityType = EntityType.BASIC_CREATURE;
	/**
	 * Booleans used for debugging
	 * */
	public boolean debug = false, healthiest = false;
	/**
	 * This creature's child.
	 * */
	public BasicCreature child = null;
	/**
	 * How many updates the creature has been alive for
	 * */
	public int timeAlive = 0;

	/**
	 * 
	 * [0]: interactions = how the creature interacts with its environment 
	 * [1]: perceptions = how far the creature sees objects in its environment
	 * [2]: diet = how well the creature digests different entities in the environment 
	 * 
	 * */
	public Map<EntityType, float[]> behaviors = new HashMap<EntityType, float[]>();
	
	/**
	 * How drawn to or repealed by an entity this creature is
	 * */
	private  Map<EntityType, Float> learntInteraction = new HashMap<EntityType, Float>();

	/**
	 * This creature's base stats
	 * */
	public DNA dna;
	/**
	 * This creature's first generation's stats
	 * */
	private DNA ancestorDNA;
	//Debug data array
	public String[] debugData = new String[10];
	//The colors for showing how far the creature can see
	public final Color perceptionColor[] = {Color.RED, Color.BLUE, Color.GREEN};
	/**Always 1*/
	private final float defaultbehavior= 1;
	
	private Vector2D acceleration = new Vector2D(), velocity, screenCenter = new Vector2D(Screen.WIDTH/2, Screen.HEIGHT/2), empty = new Vector2D();	
	private int timeoffscreen = 0, timeSinceLastMeal = 0;
	private  int fps = 60;
	private float healthDegen = .1f;
	public float health = fps*2-healthDegen;
	
	private float maxHealth = health;
	private float maxSpeed  = 1;
	private float maxTurnSpeed = maxSpeed * .05f;	
	private GeneralPath body = new GeneralPath();
	int bufferWidth = Screen.WIDTH - 100;
	int bufferHeight = Screen.HEIGHT - 100;
	
	//creates a creature with a random shape and interactions 
	public BasicCreature(Vector2D position){		
		this.position = position;
		this.velocity = new Vector2D(Maths.generateRandomFloatBetween(1, maxSpeed), Maths.generateRandomFloatBetween(1, maxSpeed));					
		body = Shapes.getRandomPath(Maths.generateRandomIntBetween(3, 10));
		getInterations();	
		this.dna = new DNA(behaviors, body, Maths.generateRandomString(5));
		this.ancestorDNA = dna;
	}
	
	//creates a creature based on its parents stats
	private BasicCreature(DNA dna, Vector2D position, Vector2D velocity, DNA ansestorDNA){
		this.dna = dna;
		this.ancestorDNA = ansestorDNA;
		this.position = position;
		this.velocity = velocity;
		this.body = dna.body;
		this.behaviors = dna.behaviors;
		for(EntityType type : EntityType.getAllTypes()){	
			learntInteraction.put(type, behaviors.get(type)[0]);
		}
	}
	
	public String toString(){
		return "Family: " + dna.familyName + "\n" + dna +  "\n-----Change in DNA-----\n" + this.dna.toString(ancestorDNA);		
	}
	
	//sets this creatures interactions to random stats
	private void getInterations() {
		for(EntityType type : EntityType.getAllTypes()){	
			float value = Maths.generateRandomFloatBetweenExclusive(-defaultbehavior, defaultbehavior,0 , 0);
			learntInteraction.put(type, value);
			float[] behavior = new float[3];
			behavior[0] = value;
			behavior[1] = 100 +  Maths.generateRandomFloatBetweenExclusive(-defaultbehavior, defaultbehavior,-100 , 100);
			behavior[2] = Maths.generateRandomFloatBetweenExclusive(-defaultbehavior,  defaultbehavior,0 , 0);
			behaviors.put(type, behavior);
		}		
	}
	
	//try to eat food
	public void eat(Food foods[]){
		//do anything special to the array
		getClosestFood(foods);
	}
	
	//try to mate with another creature
	public void mate(Vector<BasicCreature> creatures){
		//do anything special to the array
		getClosestCreature(creatures);		
	}
	
	/**
	 * Reduce the creatures health by the healthdegen amount. if the creature has been alive for more than 10 seconds and has more that 50% of its health
	 * create a new child with stats based on this creature's. Check if the creature is still in the bounds of the screen. If the creature
	 * has not eaten for more than 10 seconds it changes direction to a random direction. Change the creatures poisiton by the acceleration 
	 * and add the acceleration to the velocity. Reset the acceleration to 0.
	 * */
	public void update(){			
		timeAlive++;		
		if(timeAlive % (10*fps) == 0 && health > maxHealth/2){
			Vector2D newVelocity = velocity;
			newVelocity.scale(-1);
			child = new BasicCreature(dna.mutate(), position, newVelocity, ancestorDNA);			
		}
		health -= healthDegen;
		checkForBounds();
		//if the creature has been meandering about for more than 10 seconds turn in a random direction
		if(timeSinceLastMeal/60 > 10){
			seek(Vector2D.getRandomVector2D(new Random().nextInt(bufferWidth) + 50, new Random().nextInt(bufferHeight) + 50), 2);
			timeSinceLastMeal = 0;
		}
		position = position.add(velocity);		
		velocity = velocity.add(acceleration);
		acceleration.scale(0);
	}
	
	/**
	 * Draws the creature and debug information
	 * */
	public void show(Graphics2D g){		
		if(healthiest){
			g.setColor(new Color(255, 255, 0, 130));
			int r = (int) (defaultbehavior*100/2);
			g.fillOval(position.intX() - r/2, position.intY() - r/2, r, r);
		}		
		AffineTransform pathTransform = new AffineTransform();
		pathTransform.translate (position.intX(), position.intY());
		pathTransform.rotate(velocity.getX(), velocity.getY());
		pathTransform.scale(.75f, .5f);
		Shape transformedPath =	body.createTransformedShape (pathTransform);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.CYAN);
		g.fill(transformedPath);
		g.setColor(Color.GREEN);
		g.draw (transformedPath);		
		if(debug || healthiest)
			debug(g);			
	}
	
	/**
	 * draws and sets the debug information
	 * */
	private void debug(Graphics2D g){
		
		g.setStroke(new BasicStroke(2));
		EntityType types[] = EntityType.getAllTypes(); 	
		for(int i = 0; i < types.length; i++){
			g.setColor(perceptionColor[i]);
			float size = behaviors.get(types[i])[1];
			g.drawOval((int) (position.intX() - size/2), (int) (position.intY() - size/2), (int)size, (int)size);	
			debugData[i] = types[i].toString() + " Per: " +  behaviors.get(types[i])[1] + " In: " + behaviors.get(types[i])[0] + " | Learnt Difference = " + ((behaviors.get(types[i])[0]) - learntInteraction.get(types[i]));
			
		}
		debugData[3] = "Health : " + Math.round(health) + " " + Math.round(health/maxHealth*100) + "% Time Alive : " + Math.round(timeAlive/60) + " Seconds";
		debugData[4] = "Time Since Eaten : " + (timeSinceLastMeal/60) + " seconds";
		debugData[5] = "Family : " + dna.familyName;
		debugData[8] = "current Speed " + Math.round(velocity.magnitude());		
	}
	
	//Finds the closest creature and seeks it if any are found
	private void getClosestCreature(Vector<BasicCreature> creatures){
		double record = Double.POSITIVE_INFINITY;
		int closest = -1;
		float dist = -1;
		for(int i = 0; i < creatures.size(); i++){
			if(creatures.get(i) == this)
				continue;
			dist = creatures.get(i).position.dist(this.position);	
			if(dist < record && dist < behaviors.get(creatures.get(i).entityType)[1]/2){
				record = dist;
				closest = i;
			}
		}
		if(record < maxSpeed && dist != -1){
			//Add mating maybe
			//For now they will just frolic together 
		}
		else if(closest > -1){
			float currentAttraction = Maths.map(health, 0, maxHealth, -behaviors.get(creatures.get(closest).entityType)[0]/2, behaviors.get(creatures.get(closest).entityType)[0]);
			if(debug){
				if(debugData != null){
					
					if(behaviors.get(creatures.get(closest).entityType)[0] > 0){
						debugData[6] = "Running Towards " + creatures.get(closest).entityType + " " + currentAttraction;
					}else{
						debugData[6] = "Running Away From " + creatures.get(closest).entityType + " " + currentAttraction;
					}
				}
			}			
			seek(creatures.get(closest).position, currentAttraction);
		}
	}
	
	//Find the closest food on the screen
	private void getClosestFood(Food foods[]){
		timeSinceLastMeal++;
		double record = Double.POSITIVE_INFINITY;
		int closest = -1;
		float dist = -1;
		for(int i = 0; i < foods.length; i++){
			dist = foods[i].position.dist(this.position);	
			if(dist < record && dist < behaviors.get(foods[i].entityType)[1]/2){
				record = dist;
				closest = i;
			}
		}
		if(record < maxSpeed && dist != -1){
			float changeInHealth = foods[closest].calories * dna.behaviors.get(foods[closest].entityType)[2];
			
			//float currentPerception = perceptions.get(foods[closest].entityType);
			float currentinteraction = learntInteraction.get(foods[closest].entityType);
			if(changeInHealth > 0){
				currentinteraction =  Math.min(currentinteraction + defaultbehavior * .05f, dna.behaviors.get(foods[closest].entityType)[0]*2);
			}else{
				currentinteraction = Math.min(currentinteraction - defaultbehavior * .2f, dna.behaviors.get(foods[closest].entityType)[0]*2);
			}
			learntInteraction.put(foods[closest].entityType,currentinteraction);
			health = Math.min(maxHealth, health + changeInHealth);
			timeSinceLastMeal = 0;
			foods[closest] = new Food();
		}
		else if(closest > -1){
			if(debug){
				if(debugData != null){
					if(behaviors.get(foods[closest].entityType)[0] > 0){
						debugData[7] = "Running Towards " + foods[closest].entityType;
					}else{
						debugData[7] = "Running Away From " + foods[closest].entityType;
					}
				}
			}
			seek(foods[closest].position, behaviors.get(foods[closest].entityType)[0]);
		}
	}
	
	//seek the target Vector2D and scales the steering force by the scale
	private void seek(Vector2D target, float scale){
		Vector2D desired = target.sub(position);
		desired.setMagnitude(maxSpeed);
		Vector2D steer = desired.sub(velocity);		
		steer.setMagnitude(maxTurnSpeed);
		steer.scale(scale);
		applyForce(steer);
	}
	
	private void applyForce(Vector2D force){		
		acceleration = acceleration.add(force);
	}
	
	//if the creature is out of the boundary the creature then seeks the center of the screen
	private boolean checkForBounds(){
		int buffer = 50;
		if(position.getX() < buffer || position.getX() > bufferWidth - buffer || position.getY() < buffer || position.getY() > bufferHeight - buffer){
			timeoffscreen++;
			if(timeoffscreen / fps < 3)
				seek(screenCenter, 2.5f);
			
			return false;
		}else
			timeoffscreen = 0;
		return true;
	}

	public void debugSwitch(boolean off) {
		if(off)
			debug = false;
		else
			debug = true;
		
	}

	
	
}
