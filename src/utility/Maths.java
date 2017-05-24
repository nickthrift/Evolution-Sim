package utility;

import java.security.SecureRandom;
import java.util.Random;

public class Maths {
	
	public static void main(String[] args) {
		int health = 10;
		int maxHealth = health;
		for(int i = 0; i < maxHealth; i++){
			System.out.println(Maths.map(health, 0, maxHealth, -1, 1));
			health--;		
		}
		System.out.println(Maths.map(health, 0, maxHealth, -1, 1));
	}
	
	
	static public final float map(float value, float istart, float istop, float ostart, float ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}	
	
	public static int generateRandomIntBetween(int min, int max){
		Random r = new Random();		
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static float generateRandomFloatBetween(float min, float max){
		Random r = new Random();		
		return min + (max - min) * r.nextFloat() ;
	}
	
	public static double generateRandomDoubleBetween(double min, double max){
		Random r = new Random();		
		return min + (max - min) * r.nextDouble() ;
	}
	
	public static int generateRandomIntBetweenExclusive(int min, int max, int notMin, int notMax){
		Random r = new Random();
		if(r.nextFloat() > .5)
			return generateRandomIntBetween(min, notMin-1);
		return generateRandomIntBetween(notMax+1, max);
	}
	
	public static float generateRandomFloatBetweenExclusive(float min, float max, float notMin, float notMax){
		Random r = new Random();
		if(r.nextFloat() > .5)
			return generateRandomFloatBetween(min, notMin-.1f);
		return generateRandomFloatBetween(notMax+.1f, max);
	}
	
	public static double generateRandomDoubleBetweenExclusive(double min, double max, double notMin, double notMax){
		Random r = new Random();
		if(r.nextFloat() > .5)
			return generateRandomDoubleBetween(min, notMin-.1);
		return generateRandomDoubleBetween(notMax+.1, max);
	}
	
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public static String generateRandomString(int len){
	   StringBuilder sb = new StringBuilder(len);
	   for( int i = 0; i < len; i++) 
	      sb.append(AB.charAt(rnd.nextInt(AB.length())));
	   return sb.toString();
	}
	
	
}
