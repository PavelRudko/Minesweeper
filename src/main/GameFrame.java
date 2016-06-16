package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int MARGIN = 40, BUTTON_WIDTH = 80, BUTTON_HEIGHT = 30;

	public GameFrame() {
		super("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		GameField gameField = new GameField();
		getContentPane().setLayout(null);
		add(gameField);
		gameField.setLocation(0, MARGIN);
		
		setBounds(100, 100, gameField.getWidth() + 6, gameField.getHeight() + MARGIN + 29);
		
		JButton restartButton = new JButton("Restart");
		restartButton.setBounds(getWidth() / 2 - BUTTON_WIDTH / 2, MARGIN / 2 - BUTTON_HEIGHT / 2, 
				BUTTON_WIDTH, BUTTON_HEIGHT);
		add(restartButton);
		
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameField.initialize();
			}
			
		});
	}
	
}
