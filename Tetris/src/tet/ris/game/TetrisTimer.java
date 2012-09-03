package tet.ris.game;
// Kevin Brandstatter 
import java.util.TimerTask;

public class TetrisTimer extends TimerTask
{
    private Piece f;
    private Canvas canvas;
    
    public TetrisTimer(Piece g, Canvas p)
    {
        f = g;
        canvas = p;
        
    }
    public void setPiece(Piece g)
    {
        f = g;
    }
    public void run()
    {
        if (f != canvas.getPiece())
        {
            setPiece(canvas.getPiece());
        }
        if(f.getLowestY() < canvas.getHeight()-30 && canvas.isOpenSpace(20, 0)  && !canvas.isPaused() && canvas.RUNNING())
        {
            f.moveD();
        }
        else if (f.getLowestY() == canvas.getHeight()-30 || !canvas.isOpenSpace(20,0))
        {
            canvas.addToBottom(f);
            canvas.checkRows();
            if(!canvas.gameOver())
            {
                canvas.newPiece();
                setPiece(canvas.getPiece());
            }
        }
        canvas.repaint();
        if(canvas.gameOver())
        {
            super.cancel();
        }
        if (canvas.NEWGAME())
        {
            super.cancel();
        }
        if (canvas.numPieces() == 10)
        {
            if (canvas.getSpeed() > 5)
            {
                super.cancel();
                canvas.goFaster();
                canvas.increaseLevel();
                canvas.resetTimer(canvas.getSpeed());
            }
        }
    }
}
