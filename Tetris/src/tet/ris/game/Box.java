package tet.ris.game;
//Mike Hubbel
import java.awt.*;
public class Box extends Piece 
{
    public Box()
    {
       super();
    }
    public Box(int w, int z)
    {
        super(w,z);
        blocks[0].set(x,y);
        blocks[1].set(x + 20,y);
        blocks[2].set(x, y + 20);
        blocks[3].set(x + 20, y + 20);
        for (int v = 0; v < blocks.length; v++)
        {
            blocks[v].setColor(Color.yellow);
        }
    }
     public void set(int a, int s)
    {
        x = a;
        y = s;
    }
    public void rotate()
    {
    }
}