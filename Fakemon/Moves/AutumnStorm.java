/*
A damage-dealing move. This move has four times the critical hit ratio.

When completed, it will also have a 50% chance of raising the user's evasion by 1 stage.

This will be the signature move of the grass starter.
*/

import fakemon.MoveInfo;
import fakemon.Type;


public class AutumnStorm extends MoveInfo {
	public AutumnStorm (){
		init("Autumn Storm", 10, 16, 100, 75, false, Category.SPECIAL, Type.getByName("grass"));
		
		
	}
	@Override
	public float getCritRateMod(){
		return 4.0f;
	}
}
