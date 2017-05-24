package utility;

import java.awt.Point;
import java.util.Random;
/**
 * 
 * A 2 dimensional Vector with an x and a y coordinate.
 * 
 * @author Nick
 * @version 1.0 
 * 
 * */
public class Vector2D {
	
	private float x;
	private float y;
	
	/**
	 * Default constructor that sets the x and y location to 0, 0.
	 */
	public Vector2D(){
		this.x = 0;
		this.y = 0;
	}
	/**
	 * Constructor that sets the x and y.
	 * @param x : sets the x value to the input value.
	 * @param y : sets the y value to the input value.
	 */
	public Vector2D(float x, float y){
		this.x = x;
		this.y = y;
	}
	/**
	 * Constructor that sets the x and y. Doubles are cast into floats.
	 * @param x : sets the x value to the input value.
	 * @param y : sets the y value to the input value.
	 */
	public Vector2D(double x, double y){
		this.x = (float) x;
		this.y = (float) y;
	}
	/**
	 * Constructor that sets the x and y values to the Point's x and y.
	 * @param point : gets the Point's x and y value to use for the Vector2D coordinates.
	 */
	public Vector2D(Point point){
		this.x = point.x;
		this.y = point.y;
	}
	
	/**
	 * Constructor that sets the x and y values to the Vector2D's x and y.
	 * @param point : gets the Vector2D's x and y value to use for the new Vector2D coordinates.
	 */
	public Vector2D(Vector2D v){
		this.x = v.x;
		this.y = v.y;
	}
	
	/**
	 * Creates a new Vector and sets its coordinates to random locations constrained by the input limits.
	 * @param xLimit : the highest x value that can be set for the random Vector2D location.
	 * @param yLimit : the highest x value that can be set for the random Vector2D location.  
	 */
	public static Vector2D getRandomVector2D(int xLimit, int yLimit){
		return new Vector2D(new Random().nextInt(xLimit+1), new Random().nextInt(yLimit+1));		
	}
	
	/**
	 * Get the Vector2D's position as a String.
	 * @return String
	 */	
	public String toString(){
		return "Vector(" + x + ", " + y + ")";
	}
	
	/**
	 * Adds 2 vectors together and returns a new Vector2D
	 * @param v2 : the vector that you want to add.
	 * @return Vector2D
	 */
	public Vector2D add(Vector2D v2){
		return new Vector2D(this.x+v2.x, this.y+v2.y);
	}
	
	/**
	 * Adds 2 vectors together and saves the result in the target parameter.
	 * @param v2 : the vector that you want to add.
	 * @param target : the Vector2D where you want the result saved.
	 */
	public void add(Vector2D v2, Vector2D target){
		target = add(v2);
	}
	
	/**
	 * Subtracts 2 vectors and returns a new Vector2D
	 * @param v2 : the vector that you want to subtract.
	 * @return Vector2D
	 */
	public Vector2D sub(Vector2D v2){
		return new Vector2D(x-v2.x, y-v2.y);		
	}
	
	/**
	 * Subtracts 2 vectors and saves the result in the target parameter.
	 * @param v2 : the vector that you want to subtract.
	 * @param target : the Vector2D where you want the result saved.
	 */
	public void sub(Vector2D v2, Vector2D target){
		target = sub(v2);
	}
	
	/**
	 * Scales the x and y positions. 
	 * @param m : how much to scale the coordinates by. 
	 */
	public void scale(float m){
		this.x *= m;
		this.y *= m;		
	}
	
	/**
	 * Scale the x and y positions. 
	 * @param m : how much to scale the coordinates by. 
	 * @param target : the Vector2D where you want the result saved.
	 */
	public void scale(float m, Vector2D target){
		target = new Vector2D(this.x * m, this.y * m);		
	}	
	
	/**
	 * Gets the magnitude (length) of the Vector2D
	 */
	public double magnitude(){
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * Sets the magnitude(length) of the Vector2D.
	 * @param nLen : the new length of the Vector2D.
	 */
	public void setMagnitude(float nLen){
		normalize();
		scale(nLen);
	}
	
	/**
	 * Normalizes the Vector2D.	 
	 */
	public void normalize() {
		double magnitude = magnitude();
	    if(magnitude != 0){
	    	x /= magnitude;
	    	y /= magnitude;
	    }	   
	}  
	
	/**
	 * Normalizes the Vector2D and saves the result in a seperate Vector2D
	 * @param target : the Vector2D where you want the result saved. 
	 */
	public void normalize(Vector2D target) {	
		double magnitude = magnitude();
	    if(magnitude != 0){
	    	target.x = (float) (x / magnitude);
	    	target.y = (float) (y / magnitude);
	    }
	} 	
	
	/**
	 * Gets the distance between 2 Vector2Ds.
	 * @return Float.	 
	 */
	public float dist(Vector2D v2){
		return (float)Math.sqrt(Math.pow(v2.x-x, 2)+Math.pow(v2.y-y, 2));
	}
	
	/**
	 * Gets the radian between 2 Vector2Ds.
	 * @param v2 : the second Vector2D. 
	 * @return Float.		 
	 */
	public float getRadian(Vector2D v2){
		return (float) Math.atan2(y - v2.y,x - v2.x);
	}
	
	/**
	 * Gets the dot product between 2 Vector2Ds. 
	 * @param v2 : the second Vector2D.
	 * @return Double.
	 */
	public double dotProduct(Vector2D v2) {
	        return this.x * v2.x + this.y * v2.y;
	}
	
	/**
	 * Gets the rounded Integer version of x.
	 * @return Integer.
	 */
	public int intX(){
		return Math.round(x);
	}
	
	/**
	 * Gets the rounded Integer version of y.
	 * @return Integer.
	 */
	public int intY(){
		return Math.round(y);
	}

	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
		
	public void setX(double x) {
		this.x = (float) x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setY(double y) {
		this.y = (float) y;
	}
	
}
