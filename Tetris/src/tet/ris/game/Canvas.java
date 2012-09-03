package tet.ris.game;
// Kevin Brandstatter
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;

import javax.swing.JPanel;
import java.util.*;
import java.util.jar.JarFile;
import java.io.*;

import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
@SuppressWarnings("serial")
public class Canvas extends JPanel implements KeyListener
{
    private int width;
    private int height;
    private Piece p;
    private Block[][] bottom;
    private Timer timer;
    private int speed;
    private int score;
    private boolean isRunning;
    private Record[] records;
    private boolean paused;
    private boolean HOLD;
    private Piece holdPiece;
    private PieceCounter PC = new PieceCounter();
    private int level = 1;
    private boolean isNewGame = false;
    private boolean CTRL = false;
    private BufferedImage banner, buffer;
    private Graphics2D g2;
    private final String Keyword = "Tetris";
    private int key = 0;
    private String encWord;
    private char[] ABCs = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
    					   'n','o','p','q','r','s','t','u','v','w','x','y','z',+
    					   'A','B','C','D','E','F','G','H','I','J','K','L','M',+
    					   'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    public Canvas(int b, int n)
    {
        width = b;
        height = n;
        speed = 400;
        p = new L(90, 10);
        timer = new Timer(); 
        bottom = new Block[52][22];
        addKeyListener(this);
        score = 0;
        records = new Record[10];
        HOLD = false;
//        try{
//        	File h = new File ("Tetris/tester");
//        	h.createNewFile();
//        }catch (Exception fail){
//        	JOptionPane.showMessageDialog(null, "Fail");
//        }
    try{
    	JarFile tet = new JarFile("Tetris.jar");
    	//InputStream is = this.getClass().getResourceAsStream("Stuff/TetrisLogo.jpg");
    	banner = ImageIO.read(tet.getInputStream(tet.getEntry("TetrisLogo.jpg")));
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = buffer.createGraphics();
        
        g2.drawImage(banner, 0, 0, null);
    }catch (Exception j){
        try 
        {
            banner = ImageIO.read(new File("TetrisLogo.jpg"));
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2 = buffer.createGraphics();
            g2.drawImage(banner, 0, 0, null);
        }catch(IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR: Can't load image data");
        }
    }
//    try{
//    	JarFile tet = new JarFile("Tetris.jar");
//    	Scanner scoreInput = new Scanner(tet.getInputStream(tet.getEntry(".scores")));
//    	Scanner sp;
//    	String dummy;
//    	int fileScore;
//    	String initials;
//    	for (int x = 0; x < records.length; x++)
//    	{
//    		dummy = scoreInput.nextLine();
//    		sp = new Scanner(dummy).useDelimiter("\\s*,\\s*");
//    		fileScore = sp.nextInt();
//    		initials = sp.next();
//    		records[x] = new Record(fileScore, initials);
//    	}
//    }catch(Exception fail){
        File theFile = new File(".scores");
        try {
     	Scanner fileInput = new Scanner(theFile);
      	Scanner stringParse;
        	String dummy = fileInput.nextLine();
        	int fileScore;
        	String initials;
        	stringParse=new Scanner(dummy);
        	encWord = stringParse.next();
        	for (int x = 0; x < records.length; x++)
        	{
        		dummy = fileInput.nextLine();
        		stringParse = new Scanner(dummy).useDelimiter("\\s*,\\s*");
        		fileScore = stringParse.nextInt();
        		initials = stringParse.next();
        		records[x] = new Record(fileScore, initials);
        	}
        } catch(Exception ex) {
       		try {
       			theFile.createNewFile();
       			FileOutputStream fOutput = new FileOutputStream(theFile);
       			PrintStream pOutput = new PrintStream(fOutput);
       			encWord = Keyword;
       			pOutput.println(encWord);
       			for(int x = records.length-1; x >= 0; x--) {
        			records[((records.length-1)-x)] = new Record((x+1) * 10000, "AAA");
        		}
       			encryptOriginal();
        		for(int x = 0; x < records.length; x++) {
        			pOutput.println(records[x].printOutput());
        		}
        		fOutput.close();
        		pOutput.close();
       		} catch (IOException eb) {
       			JOptionPane.showMessageDialog(this, "Cannot create scores file!", "ERROR", JOptionPane.ERROR_MESSAGE);
       		}
        }
//    }
        isRunning = false;
        paused = false;
        newPiece();
    }
    public void setSpeed(int s)
    {
        speed = s;
    }
    public int getSpeed()
    {
        return speed;
    }
    public Block[][] getBottom()
    {
        return bottom;
    }
    public void addToBottom(Piece v)
    {
        for(int b = 0; b < p.getBlocks().length; b++)
        {
            bottom[(p.getBlocks()[b].getY()/10)][(p.getBlocks()[b].getX()/10)] = p.getBlocks()[b];
        }
    }
    public void newPiece()
    {
        Piece m;
        Random r = new Random();
        int j = r.nextInt(5);
        if(j == 0)
        {
            m = new T(90, 10);
        }
        else if(j == 1)
        {
            m = new L(90, 10);
        }
        else if(j == 2)
        {
            m = new Other(90, 10);
        }
        else if(j == 3)
        {
            m = new Box(90, 10);
        }
        else
        {
            m = new Line(90, 10);
        }
        p = m;
        PC.addPiece();
    }
    public int numPieces()
    {
        return PC.getPieces();
    }
    public Piece getPiece()
    {
        return p;
    }
    public int getHeight()
    {
        return height;
    }
    public boolean isFocusable()
    {
        return true;
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(width, height);
    }

