/*
Paralyze.

The afflicted has a 25% chance of failing to move. Their speed drops to a quarter of its normal value.

*/


package effects;

import fakemon.Move;
import fakemon.Pokemon;
import fakemon.Screen;
import fakemon.Util;

public class ParalyzeEffect extends Effect{

	// 75% that they can move, 25% they can't.
	public boolean canAttack(Screen screen, Move m){
		boolean canAttack = Util.flip(.75f);
		if(!canAttack)
			screen.displayMessage(target.getName() + " couldn't move!");

		return canAttack;
	}

	// Applies a huge penalty to speed.
	public float getStatMod(int stat){
		if(stat == SPEED)
			return .25f;
		return 1;
	}
	
	// Nothing special happens when you stack paralysis.
	@Override
	public boolean add(Effect e,Screen screen) {
		
		return false;
	}

	// Nothing conflicts with paralysis.
	@Override
	public boolean conflicts(Effect e,Screen screen) {
		return false;
	}

	// Paralysis prevents paralysis.
	public boolean prevents(Effect e,Screen screen)
	{
		if(e instanceof ParalyzeEffect)
		{
			return true;
		}
		return false;
	}

	// Nothing special happens when removing paralysis.	
	public void onRemove(Screen screen){
		
	}

	// Application message.
	public void onNewApply(Screen screen,Pokemon user, Pokemon target){
		super.onNewApply(screen, user, target);
		screen.displayMessage(target.getName() + " was paralyzed!");
	}

}
