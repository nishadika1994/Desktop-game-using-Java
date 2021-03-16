/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Score implements Runnable{

	int ma = 0;
	int life ;
	public int calacualteScore() {
		
		/*if(life == 0)
			try {
				Thread.sleep(10);
				ma = ma+10;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		return ma;
	
	}

	public int calacualteScore1(int dELAY2, int waiting) {
		
				
		int all = 10 + 100*(dELAY2/waiting);
		
		return ma + all;
	}

	public void life(int life) {
		
		this.life = life;
	}
	@Override
	public void run() {
		
		
		while(true)
		{
		      }
		    }
		
		}
		
		
	
	


        