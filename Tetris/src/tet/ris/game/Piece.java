package tet.ris.game;

public abstract class Piece
{
    protected int x;
    protected int y;
    protected Block [] blocks;
    protected int Type = 0;
    
    public Piece()
    {
        x = 0;
        y = 0;
        blocks = new Block [4];
        for (int x = 0; x < blocks.length; x++)
        {
            blocks[x] = new Block();
        }
    }
    public Piece(int w, int z)
    {
        x = w;
        y = z;
        blocks = new Block [4];
        for (int x = 0; x < blocks.length; x++)
        {
            blocks[x] = new Block();
        }
    }
    public void moveD()
    {
        for (int x = 0; x < blocks.length; x++)
        {
            blocks[x].moveD();
        }
        x = blocks[0].getX();
        y = blocks[0].getY();
    }
    public void moveR()
    {
        for (int x = 0; x < blocks.length; x++)
        {
            blocks[x].moveR();
        }
        x = blocks[0].getX();
        y = blocks[0].getY();
    }
    public void moveL()
    {
        for (int x = 0; x < blocks.length; x++)
        {
            blocks[x].moveL();
        }
        x = blocks[0].getX();
        y = blocks[0].getY();
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public abstract void rotate();
    public Block[] getBlocks()
    {
        return blocks;
    }
    public int getLowestY()
    {
        int lowest = 0;
        for (int a = 0; a < blocks.length; a++)
        {
            if (blocks[a].getY() > lowest)
            {
                lowest = blocks[a].getY();
            }
        }
        return lowest;
    }
    public int getRightX()
    {
        int lowest = 0;
        for (int a = 0; a < blocks.length; a++)
        {
            if (blocks[a].getX() > lowest)
            {
                lowest = blocks[a].getX();
            }
        }
        return lowest;
    }   
    public int getLeftX()
    {
        int lowest = 1000;
        for (int a = 0; a < blocks.length; a++)
        {
            if (blocks[a].getX() < lowest)
            {
                lowest = blocks[a].getX();
            }
        }
        return lowest;
    }
    public int getTopY()
    {
        int lowest = 1000;
        for (int a = 0; a < blocks.length; a++)
        {
            if (blocks[a].getY() < lowest)
            {
                lowest = blocks[a].getY();
            }
        }
        return lowest;
    }
    public void setXY(int q, int w)
    {
        x = q;
        y = w;
    }
    public int getType()
    {
        return Type;
    }
}
