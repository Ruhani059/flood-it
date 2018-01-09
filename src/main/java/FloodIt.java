/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Ruhani
 */
public class FloodIt {
    int floodedColor;
    int currentPosition;
    int parent;
    int[][] board;
    int[][] traversed;
    int g;
    int h;
    int f;

    public FloodIt() {
    }

    public FloodIt(int[][] board,int position) {

        this.board = FloodIt.arrayCopy2D(board);
        this.floodedColor = 0;
        int count=0;
        this.traversed = new int[board.length][board.length];        
        
        initializeVisited();
        calculate_G(this.board, this.board[0][0], 0 , 0);
        
        this.g = board.length * board.length - floodedColor;
        this.h = heuristic_1();
        this.f = this.g + this.h;
        this.currentPosition = position;
        this.parent = 0;
    }
    
    public void initializeVisited(){
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j <board.length ; j++) {
                traversed[i][j] = 0;
            }
        }        
    }
    
    public void calculate_G (int[][] array, int color, int row, int col){
        traversed[row][col] = 1;
        this.floodedColor = this.floodedColor  + 1;

        if(row+1 <array.length && traversed[row+1][col]==0) {
            if(array[row+1][col] == color){
                calculate_G(array,color, row+1, col);
            }
        }
    
        if(row-1 >= 0 && traversed[row-1][col]==0) {
            if(array[row-1][col] == color){ 
                calculate_G(array,color, row-1, col);
            }
        }
        
        if(col+1 <array.length && traversed[row][col+1]==0) {
            if(array[row][col+1] == color){ 
                calculate_G(array,color, row, col+1);
            }
        }

        if(col-1 >= 0 && traversed[row][col-1]==0) {
            if(array[row][col-1] == color){
                calculate_G(array,color, row, col-1);
            }
        }
    }

    public boolean isFinish()
    {        
        int temp = this.board[0][0];
        for (int[] board1 : board) {
            for (int c : board1) {
                if (c != temp) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showArray(int[][] temp)
    {        
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                System.out.print(temp[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public int getRow(int position) {
        return position/board.length;
    }
    
    public int getColumn(int position) {
        return position%board.length;
    }

    public int heuristic_1()
    {
        Vector<Integer>vector = new Vector<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(!vector.contains(board[i][j])) {
                    vector.add(board[i][j]);
                }
            }
        }

        return vector.size()-1; // this is  the minimum no of moves required for reaching destination
//        return 0;
    }
    
    public static int [][] arrayCopy2D (int[][] array)
    {
        int[][] newArray = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }

}
