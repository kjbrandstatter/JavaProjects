package tet.ris.game;
//Mike Hubbel
public class Help
{
    private String controls;
    private String scoring;
    public Help()
    {
        controls = "Controls: \n";
        controls += "Left: A or Left arrow\n";
        controls += "Right: D or Right arrow\n";
        controls += "Drop to bottom: W or Up arrow\n"; 
        controls += "Drop Faster: S or Down arrow\n";
        controls += "Rotate: Space bar\n";
        controls += "Pause: P\n";
        
        scoring = "Scoring:\n";
        scoring += "Single: Level * 40\n";
        scoring += "Double: Level * 100\n";
        scoring += "Triple: Level * 300\n";
        scoring += "Tetris: Level * 1200\n";
    }
    public String Controls()
    {
        return controls;
    }
    public String Scoring()
    {
        return scoring;
    }
}
