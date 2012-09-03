package Pacman.game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Executor{
	private JFrame screen;
	private GameScreen game;
	private JMenuBar menus;
	private JMenu functions;
	private JMenu help;
	private JMenuItem newGame, controls, scoring;
	private menuBarListener menuListener;
	public Executor(){
		menuListener = new menuBarListener();
		menus = new JMenuBar();
		functions = new JMenu("Game");
		help = new JMenu("Help");
		scoring = new JMenuItem("Scoring");
		scoring.addActionListener(menuListener);
		controls = new JMenuItem("Controls");
		controls.addActionListener(menuListener);
		newGame = new JMenuItem("New Game  (Ctrl+N)");
		newGame.addActionListener(menuListener);
		functions.add(newGame);
		help.add(controls);
		help.add(scoring);
		menus.add(functions);
		menus.add(help);
		
		screen = new JFrame("Pacman");
		game = new GameScreen();
		Container c = screen.getContentPane();
		c.add(game, "Center");
		screen.setJMenuBar(menus);
		
		
		screen.setLocation(100,100);
		screen.setResizable(false);
		screen.pack();
		screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public static void main(String[] args){
		Executor x = new Executor();
		x.show();
	}
	public void show(){
		screen.setVisible(true);
		while(!game.hasFocus()){
			game.requestFocusInWindow();
		}
	}
	private class menuBarListener implements ActionListener{
		public menuBarListener(){}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newGame){
				game.newGame();
			}
			else if (e.getSource() == controls){
				
			}
			else if (e.getSource() == scoring){
				
			}
		}
	}
}
