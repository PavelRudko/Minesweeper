package logic;

import java.util.Random;

public class Board {

	private static Random random = new Random();
	
	private int rowCount, columnCount, closedLeft, minesCount;
	private boolean isGameOver, isGameStarted;
	private Cell[][] cells;
	
	public Board(int rowCount, int columnCount, int minesCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.minesCount = minesCount;
		
		cells = new Cell[rowCount][columnCount];
		
		for(int r = 0; r < rowCount; r++) {
			for(int c = 0; c < columnCount; c++) {
				cells[r][c] = new Cell();
			}
		}
		
		closedLeft = rowCount * columnCount - minesCount;
	}
	
	public void cellClicked(int row, int column, boolean leftKey) {
		Cell cell = getCell(row, column);
		if(leftKey) {
			
			if(!isGameStarted) {
				isGameStarted = true;
				generate(row, column);
			}
			
			if(cell.isMine() && isGameStarted) {
				showAllMines();
				isGameOver = true; 
				return;
			}
			
			openCell(cell, row, column);
			
			if(isVictory()) {
				showAllMines();
			}
		}
		
		else {
			if(!cell.isClicked()) {
				cell.setFlagged(!cell.isFlagged());
			}
		}
		
	}
	
	public boolean isVictory () {
		return closedLeft == 0;
	}
	
	public boolean isGameOver () {
		return isGameOver;
	}
	
	public Cell getCell(int row, int column) {
		return cells[row][column];
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	
	private void showAllMines () {
		for (int r = 0; r < getRowCount(); r++) {
			for (int c = 0; c < getColumnCount(); c++) {
				Cell cell = getCell(r, c);
				if(cell.isMine()) {
					cell.setClicked(true);
					cell.setFlagged(false);
				}
			}
		}
	}
	
	private void openCell (Cell cell, int row, int column) {
		cell.setClicked(true);
		closedLeft--;
		
		int startR = Math.max(0, row - 1);
		int startC = Math.max(0, column - 1);
		int endR = Math.min(row + 1, getRowCount() - 1);
		int endC = Math.min(column + 1, getColumnCount() - 1);
		int count = 0;
		
		for(int r = startR; r <= endR; r++) {
			for(int c = startC; c <= endC; c++) {
				if(getCell(r, c).isMine()) {
					count++;
				}
			}
		}
		
		cell.setNumber(count);
		if(count != 0) {
			return;
		}
		
		for(int r = startR; r <= endR; r++) {
			for(int c = startC; c <= endC; c++) {
				cell = getCell(r, c);
				if(!cell.isFlagged() && !cell.isClicked()) {
					openCell(cell, r, c);
				}
			}
		}

	}

	private void generate(int row, int column) {
		int cellsCount = getRowCount() * getColumnCount();
		int currentCell = row * getColumnCount() + column;
		
		int[] cells = new int[cellsCount];
		
		for(int i = 0; i < cellsCount; i++) {
			if(i != currentCell) {
				cells[i] = i;
			}
		}
		
		for(int i = 0; i < cellsCount; i++) {
			int j = random.nextInt(cellsCount - 1);
			int tmp = cells[i];
			cells[i] = cells[j];
			cells[j] = tmp;
		}
		
		for(int i = 0; i < minesCount; i++) {
			int r = cells[i] / getColumnCount();
			int c = cells[i] % getColumnCount();
			
			getCell(r, c).setMine(true);
		}
		
	}
	
}
