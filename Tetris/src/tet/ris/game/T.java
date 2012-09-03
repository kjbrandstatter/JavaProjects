package tet.ris.game;
// Kevin Brandstatter
import java.awt.*;
public class T extends Piece 
{
    public T()
    {
        super();
    }
    public T(int w, int z)
    {
        super(w, z);
        blocks[0].set(x, y);
        blocks[1].set(x - 20, y);
        blocks[2].set(x, y + 20);
        blocks[3].set(x + 20, y);
        for (int v = 0; v < blocks.length; v++)
        {
            blocks[v].setColor(Color.blue);
        }
    }
    public void rotate()
    {
        for(int q = 1; q < blocks.length; q++)
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
