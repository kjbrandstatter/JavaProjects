package Pacman.game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.Timer;

import javax.imageio.*;
import javax.swing.*;

public class GameScreen extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage map;
	private char[][] mapArr;
	private BufferedImage[][] pacImgs;
	private BufferedImage[] dotImgs;
	private Pacman pac;
	private Ghost[] ghosts;
	private HashMap<String, BufferedImage> ghostImgs;
	private int height, width;
	private Timer gameRunner;
	private long speed;
	private int score;
	private boolean pause;
	private boolean counting;
	private Countdown count;
	private boolean started;
	private int lives;
	private boolean pacMouth = true;
	private boolean ghostSlower = true;
	public GameScreen(){
		lives = 3;
		started = false;
		count = new Countdown();
		counting = false;
		pause = true;
		score = 0;
		speed = 30;
		height = 590;
		width = 530;
		mapArr = new char[29][26];
		pacImgs = new BufferedImage[4][4];
		dotImgs = new BufferedImage[2];
		ghostImgs = new HashMap<String, BufferedImage>();
		ghosts = new Ghost[4];
		loadMap();
		loadDots();
		loadPac();
		loadGhosts();
		pac = new Pacman();
		addKeyListener(new Controller());
		gameRunner = new Timer();
		gameRunner.scheduleAtFixedRate(new pacRunner(), 500L, speed);
		gameRunner.scheduleAtFixedRate(new ghostRunner(), 500L, speed);
	}
	private class Controller implements KeyListener{
		public Controller(){}
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP){
				//if ((pac.getX()-5)%20 == 0 || pac.getD() == 'D')
					pac.setLastPressed('U');
				//pac.move();
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN){
				//if ((pac.getX()-5)%20 == 0 || pac.getD() == 'U')
					pac.setLastPressed('D');
				//pac.move();
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT){
				//if ((pac.getY()-5)%20 == 0 || pac.getD() == 'R')
					pac.setLastPressed('L');
				//pac.move();
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				//if ((pac.getY()-5)%20 == 0 || pac.getD() == 'L')
					pac.setLastPressed('R');
				//pac.move();
			}
			else if (e.getKeyCode() == KeyEvent.VK_ENTER){
				pause = false;
				started = true;
			}
			repaint();
		}
		public void keyReleased(KeyEvent e) {	}
		public void keyTyped(KeyEvent e){
			if (e.getKeyChar() == 'p')
				pause = true;
		}
	}
	private class pacRunner extends TimerTask{
		public pacRunner(){}
		public void run() {
			if (pause || !started)
				return;
			try{
			switch (pac.getD()){
				case 'R':
					if ((pac.getX()+15)%20 != 0)
						pac.move();
					else if (mapArr[(pac.getY()-5)/20][(pac.getX()/20)+1] != 'B')
						pac.move(mapArr);
					else 
						pac.move(mapArr);
					break;
				case 'L':
					if ((pac.getX()-5)%20 != 0)
						pac.move();
					else if (mapArr[(pac.getY()-5)/20][(pac.getX()/20)-1] != 'B')
						pac.move(mapArr);
					else 
						pac.move(mapArr);
					break;
				case 'U':
					if ((pac.getY()-5)%20 != 0)
						pac.move();
					else if (mapArr[((pac.getY())/20)-1][pac.getX()/20] != 'B')
						pac.move(mapArr);
					else 
						pac.move(mapArr);
					break;
				case 'D':
					if ((pac.getY()+15)%20 != 0){
						if (mapArr[((pac.getY())/20)+1][pac.getX()/20] != 'X')
							pac.move();
					}
					else if (mapArr[((pac.getY())/20)+1][pac.getX()/20] != 'B'){
						if (mapArr[((pac.getY())/20)+1][pac.getX()/20] != 'X')
							pac.move(mapArr);
					}
					else 
						pac.move(mapArr);
					break;
				}
			}catch(Exception e){
				if (pac.getY() == 265)
					pac.transport();
				else 
					pac.move(mapArr);
			}
			eat();
			if (pacMouth){
				pac.mouth(true);
				pacMouth = false;
			}
			else
				pacMouth = true;
			for (Ghost gh : ghosts){
	    		if (pac.getMid().intersects(gh.getMid())){
	    			if (gh.color().equals(gh.origColor)){
	    				lives--;
	    				if (lives == 0){
	    					gameRunner.cancel();
	    					JOptionPane.showMessageDialog(null,"Game Over");
	    				}
	    				else 
	    					reset();
	    			}
	    			else {
	    				gh.respawn();
	    				score += 500;
	    				//eatGhost(gh);
	    			}
	    		}
	    	}
			repaint();
		}
	}
	private class ghostRunner extends TimerTask{
		public ghostRunner(){}
		public void run() {
			if (pause || !started)
				return;
			if (ghosts[0].origColor != ghosts[0].color() && ghostSlower){
				ghostSlower = false;
				return;
			}
			else if (ghosts[0].origColor != ghosts[0].color() && !ghostSlower)
				ghostSlower = true;
			for (Ghost gh : ghosts){
				try{
					switch (gh.getD()){
						case 'R':
							if ((gh.getX()+15)%20 != 0)
								gh.move();
							else if (mapArr[(gh.getY()-5)/20][(gh.getX()/20)+1] != 'B'){
								gh.setD(mapArr);
								if(inMiddle(gh))
									gh.setD('U');
								gh.move();
							}
							else{
								gh.setD(mapArr);
								gh.move();
							}
							break;
						case 'L':
							if ((gh.getX()-5)%20 != 0)
								gh.move();
							else if (mapArr[(gh.getY()-5)/20][(gh.getX()/20)-1] != 'B'){
								gh.setD(mapArr);
								if(inMiddle(gh))
									gh.setD('U');
								gh.move();
							}
							else{
								gh.setD(mapArr);
								gh.move();
							}
							break;
						case 'U':
							if ((gh.getY()-5)%20 != 0)
								gh.move();
							else if (mapArr[((gh.getY())/20)-1][gh.getX()/20] != 'B'){
								if (!inMiddle(gh))
									gh.setD(mapArr);
								if(inMiddle(gh))
									gh.setD('U');
								gh.move();
							}
							else{
								gh.setD(mapArr);
								gh.move();
							}
							break;
						case 'D':
							if ((gh.getY()+15)%20 != 0)
								gh.move();
							else if (mapArr[((gh.getY())/20)+1][gh.getX()/20] != 'B'){
								gh.setD(mapArr);
								if(inMiddle(gh))
									gh.setD('U');
								gh.move();
							}
							else{
								gh.setD(mapArr);
								gh.move();
							}
							break;
						}
				}catch(Exception e){
					if (gh.getY() == 265)
						gh.transport();
					else {
						gh.setD(mapArr);
						gh.move();
					}
				}
			}
		}
	}
	private class Countdown extends TimerTask{
		int t;
		public Countdown(){
			t = 15*5;
		}
		public void end(){
			super.cancel();
		}
		public void run(){
			if (pause || !started)
				return;
			t--;
			if (t == 0){
				for (Ghost gh : ghosts){
					gh.color(gh.origColor);
				}
				counting = false;
				super.cancel();
			}
			else if (t < 15 || t > 60){
				for (Ghost gh : ghosts){
					if (gh.color().equals("blue"))
						gh.color("white");
					else 
						gh.color("blue");
				}
			}
			else {
				for (Ghost gh : ghosts){
					gh.color("blue");
				}
			}
		}
	}
	public void loadMap(){
		try{
			map = ImageIO.read(new File("Images/pacmanScreen.jpg"));
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "Fail");
		}
		File mapData = new File(".map");
		try{
			Scanner scan = new Scanner(mapData);
			int l = 0;
			while (scan.hasNext()){
				mapArr[l] = scan.nextLine().toCharArray();
				l++;
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "Failed to load map data");
		}
	}
	public void loadDots(){
		try{
			dotImgs[0] = ImageIO.read(new File("Images/dots/littleDot.jpg"));
			dotImgs[1] = ImageIO.read(new File("Images/dots/bigDot.jpg"));
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "Failed to load dot images");
		}
	}
	public void loadPac(){
		try{
			pacImgs[0][0] = ImageIO.read(new File("Images/pacImgs/pacClose.jpg"));
			pacImgs[1][0] = ImageIO.read(new File("Images/pacImgs/pacHalfOpen.jpg"));
			pacImgs[1][1] = ImageIO.read(new File("Images/pacImgs/pacHalfOpenU.jpg"));
			pacImgs[1][2] = ImageIO.read(new File("Images/pacImgs/pacHalfOpenL.jpg"));
			pacImgs[1][3] = ImageIO.read(new File("Images/pacImgs/pacHalfOpenD.jpg"));
			pacImgs[2][0] = ImageIO.read(new File("Images/pacImgs/pacOpen.jpg"));
			pacImgs[2][1] = ImageIO.read(new File("Images/pacImgs/pacOpenU.jpg"));
			pacImgs[2][2] = ImageIO.read(new File("Images/pacImgs/pacOpenL.jpg"));
			pacImgs[2][3] = ImageIO.read(new File("Images/pacImgs/pacOpenD.jpg"));
			pacImgs[3][0] = ImageIO.read(new File("Images/pacImgs/pacHalfOpen.jpg"));
			pacImgs[3][1] = ImageIO.read(new File("Images/pacImgs/pacHalfOpenU.jpg"));
			pacImgs[3][2] = ImageIO.read(new File("Images/pacImgs/pacHalfOpenL.jpg"));
			pacImgs[3][3] = ImageIO.read(new File("Images/pacImgs/pacHalfOpenD.jpg"));
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "Failed to load pacman images");
		}
	}
	public void loadGhosts(){
		ghosts[0] = new Ghost(245,285,"orange");
		ghosts[1] = new Ghost(245,265,"azul");
		ghosts[2] = new Ghost(265,265,"pink");
		ghosts[3] = new Ghost(265,285,"red");
		try{
			ghostImgs.put("orange", ImageIO.read(new File("Images/ghosts/ghostOrange.jpg")));
			ghostImgs.put("pink", ImageIO.read(new File("Images/ghosts/ghostPink.jpg")));
			ghostImgs.put("red", ImageIO.read(new File("Images/ghosts/ghostRed.jpg")));
			ghostImgs.put("azul", ImageIO.read(new File("Images/ghosts/ghostAzure.jpg")));
			ghostImgs.put("blue", ImageIO.read(new File("Images/ghosts/ghostBlue.jpg")));
			ghostImgs.put("white", ImageIO.read(new File("Images/ghosts/ghostWhite.jpg")));
		}catch (Exception e){
			JOptionPane.showMessageDialog(this, "Failed to load ghost images");
		}
	}
	public Dimension getPreferredSize(){return new Dimension(width+70, height);}
    public Dimension getMinimumSize(){return new Dimension(width+70, height);}
    public Dimension getMaximumSize(){return new Dimension(width+70, height);}
    
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	g.drawImage(map, 0, 0, null);
    	for (int y = 0; y < mapArr.length; y++){
    		for (int x = 0; x < mapArr[y].length; x++){
    			switch (mapArr[y][x]){
    				case 'D':
    					{g.drawImage(dotImgs[0], 5+(x*20),5+(y*20),null);
    					break;}
    				case 'A':
    					{g.drawImage(dotImgs[1], 5+(x*20),5+(y*20),null);
    					break;}
    				default:
    					break;
    			}
    		}
    	}
    	for (Ghost gh : ghosts){
    		g.drawImage(ghostImgs.get(gh.color()), gh.getX(), gh.getY(), null);
    	}
    	if (pac.mouth() > 0){
    		int dInt = 0;
        	switch (pac.getD()){
        	case 'U':
    			dInt = 1;
    			break;
    		case 'D':
    			dInt = 3;
    			break;
    		case 'L':
    			dInt = 2;
    			break;
    		case 'R':
    			dInt = 0;
    			break;
    		default:
    			break;
        	}  
    		g.drawImage(pacImgs[pac.mouth()][dInt],pac.getX(), pac.getY(),null);
    	}
    	else
    		g.drawImage(pacImgs[0][0],pac.getX(), pac.getY(),null);
    	if (pause && started){
    		g.fillRect(width/2-50, height/2-50, 100, 60);
    		g.setColor(Color.BLUE);
    		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    		g.drawString("PAUSED", width/2-40, height/2-15);
    	}
    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    	g.drawString("Lives:", width, 20);
    	g.drawString(lives+"", width+20, 40);
    	g.drawString("Score:", width, 60);
    	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    	g.drawString(score + "", width, 80);
		checkScreen();
    }
    public void eat(){
    	if (mapArr[(pac.getY()+5)/20][(pac.getX()+5)/20] == 'D'){
    		mapArr[(pac.getY()+5)/20][(pac.getX()+5)/20] = 'O';
    		score += 100;
    	}
    	if (mapArr[(pac.getY()+5)/20][(pac.getX()+5)/20] == 'A'){
    		mapArr[(pac.getY()+5)/20][(pac.getX()+5)/20] = 'O';
    		for (Ghost gh : ghosts){
    			gh.color("blue");
    		}
    		if (counting){
    			count.end();
    			count = new Countdown();
    			gameRunner.scheduleAtFixedRate(count, 0L, 200);
    		}
    		else{
    			count = new Countdown();
    			gameRunner.scheduleAtFixedRate(count, 0L, 200);
    			counting = true;
    		}
    		score += 100;
    		//TODO insert code here
    	}
    }
    public boolean inMiddle(Ghost ghost){
    	Rectangle r = new Rectangle(9,11, 8,5);
    	int xInd = (ghost.getX()-5)/20;
    	int yInd = (ghost.getY()-5)/20;
    	return r.contains(new Point(xInd, yInd));
    }
    public void reset(){
    	started = false;
    	ghosts[0].setXY(245,285);
		ghosts[1].setXY(245,265);
		ghosts[2].setXY(265,265);
		ghosts[3].setXY(265,285);
		pac = new Pacman();
    }
    public void checkScreen(){
    	boolean gameOver = true;
    	for (char[] r : mapArr){
    		for (char c : r){
    			if (c == 'A' || c == 'D'){
    				gameOver = false;
    				break;
    			}
    		}
    	}
    	if (gameOver){
    		gameRunner.cancel();
    		started = false;
    		pause = true;
    	}
    }
    public void newGame(){
    	lives = 3;
		started = false;
		count = new Countdown();
		counting = false;
		pause = true;
		score = 0;
		speed = 30;
		height = 590;
		width = 530;
		mapArr = new char[29][26];
		pacImgs = new BufferedImage[4][4];
		dotImgs = new BufferedImage[2];
		ghostImgs = new HashMap<String, BufferedImage>();
		ghosts = new Ghost[4];
		loadMap();
		loadDots();
		loadPac();
		loadGhosts();
		pac = new Pacman();
		addKeyListener(new Controller());
		gameRunner = new Timer();
		gameRunner.scheduleAtFixedRate(new pacRunner(), 500L, speed);
		gameRunner.scheduleAtFixedRate(new ghostRunner(), 500L, speed);
		repaint();
    }
    public void scoreOutput(){
    	//TODO write this method
    }
}
