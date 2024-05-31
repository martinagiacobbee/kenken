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

    public String toString(){
        return "Blocco "+this.getClass()+" con vincoli: "+this.constraint+", "+this.result;
    }

    @Override
    public void setResult(int result) {
        this.result = result;
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

        Cell first = cells.getFirst();
        int sub = grid[first.getRow()][first.getCol()];
        for(int i=1; i<cells.size(); i++){
            int value = grid[cells.get(i).getRow()][cells.get(i).getCol()];
            sub-=value;
            if(!checkGrid(cells.get(i), grid)) return false;
        }
        return sub == result;
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
