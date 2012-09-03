package tet.ris.game;

public class PieceCounter
{
    private int numPieces;
    public PieceCounter()
    {
        numPieces = 0;
    }
    public void addPiece()
    {
        numPieces++;
    }
    public int getPieces()
    {
        return numPieces;
    }
    public void reset()
    {
        numPieces = 0;
    }
}
