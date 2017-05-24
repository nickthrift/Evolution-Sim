package enums;

import java.awt.Color;

public enum EntityType {
	BASIC_CREATURE, FOOD, POISON;
	
	
	
	public static EntityType[] getAllTypes(){
		EntityType r[] = {BASIC_CREATURE, FOOD, POISON};	
		return r;
	}
}
