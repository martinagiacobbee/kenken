package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubBlock extends Block implements Serializable {
    private int result;
    private List<Cell> cells;
    private String constraint, op;

    public SubBlock(int result, String constraint, String op) {

        this.op=op;
        this.result = result;
        this.cells = new ArrayList<>();
        this.constraint = constraint;
    }

    @Override
    public String toString(){
        return "Blocco "+this.getClass()+" con vincoli: "+this.constraint+", "+this.result;
    }

    @Override
    public String getOperator(){
        return this.op;
    }

    public boolean isSatisfied(int[][] grid) {

        int i=0;
        Cell cellX = cells.get(i);
        Cell cellY = cells.get(i+1);

        int x = Math.abs(grid[cellX.getRow()][cellX.getCol()]);
        int y = Math.abs(grid[cellY.getRow()][cellY.getCol()]);

        if(x-y == result || y-x == result) return true;
        return false;
    }

    private boolean checkGrid(Cell cell, int[][] grid) {
        int value = grid[cell.getRow()][cell.getCol()];

        for (int i = 0; i < grid.length; i++) {
            // Controllo sulla riga
            if (i != cell.getCol() && grid[cell.getRow()][i] == value) {
                return false;

            }
            // Controllo sulla colonna
            if (i != cell.getRow() && grid[i][cell.getCol()] == value) {
                return false;

            }
        }
        return true;
    }

    @Override
    public String getConstraint(){
        return this.constraint;
    }

    @Override
    public int getResult() {
        return result;
    }

    @Override
    public List<Cell> getCells() {
        return cells;
    }

    @Override
    public void setCells(List<Cell> cells) {
        this.cells = new ArrayList<>(cells);
    }
}
