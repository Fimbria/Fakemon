/*
Deals bug damage.

When completed, this will have a 50% of making the opponent flinch. However, if the target flinches, it raises the target's speed by 2 stages.

This will be the signature move of Bugpag.
*/

import fakemon.MoveInfo;
import fakemon.Type;


public class Creep extends MoveInfo {
	public Creep (){
		init("Creep", 20, 36, 100, 40, true, Category.PHYSICAL, Type.getByName("bug"));
	}

}