    public Dimension getMaximumSize()
    {
        return new Dimension(width, height);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for (int e = 0; e < p.getBlocks().length; e++)
        {
            g.setColor(p.getBlocks()[e].getColor());
            g.fillRect(p.getBlocks()[e].getX(), p.getBlocks()[e].getY(), 19, 19);
        }
        for (int b = 0; b < bottom.length; b++)
        {
            for (int z = 0; z < bottom[b].length; z++)
            {
                if(bottom[b][z] != null)
                {
                    g.setColor(bottom[b][z].getColor());
                    g.fillRect(z*10, b*10, 19, 19);
                }
            }
        }
    }

    public void paintBorder(Graphics g)
    {
        float q = 20;
        g.setColor(Color.black);
        g.drawRect(10, 10, width - 120, height - 20);
        g.drawLine(10, 90, width-110, 90);
        g.setFont(g.getFont().deriveFont(q));
        g.drawString("Score: ", width-100, height/3);
        g.drawString(score + "", width-100, height/3 + 25);
        g.drawString("Level: " + level, width-100, height/5);
        g.drawRect(width-105, height/2+10, 90, 49);
        g.drawString("Hold", width -100, height/2);
        //g.drawRect(width - 105, height/2+60, 90, 190);
        g.drawImage(banner, width - 105, height/2+61, null);
        if (!isRunning)
        {
            g.drawString("Press Enter to start", 20, height/2);
        }
        if (paused)
        {
            g.drawString("Game Paused. ", 40, height/2);
            g.drawString("Press P to resume", 20, height/2 + 25);
        }
        if (HOLD)
        {
            for (int e = 0; e < holdPiece.getBlocks().length; e++)
            {
                g.setColor(holdPiece.getBlocks()[e].getColor());
                g.fillRect(holdPiece.getBlocks()[e].getX(), holdPiece.getBlocks()[e].getY(), 19, 19);
            }
        }
    }

