package stack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;


public class Stack extends JWindow {
	
	/** Swing components are serializable and require serialVersionUID */
	private static final long serialVersionUID = 6248477390124803341L;
	/** Splash screen duration */
	private final int duration;
	
	ImageIcon logo = new ImageIcon(getClass().getResource("images/logo.png"));
	
	public Stack(int duration) {
		this.duration = duration;
	}
	
	/**
	 * Splash Screen lab demo from CET-CS Course at Alfonquin College
	 * @author Svillen Ranev, Daniel Cormier
	 */
	public void splashWindow() {
		JPanel content = new JPanel(new BorderLayout());
		
		 int width =  400;
		 int height = 695;
		 
		 Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		 int x = (screen.width-width)/2;
		 int y = (screen.height-height)/2;
		 
		 setBounds(x,y,width,height);
		 
		 JLabel label = new JLabel(new ImageIcon(getClass().getResource("images/splashscreen.gif"))); 
		 content.add(label, BorderLayout.CENTER);
	     setContentPane(content);
	     
	     //make the splash window visible
	     setVisible(true);

	     // Snooze for awhile, pretending the code is loading something awesome while
	     // our splashscreen is entertaining the user.
	     try {
	     	
	     	 Thread.sleep(duration); }
	     catch (InterruptedException e) {/*log an error here?*//*e.printStackTrace();*/}
	     //destroy the window and release all resources
	     dispose(); 
	     //You can hide the splash window. The resources will not be released.
	     setVisible(false);
		 
	}

	public static void main(String[] args) {
		
		int duration = 10000;
	    if(args.length == 1){
	    	try{
	    	 duration = Integer.parseInt(args[0]);
	    	}catch (NumberFormatException mfe){
	    	  System.out.println("Wrong command line argument: must be an integer number");
	    	  System.out.println("The default duration 10000 milliseconds will be used");
	    	  //mfe.printStackTrace();	
	    	} 
	    }
	    
	    Stack s = new Stack(duration);
	    s.splashWindow();
	    
	    
	    
		
		EventQueue.invokeLater(new Runnable(){
		       @Override
		       public void run(){ 	
		        JFrame frame = new StackMVC();
		        frame.setIconImage(s.logo.getImage());
		        frame.setTitle("Bounce");
		        frame.setMinimumSize(new Dimension(400, 695));
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setLocationByPlatform(true);
		        frame.pack();
		        frame.setVisible(true);  
		        frame.setResizable(false);
		        frame.setLocationRelativeTo(null);//screen center
		       
		       }
		});

	}

}
