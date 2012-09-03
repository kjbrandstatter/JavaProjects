package tet.ris.game;
//Mike Hubbel
import java.util.*;
import java.awt.*;
public class L extends Piece 
{
    Random p = new Random();
    private int Type;
    public L()
    {
       super();
    }
    public L(int w, int z, int type)
    {
        super(w,z);
        Type = type;
        Color crazy = new Color(255, 100, 239);
        blocks[0].set(x,y);
        blocks[1].set(x-20,y);
        blocks[2].set(x + 20, y);
        if(type == 0)
        {
            blocks[3].set(x + 20, y + 20);
        }
        else
        {
            blocks[3].set(x - 20, y + 20);
        }
        for (int v = 0; v < blocks.length; v++)
        {
            blocks[v].setColor(crazy);
        }
    }
    public L(int w, int z)
    {
        super(w,z);
        Color crazy = new Color(255, 100, 239);
        blocks[0].set(x,y);
        blocks[1].set(x-20,y);
        blocks[2].set(x + 20, y);
        if(p.nextInt(2) == 0)
        {
            blocks[3].set(x + 20, y + 20);
            Type = 0;
        }
        else
        {
            blocks[3].set(x - 20, y + 20);
            Type = 1;
        }
        for (int v = 0; v < blocks.length; v++)
        {
            blocks[v].setColor(crazy);
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
    }
}