    public void keyPressed(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(p.getLeftX() > 10 && isOpenSpace(0, -20) && !gameOver() && !paused && isRunning)
            {
                p.moveL();
            }
            repaint();
        }
        if (keyevent.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(p.getRightX() < width-130 && isOpenSpace(0, 20) && !gameOver() && !paused && isRunning)
            {
                p.moveR();
            }
            repaint();
        }
        if (keyevent.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if (p.getLowestY() < height-30 && isOpenSpace(20, 0) && !gameOver() && !paused && isRunning)
            {
                p.moveD();
            }
//             if (p.getLowestY() == height-30 || !isOpenSpace(20, 0) && !gameOver())
//             {
//                 addToBottom(p);
//                 checkRows();
//                 newPiece();
//                 timer.scheduleAtFixedRate(new TetrisTimer(p, this), 0L, 400L);
//             }
            repaint();
        }
        if (keyevent.getKeyCode() == KeyEvent.VK_UP)
        {
            while(p.getLowestY() < height-30 && isOpenSpace(20, 0) && !gameOver() && !paused && isRunning)
            {
                p.moveD();
            }
//             if (p.getLowestY() == height-30 || !isOpenSpace(20, 0) && !gameOver())
//             {
//                 addToBottom(p);
//                 checkRows();
//                 newPiece();
//                 timer.scheduleAtFixedRate(new TetrisTimer(p, this), 0L, 400L);
//             }
            repaint();
        }
//         if (keyevent.getKeyCode() == keyevent.VK_UP)
//         {
//             if (canRotate() && !paused)
//             {
//                 p.rotate();
//             }
//             repaint();
//         }
//        if (keyevent.getKeyCode() == KeyEvent.VK_DELETE)
//        {
//            clearBottom();
//           score = score - 100;
//            repaint();
//        }
        if(keyevent.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (gameOver())
            {
                newPiece();
                clearBottom();
                score = 0;
                //setMultiplier();
                isRunning = true;
                HOLD = false;
                isNewGame = false;
                timer.scheduleAtFixedRate(new TetrisTimer(p, this), 0L, speed);
            }
            else if(!isRunning && !paused)
            {
                //setMultiplier();
                isNewGame = false;
                timer.scheduleAtFixedRate(new TetrisTimer(p, this), 0L, speed);
                isRunning = true;
            }
        }
        if(keyevent.getKeyCode() == KeyEvent.VK_CONTROL)
        {
            CTRL = true;
        }
        if (keyevent.getKeyCode() == KeyEvent.VK_N)
        {
            if (CTRL)
            {
                resetGame();
            }
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
        if(keyevent.getKeyCode() == KeyEvent.VK_CONTROL)
        {
            CTRL = false;
        }
    }

