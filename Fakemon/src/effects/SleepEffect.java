/*
Sleep.

Intended to prevent most actions for several turns.
*/

package effects;

import fakemon.BattleScreen;
import fakemon.Move;
import fakemon.Pokemon;
import fakemon.Screen;
import fakemon.Util;

public class SleepEffect extends Effect{
	// Number of turns sleep lasts.
	int turnsLeft;

	// The afflicted can't attack when asleep.
	@Override
	public boolean canAttack(Screen screen, Move m){
		boolean canAttack = false;
		if(!canAttack)
			screen.displayMessage(target.getName() + " is fast asleep!");

		return canAttack;
	}

	@Override
	public boolean add(Effect e,Screen screen) {
		
		return false;
	}

	// Can't sleep a sleeping creature, not even to extend the timer.
	@Override
	public boolean prevents(Effect e,Screen screen)
	{
		if(e instanceof SleepEffect)
		{
			return true;
		}
		return false;
	}

	// Message when waking.
	@Override
	public void onRemove(Screen screen){
		screen.displayMessage(target.getName() + " wakes up!");
	}

	// Message when applying the efect.
	@Override
	public void onNewApply(Screen screen,Pokemon user, Pokemon target){
		super.onNewApply(screen, user, target);
		screen.displayMessage(target.getName() + " falls asleep!");
		turnsLeft = Util.rand(1, 3);
	}
	// Sleep only lasts so long, and a creature wakes at the start of a turn.
	@Override
	public void onTurnStart(BattleScreen screen){
		turnsLeft--;
		if(turnsLeft <= 0)
			end();
	}

	// Nothing conflicts with sleep.
	@Override
	public boolean conflicts(Effect e, Screen screen) {
		return false;
	}

}
