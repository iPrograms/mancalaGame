
/*****************************************************************
 * Model has the data in 2X7 array and solves it
 * 
 * @author Igor Sorokin, Manzoor Ahmed
 * @version 1.0
 * Mancala project CS 151 
 * November 19,2012
 * ***************************************************************/

import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Model implements Cloneable {

	public  Pit[][] board;		   //the main board that contains pits
	boolean extraTurn = false;
	private Pit[][] undo;          //backed up baord 
	private boolean undoExtraTurn; //player's extra turn
	private ArrayList<ChangeListener> listener;
	boolean saved = true;  
	int expectedX;		           //expected player 
	boolean once = true;
	
	//default constructor with number of stones in each Pit
	Model(int stones)
	{
		board = new Pit[2][7];
		listener = new ArrayList<ChangeListener>();

        //put stones in each Pit
		for(int i=0;i<2; i++){
			for(int j=0;j<7;j++){
				board[i][j]= new Pit(stones);
			}
		}
		
		board[0][6] = new Pit(0); //left mancala initialy no stones
		board[1][6] = new Pit(0); //right mancala initialy no stones
	}
	
	/**********************************************************************
	 * Adds a change listener to the Pit
     * @param listener the change listener to add
	 * ********************************************************************/
	public void addChangeListener(ChangeListener cl){
		listener.add(cl);
	}
	
	/***********************************************************************
	 * solve board from given postion
	 * @param x, 0 for player1, 1 for player 2
	 * @param y, the column 
	 * @return flase, if the board is not solved
	 * *********************************************************************/
	public boolean solve(int x, int y)//return true for end case
	{	
		int orginalX = x;
		int storedStones = board[x][y].getStones();// get the stones from given pit
		board[x][y].setStones(0);                  //erase

		while (storedStones != 0)
		{
			for (int i = y+1; i < 7; i++)
			{
				if (!(orginalX != x && i == 6))
				{
					board[x][i].addStone();
					storedStones--;
				}
				if (storedStones == 0)//last turn
				{
					if (orginalX == x && board[x][i].getStones() == 1 && i != 6)//on its side and empty pit Capture Case
					{
						if (x == 0) 
						{
							if (board[1][5-i].getStones() > 0){
							board[x][6].setStones(board[x][6].getStones() + board[1][5-i].getStones() + 1);
							board[1][5-i].setStones(0); 
							board[x][i].setStones(0);}
						}
						else 
						{
							if (board[1][5-i].getStones() > 0){
							board[x][6].setStones(board[x][6].getStones() + board[0][5-i].getStones() + 1);
							board[0][5-i].setStones(0);
							board[x][i].setStones(0);}
						}
					}
					if (orginalX == x && i == 6) extraTurn = true;
					int count0 = 0;
					for (int z = 0; z < 6; z++)
					{
						if (board[0][z].getStones() == 0) count0++;
					}
					if (count0 == 6) return true;
					
					int count1 = 0;
					for (int z = 0; z < 6; z++)
					{
						if (board[1][z].getStones() == 0) count1++;
					}
					if (count1 == 6) return true;
					
					return false;
				}
			}
			if (x == 0) x++; else x--;
			y = -1;
		}
		return false;
	}
	
	/**************************************************************
	 *Any time data gets updated
	 * ************************************************************/
	private void notifyListeners(){	
		
		// Notify all observers of the change to the Pits
		ChangeEvent event = new ChangeEvent(this);
		
		for (ChangeListener lis : listener){
			lis.stateChanged(event);
		}
	} 
	/***************************************************************************
	 * Print board on the terminal, used only for testing perpouse only. Ignore 
	 * ************************************************************************/
	public void print()
	{
		for(int j=6;j>=0;j--){
			System.out.print("("+board[1][j].toString()+")");
		}
		System.out.print("\n   ");
		for(int j=0;j<7;j++){
			System.out.print("("+board[0][j].toString()+")");
		}
		System.out.println();
	}
	
	/*************************************************************************
	 * copy stones from board to undo 
	 * ***********************************************************************/
	public void save()
	{
		undo = new Pit[2][7];
		for(int i=0;i<2; i++)
		{
			for(int j=0;j<7;j++)
			{
				undo[i][j]= new Pit(board[i][j].getStones());
			}
		}
		undoExtraTurn = extraTurn;
		saved = true;
	}
	
	/***********************************************************************
	 * Write back to board
	 * *********************************************************************/
	public void load()
	{
		board = new Pit[2][7];
		
		for(int i=0;i<2; i++){
			for(int j=0;j<7;j++){
				board[i][j]= new Pit(undo[i][j].getStones());
			}
		}
		if (saved)
		{
			if (expectedX == 0) expectedX++; 
			else expectedX--;
			saved = false;
		}
	}

	
	/*******************************************************************
	 * Solve the board 
	 * @param i1, player1 or player2
	 * @param i2, column 
	 * ***************************************************************/
	boolean input(int i1, int i2) 
	{	
		if (once) {expectedX = i1; once = false;}
		
		if (i1 == 2) {load(); extraTurn = undoExtraTurn;}
		if (expectedX == i1 && (board[i1][i2].getStones() != 0))
		{
			save();
			if (solve(i1, i2)) 
			{
				System.out.println("Player0: " + board[0][6].getStones());
				System.out.println("Player1: " + board[1][6].getStones());
			}
			if (!extraTurn)
			{
				if (expectedX == 0) expectedX++; 
				else expectedX--;
			}
			else extraTurn = false;
			return true;
		}
		else return false;
	}
	
	/************************************************************************
	 * chek if game is over
	 * **********************************************************************/
	public boolean  isGameOver(){
		
		boolean empty;
				//check player tow's pits
		    	if(board[0][0].getStones()==0 && board[0][1].getStones()==0 && board[0][2].getStones()==0 
		    			&&board[0][3].getStones()==0&&board[0][4].getStones()==0 && board[0][5].getStones()==0 ){
		    		empty=true;
		    		return empty;
		    	}
		    	//check player one's pits
		    	else if(board[1][0].getStones()==0 && board[1][1].getStones()==0 && board[1][2].getStones()==0 
		    			&&board[1][3].getStones()==0&&board[1][4].getStones()==0 && board[1][5].getStones()==0){
		    		empty=true;
		    		return empty;
		    	}
		return false; //both full
	}
}

