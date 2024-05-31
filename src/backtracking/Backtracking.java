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
    private int[][] soluzione;
    private int numMaxSoluzioni;

    private List<Cell> prossimeScelte;



    public Backtracking(Grid g) {
        this.prossimeScelte= new ArrayList<>();
        this.size = g.getSize();
        this.game = g;
        grid = g.getGrid();
        blocks = new LinkedList<>();
        blocks.addAll(g.getBlocks());

        this.soluzione = new int[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                soluzione[i][j]=0;
            }
        }
    }

    @Override
    protected Cell prossimoPuntoDiScelta(List<Cell> ps, Cell p){
        Cell res = new Cell(0,0);
        if(!prossimeScelte.isEmpty()){
            res = prossimeScelte.getFirst();
            prossimeScelte.remove(res);
        }
        return res;
    }


    @Override
    protected boolean assegnabile(Cell cell, Integer s) {
        /*
        System.out.println("Assegnabile: "+ (grid[cell.getRow()][cell.getCol()] == -1 && isValidValue(cell, s)));
        // Assegna il valore alla cella per il controllo

        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                if(grid[cell.getRow()][cell.getCol()] == -1) {
                    return grid[cell.getRow()][cell.getCol()] == -1 && isValidValue(cell, s);
                }
            }
        }
        System.out.println("Tutte le celle sono state occupate.");
        for(Block block : blocks) {
             //tutte le celle nel blocco sono state assegnate
            if (!block.isSatisfied(grid)) {
                System.out.println("Blocco " + block.toString() + " non soddisfatto ");
                for(Cell c2 : block.getCells()) {
                    deassegna(c2);
                }
                return false;
            }
        }

        return true;
       // return grid[cell.getRow()][cell.getCol()] == -1 && isValidValue(cell, s);*/

        if(isValidValue(cell, s)){
            assegna(cell,s);
            Block block = cell.getBlock();
            if(block.isSatisfied(grid)) return true;
            else{
                deassegna(cell);
                return false;
            }
        }

        return false;
    }

    @Override
    protected void assegna(Cell ps, Integer s) {
        System.out.println("Ho assegnato "+s+" alla cella ("+ps.getRow()+","+ps.getCol()+").");
        game.setGridValue(ps.getRow(), ps.getCol(), s);
        grid[ps.getRow()][ps.getCol()] = s;
        prossimeScelte.remove(ps);
    }

    @Override
    protected void deassegna(Cell ps) {
        System.out.println("Ho deassegnato la cella ("+ps.getRow()+","+ps.getCol()+").");
        game.setGridValue(ps.getRow(), ps.getCol(), -1);
        grid[ps.getRow()][ps.getCol()] = -1;
        prossimeScelte.add(ps);
    }

    @Override
    protected void scriviSoluzione() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                soluzione[i][j] = grid[i][j];
                System.out.print(grid[i][j] + " ,");
            }
            System.out.println();
        }
    }

    public int[][] getSoluzione(){
        return soluzione;
    }

    @Override
    protected boolean esisteSoluzione(Cell cell) {
        for( int i=0; i<size; i++ )
            for(int j=0; j<size; j++)
                if(grid[i][j]==-1)
                    return false;
        //numSoluzioni--;
        return true;
    }

    @Override
    protected boolean ultimaSoluzione(Cell cell) {
        //return numSoluzione<numMaxSoluzioni;
        return numSoluzioni<0;
    }

    @Override
    protected List<Cell> puntiDiScelta() {
        List<Cell> l= new ArrayList<>();
        /*for ( int i= 0; i<size ;i++)
            for(int j=0;j<size;j++)
                l.add(new Cell(i,j));*/
        for(Block b: blocks){
            l.addAll(b.getCells());
            prossimeScelte.addAll(b.getCells());
        }
        return l;
    }

    @Override
    protected Collection<Integer> scelte(Cell cell) {
        Collection<Integer> cells=new ArrayList<>();
        for (int i =-1; i<10;i++)
            cells.add(i+1);
        return cells;
    }


    @Override
    public void risolvi() {
        System.out.println("avvio risoluzione");
        /*Cell c = game.getCell(0,0); //funziona solo se tutta la griglia è occupata dai blocchi
        if (c == null){
            c = new Cell(0, 0); //da togliere quando funziona
        }*/
        Cell c = blocks.getFirst().getCells().getFirst();
        prossimeScelte.remove(c);
        tentativo(puntiDiScelta(), c);
    }

    private boolean isValidValue(Cell cell, int value) {
        // Verifica se il valore è già presente nella stessa riga o colonna
        for (int i = 0; i < grid.length; i++) {
            // Controllo sulla riga
            if (i != cell.getCol() && grid[cell.getRow()][i] == value ) {
                System.out.println("Uguale alla colonna");
                return false;
            }
            // Controllo sulla colonna
            if (i != cell.getRow() && grid[i][cell.getCol()] == value) {
                System.out.println("Uguale alla riga");
                return false;
            }
        }
        return true;

        // Controlla se tutti i blocchi sono soddisfatti con questa assegnazione

        /*for(Block block : blocks) {
            for (Cell c : block.getCells()) {
                if(grid[c.getRow()][c.getCol()] == -1){
                    return false; //prova ad andare avanti

                }else{ //tutte le celle nel blocco sono state assegnate
                    if (!block.isSatisfied(grid)) {
                        System.out.println("Blocco " + block.toString() + " non soddisfatto ");
                        for(Cell c2 : block.getCells()) {
                            deassegna(c2);
                        }
                        return false;
                    }
                }
            }
        }

        return true;*/
    }

    public static void main(String[] args) {
        Grid g = new Grid(3);
        Block block = Block.createBlock("+", 2);
        Block block2 = Block.createBlock("-", 3);
        g.addBlock(block);
        g.addBlock(block2);
        g.createRandomBlocks();
        for(Block b : g.getBlocks()){
            System.out.println("Blocco: "+b.toString());
            for(Cell cell : b.getCells()){
                System.out.println("Celle: "+cell.toString());
            }
        }
        g.risolvi();
    }
}
