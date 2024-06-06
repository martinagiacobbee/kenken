package model;

import backtracking.Backtracking;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//model
public class Grid implements Serializable {
    private Random rand = new Random();
    private int size;
    private int[][] grid;
    private List<Block> blocks;
    private Backtracking resolver;

    private int rimanenti;


    public Grid(int size) {
        this.size = size;
        this.grid = new int[size][size];
        this.rimanenti= size*size;
        this.blocks = new LinkedList<Block>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.size(); i++) {
            sb.append("Block of class: " + blocks.get(i).getClass().getName());
            sb.append("  ||  Celle: ");
            for (Cell c : blocks.get(i).getCells()) {
                sb.append(c.toString() + " ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int getGridValue(int row, int col) {
        return this.grid[row][col];
    }

    public Cell getCell(int row, int col) {
        for (Block b : blocks) {
            for (Cell c : b.getCells()) {
                if (c.getRow() == row && c.getCol() == col) {
                    return c;
                }
            }
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid[i][j] = 0;
            }
        }
    }

    public void setGridValue(int row, int col, int val) {
        this.grid[row][col] = val;
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }

   public void setGrid(int[][] g){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                this.grid[i][j] = g[i][j];
            }
        }
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

    public LinkedList<Grid> risolvi(int max) {
        resolver = new Backtracking(this);
        resolver.risolvi(max);
        return resolver.getSolList();

    }

    public void createRandomBlocks() {
        boolean[][] occupied = new boolean[size][size];
        initGrid();
        int blocchiRimanenti=blocks.size()+1;
        rimanenti = size*size;
        for (Block block : blocks) {
            blocchiRimanenti--;
            int blockSize;
            if(block.getOperator().equals("-")|| block.getOperator().equals("/")){
                blockSize=2; //dimensione di due celle, sempre
            }
            else blockSize = rand.nextInt(4) + 1; // Dimensione del blocco tra 1 e 5

            System.out.println("Blocksize: "+blockSize);
            int limite = (rimanenti/blocchiRimanenti)+1;
            System.out.println("Limite: "+limite);
            while(blockSize>= limite){
                blockSize = rand.nextInt(4) + 1; //rigenera finchè il numero di celle non è accettabile
                System.out.println("Blocksize rigenerato: "+blockSize);
            }
            List<Cell> cells = new ArrayList<>();

            // Trova una posizione iniziale non occupata
            int row, col;
            do {
                row = rand.nextInt(size);
                col = rand.nextInt(size);
            } while (occupied[row][col]);

            // Aggiunge la cella di partenza al blocco
            Cell starter = new Cell(row, col);
            starter.setBlock(block);
            cells.add(starter);

            occupied[row][col] = true;

            int iter=0;
            // Espande il blocco verso l'alto, il basso, la sinistra o la destra
            while (cells.size() < blockSize) {
                iter++;
                if(iter>20){
                    System.out.println("Sono oberato.");
                    rimanenti=1; //assunzione forte
                    break;
                }
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
                    Cell c = new Cell(newRow, newCol);
                    cells.add(c);
                    c.setBlock(block);
                    occupied[newRow][newCol] = true;
                }
            }

            // Assegna le celle al blocco
            block.setCells(cells);
            rimanenti -= blockSize;
        }
        System.out.println("Ho terminato l'inizializzazione.");

        for (Block b : blocks) {
            if(rimanenti != 0 && !b.getOperator().equals("-") && !b.getOperator().equals("/")) {
                //non posso espandere SubBlock nè DivBlock
                expandBlock(b, occupied, b.getCells()); //tenta di espandere un blocco per volta
            }
        }

    }

    private void initGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = -1;
            }
        }
    }


    private void expandBlock(Block block, boolean[][] occupied, List<Cell> cells) {
        int dim = block.getCells().size()+rimanenti;
        int lunghezza = block.getCells().size();
        System.out.println("Lunghezza: "+lunghezza+" Rimanenti: "+rimanenti);

        do{
            //System.out.println("Lunghezza: "+lunghezza);
            Cell lastCell = cells.get(cells.size() - 1);
            int newRow = lastCell.getRow();
            int newCol = lastCell.getCol();

            for(int i=0; i<size; i++) {
                if(!occupied[newRow][i]) {
                    Cell cell = new Cell(newRow, i);
                    occupied[newRow][i] = true;
                    cell.setBlock(block);
                    block.getCells().add(cell);
                    lunghezza++;
                    System.out.println("Ho espanso sulla riga");
                    //break;
                }
                else if(!occupied[i][newCol]) {
                    Cell cell = new Cell(i, newCol);
                    occupied[i][newCol] = true;
                    cell.setBlock(block);
                    block.getCells().add(cell);
                    lunghezza++;
                    System.out.println("Ho espanso sulla colonna");
                    //break;
                }
            }
            //non c'erano blocchi vicini disponibili: espando dove capita
            for(int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    if(!occupied[i][j]) {
                        Cell cell = new Cell(i, j);
                        cell.setBlock(block);
                        occupied[i][j] = true;
                        block.getCells().add(cell);
                        lunghezza++;
                        System.out.println("Ho espanso per disperazione");
                    }
                }
            }
        }while (lunghezza < dim);
        rimanenti=0;
    }

}




