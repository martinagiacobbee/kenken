package backtracking;

import model.Block;
import model.Cell;
import model.Grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Backtracking extends Problema<Cell, Integer> {
    private int[][] grid;
    private Grid game;
    private List<Block> blocks;
    private int size;
    private int numSoluzioni=1;

    public Backtracking(Grid g) {
        this.size = g.getSize();
        this.game = g;
        grid = new int[size][size];
        blocks = new LinkedList<>();
        blocks.addAll(g.getBlocks());
    }
    @Override
    protected boolean assegnabile(Cell cell, Integer s) {
        return grid[cell.getRow()][cell.getCol()] == 0 && isValidValue(cell, s);
    }

    @Override
    protected void assegna(Cell ps, Integer s) {
        grid[ps.getRow()][ps.getCol()] = s;
    }

    @Override
    protected void deassegna(Cell ps) {
        grid[ps.getRow()][ps.getCol()] = 0;
    }

    @Override
    protected void scriviSoluzione() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
                System.out.println();
            }
        }
    }

    @Override
    protected boolean esisteSoluzione(Cell cell) {
        for( int i=0; i<size; i++ )
            for(int j=0; j<size; j++)
                if(grid[i][j]==0)
                    return false;
        numSoluzioni--;
        return true;
    }

    @Override
    protected boolean ultimaSoluzione(Cell cell) {
        return numSoluzioni<0;
    }

    @Override
    protected List<Cell> puntiDiScelta() {
        List<Cell> l= new LinkedList<>();
        for ( int i= 0; i<size ;i++)
            for(int j=0;j<size;j++)
                l.add(new Cell(i,j));
        return l;
    }

    @Override
    protected Collection<Integer> scelte(Cell cell) {
        Collection<Integer> cells=new ArrayList<>();
        for (int i =0; i<size;i++)
            cells.add(i+1);
        return cells;
    }

    @Override
    protected void risolvi() {
        tentativo(puntiDiScelta(),new Cell(0,0));

    }

    private boolean isValidValue(Cell cell, int value) {
        // Verifica se il valore è già presente nella stessa riga o colonna
        for (int i = 0; i < grid.length; i++) {
            // Controllo sulla riga
            if (grid[cell.getRow()][i] == value) {
                return false;
            }
            // Controllo sulla colonna
            if (grid[i][cell.getCol()] == value) {
                return false;
            }
        }

        // Assegna il valore alla cella per il controllo
        assegna(cell, value);

        // Controlla se tutti i blocchi sono soddisfatti con questa assegnazione
        for (Block block : blocks) {
            if (!block.isSatisfied(grid)) {
                deassegna(cell);
                return false;
            }
        }

        return true;
    }
}
