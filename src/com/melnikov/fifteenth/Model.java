package com.melnikov.fifteenth;

import java.util.Random;

public class Model {

	private int[][] matrix = new int[Constants.BOARD_X][Constants.BOARD_Y];
		
	public Model() {
		initMatrix();
	}
	
	public void initMatrix(){
		
		Random randomizer = new Random();
		int k = 1;
		
		for(int i=0; i<Constants.BOARD_X; i++){
			
			for(int j=0; j<Constants.BOARD_Y; j++){
					
				matrix[i][j] = k++; 
				
			}
		}
		
		
		//Shake matrix
		for(int i=0; i<100; i++){
			
			int fromX = randomizer.nextInt(Constants.BOARD_X);
			int fromY = randomizer.nextInt(Constants.BOARD_Y);
			int toX = randomizer.nextInt(Constants.BOARD_X);
			int toY = randomizer.nextInt(Constants.BOARD_Y);
			
			int tmp = matrix[toX][toY];
			matrix[toX][toY] = matrix[fromX][fromY];
			matrix[fromX][fromY] = tmp;
			
		}
	}

	public Move cellMove(int fromX, int fromY) {

		if((fromX > Constants.BOARD_X-1) || (fromX < 0) || (fromY > Constants.BOARD_Y - 1) || (fromY < 0)){
		
			throw new IllegalArgumentException();
			
		}
		
		int pX = -1;
		int pY = -1;
		
		
		breakpoint2:
		for(int i = 0; i<Constants.BOARD_X; i++){
			
			for(int j = 0; j<Constants.BOARD_Y; j++){
	
				if(matrix[i][j] == Constants.EMPTY_CELL){
					pX = i;
					pY = j;
					break breakpoint2;
				}
				
			}
			
		}
		
		Move move = Move.NULL;
		
		if(fromX + 1 == pX && fromY == pY){
			move = Move.DOWN;
		}else if(fromX - 1 == pX && fromY == pY){
			move = Move.UP;
		}else if(fromY + 1 == pY && fromX ==pX){
			move = Move.RIGHT;
		}else if(fromY -1 == pY && fromX == pX){
			move = Move.LEFT;
		}

		if(move != Move.NULL){
			int tmp = matrix[pX][pY];
			matrix[pX][pY] = matrix[fromX][fromY];
			matrix[fromX][fromY] = tmp;
		}
		
		return move;
		
	}
	
	public boolean checkWin(){
		
		boolean win = true;
		
		int cellValue = 0;
		
		breakpoint1:
		for(int i=0; i<Constants.BOARD_X; i++){

			for(int j=0; j<Constants.BOARD_Y; j++){
				
				if(matrix[i][j]<cellValue){
					win = false;
					break breakpoint1;
				}
				
				cellValue = matrix[i][j];
			}
		}
		
		return win;
	}
	
	public void showMatrix(){
		for(int i = 0; i<Constants.BOARD_X; i++){
			
			for(int j = 0; j<Constants.BOARD_Y; j++){
				
				System.out.print(matrix[i][j]+" ");
				
			}
			
			System.out.print("\n");
		}
	}

	public int[][] getMatrix() {
		return matrix;
	}
	
}
