/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Priority;
import java.lang.Integer;

/**
 *
 * @author Ruhani
 */
public class MainClass {
    ArrayList<FloodIt> solution; //stores the sequence of boards upto solution
    int minMove; //the number of moves
    Vector<Integer>notCouple;
    Vector<Integer>couple;
    Vector<Integer>chosenColor;
    int[][] visited;

    public MainClass() {

        minMove = -1;
        solution = new ArrayList<FloodIt>();
        notCouple = new Vector<>();
        couple = new Vector<>();
        chosenColor = new Vector<>();
        
    }

    public int moves() 			 // Returns the minimum number of moves to solve
    {							 // initial board
        return minMove;
    }

    public ArrayList<FloodIt> solution() // sequence of boards in the
    {									// shortest solution
        return this.solution;
    }

                
    public void beginGame(int [][] inputArray) {

        Vector<Integer>positionVisitedVector = new Vector<>();                
        
        PriorityQueue<FloodIt>priorityQueue = new PriorityQueue<FloodIt>(50, new  Comparator<FloodIt>(){
            @Override
            public int compare(FloodIt o1, FloodIt o2) {
                return o1.f - o2.f;
            }
        });

            priorityQueue.add(new FloodIt(inputArray, 0));
            positionVisitedVector.add(0);

            while (!priorityQueue.isEmpty()) {
                //Board node = Pop(PQ)
                FloodIt currentFloodIt = priorityQueue.poll();

                if (currentFloodIt != null) {

//                        System.out.println("Pos = "+currentFloodIt.currentPosition+" Parent = "+currentFloodIt.parent+" g = "+currentFloodIt.g+" h = "+currentFloodIt.h+" f = "+currentFloodIt.f);
//                        currentFloodIt.showArray(currentFloodIt.board);

                    minMove++;
                    solution.add(currentFloodIt);
                    chosenColor.add(currentFloodIt.board[currentFloodIt.getRow(currentFloodIt.currentPosition)][currentFloodIt.getColumn(currentFloodIt.currentPosition)]);
                    

                    if(currentFloodIt.isFinish()) {
//                            System.out.println("Finished");
//                            currentFloodIt.showArray(currentFloodIt.board);
                        break;
                    }

                    notCouple.clear();
                    couple.clear();
                    for (int i = 0; i <currentFloodIt.board.length ; i++) {
                        for (int j = 0; j <currentFloodIt.board.length ; j++) {
                            visited[i][j] = 0;
                        }
                    }
//                        System.out.println("Calling Recur");
                    recur(currentFloodIt.board, currentFloodIt.board[0][0], 0, 0);
                    for (int next : notCouple) {
//                            System.out.println(next);
                        int[][] temp = new int[currentFloodIt.board.length][currentFloodIt.board.length];
                        temp = FloodIt.arrayCopy2D(currentFloodIt.board);
                        for (int tempPos : couple) {
                            temp[tempPos/currentFloodIt.board.length][tempPos%currentFloodIt.board.length] = temp[next/currentFloodIt.board.length][next%currentFloodIt.board.length];
                        }
//                            recur(temp, temp[0][0], temp[next/currentFloodIt.board.length][next%currentFloodIt.board.length], 0, 0);
//                           currentFloodIt.showArray(temp);
                        priorityQueue.add(new FloodIt(temp, next));

                    }
                    couple.clear();
                    notCouple.clear();

                }      
            }


                //for bonus - show_result_in_gui(solution);
                 
            // solve a slider puzzle, should take
            //  input from file

    }    
    
    
    public void recur (int[][] array, int color, int newColor, int row, int col){
 //       System.out.println("("+row+","+col+")");
        visited[row][col] = 1;
        array[row][col] = newColor;
        FloodIt fi = new FloodIt();
 //       fi.showArray(array);

        if(row+1 <array.length && visited[row+1][col]==0) {
            if(array[row+1][col] == color || array[row+1][col] == newColor){
//                array[row+1][col] = newColor;
                recur(array,color,newColor, row+1, col);            
            }
        }
    
        if(row-1 >= 0 && visited[row-1][col]==0) {
            if(array[row-1][col] == color || array[row-1][col] == newColor){
//                array[row-1][col] = newColor;
                recur(array,color,newColor,row-1, col);            
            }
        }
        
        if(col+1 <array.length && visited[row][col+1]==0) {
            if(array[row][col+1] == color || array[row][col+1] == newColor){
//                array[row][col+1] = newColor;
                recur(array,color,newColor, row, col+1);            
            }
        }

        if(col-1 >= 0 && visited[row][col-1]==0) {
            if(array[row][col-1] == color || array[row][col-1] == newColor){
//                array[row][col-1] = newColor;
                recur(array,color,newColor,row, col-1);            
            }
        }
    }

    public static int getPos(int row, int col, int size) {
        return row*size+col;
    }

    public void recur (int[][] array, int color, int row, int col){
//        System.out.println("("+row+","+col+")");
        visited[row][col] = 1;
        couple.add(getPos(row, col, array.length));
//        array[row][col] = newColor;
//        FloodIt fi = new FloodIt();
//        fi.showArray(array);

        if(row+1 <array.length && visited[row+1][col]==0) {
            if(array[row+1][col] != color){
                notCouple.add(getPos(row+1, col, array.length));
                visited[row+1][col] = 1;
            }            
            else{ 
                recur(array,color, row+1, col);
//                couple.add(getPos(row+1, col, array.length));
            }
        }
    
        if(row-1 >= 0 && visited[row-1][col]==0) {
            if(array[row-1][col] != color){ 
                notCouple.add(getPos(row-1, col, array.length));
                visited[row-1][col] = 1;
            }            
            else{ 
                recur(array,color, row-1, col);
//                couple.add(getPos(row-1, col, array.length));
            }
        }
        
        if(col+1 <array.length && visited[row][col+1]==0) {
            if(array[row][col+1] != color){ 
                notCouple.add(getPos(row, col+1, array.length));
                visited[row][col+1] = 1;
            }            
            else{ 
                recur(array,color, row, col+1);
//                couple.add(getPos(row, col+1, array.length));
            }
        }

        if(col-1 >= 0 && visited[row][col-1]==0) {
            if(array[row][col-1] != color){
                notCouple.add(getPos(row, col-1, array.length));
                visited[row][col-1] = 1;
            }
            else{ 
                recur(array,color, row, col-1);
//                couple.add(getPos(row, col-1, array.length));
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("your Application is Starting... Please Wait a moment.");
        new MainClass().processInput();
    }

    public void processInput() {
        InputStream inputStream = this.getClass().getResourceAsStream("input.txt");
        Scanner in = new Scanner(inputStream);
        while(true)
        {
            int N = in.nextInt();
            if(N==0) return;

            MainClass solver = new MainClass();
//                solver.processInput();
            solver.visited = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    solver.visited[i][j] = 0;
                }
            }

            int[][] inputArray = new int[N][N];
            for (int i = 0; i < N; i++){
                for (int j = 0; j < N; j++){
                    inputArray[i][j] = in.nextInt();
                }
            }
            solver.beginGame(inputArray);
            System.out.println("Minimum number of moves = " + solver.moves());
            ArrayList<FloodIt> solutions = solver.solution();
            System.out.print("Color Chosen Sequence : ");
            solver.chosenColor.removeElementAt(0);
            for (int color : solver.chosenColor) {
                System.out.print(color+" ");
            }
            System.out.println("");
            for (FloodIt solution : solutions) {
                solution.showArray(solution.board);
            }
        }
    }

}
