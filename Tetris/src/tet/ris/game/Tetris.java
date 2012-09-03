package tet.ris.game;
// Kevin Brandstatter
import java.awt.Container;
import javax.swing.*;
import java.awt.event.*;
@SuppressWarnings("serial")
public class Tetris extends JPanel implements ActionListener, WindowListener
{
    private Canvas canvas;
    private JFrame myFrame;
    private JMenuItem newGame;
    //private JMenuItem Medium;
    //private JMenuItem Fast;
    //private JMenuItem Insane;
    private JMenuItem Con;
    private JMenuItem Sc;
    private JMenu FileM;
    private JMenu HelpMenu;
    //private int choice;
    private JMenuBar menu;
    private Help help;

    public Tetris(int i, int j)
    {
        //try {
        //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //} catch (Exception e) {
        //    @SuppressWarnings("unused")
		//	int dummy = 0;
        //}
        
        myFrame = new JFrame("Tetris");
        help = new Help();
        menu = new JMenuBar();
        FileM = new JMenu("File");
        HelpMenu = new JMenu("Help");
        
        newGame = new JMenuItem("New Game   Ctrl+N");
        newGame.addActionListener(this);
//         
//         Medium = new JMenuItem("Medium");
//         Medium.addActionListener(this);
// 
//         Fast = new JMenuItem("Fast");
//         Fast.addActionListener(this);
// 
//         Insane = new JMenuItem("Insane");
//         Insane.addActionListener(this);
//         
//         
        FileM.add(newGame);
//         SpeedM.add(Medium);
//         SpeedM.add(Fast);
//         SpeedM.add(Insane);
        menu.add(FileM);
        
        
        Con = new JMenuItem("Controls");
        Con.addActionListener(this);
        
        Sc = new JMenuItem("Scoring");
        Sc.addActionListener(this);
        
        HelpMenu.add(Con);
        HelpMenu.add(Sc);
        menu.add(HelpMenu);
        
        myFrame.setJMenuBar(menu);
        
        canvas = new Canvas(i, j);
        Container container = myFrame.getContentPane();
        container.add(canvas, "Center");
        
        myFrame.setResizable(false);
        myFrame.pack();
        myFrame.setLocation(100, 100);
        //myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myFrame.addWindowListener(this);
    }
    public void show()
    {
        myFrame.setVisible(true);
        canvas.requestFocus();
    }
    public void hide()
    {
        myFrame.setVisible(false);
    }
    public static void main(String args[])
    {
        Tetris tetris = new Tetris(320, 520);
        tetris.show();
    }
    public void setSpeed(int newSpeed)
    {
        canvas.setSpeed(newSpeed);
    }
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == newGame)
        {
            canvas.resetGame();
        }
//         else if (e.getSource() == Medium)
//         {
//             canvas.setSpeed(300);
//         }
//         else if (e.getSource() == Fast)
//         {
//             canvas.setSpeed(200);
//         }
//         else if (e.getSource() == Insane)
//         {
//             canvas.setSpeed(100);
//         }
        else if (e.getSource() == Con)
        {
            JOptionPane.showMessageDialog(canvas, help.Controls(), "Controls", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (e.getSource() == Sc)
        {
            JOptionPane.showMessageDialog(canvas, help.Scoring(), "Scoring", JOptionPane.INFORMATION_MESSAGE);
        }
    }
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent q) {
		System.exit(ABORT);
	}
	@Override
	public void windowClosing(WindowEvent p) {
		System.exit(ABORT);
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
