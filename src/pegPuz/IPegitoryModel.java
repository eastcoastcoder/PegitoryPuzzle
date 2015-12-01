package pegPuz;

public interface IPegitoryModel extends IGameModel{
	public int getTerritory(int holeLoc);
	
	public int getPlayerOneScore();
	
	public int getPlayerTwoScore();
	
	public int getPlayersTurn();
	
	public void setWinMethod(WinMethods method);
	
	public boolean setWinTarget(int target);

	public int getWinTarget();
}
