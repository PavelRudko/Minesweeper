package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JPanel;

import logic.Board;
import logic.Cell;
import util.ImageLoader;

public class GameField extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 40;
	private static final int ROW_COUNT = 9;
	
	@SuppressWarnings("serial")
	private static HashMap<Integer, Color> colors = new HashMap<Integer, Color>() {{
		put(1, Color.GREEN);
		put(2, Color.CYAN);
		put(3, Color.BLUE);
		put(4, Color.ORANGE);
		put(5, Color.PINK);
		put(6, Color.BLACK);
	}};
	
	private BufferedImage cellImage, mineImage, flagImage;
	private Board board;
	
	public GameField() {
		cellImage = ImageLoader.loadImage("cell.png");
		mineImage = ImageLoader.loadImage("mine.png", true);
		flagImage = ImageLoader.loadImage("flag.png", true);
		setSize(CELL_SIZE * ROW_COUNT, CELL_SIZE * ROW_COUNT);
		addMouseListener(this);
		initialize();
	}
	
	public void initialize() {
		board = new Board(ROW_COUNT, ROW_COUNT, 9);
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(board.isGameOver()) {
			return;
		}
		int row = e.getY() / CELL_SIZE;
		int column = e.getX() / CELL_SIZE;
		board.cellClicked(row, column, e.getButton() == MouseEvent.BUTTON1);
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setFont(new Font("Arial", Font.BOLD, (int)(CELL_SIZE * 0.75f)));
		
		for(int r = 0; r < board.getRowCount(); r++) {
			for(int c = 0; c < board.getColumnCount(); c++) {
				Cell cell = board.getCell(r, c);
				drawCell(g, cell, r, c);
			}
		}
		
		if(board.isVictory()) {
			drawCenteredText(g, Color.BLACK, "You win");
		}
		if(board.isGameOver()) {
			drawCenteredText(g, Color.BLACK, "You lose");
		}
	}
	
	private void drawCenteredText(Graphics g, Color color, String text) {
		g.setColor(color);
		FontMetrics fm = g.getFontMetrics();
	    Rectangle2D r = fm.getStringBounds(text, g);
	    int x = (this.getWidth() - (int) r.getWidth()) / 2;
	    int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
	    g.drawString(text, x, y);
	}
	
	private void drawCell(Graphics g, Cell cell, int row, int column) {
		if(!cell.isClicked()) {
			drawImage(g, cellImage, row, column);
			
			if(cell.isFlagged()) {
				drawImage(g, flagImage, row, column);
			}
		}
		else {
			if(cell.isMine()) {
				drawImage(g, mineImage, row, column);
			}
			if(cell.hasNumber()) {
				drawNumber(g, cell.getNumber(), row, column);
			}
		}
	}
	
	private void drawNumber(Graphics g, int number, int row, int column) {		
		g.setColor(getColor(number));	
		int y = row * CELL_SIZE + (int)(CELL_SIZE * 0.75f);
		int x = column * CELL_SIZE + CELL_SIZE / 3;
		g.drawString("" + number, x, y);
	}
	
	private void drawImage(Graphics g, BufferedImage image, int row, int column) {
		g.drawImage(image, column * CELL_SIZE, row * CELL_SIZE, null);
	}
	
	private Color getColor(int number) {
		return colors.containsKey(number) ? colors.get(number) : Color.BLACK;
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	
}
