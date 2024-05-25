package model;

import java.util.ArrayList;
import java.util.List;

public class SubBlock extends Block {
    private int result;
    private List<Cell> cells;
    private String constraint;

    public SubBlock(int result, String constraint) {
        this.result = result;
        this.cells = new ArrayList<>();
        this.constraint = constraint;
    }
    @Override
    public void setResult(int result) {
        this.result = result;
    }

    public void addCell(int row, int col) {
        this.cells.add(new Cell(row, col));
    }

    public boolean isSatisfied(int[][] grid) {
        int sub = 0;
        for (Cell cell : cells) {
             sub-= grid[cell.getRow()][cell.getCol()];
        }

        return Math.abs(sub) == result;
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
        this.cells = List.copyOf(cells);
    }
}
