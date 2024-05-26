package model;

import backtracking.Backtracking;
import view.GridView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//model
public class Grid {
    private Random rand = new Random();
    private int size;
    private int[][] grid;
    private List<Block> blocks;
    private static List<GridView> viewObservers;
    private Backtracking resolver;

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < blocks.size(); i++){
            sb.append("Block of class: "+blocks.get(i).getClass().getName());
            sb.append("  ||  Celle: ");
            for(Cell c : blocks.get(i).getCells()){
                sb.append(c.toString()+" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    public Grid(int size) {
        this.size = size;
        this.grid = new int[size][size];
        this.blocks = new LinkedList<Block>();
        this.viewObservers = new LinkedList<>();
    }

    public int getGridValue(int row, int col) {
        return this.grid[row][col];
    }

    public void clear(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                this.grid[i][j] = 0;
            }
        }
    }

    public void setGridValue(int row, int col, int val){
        this.grid[row][col] = val;
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    public void notifyObservers() {
        for(GridView view : viewObservers) {
            view.update(this);
        }
    }

    public void addObserver(GridView view) {
        viewObservers.add(view);
    }

    public int getSize() {
        return size;
    }

    public int[][] getGrid() {
        return grid;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void risolvi(){
        resolver = new Backtracking(this);
        resolver.risolvi();
    }
    public void createRandomBlocks() {
        boolean[][] occupied = new boolean[size][size];

        for (Block block : blocks) {
            int blockSize = rand.nextInt(3) + 2; // Dimensione del blocco tra 2 e 4
            List<Cell> cells = new ArrayList<>();

            // Trova una posizione iniziale non occupata
            int row, col;
            do {
                row = rand.nextInt(size);
                col = rand.nextInt(size);
            } while (occupied[row][col]);

            // Aggiunge la cella di partenza al blocco
            cells.add(new Cell(row, col));
            occupied[row][col] = true;

            // Espande il blocco verso l'alto, il basso, la sinistra o la destra
            //expandBlock(block, occupied, blockSize, cells);
            while (cells.size() < blockSize) {
                Cell lastCell = cells.get(cells.size() - 1);
                int direction = rand.nextInt(4); // 0: su, 1: giù, 2: sinistra, 3: destra
                int newRow = lastCell.getRow();
                int newCol = lastCell.getCol();

                switch (direction) {
                    case 0: // su
                        newRow--;
                        break;
                    case 1: // giù
                        newRow++;
                        break;
                    case 2: // sinistra
                        newCol--;
                        break;
                    case 3: // destra
                        newCol++;
                        break;
                }

                // Verifica se la nuova cella è valida e non occupata
                if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size && !occupied[newRow][newCol]) {
                    cells.add(new Cell(newRow, newCol));
                    occupied[newRow][newCol] = true;
                }
            }

            // Assegna le celle al blocco
            block.setCells(cells);
        }
    }
}

        /*verifica che siano tutte occupate
        while(!allCellsOccupied(occupied)){
            for(Block block : blocks){
                if(!canExpandBlock(block.getCells(), occupied, size)){
                    break;
                    //expandBlock(block, occupied, blocksize, block.getCells());
                }else expandBlock(block, occupied, block.getCells().size(), block.getCells());
            }
        }
    }

    private void expandBlock(Block block, boolean[][] occupied, int blockSize, List<Cell> cells) {
        while (cells.size() < blockSize) {
            Cell lastCell = cells.get(cells.size() - 1);
            int direction = rand.nextInt(4); // 0: su, 1: giù, 2: sinistra, 3: destra
            int newRow = lastCell.getRow();
            int newCol = lastCell.getCol();

            switch (direction) {
                case 0: // su
                    newRow--;
                    break;
                case 1: // giù
                    newRow++;
                    break;
                case 2: // sinistra
                    newCol--;
                    break;
                case 3: // destra
                    newCol++;
                    break;
            }

            // Verifica se la nuova cella è valida e non occupata
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size && !occupied[newRow][newCol]) {
                cells.add(new Cell(newRow, newCol));
                occupied[newRow][newCol] = true;
            }
        }

        // Assegna le celle al blocco
        block.setCells(cells);
    }*/


    // Metodo di supporto per verificare se tutte le celle sono occupate
    /*private boolean allCellsOccupied(boolean[][] occupied) {
        for (int i = 0; i < occupied.length; i++) {
            for (int j = 0; j < occupied[i].length; j++) {
                if (!occupied[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Metodo di supporto per verificare se un blocco può essere espanso
    private boolean canExpandBlock(List<Cell> cells, boolean[][] occupied, int size) {
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();

            if ((row > 0 && !occupied[row - 1][col]) || (row < size - 1 && !occupied[row + 1][col]) ||
                    (col > 0 && !occupied[row][col - 1]) || (col < size - 1 && !occupied[row][col + 1])) {
                return true;
            }
        }
        return false;
    }*/

