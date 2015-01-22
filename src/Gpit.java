
import javax.swing.*;

/****************************************************************
 * @author Manzoor Ahmed, Erick Macias
 * @version 1.0
 * November 21, 2012
 * Mancala group project
 * **************************************************************/
public class Gpit extends JComponent {

	private String player;             //player's name
	private boolean isClicked = false; //if clicked
	private String imageName;          //image name
	private int column;                //column number
	private ImageIcon image;           //Image
	
	//Constructor with given player name, and image
	public Gpit(String playername,String imgname){
		
		player=playername;				//player name
		imageName=imgname;				//image name
		image = new ImageIcon(imgname); //image
		
		JLabel label = new JLabel(image);
		JButton button = new JButton();
		button.add(label);
		setVisible(true);	
	}
	/**Set player */
	public String getPlayer(){
		return player;
	}
	
	/******************************************************
	 * Checks if this Gpit is clicked
	 * @return true, if clicked, false otherwise
	 * ***************************************************/
	public boolean isClicked(){
		return isClicked;
	}
	
	public String getImg(){
		return this.imageName;
	}
	public int getColumn(){
		return this.column;
	}
	
	/********************************************************
	 * Set column 
	 *@param col, the column number
	 ********************************************************/
	public void setColumn(int col){
		this.column= col;
	}
	
	/********************************************************
	 * Set Player
	 *@param player, player name
	 ********************************************************/
	public void setPlayer(String player){
		this.player=player;
	}
	
	/********************************************************
	 * Set image name 
	 *@param img, image name
	 ********************************************************/
	public void setImg(String img){
		this.imageName=img;
	}
	
	/********************************************************
	 * Set image
	 *@param img, new ImageIcon
	 ********************************************************/
	public void setImageIcon(ImageIcon img){
		image=img;
		repaint();
	}
}
