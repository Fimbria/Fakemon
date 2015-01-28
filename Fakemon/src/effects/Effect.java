package effects;

import fakemon.BattleScreen;
import fakemon.Move;
import fakemon.Pokemon;
import fakemon.Screen;
import fakemon.Type;

public abstract class Effect {
	Pokemon user;
	Pokemon target;
	public final static int ATTACK = 1;
	public final static int DEFENSE = 2;
	public final static int SPECIAL_ATTACK = 3;
	public final static int SPECIAL_DEFENSE = 4;
	public final static int SPEED = 5;
	public final static int EVASION = 6;
	public final static int ACCURACY = 7;
	public final static String[] statNames = {"Health","Attack","Defense","Special Attack","Special Defense","Speed","Evasion","Accuracy"};
	private boolean done;
	// Executed when struck.
	public void onDefend(){}
	// Executed when striking.
	public void onAttack(){}
	// Executed when the battle ends.
	public void onBattleEnd(){}
	// Executed when a turn ends.
	public void onTurnEnd(BattleScreen screen){}
	// Returns whether a creature can act.
	public boolean canAttack(Screen screen, Move m){
		return true;
	}
	// Returns whether a creature can be attacks.
	public boolean canBeAttacked(Move m,BattleScreen screen){
		return true;
	}

	public Type getTypeMod(){
		return null;
	}

	// Returns a multiplier on the stat pased in.
	public float getStatMod(int stat){
		return 1;
	}

	// Returns true if this effect would prevent the effect passed in.
	public boolean prevents(Effect e,Screen screen)
	{
		return false;
	}

	// Returns true if the afflicted creature can swap out.
	public boolean canSwap(BattleScreen screen){
		return true;
	}
	// Executed when applying the effect to a creature afflicted with the same effect.
	public abstract boolean add(Effect e,Screen screen);
	// Returns true if the effect can't apply to the target due to an effect.
	public abstract boolean conflicts(Effect e,Screen screen);
	// Returns true if the effect can apply to the target due to its nature.
	public boolean canBeApplied(Pokemon p,Screen screen)
	{
		return true;
	}

	// Executed when the battle ends. Returns done, a boolean for whether to remove the effect after battle.
	public final boolean isOver(){
		return done;
	}

	// Terminates the effect.
	public final void end(){
		done = true;
	}

	// Intended for override. Executed when the effect is removed.
	public void onRemove(Screen screen){}

	// Intended for override. Executed when the effect is applied.
	public void onNewApply(Screen screen, Pokemon user, Pokemon target){
		this.user = user;
		this.target = target;
	}

	// Intended for override. Returns the amount to multiply the afflicted creature's damage.
	public float getDamMod(){
		return 1;
	}

	// Intended for override. Executed when a turn starts.
	public void onTurnStart(BattleScreen screen){}
}
