import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.*;
import java.net.URL ;

/**
 * A Swing GUI element that displays a grid on which you can draw images, text and lines.
 */
public class SwingArena  extends JPanel implements ActionListener,KeyListener,Runnable{


	//Sound sound = new Sound ();
    private LinkedList<ArenaListener> listeners = null;
    private int gridWidth = 9;
    private int gridHeight = 9;
    private double gridSquareSize; 
    int gridX =1;
    int gridY= 1;
    int gridXX =1;
    int gridYY= 1;
    
	//private Timer timer;
	private final int DELAY = 30;
	GamePlayer game;
	int move=0;
	int robotmovoe =0;
	
	int cgx =0;  //robots X codinates
	int cgy =0;  //robots y codinates
	int robmove =0; 
	Random rand; 
	String  message ="Destroy All Robots !";
	String messageB ="SCORE :";
	String messageC ="X";
	String messageD ="Life";
	int life =5;
	int msgy = 100;
	int msgypos= 0; 
	int robotmoveVeriable =0;
	int mx=0;
	int my=0;
	int startGame =0;
	int marks =0;
	String gameovermsg =("G A M E  O V E R");
	String score=("Score - ");
	int timeintro =0;
	int ma;
	Score scor = new Score();
	private Thread thread;
	int allMark;
	Thread runner;
    int number = 1000;
	   
   
    public SwingArena()
    {
    	addKeyListener (this);
 		setFocusable (true); 
 		setBackground (Color.WHITE);
 		game = new GamePlayer ();
 		
    }
   
 
    
	@Override
	public void addNotify() {
		// TODO Auto-generated method stub
		super.addNotify();
		thread = new Thread(this);
		thread.start();
        
     
	}

