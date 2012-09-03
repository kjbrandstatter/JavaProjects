package tet.ris.game;
//Mike Hubbel
import java.awt.*;
public class Line extends Piece 
{
    public Line()
    {
       super();
    }
    public Line(int w, int z)
    {
        super(w,z);
        blocks[0].set(x,y);
        blocks[1].set(x + 20,y);
        blocks[2].set(x - 20, y);
        blocks[3].set(x + 40, y );
        for (int v = 0; v < blocks.length; v++)
        {
            blocks[v].setColor(Color.green);
        }
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
        if (blocks[3].getX() > x)
            {
                blocks[3].set(blocks[3].getX()-40, blocks[3].getY()-40);
            }
            else if (blocks[3].getX() == x)
            {
                if (blocks[3].getY() < y)
                {
                    blocks[3].set(blocks[3].getX() - 40, blocks[3].getY() + 40);
                }
                else
                {
                    blocks[3].set(blocks[3].getX() + 40, blocks[3].getY() - 40);
                }
            }
            else 
            {
                blocks[3].set(blocks[3].getX()+40, blocks[3].getY()+40);
            }   
    }
}
   