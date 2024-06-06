package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DivBlock extends Block implements Serializable {
    private int result;
    private List<Cell> cells;
    private String constraint;
    private String op;

    public DivBlock(int result, String constraint, String op) {

        this.op = op;
        this.result = result;
        this.constraint = constraint;
        this.cells = new ArrayList<>();
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

        Cell cell1 = cells.get(0);
        Cell cell2 = cells.get(1);
        int x = grid[cell1.getRow()][cell1.getCol()];
        int y = grid[cell2.getRow()][cell2.getCol()];

        // Il risultato è controllato in ambedue le direzioni (x/y o y/x)
        return ((x != 0 && y != 0) &&
                ((x / y == result && x % y == 0) ||
                        (y / x == result && y % x == 0))) && checkGrid(cell1, grid) && checkGrid(cell2, grid);
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
    public String getConstraint() {
        return constraint;
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