	/**
     * Adds a callback for when the user clicks on a grid square within the arena. The callback 
     * (of type ArenaListener) receives the grid (x,y) coordinates as parameters to the 
     * 'squareClicked()' method.
     */
    public void addListener(ArenaListener newListener)
    {
        if(listeners == null)
        {
            listeners = new LinkedList<>();
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent event)
                {
                    int gridX = (int)((double)event.getX() / gridSquareSize);
                    int gridY = (int)((double)event.getY() / gridSquareSize);
                    int gridXX = event.getX();
                    int gridYY = event.getY();

                    if(gridX < gridWidth && gridY < gridHeight)
                    {
                        for(ArenaListener listener : listeners)
                        {   
                            listener.squareClicked(gridX, gridY);
                     }
                    }
                    

                    if(gridXX < gridWidth && gridYY < gridHeight)
                    {
                    	if(gridXX == cgx && gridYY == cgy ){
                    		marks = marks +10;
                     }
                    }
              }
            });
        }
              listeners.add(newListener);
    }
    
    
    
    /**
     * This method is called in order to redraw the screen, either because the user is manipulating 
     * the window, OR because you've called 'repaint()'.
     *
     */
    @Override
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);		
    	
      	 

		
		drawImage(g);
		drawMsg (g);
		
		if(gridX ==cgx && gridY == cgy ) gameover(g);
				
	    if (life <1 ) gameover(g);  
		
		Toolkit.getDefaultToolkit().sync();
    }
    
    
    /** 
     * Draw an image in a specific grid location. *Only* call this from within paintComponent(). 
      */
    private void drawImage(Graphics g) {
	
		try {
			BufferedImage background =ImageLoading.loadImg("/img/Background.jpg");
			BufferedImage player =ImageLoading.loadImg("/img/pngwave.png");
			BufferedImage robo1 =ImageLoading.loadImg("/img/droid2.png");
			BufferedImage robo2 =ImageLoading.loadImg("/img/1554047213.png");
			BufferedImage robo3 =ImageLoading.loadImg("/img/droid2.png");
			BufferedImage robo4 =ImageLoading.loadImg("/img/rg1024-robot-carrying-things-4.png");
			BufferedImage tower =ImageLoading.loadImg("/img/rg1024-isometric-tower.png");
			BufferedImage macc =ImageLoading.loadImg("/img/cross.png");
			BufferedImage log =ImageLoading.loadImg("/img/logo.jpg");
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLUE);
			Font font = new Font("Verdana", Font.BOLD, 18);
			g2d.setFont(font);

			if (startGame == 1) {	
	            setBackground (Color.black);  

				g2d.drawImage (background ,0, 10, this); 

				Graphics2D gfx = (Graphics2D) g;
		         gfx.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
		                              RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		         
		         // First, calculate how big each grid cell should be, in pixels. (We do need to do this
		         // every time we repaint the arena, because the size can change.)
		         gridSquareSize = Math.min(
		             (double) getWidth() / (double) gridWidth,
		             (double) getHeight() / (double) gridHeight);
		             
		         int arenaPixelWidth = (int) ((double) gridWidth * gridSquareSize);
		         int arenaPixelHeight = (int) ((double) gridHeight * gridSquareSize);
		             
		             
		         // Draw the arena grid lines. This may help for debugging purposes, and just generally
		         // to see what's going on.
		         gfx.setColor(Color.GRAY);
		         gfx.drawRect(0, 0, arenaPixelWidth - 1, arenaPixelHeight - 1); // Outer edge

		         for(int gridX = 1; gridX < gridWidth; gridX++) // Internal vertical grid lines
		         {
		             int x = (int) ((double) gridX * gridSquareSize);
		             gfx.drawLine(x, 0, x, arenaPixelHeight);
		         }
		         
		         for(int gridY = 1; gridY < gridHeight; gridY++) // Internal horizontal grid lines
		         {
		             int y = (int) ((double) gridY * gridSquareSize);
		             gfx.drawLine(0, y, arenaPixelWidth, y);
		         }
		         gfx.setColor(Color.BLUE);
				 g2d.setFont(font);

			    g2d.drawImage (player, game.getax(), game.getay(), this); 
				if (robotmoveVeriable == 1)	g2d.drawImage (macc, mx, my, this);					
				if (robotmovoe == 0 ) createRobots();
				
				if (robmove==1 && robotmovoe >0 && robotmovoe < 11) {
					
					g2d.drawImage (robo1, cgx, cgy, this);
			        g2d.drawString("Robot Kal"+number,  cgx, cgy);
			     
				}
				if (robmove==2 && robotmovoe >0 && robotmovoe < 11 ) {
					g2d.drawImage (robo2, cgx, cgy, this);
			        g2d.drawString("Robot Max" + number,  cgx, cgy);

				}
				if (robmove==3 && robotmovoe >0 && robotmovoe < 11 ) {
					g2d.drawImage (robo3, cgx, cgy, this); 
			        g2d.drawString("Robot Jin" + number,  cgx, cgy);

				}
				if (robmove==4 && robotmovoe >0 && robotmovoe < 11 ) {
					g2d.drawImage (robo4, cgx, cgy, this);
			        g2d.drawString("Robot Ahk" +number,  cgx, cgy);
  
				}
				g2d.drawImage (tower , 300,300,this); 
		}
			else if (startGame == 0) {
				g2d.drawImage (log,200,300,this);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    public void drawMsg (Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font font = new Font("Verdana", Font.BOLD, 20);

		g2d.setColor(Color.RED);
        g2d.setFont(font);
        if (startGame == 1) {
			g2d.drawString(message ,msgy,100); // command
			g2d.drawString (messageB , 50, 600);  // health msgvff
			
				g2d.drawString (Integer.toString(marks + allMark) , (150),600);  // helth
				//marks = marks+10;
				
				g2d.drawString (messageD , 50, 50);
				int ga = life;
				while (ga >0 ) {
					g2d.drawString (messageC , (100+(ga*15)),50);
					ga -=1;
				}

			
			}
		}
		
       
   
    public void gameover (Graphics g) { //game over msg
		
		try {
			Graphics2D g2d = (Graphics2D) g;
			Font font = new Font("Verdana", Font.BOLD, 45);
			java.awt.FontMetrics metr = this.getFontMetrics(font);
			g2d.setColor(Color.WHITE);
			g2d.setFont(font);
			g2d.drawString (gameovermsg, 150,370);
			g2d.drawString (score, 300,500 );
			
			
				
				String finpunt = Integer.toString(marks + allMark);
				g2d.drawString (finpunt, 470,500);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
	
   
   public void run() {

			        long beforeTime, timeDiff, sleep;
				
			        beforeTime = System.currentTimeMillis();
			        while (true) {
				      
			        actionPerformed(null);
		            repaint();
							//score3();
					number = number+ 1;
							 
		            timeDiff = System.currentTimeMillis() - beforeTime;
		            sleep = DELAY - timeDiff;
				           
		            if (sleep < 0) {
		                sleep = 2;
		            }
				
 		            try {
 		            	// Thread.sleep(sleep);
				           Thread.sleep(100);
				
			          } catch (InterruptedException e) {
				                
	    	                String msg = String.format("Thread interrupted: %s", e.getMessage());
				                
				                JOptionPane.showMessageDialog(this, msg, "Error", 
				                    JOptionPane.ERROR_MESSAGE);
		            }
				
 		            beforeTime = System.currentTimeMillis();
        }
    }

	public void labeldestroy() {   //flag "MESSAGE TO KILL ROBOTS" 
		 SwingUtilities.invokeLater(new Runnable()
	        {
	            @Override 
	            public void run()
	            {            
	            	try {
	        			if (msgy == 700 ) msgypos = 1;
	        			if (msgy == 90 ) msgypos = 0;
	        			if (msgypos ==0) msgy += 10;
	        			if (msgypos ==1) msgy -=10;
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}	           
	            	  }
	               });
			}
	

    public int score3() {  //calcualte marks for mouse click and drestoring robots
	
		try {
			Score s1 = new Score();
			int marks2 = s1.calacualteScore();
			marks =  marks + marks2;
			
			return marks;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

    }

	public void movebig(){       // player movuments using arrow keys
		
		 SwingUtilities.invokeLater(new Runnable()
	        {
				@Override 
	            public void run()
	            {         
	            	            	
	            	try {
	        			if (move==1)  game.moveleft();
	        			else if (move==2) game.moveright();
	        			else if (move==3) game.moveup ();
	        			else if (move==4) game.movedown ();
	        			        			
	        			
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		} 
	            }
	            
	        });
		
	}
	
	
	
	public void createRobots() {
		
		 SwingUtilities.invokeLater(new Runnable()
	        {
	            @Override 
	            public void run()
	            {   	try {
	    			rand = new Random ();
	    			robmove = rand.nextInt(5);
	    			
	    			robmove +=1;
	    			
	    			
	    			if (robmove==1 || robmove ==3) cgx= rand.nextInt(300);
	    			if (robmove ==1 )cgy= 1;
	    			
	    			if (robmove ==2 ) cgx =300;
	    			if (robmove == 2 ||robmove == 4 ) cgy = rand.nextInt(500);
	    			
	    			if (robmove ==3) cgy = 500;
	    			if (robmove==4) cgx=1;
	    					
	    			robotmovoe=1;
	    			
	    	//		Thread.sleep(100);
	    			
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}         
	            }
	        });
	
	}
	
	
	
	
	public void moveRobot() {  // all robots(enamies) movements
	
		 SwingUtilities.invokeLater(new Runnable()
	        {
	            @Override 
	            public void run()
	            {      
	            	try {
	        			if (robotmovoe >0 && cgx< 480) cgx+=7;
	        			if (robotmovoe >0 && cgx> 500) cgx-=7;
	        			if (robotmovoe >0 && cgy< 340) cgy+=7;
	        			if (robotmovoe >0 && cgy> 340) cgy-=7;
	        			robotmovoe +=1;
	        			if (robotmovoe ==11) robotmovoe= 1; robotmoveVeriable =0;
	        		
	        		
	        		
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            }
	        });
		
		
		
	}
	
	
	
	public Rectangle getRobotDestroyDetaction(){// player colluction detection rectangle making
        try {
			BufferedImage robo1 =ImageLoading.loadImg("/img/droid2.png");

			        int width = robo1.getWidth(null);
			        int height = robo1.getHeight(null);

			return new Rectangle(cgx, cgy, width, height);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void destroy(){
		
	try {
		if(gridX ==cgx && gridY == cgy ) {
				marks = marks+10;
				;
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
		
	
	public Rectangle getCarselDetaction() { //tower coloution detaction rectangele making
        try {
			BufferedImage tower =ImageLoading.loadImg("/img/rg1024-isometric-tower.png");
			int width = tower.getWidth(null);
			int height = tower.getHeight(null);
			
		return new Rectangle(300, 300, width, height);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public void controllacollisioniA () {
		
		 SwingUtilities.invokeLater(new Runnable()
	        {
	            @Override 
	            public void run()
	            {      
	            	try {
	        			Rectangle r1 = getRobotDestroyDetaction();
	        			Rectangle r2 = getCarselDetaction();
	        			
	        			

	        			if (robotmovoe ==1 ) {
	        				if (r1.intersects(r2)) robotmovoe =0; 
	        				if (r1.intersects(r2)) life -= 1;  //Life enable level
	        				//if (r1.intersects(r2)) life = ;
	        				
	        			
	        			}
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            }
	        });
		
		
	}
	
	public Rectangle getPlayerDetaction() {
		
		try {
			BufferedImage cu = ImageLoading.loadImg("/img/pngwave.png");
			int widthPlayer = cu.getWidth(null);
			int heightPlayer = cu.getHeight(null);
			
			return new Rectangle(game.cx, game.cy, widthPlayer, heightPlayer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	public void controllocollisioniB () { //
		Rectangle r1 =getRobotDestroyDetaction ();
		Rectangle r2 = getPlayerDetaction ();
		
		if (robotmoveVeriable ==0 && r1.intersects (r2) && robotmovoe >1 ) robotmoveVeriable =1; mx=cgx; my= cgy;
		if (r1.intersects (r2)) robotmovoe =0 ;
		if (r1.intersects (r2)) {
			int waiting = 100;
			 marks +=10;
			 allMark =  scor.calacualteScore1(DELAY,waiting);
			 marks = marks + allMark;
		}
		       
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		try {
			int key = ke.getKeyCode();
			
			if (key == KeyEvent.VK_LEFT) {
			   move=1;
			    
			}

			if (key == KeyEvent.VK_RIGHT) {
			   move=2;
			}

			if (key == KeyEvent.VK_UP) {
			    move=3;
			}

			if (key == KeyEvent.VK_DOWN) {
			    move=4;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		try {
			if (startGame == 0) startGame =1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		try {
			if (life >0) {
				if (startGame ==0 && timeintro < 5 ) timeintro +=1;
				if (startGame ==1) { 
				movebig(); 
				moveRobot();
				controllacollisioniA(); 
				controllocollisioniB (); 
				destroy();
				labeldestroy();
				score3();
				marks = marks+1;           //Marks per second
			   }
			 }
				
			repaint();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
	
}
	
	


