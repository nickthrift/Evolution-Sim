package render;

import java.awt.geom.GeneralPath;
import java.util.Random;

import utility.Maths;

public class Shapes {
	
	public static GeneralPath getRandomPath(int numLines){
		GeneralPath path = new GeneralPath();
		Random r = new Random();
		float oldX = 0;
		float oldY = 0;
		float xCoords[] = new float[numLines];
		float yCoords[] = new float[numLines];
		
		path.moveTo(0, 0);
		for(int i = 0; i < numLines; i++){
			float x = Maths.generateRandomFloatBetween(-30, 20) + oldX;
			float y = Maths.generateRandomFloatBetween(-20, 30) + oldY;
			xCoords[i] = x;
			yCoords[i] = y;
			path.lineTo(x, y);
			oldX = x;
			oldY = y;
		}
		mirrorGeneralPath(path, xCoords, yCoords);
		
		return path;
	}
	
	
	
	public static void mirrorGeneralPath(GeneralPath path, float[] xCoords, float[] yCoords){
		//System.out.println(path.toString());
		path.moveTo(0, 0);
		for(int i  = 0; i < xCoords.length; i++)
			path.lineTo(xCoords[i], -yCoords[i]);
	}
	
	
	
}
