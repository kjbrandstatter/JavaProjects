package tet.ris.game;
//Mike Hubbel
import java.awt.*;
import java.util.*;
public class Other extends Piece
{
    Random p = new Random();
    public Other()
    {
       super();
    }public Other(int w, int z, int type)
    {
        super(w,z);
        Type = type;
        blocks[0].set(x,y);
        blocks[2].set(x, y + 20);
        if (Type == 0)
        {
            blocks[1].set(x - 20,y);
            blocks[3].set(x + 20, y + 20);
        }
        else
        {
            blocks[1].set(x + 20,y);
            blocks[3].set(x - 20, y + 20);
        }
        for (int v = 0; v < blocks.length; v++)
        {
            blocks[v].setColor(Color.red);
        }
        
    }
    public Other(int w, int z)
    {
        super(w,z);
        blocks[0].set(x,y);
        blocks[2].set(x, y + 20);
        if (p.nextInt(2) == 0)
        {
            blocks[1].set(x - 20,y);
            blocks[3].set(x + 20, y + 20);
            Type = 0;
        }
        else
        {
            blocks[1].set(x + 20,y);
            blocks[3].set(x - 20, y + 20);
            Type = 1;
        }
        for (int v = 0; v < blocks.length; v++)
        {
            blocks[v].setColor(Color.red);
        }
        
    }
    public int getType()
    {
        return Type;
    }
     public void set(int a, int s)
    {
        x = a;
        y = s;
        
    }
    public void rotate()
    {
        for (int q = 1; q < blocks.length-1; q++)
        {
            if (blocks[q].getX() > x)
            {
                blocks[q].set(blocks[q].getX()-20, blocks[q].getY()-20);
            }
            else if (blocks[q].getX() == x)
            {
                if (blocks[q].getY() < y)
                {
                    blocks[q].set(blocks[q].getX() - 20, blocks[q].getY() + 20);
                }
                else
                {
                    blocks[q].set(blocks[q].getX() + 20, blocks[q].getY() - 20);
                }
            }
            else 
            {
                blocks[q].set(blocks[q].getX()+20, blocks[q].getY()+20);
            }   
        }
        if (blocks[3].getY() > y)
        {
            if (blocks[3].getX() > x)
            {
                blocks[3].set(blocks[3].getX(), blocks[3].getY()-40);
            }
            else if (blocks[3].getX() < x)
            {
                blocks[3].set(blocks[3].getX() + 40, blocks[3].getY());
            }
        }
        else
        {
            if (blocks[3].getX() > x)
            {
                blocks[3].set(blocks[3].getX()-40, blocks[3].getY());
            }
            else if (blocks[3].getX() < x)
            {
                blocks[3].set(blocks[3].getX(), blocks[3].getY() + 40);
            }
        }
    }
}