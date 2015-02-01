/* 
 * This class allows players to control their creatures in combat.
 * It creates a menu so the player can select a move.
 * 
 * 
 */

package fakemon;

public class PlayerAI implements BattleAI{
	/*public BattleAction getAction(BattleScreen battle, int trainer, int pokemon) {
		MoveMenuDialogBox box = new MoveMenuDialogBox(battle.dialogLoc,battle);
		
		box.moveInit(battle.trainers[trainer], battle.acPokemon[trainer][pokemon]);
		battle.dialog = box;
		box.go();
		
		return box.getMove();
	}*/
	
	// This lets the player select a second pokemon when one faints or retreats.
	// This tells the game that players don't have a second pokemon.
	@Override
	public Pokemon getNextPokemon(BattleScreen battle) {
		
		return null;
	}
	@Override
	public void requestBattleAction(final BattleScreen battle, final int trainer,final int pokemon) {
		new Thread() {
			public void run() {
				MoveMenuDialogBox box = new MoveMenuDialogBox(battle.dialogLoc,battle);
				
				box.moveInit(battle.trainers[trainer], battle.acPokemon[trainer][pokemon]);
				battle.dialog = box;
				box.go();
				                           
				battle.addAction(trainer, pokemon,box.getMove());
			}
		}.start();
	}
	public boolean canRequestBattleAction(BattleScreen battle, int trainer,int pokemon){
		return !battle.dialog.isActive();
	}
}
