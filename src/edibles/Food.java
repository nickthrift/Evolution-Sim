package edibles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import enums.EntityType;
import screen.Screen;
import utility.Maths;
import utility.Vector2D;

public class Food {
	public Vector2D position;
	public EntityType entityType;
	//public int nourishment = 0;
	public boolean rotten = false;
	public float calories;
	
	int bufferWidth = Screen.WIDTH - 100;
	int bufferHeight = Screen.HEIGHT - 100;
	

	int caloriesCap = 75;
	int r;
	int rC, gC, bC, alpha = 0;
	Color colors[] = {new Color(66, 244, 161, alpha), new Color(242, 133, 65, alpha)};
	Color color;
	int type;
	//String types[] = {"Positive","Negative"};
	int timeRipe=  0;
	
	public Food(){
		this.position = new Vector2D(new Random().nextInt(bufferWidth) + 50, new Random().nextInt(bufferHeight) + 50);
		this.type = Maths.generateRandomIntBetween(1, colors.length);
		setup();
	}
	
	public Food(Vector2D position){
		this.position = position;
		this.type = Maths.generateRandomIntBetween(1, colors.length);
		setup();
	}
	
	public Food(Vector2D position, int type){
		this.position = position;
		this.type = type;
		setup();
	}
	
	public Food(int type){
		this.position = new Vector2D(new Random().nextInt(bufferWidth) + 50, new Random().nextInt(bufferHeight) + 50);
		this.type = type;
		setup();
	}
	
	public String toString(){
		return "Food : " + entityType + " \n Position : " + position.toString() + " \n Calories : " + calories;
	}
	
	private void setup(){
		
		calories = Maths.generateRandomIntBetween(1, caloriesCap);
		rC = colors[type-1].getRed();
		gC = colors[type-1].getGreen();
		bC = colors[type-1].getBlue();
		
		alpha = (int) Maths.map(calories, 1, 100, 135, 255);
		color = new Color(rC, gC, bC, alpha);
		
		this.r = (int) Math.max(5,10 *(calories/caloriesCap));
		
		entityType = EntityType.getAllTypes()[type];
	}
	
	public void update(){
		//after 5 seconds the fruit is rotten and disappears from the screen
		if(timeRipe/60 > 5)
			rotten = true;
		
		if(!rotten){
			if(calories < caloriesCap){
				calories += .1f;
				alpha = (int) Maths.map(calories, 1, 75, 0, 255);
				color = new Color(rC, gC, bC, alpha);
				this.r = (int) Math.max(5,10 *(calories/caloriesCap));
			}else{
				//adds some randomness to the food going bad
				if(Math.random() > .25)
					timeRipe++;
			}
		}
	}
	
	public void show(Graphics2D g){
		g.setColor(color);		
		g.fillOval(position.intX(), position.intY(), r , r);
	}
	
	
	
}
