package effects;

import fakemon.Pokemon;
import fakemon.Screen;
import fakemon.Util;


/*
 * This effect adds and tracks stat mod efefcts.
 * 
 * @param {int} type - An integer representing which stat is affected. Should range from 1 to 6. The static variables ATTACK, ACCURACY, and others correspond to those values.
 * @param {int} mod - the amount to change the effect by. 12 will raise the effect from minimum (-6), to maximum (6). -12 will go the opposite way.
 */
public class StatModEffect extends Effect {
	private int[] mods = new int[ACCURACY+1];
	public StatModEffect(int type, int mod){
		mods[type] = mod;
	}
	@Override
	public boolean add(Effect e,Screen screen) {
		if(e instanceof StatModEffect)
		{
			StatModEffect eff = (StatModEffect) e;
			for(int i = 0; i <= ACCURACY;i++)
			{
				int newMod = Math.min(6,Math.max(mods[i]+eff.mods[i],-6));
				int diff = newMod -mods[i];
				mods[i] = newMod;
				
				
				if(eff.mods[i] == 0) continue;
				
				String message =  Util.possessive(target.getName()) + " " + statNames[i];

				if(eff.mods[i] < 0)
				{
					if(diff == 0)
						message += " could not go any lower!";
					else if(diff == -1)
						message += " fell!";
					else if(diff == -2)
						message += " harshly fell!";
					else if(diff <= -3)
						message += " severely fell!";
					if(mods[i] == -12)
						message += " " + Util.possessive(target.getName()) + " " + statNames[i] + " is minimized!";

				}
				else if(eff.mods[i] > 0)
				{
					if(diff == 0)
						message += " could not go any higher!";
					else if(diff == 1)
						message += " rose!";
					else if(diff == 2)
						message += " rose sharply!";
					else if(diff <= 3)
						message += " rose drastically!";
					if(mods[i] == 12)
						message += " " + Util.possessive(target.getName()) + " " + statNames[i] + " is maximized!";
				}
				screen.displayMessage(message);
			}
			return true;
		}
		return false;
	}

	// Effect ends at the end of battle.
	public void onBattleEnd(){
		end();
	}

	// Returns the amount by which to multiply the afflicted creature's stat.
	// @param {int} type - the stat to modify.
	public float getStatMod(int type){
		if(type < ATTACK || type > ACCURACY) return 1; // checks for out-of-bounds errors
		return getModPercent(type);
	}

	// Stat mods don't conflict with anything.
	@Override
	public boolean conflicts(Effect e,Screen screen) {
		return false;
	}

	// Stat mods can triple or third type 5 (SPEED) or type 6 (ACCURACY). For all others, they can quadruple or quarter.
	private float getModPercent(int type){
		int i = mods[type];
		if(type > 4)
			return (float)Math.max(3, 3+i)/Math.max(3, 3-i);
		return (float)Math.max(2, 2+i)/Math.max(2, 2-i);
	}

	// Gives a message for new applications of this effect.
	public void onNewApply(Screen screen,Pokemon user, Pokemon target){
		super.onNewApply(screen, user, target);
		
		for(int i = 0; i <= ACCURACY;i++)
		{
			int diff = mods[i];
			if(diff == 0) continue;
			
			String message = Util.possessive(target.getName()) + " " + statNames[i];

			if(diff < 0)
			{
				if(diff == -1)
					message += " fell!";
				else if(diff == -2)
					message += " harshly fell!";
				else if(diff <= -3)
					message += " severely fell!";
				if(mods[i] == -12)
					message += " " + Util.possessive(target.getName()) + " " + statNames[i] + " " + " was minimized!";
			}
			else if(diff > 0)
			{
				if(diff == 1)
					message += " rose!";
				else if(diff == 2)
					message += " rose sharply!";
				else if(diff <= 3)
					message += " rose drastically!";
				if(mods[i] == 12)
					message += " " + Util.possessive(target.getName()) + " " + statNames[i] + " " + " was maximized!";
			}
			screen.displayMessage(message);
		}
		for(int i = 0; i <= ACCURACY;i++)
		{
			mods[i] = Math.min(6,Math.max(mods[i],-6));
		}
	}
 
}
