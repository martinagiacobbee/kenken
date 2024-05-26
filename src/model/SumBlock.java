package model;

import java.util.ArrayList;
import java.util.List;

public class SumBlock extends Block {
    private int result;
    private List<Cell> cells;
    private String constraint;

    public SumBlock(int result, String constraint) {
        this.result = result;
        this.cells = new ArrayList<>();
        this.constraint = constraint;
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
    }

    public void removeCell(Cell cell){
        this.cells.remove(cell);
    }

    public void removeAll(){
        cells.clear();
    }

    @Override
    public void setResult(int result) {
        this.result = result;
    }

    public boolean isSatisfied(int[][] grid) {
        int sum = 0;
        for (Cell cell : cells) {
            sum += grid[cell.getRow()][cell.getCol()];
        }
        return sum == result;
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

