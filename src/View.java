
/*************************************************************
 * View shows all GUI components, and solves the mancala 
 * 
 * @author Manzoor Ahmed,Igor Sorokin,Erik Macias
 * @version 3.0
 * November 21,2012
 * CS151 group project
 * 
 * **********************************************************/
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ChangeListener{
	
	static int startStones =3;// by default
	//static String playerName = null;
	
	static int player1_Score;
	static int player2_Score;
	
	static Model model;
	public static void main(String[] args){
		
		//All labels 
		final JButton undo = new JButton("UNDO");
		final JButton start = new JButton("Start");
		final JLabel defstones = new JLabel ("Enter 4 if you need four stones in each pit :  ");
		
		///show score and message
		final JLabel score = new JLabel("First Player     :    ");
		final JLabel score1 = new JLabel("Second Player   :    ");
		final JLabel message = new JLabel("");
	    
		
		final JFrame frame = new JFrame("Mancala game");
	    final JPanel mainPanel = new JPanel();
	    final JPanel stonePanel = new JPanel();
	     
	    //Panels for each side of the mainPanel
	    final JPanel leftPanel = new JPanel();
	    final JPanel rightPanel = new JPanel();
	    final JPanel topPanel = new JPanel();
	    final JPanel downPanel = new JPanel();
	    final JPanel centerPanel = new JPanel();
	    final JPanel centerInner = new JPanel(new FlowLayout());	 
	    final JPanel messagePanel = new JPanel();
	    
	    
	    //Set size of all panels
	    leftPanel.setPreferredSize(new Dimension(150,frame.getHeight()-200));
	    rightPanel.setPreferredSize(new Dimension(150,frame.getHeight()-200));
	    topPanel.setPreferredSize(new Dimension(frame.getWidth(),130));
	    messagePanel.setPreferredSize(new Dimension(900,50));
	    
	    //Background color of each panels
	    leftPanel.setBackground(Color.white);
	    rightPanel.setBackground(Color.white);
	    topPanel.setBackground(Color.WHITE);
	    downPanel.setBackground(Color.gray);
	    centerPanel.setBackground(Color.white);
	    centerInner.setBackground(Color.orange);
	    messagePanel.setBackground(Color.white);
	    mainPanel.setLayout(new BorderLayout());
	 
	    stonePanel.add(defstones);
	    
	    //Hide initialy
	    topPanel.setVisible(false);
	    downPanel.setVisible(false);
	    leftPanel.setVisible(false);
	    rightPanel.setVisible(false);
	    
	    //Add to top panel
	    messagePanel.add(message);
	    topPanel.add(messagePanel);
	    topPanel.add(score);
	    topPanel.add(score1);
	    

	    
	    //Input number of stones
	    final JTextField  input = new JTextField("max 4, min 3");
	    input.setSize(20, 30);
	    input.setBackground(Color.white);
	    
	    //Add start button, input textfield
	    centerInner.add(defstones);
	    centerInner.add(input); 
	    centerInner.add(start);
	    
        /////////////////When user clicks on start
	    start.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				//if start button is clicked
				if(event.getActionCommand()=="Start"){
					centerInner.setVisible(false);
					frame.remove(centerInner);
				     //If input contains something else, set stones to 3
					 if(!input.getText().trim().equals("4")){
						 startStones=3;
						 model = new Model(3);
					 }
					 else{
						 startStones=4;  // user wants four stones
						 model= new Model(4);
					 }
					 
					//Game starts, show hidden panels
					System.out.println(startStones);
					topPanel.setVisible(true);
					leftPanel.setVisible(true);
					rightPanel.setVisible(true);
					downPanel.setVisible(true);
					
					
					final JButton player1_manc = new JButton();
					final JButton player2_manc = new JButton();
					player2_manc.setIcon(new ImageIcon("manc.gif"));
					player2_manc.setBackground(Color.white);
					player2_manc.setBorderPainted(false);
					
					player1_manc.setIcon(new ImageIcon("manc.gif"));
					player1_manc.setBackground(Color.white);
					player1_manc.setBorderPainted(false);
			
					player1_manc.setPreferredSize(new Dimension(100,300));
					player2_manc.setPreferredSize(new Dimension(100,300));
					
					rightPanel.add(player1_manc);
					leftPanel.add(player2_manc);
					
				   GridLayout mancala = new GridLayout(0,6);
				   centerPanel.removeAll();
				   centerPanel.setLayout(mancala);
				   
				   		//add Gpits to centerpanel with actionlistener
				    	for(int i=0;i<2;i++){ 
						    for(int j=0;j<6;j++){
							     
						    	final Gpit pit;
							     if(startStones == 3) pit = new Gpit("","3.gif");
							     else pit = new Gpit("","4.gif");
								 	
							     	if (i == 0) pit.setColumn(5-j);
								 	else pit.setColumn(j);
								     
								     if(i==1){
								    	 pit.setPlayer("Player1");
								     }
								     else{
								    	 pit.setPlayer("Player2");
								     }
								   		
								     final JButton b = new JButton();
								     
								     //decoration 
								     b.setIcon(new ImageIcon("back.gif"));
								     b.setBackground(Color.white);
								     b.setBorderPainted(false);
								     
								     final ImageIcon img = new ImageIcon(pit.getImg());
								     final JLabel label = new JLabel(img);
						
									b.add(label);
									centerPanel.add(b);		
									b.addActionListener(new ActionListener(){
			
									@Override
									public void actionPerformed(ActionEvent arg0) {
										
									   String n = pit.getPlayer();
									   int nInt;
									   System.out.println(n);
									   
									   if(n.equalsIgnoreCase("Player1")){
										   nInt = 0;
									  
										   model.print();
										   System.out.println(pit.getColumn());
									   }
									   else{
									   	nInt = 1;
										  
										   model.print();
										   System.out.println(pit.getColumn());
									   }
									   
									   //if correct user clicked
									  if (model.input(nInt, pit.getColumn())){
									   
									   
									   Component manc1 = rightPanel.getComponent(0);
									   final ImageIcon imgMac1 = new ImageIcon(Integer.toString(model.board[0][6].getStones())+ ".gif");
									   final JLabel label1 = new JLabel(imgMac1);
									   ((JButton)manc1).removeAll();
									   ((JButton)manc1).add(label1);
									   
									   //show player once's score
									   score.setText("First Player :         (" + Integer.toString(model.board[0][6].getStones())+")");
									   
									   Component manc2 = leftPanel.getComponent(0);
									   final ImageIcon imgMac2 = new ImageIcon(Integer.toString(model.board[1][6].getStones())+ ".gif");
									   final JLabel label2 = new JLabel(imgMac2);
									   ((JButton)manc2).removeAll();
									   ((JButton)manc2).add(label2);
									   
									   //show player2's score
									   score1.setText("   Second Player :       (" + Integer.toString(model.board[1][6].getStones())+")");
									   
									   //get the components back
									   Component[] buttons = centerPanel.getComponents();
									   
									   int x = 0;//counter
									   for(int i=1;i>=0;i--){
										    for(int j=0;j<6;j++){
											 message.setText("");
										   	 final Gpit pit;
											    //if first row
										   	 	if (i == 0) pit = new Gpit("", Integer.toString(model.board[i][j].getStones()) + ".gif");
											     else pit = new Gpit("", Integer.toString(model.board[i][5-j].getStones()) + ".gif");
										   	 	 //if second row
											     if (i == 0) pit.setColumn(j);
												  else pit.setColumn(5-j);
												     
												     if(i==1){
												    	 pit.setPlayer("Player1");
												     }
												     else{
												    	 pit.setPlayer("Player2");
												     }
												     
												     final ImageIcon img = new ImageIcon(pit.getImg());
												     final JLabel label = new JLabel(img);
												     //remove everythin from old button
												     ((JButton)buttons[x]).removeAll();
													 ((JButton)buttons[x]).add(label); //update image
													  x++;
										    }}
									    frame.validate();
										frame.repaint();
									}
									  else{
										 //check if no stones left 
										  if(model.isGameOver()==true){
											  
											  score.setText("");
											  score1.setText("");
											  
											  int p2 = model.board[0][6].getStones();
											  int p1 = model.board[1][6].getStones();
											  
											  if(p2==p1){
												  message.setText("GAME OVER :    TIE GAME!");
												  message.setForeground(Color.GREEN);
												 
											  }
											  else if(p2> p1){
												  message.setText("PLAYER 2 WINS!  SCORE : Player1  (" + p1+")" + "    Player 2  ("+p2+")");
												  message.setForeground(Color.GREEN);
												  return;
											  }
											  else{
												  message.setText("PLAYER 1 WINS!  SCORE : Player2  (" + p2+")" + "    Player 1  ("+p1+")");
												  message.setForeground(Color.GREEN);
												  return;
											  }
											  
											  return;
										  }
										  
										  message.setText("Previouse player has an extra turn!");
										  message.setForeground(Color.RED);
										  frame.validate();
										  frame.repaint();
									  }
									 
									}//end actionPer...
							    });//end AddAction...  
								
						   }//end inner for
				    	}//end outter for 
				}//end if
				else{
					startStones=3;
					System.out.println(startStones);
				}
				System.out.println("Select Stones");
			}
	    });
	    
	    
	    //undo
	    undo.addActionListener(new ActionListener()
	    {
				public void actionPerformed(ActionEvent event)
				{
					model.input(2, 0);
					
				   Component manc1 = rightPanel.getComponent(0);
				   
				   final ImageIcon imgMac1 = new ImageIcon(Integer.toString(model.board[0][6].getStones())+ ".gif");
				   final JLabel label1 = new JLabel(imgMac1);
				   ((JButton)manc1).removeAll();
				   ((JButton)manc1).add(label1);
				   
				   //update new score for player 1
				   score.setText("First Player :         (" + Integer.toString(model.board[0][6].getStones())+")");
				   
				   Component manc2 = leftPanel.getComponent(0);
				   final ImageIcon imgMac2 = new ImageIcon(Integer.toString(model.board[1][6].getStones())+ ".gif");
				   final JLabel label2 = new JLabel(imgMac2);
				   ((JButton)manc2).removeAll();
				   ((JButton)manc2).add(label2);
				   
				   //update new score for player2 
				   score1.setText("   Second Player :         (" + Integer.toString(model.board[1][6].getStones())+")");
				   message.setText("");
				   
				   Component[] buttons = centerPanel.getComponents();
				   int x = 0;
				   
				   for(int i=1;i>=0;i--){
					    for(int j=0;j<6;j++){
						    
					   	 final Gpit pit;
						     if (i == 0) pit = new Gpit("", Integer.toString(model.board[i][j].getStones()) + ".gif");
						     else pit = new Gpit("", Integer.toString(model.board[i][5-j].getStones()) + ".gif");

						     if (i == 0) pit.setColumn(j);
							  else pit.setColumn(5-j);
							     
							     if(i==1){
							    	 pit.setPlayer("Player1");
							     }
							     else{
							    	 pit.setPlayer("Player2");
							     }
							     
							     final ImageIcon img = new ImageIcon(pit.getImg());
							     final JLabel label = new JLabel(img);
					
							     ((JButton)buttons[x]).removeAll();
								 ((JButton)buttons[x]).add(label);
								  x++;
					    }}
				    frame.validate();
					frame.repaint();
				}
	    });
	    
	    centerPanel.add(centerInner);
	       
	    //mainPanel has the all the panels
	    mainPanel.add(centerPanel, BorderLayout.CENTER);
	    mainPanel.add(topPanel, BorderLayout.NORTH);
	    mainPanel.add(downPanel,BorderLayout.SOUTH);
	    mainPanel.add(leftPanel,BorderLayout.WEST);
	    mainPanel.add(rightPanel,BorderLayout.EAST);
		downPanel.add(undo,BorderLayout.WEST);
		
		mainPanel.setVisible(true);
		frame.add(stonePanel, BorderLayout.CENTER);
		frame.setSize(900,500);
		frame.setVisible(true);
		frame.add(mainPanel);
		frame.setContentPane(mainPanel);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}//end main
		

	public void stateChanged(ChangeEvent changeevent)
    {
		this.repaint();
    }

}//End View

