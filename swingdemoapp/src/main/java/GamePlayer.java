/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class GamePlayer {
    int cx= 300; //player X codinates
	int cy = 300; // Player Y codinates
	
	public void initswarm () {

	}

		
	public int getax (){
		return cx;
	}
	public int getay (){
		return cy;
	}

	
	public void moveup () {
		if (cy>70) cy -=15;
		
	}
	public void movedown () {
		if (cy<680) cy+=15;
	}
	public void moveleft () {
		if(cx>70) cx-=15;
	}
	public void moveright () {
		if (cx<680) cx +=15;
		
	} 

}