    public void keyTyped(KeyEvent keyevent)
    {
        if (keyevent.getKeyChar() == 'a' || keyevent.getKeyChar() == 'A' )
        {
            if(p.getLeftX() > 10 && isOpenSpace(0, -20) && !gameOver() && !paused && isRunning)
            {
                p.moveL();
            }
            repaint();
        }
        if (keyevent.getKeyChar() == 'd' || keyevent.getKeyChar() == 'D')
        {
            if(p.getRightX() < width-130 && isOpenSpace(0, 20) && !gameOver() && !paused && isRunning)
            {
                p.moveR();
            }
            repaint();
        }
        if (keyevent.getKeyChar() == 's' || keyevent.getKeyChar() == 'S')
        {
            if (p.getLowestY() < height-30 && isOpenSpace(20, 0) && !gameOver() && !paused && isRunning)
            {
                p.moveD();
            }
            repaint();
        }
        if (keyevent.getKeyChar() == 'w' || keyevent.getKeyChar() == 'W')
        {
            while(p.getLowestY() < height-30 && isOpenSpace(20, 0) && !gameOver() && !paused && isRunning)
            {
                p.moveD();
            }
//             if (p.getLowestY() == height-30 || !isOpenSpace(20, 0) && !gameOver())
//             {
//                 addToBottom(p);
//                 checkRows();
//                 newPiece();
//                 timer.scheduleAtFixedRate(new TetrisTimer(p, this), 0L, 400L);
//             }
            repaint();
        }
        if (keyevent.getKeyChar() == ' ' || keyevent.getKeyChar() == ' ')
        {
            if (canRotate() && !paused && isRunning)
            {
                p.rotate();
            }
            repaint();
        }
        if (keyevent.getKeyChar() == 'p' || keyevent.getKeyChar() == 'P')
        {
            paused = !paused;
        }
//         if(keyevent.getKeyChar() == 'k' || keyevent.getKeyChar() == 'K')
//         {
//             if (gameOver())
//             {
//                 newPiece();
//                 clearBottom();
//                 score = 0;
//                 setMultiplier();
//                 isRunning = true;
//                 timer.scheduleAtFixedRate(new TetrisTimer(p, this), 0L, speed);
//             }
//             else if(!isRunning && !paused)
//             {
//                 setMultiplier();
//                 timer.scheduleAtFixedRate(new TetrisTimer(p, this), 0L, speed);
//                 isRunning = true;
//             }
//         }
        if (keyevent.getKeyChar() == 'q' || keyevent.getKeyChar() == 'Q')
        {
            if (!HOLD && canSwitch() && isRunning && !paused)
            {
                holdPiece = getInstance(width-80, height/2+15);
                newPiece();
                HOLD = true;
            }
            else if (canSwitch() && isRunning && !paused)
            {
                int xSpot = p.getX();
                int ySpot = p.getY();
                Piece filler = getInstance(width-80, height/2+15);
                p = holdPiece;
                p = getInstance(xSpot, ySpot);
                holdPiece = filler;
            }
            repaint();
        }
    }
    public boolean isOpenSpace(int down, int over)
    {
        for (int e = 0; e < p.getBlocks().length; e++)
        {
            if (bottom[(p.getBlocks()[e].getY()+down)/10][(p.getBlocks()[e].getX()+over)/10] != null)
            {
                return false;
            }
        }
        return true;
    }
    public boolean canRotate()
    {
        Piece n = new Box();
        n = getInstance();
        for(int r = 0; r < n.getBlocks().length; r++)
        {
            n.getBlocks()[r].set(new Block (p.getBlocks()[r].getX(), p.getBlocks()[r].getY()));
        }
        n.setXY(p.getBlocks()[0].getX(), p.getBlocks()[0].getY());
        n.rotate();
        if (n.getRightX() > width-130 || n.getLowestY() > height-30 || n.getLeftX() < 10 || n.getTopY() < 10)
        {
            return false;
        }
        for (int e = 0; e < n.getBlocks().length; e++)
        {
            if (bottom[(n.getBlocks()[e].getY())/10][(n.getBlocks()[e].getX())/10] != null)
            {
                return false;
            }
        }
        return true;
    }
    public boolean canSwitch()
    {
        Piece n = holdPiece;
        if (n instanceof Other)
        {
            n = new Other(p.getX(), p.getY(), (p).getType());
        }
        else if (n instanceof T)
        {
            n = new T(p.getX(), p.getY());
        }
        else if (n instanceof L)
        {
            n = new L(p.getX(), p.getY(), (p).getType());
        }
        else if (n instanceof Box)
        {
            n = new Box(p.getX(), p.getY());
        }
        else
        {
            n = new Line(p.getX(), p.getY());
        }
        if (n.getRightX() > width-130 || n.getLowestY() > height-30 || n.getLeftX() < 10)
        {
            return false;
        }
        for (int e = 0; e < n.getBlocks().length; e++)
        {
            if (bottom[(n.getBlocks()[e].getY())/10][(n.getBlocks()[e].getX())/10] != null)
            {
                return false;
            }

        }
        return true;
    }
    public Piece getInstance()
    {
        Piece n;
        if (p instanceof Other)
        {
            n = new Other();
        }
        else if (p instanceof T)
        {
            n = new T();
        }
        else if (p instanceof L)
        {
            n = new L();
        }
        else if (p instanceof Box)
        {
            n = new Box();
        }
        else
        {
            n = new Line();
        }
        return n;
    }
    public Piece getInstance(int holdW, int holdH)
    {
        Piece n;
        if (p instanceof Other)
        {
            n = new Other(holdW, holdH, ((Other)p).getType());
        }
        else if (p instanceof T)
        {
            n = new T(holdW, holdH);
        }
        else if (p instanceof L)
        {
            n = new L(holdW, holdH, ((L)p).getType());
        }
        else if (p instanceof Box)
        {
            n = new Box(holdW, holdH);
        }
        else
        {
            n = new Line(holdW, holdH);
        }
        return n;
    }
    public boolean gameOver()
    {
        boolean showHighScore = false;
        for (int b = 0; b < 9; b++)
        {
            for (int z = 0; z < bottom[b].length; z++)
            {
                if(bottom[b][z] != null)
                {
                    if (isRunning)
                    {
                        showHighScore = true;
                    }
                    isRunning = false;
//                try{
//                	FileOutputStream fO = new FileOutputStream(new File("Tetris.jar/.scores"));
//                	PrintStream pO = new PrintStream(fO);
//                	JOptionPane.showMessageDialog(null, "Win");
//                	for (int y = 0; y < records.length; y++)
//                    {
//                        if (score > records[y].getScore())
//                        {
//                            String newInitials = JOptionPane.showInputDialog(this, "Enter Initials: ", "New High Score", JOptionPane.PLAIN_MESSAGE);
//                            for (int q = records.length; q > y; q--)
//                            {
//                                if (q < 10)
//                                {
//                                    records[q].setScore(records[q-1].getScore());
//                                    records[q].setInitials(records[q-1].getInitials());
//                                }
//                            }
//                            records[y].setInitials(newInitials);
//                            records[y].setScore(score);
//                            
//                            break;
//                        }
//                    }
//                    for(int x = 0; x < records.length; x++) {
//                        pO.println(records[x].printOutput());
//                    }
//                    pO.close();
//                    fO.close();
//                    
//                }catch (Exception exl){
                    
                    try{
                    	decrypt();
                    }catch(Exception e){
                    	JOptionPane.showMessageDialog(null, "Failed to retrieve scores");
                    }
                    fixScores();
                    String msg = "Score, Initials\n";
                    for(int x = 0; x < records.length; x++) {
                        msg += records[x].printOutput() + "\n";
                    }
                    if(showHighScore)
                    {
                        JOptionPane.showMessageDialog(this, msg, "High Scores", JOptionPane.INFORMATION_MESSAGE);
                    }
                    encrypt();
                    try {
                        File scoreFile = new File(".scores");
                        
                        FileOutputStream fOutput = new FileOutputStream(scoreFile);
                        PrintStream pOutput = new PrintStream(fOutput);
                        pOutput.println(encWord);
                        for(int x = 0; x < records.length; x++) {
                            pOutput.println(records[x].printOutput());
                        }
                        pOutput.close();
                        fOutput.close();
                        
                    } catch (Exception e) {
                        @SuppressWarnings("unused")
						int dummy = 0;
                    }
//                }
                    

                    
                    score = 0;
                    return true;
                }
            }
        }
        return false;
    }
    public void checkRows()
    {
        int multiplier = 0;
        for (int b = 0; b < getBottom().length; b++)
        {
            int bl = 0;
            for (int z = 0; z < getBottom()[b].length; z++)
            {
                if(getBottom()[b][z] != null)
                {
                    bl++;
                }
            }
            if (bl == 10)
            {
                deleteRow(b);
                multiplier++;
            }
        }
        increaseScore(multiplier);
    }
    public void deleteRow(int e)
    {
        for (int l = 0; l < getBottom()[e].length; l++)
        {
            getBottom()[e][l] = null;
        }
        for (int b = e; b >= 0; b--)
        {
            for (int z = 0; z < getBottom()[b].length; z++)
            {
                if(getBottom()[b][z] != null)
                {
                    getBottom()[b][z].moveD();
                    bottom[b+2][z] = bottom[b][z];
                    bottom[b][z] = null;
                }
            }
        }
    }
    public void increaseScore(int c)
    {
        if (c == 1)
        {
            score = score + level * 40;
        }
        else if (c == 2)
        {
            score = score + level * 100;
        }
        else if (c == 3)
        {
            score = score + level * 300;
        }
        else if (c == 4)
        {
            score = score + level * 1200;
        }
    }
    public void clearBottom()
    {
        for (int b = 0; b < getBottom().length; b++)
        {
            for (int z = 0; z < getBottom()[b].length; z++)
            {
                getBottom()[b][z] = null;
            }
        }
    }
    public Record[] getRecord()
    {
        return records;
    }
    //public void setMultiplier()
    //{
       // multiplier = ((400 - speed)/100) + 1;
    //}
    public boolean isPaused()
    {
        return paused;
    }
    public void resetTimer(int newSpeed)
    {
        timer.scheduleAtFixedRate(new TetrisTimer(p, this), 0L, newSpeed);
        PC.reset();
    }
    public void goFaster()
    {
        if(speed > 5)
            speed-=5;
    }
    public void increaseLevel()
    {
        level++;
    }
    public void resetGame()
    {
        score = 0;
        clearBottom();
        holdPiece = null;
        HOLD = false;
        level = 1;
        isRunning = false;
        isNewGame = true;
        PC.reset();
        newPiece();
        repaint();
    }
    public boolean NEWGAME()
    {
        return isNewGame;
    }
    public boolean RUNNING()
    {
        return isRunning;
    }
    private void encrypt(){
    	key = (int) (Math.random() * 51);
    	for(Record r : records){
    		r.encrypt(key);
    	}
    	encryptKeyWord(key);
    }
    private void encryptOriginal(){
    	for(Record r : records){
    		r.encrypt(0);
    	}
    }
    private void decrypt() throws Exception{
    	key = getEncryptionKey();
    	for(Record r : records){
    		r.decrypt(key);
    	}
    }
	private int getEncryptionKey() throws Exception{
		key = 0;
		char keyChar = Keyword.charAt(0);
		int kc = getCharNum(keyChar);
		char testChar = encWord.charAt(0);
		int tc = getCharNum(testChar);
		for(key = 0; key<52; key++){
			int temp = tc-key;
			while (temp<0){
				temp += 52;
			}
			if (temp == kc){
				return key;
			}
		}
		throw new Exception();
	}
	private int getCharNum(char keyChar) {
		for (int x = 0; x<ABCs.length; x++){
			if (ABCs[x] == keyChar){
				return x;
			}
		}
		return 0;
	}
	private void fixScores(){
		for (int y = 0; y < records.length; y++)
        {
            if (score > records[y].getScore())
            {
                String newInitials = JOptionPane.showInputDialog(this, "Enter Initials: ", "New High Score", JOptionPane.PLAIN_MESSAGE);
                for (int q = records.length; q > y; q--)
                {
                    if (q < 10)
                    {
                        records[q].setScore(records[q-1].getScore());
                        records[q].setInitials(records[q-1].getInitials());
                    }
                }
                records[y].setInitials(newInitials);
                records[y].setScore(score);
                
                break;
            }
        }
	}
	private void encryptKeyWord(int k){
		char[] name = Keyword.toCharArray();
		for(int x=0; x<name.length; x++){
			int newChar = getCharNum(name[x]) + k + x;
			while(newChar>=52){
				newChar -= 52;
			}
			name[x] = ABCs[newChar];
		}
		encWord = new String(name);
	}
}

