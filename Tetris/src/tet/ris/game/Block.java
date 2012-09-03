package tet.ris.game;
// Kevin Brandstatter
import java.awt.*;
public class Block
{
    private int x;
    private int y;
    private int numBlocks = 4;
    private int blockHeight;
    private Color color;
    public Block()
    {
        x = 0;
        y = 0;
        numBlocks = 4;
        blockHeight = 19;
        color = Color.black;
    }
    public Block(int w, int z)
    {
        x = w;
        y = z;
        numBlocks = 4;
        blockHeight = 19;
        color = Color.black;
    }
    public void set(int a, int s)
    {
        x = a;
        y = s;
    }
    public void setX(int u)
    {
        x = u;
    }
    public void setY(int p)
    {
        y = p;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void moveL()
    {
        x = x - (blockHeight+1); //edit after canvas
    }
    public void moveR()
    {
        x = x + (blockHeight+1); //edit after canvas
    }
    public void moveD()
    {
        y = y + (blockHeight+1); //edit after canvas
    }
    public void set(Block other)
    {
        x = other.getX();
        y = other.getY();
    }
    public Color getColor()
    {
        return color;
    }
    public void setColor(Color other)
    {
        color = other;
    }
    public int getNumBlocks()
    {
    	return numBlocks;
    }
}

