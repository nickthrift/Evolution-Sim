package creatures;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import utility.Maths;
import enums.EntityType;

public class DNA {


	//public Map<EntityType, Float> interactions = new HashMap<EntityType, Float>();
//	public Map<EntityType, Float> perceptions = new HashMap<EntityType, Float>();
	
	
	/**
	 * 
	 * [0]: interactions = how the creature interacts with its environment 
	 * [1]: perceptions = how far the creature sees objects in its environment
	 * [2]: diet = how well the creature digests different entities in the environment 
	 * 
	 * */
	public Map<EntityType, float[]> behaviors = new HashMap<EntityType, float[]>();
	
	
	
	public GeneralPath body;
	public String familyName;
	public int generation;
	
	private String diet = "\n";
	
	public DNA(Map<EntityType, float[]> behaviors, GeneralPath body, String familyname) {
		this.behaviors = behaviors;
		this.body = body;
		this.familyName = familyname;
		this.generation = 1;
		setDiet();
	}
	
	private DNA(){
		behaviors = new HashMap<EntityType, float[]>();		
	}
	
	private void setDiet(){
		diet = "";
		EntityType types[] = EntityType.getAllTypes();
		boolean map[] = new boolean[3];
		for(int i = 0; i < types.length; i++){
		
			map[i] = behaviors.get(types[i])[2] > 0;
		}
		
		if(!map[0] && !map[1] && !map[2])
			diet += "Creature Doesn't Eat";		
		else if(map[0] && map[1] && map[2])
			diet += "Creature is Omnivourus";
		
		else if(!map[0] && map[1] && map[2])
			diet += "Creature is Vegetarian";
		else if(map[0] && !map[1] && map[2])
			diet += "Creature Eats Creatures and Poison";
		else if(map[0] && map[1] && !map[2])
			diet += "Creature only eats Creature and Food";		
		
		else if(map[0] && !map[1] && !map[2])
			diet += "Creature only eats " + types[0];
		else if(!map[0] && map[1] && !map[2])
			diet += "Creature only eats " + types[1];
		else if(!map[0] && !map[1] && map[2])
			diet += "Creature only eats " + types[2];
		
		
		diet += "\n";
	}
	
	public String toString(){
		String str = "\n";
		for(EntityType type : behaviors.keySet()){
			str += type +"\n";
			str += " Interaction: " + behaviors.get(type)[0] + "\n";
			str += " Perception: " + behaviors.get(type)[1] + "\n";
			str += " Diet: " + behaviors.get(type)[2] + "\n";
		}
		
		return "DNA : " + str + diet;
	}
	
	public String toString(String name){
		String str = "\n";
		for(EntityType type : behaviors.keySet()){
			str += type +"\n";
			str += " Interaction: " + behaviors.get(type)[0] + "\n";
			str += " Perception: " + behaviors.get(type)[1] + "\n";
			str += " Diet: " + behaviors.get(type)[2] + "\n";
		}
		
		return "DNA " + name + " : "  + str +   "\n Generation:" +generation;
	}
	
	public String toString(DNA dna){
		String str = "\n";
		for(EntityType type : behaviors.keySet()){
			str += type +"\n";
			String s[] = {" Interaction", " Perception", " Diet"};
			for(int i = 0; i < 3; i++){
				float change;
				float oldDna = dna.behaviors.get(type)[i];
				float newDna = this.behaviors.get(type)[i];
				
				if(oldDna > newDna){
					change = oldDna - newDna;
					str += s[i] + " Went Down By : " + change + "\n";
				}else{
					change = newDna - oldDna;
					str += s[i] + " Went up By : " + change + "\n";
				}
				
			}
			//str += " Change in Interaction: " + (dna.behaviors.get(type)[0]  -behaviors.get(type)[0]) + "\n";
			//str += " Change in Perception: " + (dna.behaviors.get(type)[1]  -behaviors.get(type)[1]) + "\n";
			//str += " Change in Diet: " + (dna.behaviors.get(type)[2]  -behaviors.get(type)[2]) + "\n";
		}
		
		return "DNA : "  + str +   "\n Generation:" +generation + " \n" +  diet + "  | Ancestor was " + dna.diet;
	}
	
	private float mutationRate = 1f;
	private float mutationAmount = .05f;
	
	public DNA mutate(){
		DNA dna = new DNA();
		boolean debug = false;
		for(EntityType type : EntityType.getAllTypes()){
			float childDNA[] = new float[3];
			for(int i = 0; i < behaviors.get(type).length; i++){
				//if math.random returns a number less that the mutation rate then a stat will be mutated
				
				if(Math.random() <= mutationRate){
					float parent = this.behaviors.get(type)[i];
					switch(i){
						case 1:						
							childDNA[i] = parent + mutationAmount(parent) * 100;
							break;						
						default:
							childDNA[i] = parent + mutationAmount(parent);						
					}
				}else
					childDNA[i] = this.behaviors.get(type)[i];
				
			}
			dna.behaviors.put(type, childDNA);	
		}
		dna.body = this.body;
		dna.familyName = familyName;
		dna.generation = generation+1;
		setDiet();
		
		return dna;
	}
	
	
	private float mutationAmount(float parent){	
		return Maths.generateRandomFloatBetween(-(mutationAmount), mutationAmount);
	}
	
	
}
