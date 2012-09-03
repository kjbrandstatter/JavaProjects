package Pacman.game;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

public class Pacman{
	private int x, y;
	private int status; //true = open false = closed
	private char dir;
	private char lastPress;
	public Pacman(){
		x = 245;
		y = 325;
		status = 1;
		dir = 'R';
		lastPress = dir;
	}
	public void move(char[][] map){
		if (isValidMove(map, lastPress)){
			setD(lastPress);
		}
		else if (!isValidMove(map, dir))
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
	public void move(){
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
	public int getX(){return x;}
	public int getY(){return y;}
	public void setD(char d){dir = d;}
	public char getD() {return dir;}
	public int mouth() {return status;}
	public void mouth(boolean s) {status = (status+1)%4;}
	public void transport(){x = Math.abs(x-515);}
	public void setLastPressed(char l){lastPress = l;}
	public Rectangle getMid(){
		return new Rectangle(x+5, y+5, 10, 10);
	}
	public void setXY(int anX, int anY){
		x = anX;
		y = anY;
	}
}
