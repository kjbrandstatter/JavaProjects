package Pacman.game;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Random;
import java.util.*;

public class Ghost {
	private int x,y;
	private String color;
	protected String origColor;
	private char dir;
	private final char[] all = {'U', 'L', 'D', 'R'};
	private int imgDir;
	private Timer respawnTimer;
	private boolean stopped;
	public Ghost(int a, int b, String c){
		x = a;
		y = b;
		color = c;
		origColor = c;
		dir = 'U';
		imgDir = 0;
		respawnTimer = new Timer();
		stopped = false;
	}
	public void move(){
		if (stopped)
			return;
		switch (dir)
		{
		case 'U':
			y -= 5;
			break;
		case 'D':
			y += 5;
			break;
		case 'L':
			x -= 5;
			break;
		case 'R':
			x += 5;
		default:
			break;
		}
	}
	public int getX(){return x;}
	public int getY(){return y;}
	public void setD(char d){
		dir = d;
		if (d == 'R')
			imgDir = 0;
		else if (d == 'L')
			imgDir = 1;
	}
	public int getImgDir() {return imgDir;}
	public void setXY(int newX, int newY){ 
		x = newX;
		y = newY;
	}
	public char getD() {return dir;}
	public String color() {return color;}
	public void color(String s) {color = s;}
	public void transport(){x = Math.abs(x-505);}
	public void setD(char[][] m){
		Random r = new Random();
		int i = 0;
		do{
			for (i = 0; i < all.length; i++){
			if (all[i] == dir)
				break;
			}
			i += (r.nextInt(3)-1);
			i = (4+i)%4;
		}while (!isValidMove(m, all[i]));
		setD(all[i]);
	}
	public boolean isValidMove(char[][] ma, char test){
		HashMap<Character, Point> key = new HashMap<Character, Point>();
		key.put(new Character('U'), new Point(0,-1));
		key.put(new Character('D'), new Point(0,1));
		key.put(new Character('R'), new Point(1,0));
		key.put(new Character('L'), new Point(-1,0));
		int xInd = (x-5)/20 + key.get(test).x;
		int yInd = (y-5)/20 + key.get(test).y;
		try{
			return ma[yInd][xInd] != 'B' && ma[yInd][xInd] != 'X';
		}catch(Exception e){
			return false;
		}
	}
	public Rectangle getMid(){
		return new Rectangle(x+5, y+5, 10, 10);
	}
	private class respawnCount extends TimerTask{
		private int t;
		public respawnCount(){
			t = 3;
		}
		public void run(){
			t--; 
			if (t == 0){
				stopped = false;
				super.cancel();
			}
		}
	}
	public void respawn(){
		stopped = true;
		setXY(265,285);
		respawnTimer.scheduleAtFixedRate(new respawnCount(), 0L, 1000);
	}
}