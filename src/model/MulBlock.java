package model;

import java.util.ArrayList;
import java.util.List;

public class MulBlock extends Block {
    private int result;
    private List<Cell> cells;
    private String constraint;

    public MulBlock(int result, String constraint) {
        this.result = result;
        this.cells = new ArrayList<>();
        this.constraint = constraint;
    }

    public void addCell(int row, int col) {
        this.cells.add(new Cell(row, col));
    }



    @Override
    public boolean isSatisfied(int[][] grid) {
        int product = 1;
        for (Cell cell : cells) {
            product *= grid[cell.getRow()][cell.getCol()];
        }
        return product == result;
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
    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public List<Cell> getCells() {
        return cells;
    }

    @Override
    public void setCells(List<Cell> cells) {
        this.cells = List.copyOf(cells);
    }
}

