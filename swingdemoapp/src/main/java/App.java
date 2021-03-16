import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class App extends JFrame{
	
	// Maximum number of threads in thread pool 
	    static final int MAX_T = 5; 
    
        public App() {
            initUI();
             }
        
                private void initUI() {
            
            add(new SwingArena());
            
            setTitle("Protect The Carsel!");
            setSize(770, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);  
            setResizable(false);   
            setBackground (Color.black);  
         
        }
        public static void main(String[] args) 
                {

        SwingUtilities.invokeLater(new Runnable() {
            
    	   	@Override
    		public void run() {
    			 
    	   		App ex = new App();
    	                ex.setVisible(true);
    	            
            		
                
    		int corePoolSize = 10;
    		int maxPoolSize = 100;
    		int keepAliveTime = 120;
    		BlockingQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
    		 
    		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,
    		                         maxPoolSize,
    		                         keepAliveTime,
    		                         TimeUnit.SECONDS,
    		                         workQueue);
    		pool.execute(new SwingArena());
    				
    	           }
    		});
    }
    		
    }


    /*
     * 
     * 
     * 
     * 
     * References 
     * 
     * https://www.codejava.net/java-core/concurrency/java-concurrency-understanding-thread-pool-and-executors
     * https://www.geeksforgeeks.org/thread-pools-java/
     * http://zetcode.com/javagames/animation/
     * https://mega.nz/folder/3w0DjAjQ#K_iYe1xbC99RevfL35jaHA
     * http://zetcode.com/javagames/collision/
     * https://www.youtube.com/watch?v=l3fLvN4pWjI&list=PLH7W8KdUX6P1DRGhIktyX0hGhXDMZ9AQC&index=3
     * 
     * 
     */
