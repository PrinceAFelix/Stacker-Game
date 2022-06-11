package stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * A Simple STACKER Arcade game
 * The main goal of the game is to stack the cubes to the top without missing any blocks
 * The game starts with a good speed(default) and it goes faster as you go up the top
 * The game uses Java Swing components and the game logic is purely invented by the author
 * Note: The program was first coded in windows and soon transferred to Mac and these two provide different output
 * 		 When it runs, so users might need to make its own configuration base on their OS
 * @version 1.0
 * @author prince
 *
 */
public class StackMVC extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Represents the controller of the class
	 */
	Controller myController = new Controller();
	
	/**
	 * Represents the timer
	 */
	Timer timer;
	
	/**
	 * Represents the three red cubes
	 */
	Rectangle2D rect1, rect2, rect3;
	
	
	//Java Swing components
	JLabel mainFrame = new JLabel();
	JLabel logo;
	JButton button;


	/**
	 * Represents the height and width of the cubes
	 */
	int rectWidth = 35, rectHeight = 26;
	
	
	//Checks if user is done with first stack
	boolean isInitiate = false;

	
	//Windows Configuration
//	int xImaginary = 16;
//	int yImaginary = 555;
//	
//	int x = 16;
//	int y = 555;
//
//	int x1 = 53;
//	int y1 = 555;
//	
//	int x2 = 90;
//	int y2 = 555;
//	int xVelocity = 38;
	
	
//	//Mac Configuration
	
	//x and y represents the x and y location of the cubes
	int x = 7; 
	int y = 558;

	int x1 = 46;
	int y1 = 558;
	
	int x2 = 85;
	int y2 = 558;
	int xVelocity = 40;

	//Checks if cubes are going forward
	boolean isForward = true;
	//Checks if user missed block 1, 2 or 3
	boolean missedBlock1, missedBlock2, missedBlock3;
	
	//Represents the default speed of the timer
	int speed = 200;
	//Represents the level of the stack
	int pressCounter = 0;
	
	//Represents the color of the semis elevel
	Color semisColor = Color.WHITE;
	
	//Java Panel that holds components
	JPanel northPanel, centerPanel, southPanel;

	
	//2D JLabel that holds the grid game
	JLabel[][] gridGame = new JLabel[15][10];
	
	//Default Constructors
	StackMVC() {
		
		//Settings of Frame
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY)); 
		logo = new JLabel("STACK - TO - THE - TOP");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setVerticalAlignment(SwingConstants.CENTER);
		logo.setBorder(new EmptyBorder(30,0,30,0));//top,left,bottom,right
		logo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 28));
		logo.setForeground(Color.RED);
		
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.setPreferredSize(new Dimension(400,100));
		northPanel.setBackground(new Color(255, 130, 0, 100));
		
		northPanel.add(logo, BorderLayout.CENTER);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(500,400));
		centerPanel.setBackground(new Color(220, 220, 220));
		
		timer = new Timer(speed, this);
		timer.start();
		
		
		
		JPanel screen = new JPanel();
		screen.setLayout(new GridLayout(15, 10));
		for (int i = 0; i < 15; i++) {  //Nested Loops to place all the label in 15 by 10 Grid
			for (int j = 0; j < 10; j++) {
				gridGame[i][j] = new JLabel();
				gridGame[i][j].setBackground(Color.WHITE);
				if(i == 4) {
					gridGame[i][j].setBackground(Color.CYAN);
				}
				gridGame[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
				gridGame[i][j].setOpaque(true);
				gridGame[i][j].setPreferredSize(new Dimension(20, 20));
				screen.add(gridGame[i][j]);
			}
			
			
		
		}
		//Add the grid on the center
		centerPanel.add(screen, BorderLayout.CENTER);
		
		//South panel settings
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setPreferredSize(new Dimension(50, 100));
		southPanel.setBackground(new Color(0, 0, 204));
		
		//Button's settings and add to south panel
		button = new JButton(new ImageIcon(getClass().getResource("/stack/images/button.png")));
		moveButton(button);
		southPanel.add(button, BorderLayout.CENTER);
		
		//Add all in the main
		mainFrame.add(northPanel, BorderLayout.NORTH);
		mainFrame.add(centerPanel, BorderLayout.CENTER);
		mainFrame.add(southPanel, BorderLayout.SOUTH);
		add(mainFrame);
	}
	
	
	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		rect1 = new Rectangle2D.Double(x, y, rectWidth, rectHeight);
		rect2 = new Rectangle2D.Double(x1, y1, rectWidth, rectHeight);
		rect3 = new Rectangle2D.Double(x2, y2, rectWidth, rectHeight);

		
		g.setColor(Color.RED);
		
		g2d.fill(rect1);

		g2d.fill(rect2);
	
		g2d.fill(rect3);
		
		if(missedBlock1) {
			g.setColor(Color.WHITE);
			g2d.fill(rect1);
		}
		
		if(missedBlock2) {
			g.setColor(Color.WHITE);
			g2d.fill(rect2);
		}
		
		if(missedBlock3) {
			g.setColor(Color.WHITE);
			g2d.fill(rect3);
		}
		
		if(pressCounter == 10) {
			if(missedBlock1) {
				g.setColor(Color.CYAN);
				g2d.fill(rect1);
			}
			if(missedBlock2) {
				g.setColor(Color.CYAN);
				g2d.fill(rect2);
			}
			if(missedBlock3) {
				g.setColor(Color.CYAN);
				g2d.fill(rect3);
			}
		}
		

		
		if(pressCounter == 15) {
			g.setColor(Color.gray);
			g2d.fill(rect1);

			g2d.fill(rect2);
		
			g2d.fill(rect3);
		}
	
	}
	
	
	public JButton moveButton(JButton myButton){
		
		//Apply settings for Button
		myButton.setBackground(Color.WHITE);
		myButton.setOpaque(false);
		myButton.setPreferredSize(new Dimension(100, 100));
		myButton.setMargin(new Insets(10, 10, 10, 10));
		
		
		myButton.addActionListener(myController);
	
		return myButton;
	}
	
	/**Inner Class  to manage all of buttons and the check box
	 * 
	 * @author princ
	 *
	 */
	public class Controller implements ActionListener {
		
		int increase = 0;
		int prev = 0;
		int yHolder = 558;
		
		int counter = 14;
		int[] placeHolder = {0,1,2,3,4,5,6,7,8,9};
		int hold = 0;
		
		int prev1, prev2;
		
		int leftAdd = 1;
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			
			//Checks if there's a button clicked
			if(ae.getSource() == button) {
				
				/*
				 * Logic is that first, we do our first block and we start our stacking process
				 */
				if(isInitiate == false) {
					
					
					increase += 2;
					prev = y;
					yHolder -= 32;
					prev = yHolder;
					yHolder += increase;
					y = yHolder;
					y1 = yHolder;
					y2 = yHolder;
					yHolder = prev;
					pressCounter++;
					
					hold = getPlaceHolder(x1);
					
					
					gridGame[counter][hold].setBackground(Color.RED);
					
					prev1 = counter;
					prev2 = hold;
					gridGame[counter][hold - 1].setBackground(Color.RED);
					gridGame[counter][hold + 1].setBackground(Color.RED);

					isInitiate = true;
					
					counter--;
					repaint();
				}else {
					
					increase += 2;
					
					prev = y;
					yHolder -= 32;
					prev = yHolder;
					yHolder += increase;
					y = yHolder;
					y1 = yHolder;
					y2 = yHolder;
					yHolder = prev;
					pressCounter++;
					
					hold = getPlaceHolder(x1);

					
					prev1 = counter;
					prev2 = hold;
					
					
					
					
					if(!missedBlock1) {
						gridGame[counter][hold - 1].setBackground(Color.RED);
					}
					
					if(!missedBlock2) {
						gridGame[counter][hold].setBackground(Color.RED);
					}
					
					
					if(!missedBlock3) {
						gridGame[counter][hold + 1].setBackground(Color.RED);
					}
					
					if(missedBlock2 && missedBlock3)
						leftAdd = 0;
					
					
					
					if(pressCounter == 11) {
						semisColor = Color.CYAN;
						speed = 50;
					}
					else
						semisColor = Color.WHITE;
					
					if(gridGame[counter + 1][hold].getBackground() != Color.RED) {
						if(!missedBlock2) {
							gridGame[counter][hold].setBackground(Color.RED);
							repaint();
							pauseGame();
						}
						gridGame[counter][hold].setBackground(semisColor);
						repaint();
						missedBlock2 = true;
						
					}
					
					if(gridGame[counter + 1][hold - leftAdd].getBackground() != Color.RED) {
						if(!missedBlock1) {
							gridGame[counter][hold - 1].setBackground(Color.RED);
							repaint();
							pauseGame();
						}
						gridGame[counter][hold - 1].setBackground(semisColor);
						repaint();
						missedBlock1 = true;
					}
					
					if(gridGame[counter + 1][hold + 1].getBackground() != Color.RED){
						if(!missedBlock3) {
							gridGame[counter][hold + 1].setBackground(Color.RED);
							pauseGame();
							repaint();
						}
						gridGame[counter][hold + 1].setBackground(semisColor);
						repaint();
						missedBlock3 = true;
					}
					
					counter--;
					
					if(missedBlock2 && missedBlock3 && missedBlock1)
						endGame();
					
					
					repaint();
					
				}
				
				

			}
			
			if(pressCounter == 15) {
				button.setEnabled(false);
				finishedGame();
				
			}
			
			if(pressCounter == 10) {
				speed = 140;
				timer.setDelay(speed);
				showSemisMessage();
			}
			
			if(pressCounter == 13) {
				speed = 125;
				timer.setDelay(speed);
			}
			if(pressCounter < 10) {
				speed -= 5;
				timer.setDelay(speed);
			}
			
			if(pressCounter == 6 && !missedBlock1 && !missedBlock2 && !missedBlock3) {
				missedBlock1 = true;
			}
		}
		
		/**
		 * This method returns the index from 2d grid on which it needs to fill with color
		 * 
		 * @param xLoc represents the xLocation of the cube
		 * @return index
		 */
		public int getPlaceHolder(int xLoc) {
			
			//Windows: 53, 90, 127, 164, 201, 238, 275, 312 (Pattern of going up by 37)
			//Mac:     46, 85, 124, 163, 202, 241, 280, 319 (Pattern of going up by 39)
			int index = 0;
			switch(xLoc) {
			case 46: 
				index = placeHolder[1];
				break;
			case 85:
				index = placeHolder[2];
				break;
			case 124: 
				index = placeHolder[3];
				break;
			case 163:
				index = placeHolder[4];
				break;
			case 202: 
				index = placeHolder[5];
				break;
			case 241: 
				index = placeHolder[6];
				break;
			case 280: 
				index = placeHolder[7];
				break;
			case 319: 
				index = placeHolder[8];
				break;
			}
			
			
			return index;
		}
	}
	

	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		
		/*
		 * This conditions checks is use to checks if the cubes should go forward or backward
		 */
		if(x >= 260 && x1 >= 280 && x2 >= 320) {
			xVelocity *= -1;
			isForward = false;
			
		}else if(x < 20 && x1 < 65 && x2 < 100 && !isForward) {
			xVelocity *= -1;
			isForward = true;
		}
		

		//Constantly add
		x += xVelocity;
		x1 += xVelocity;
		x2 += xVelocity;

		repaint();
		
		if(isForward) {
			x--;
			x1--;
			x2--;
		}else {
			x++;
			x1++;
			x2++;
		}
	}
	
	/**
	 * This method uses message dialog to tell the user that they've lose the game
	 */
	public void endGame() {
		JOptionPane.showMessageDialog(null, "Game Over");
		button.setEnabled(false);
		dispose();
		System.exit(1);
	}
	
	/**
	 * This method uses message dialog to tell the user that they've finished the game successfully
	 */
	public void finishedGame() {
		JOptionPane.showMessageDialog(null, "You win!\nYou are a STACKER master");
		button.setEnabled(false);
		dispose();
		System.exit(1);
		
	}
	
	/**
	 * This method uses message dialog to tell the user that they've reached the semis level and the speed is now faster
	 */
	public void showSemisMessage() {
		JOptionPane.showMessageDialog(null, "Congrats! You reached Semis\n Speed is now faster");
	}
	
	/**
	 * This method is use to pause the game for 1 second
	 */
	public void pauseGame() {
		try
		{
		    Thread.sleep(1000);
		}
		catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
	}
	
}
