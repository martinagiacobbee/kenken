package model;

import java.util.ArrayList;
import java.util.List;

public class DivBlock extends Block {
    private int result;
    private List<Cell> cells;
    private String constraint;

    public DivBlock(int result, String constraint) {
        this.result = result;
        this.constraint = constraint;
        this.cells = new ArrayList<>();
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
    }

    public void removeCell(Cell cell){
        this.cells.remove(cell);
    }

    public void removeAll(){
        this.cells.clear();
    }

    public boolean isSatisfied(int[][] grid) {
        /*if (cells.size() != 2) {
            throw new IllegalStateException("DivBlock must contain exactly 2 cells");
        }*/

        Cell cell1 = cells.get(0);
        Cell cell2 = cells.get(1);
        int x = grid[cell1.getRow()][cell1.getCol()];
        int y = grid[cell2.getRow()][cell2.getCol()];

        // Check if division in either direction satisfies the result
        return (x != 0 && y != 0) &&
                ((x / y == result && x % y == 0) ||
                        (y / x == result && y % x == 0));
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
    public void setResult(int result) {
        this.result = result;
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
