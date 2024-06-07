package backtracking;


import model.Block;
import model.Cell;
import model.Grid;

import java.util.*;

public class Backtracking implements Problema<Cell, Integer> {
    private Grid grid;
    private int i = 0, scelta = 0;
    private int size;
    private int[][] soluzione;
    private ArrayList<Cell> cells;
    private boolean[][] initialized;
    private LinkedList<Grid> solList;


    public Backtracking(Grid grid) {
        this.grid = grid;
        this.size = grid.getSize();
        this.soluzione = new int[size][size];
        this.initialized = new boolean[size][size];

        this.solList = new LinkedList<>();

        this.cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells.add(grid.getCell(i, j)); //le inserisco in ordine
                initialized[i][j]=false;
                soluzione[i][j] = 0;
            }
        }

    }

    public LinkedList<Grid> getSolList() {
        return solList;
    }

    @Override
    public Cell primoPuntoDiScelta() {
        i = 0;
        return cells.get(i);
    }

    @Override
    public Cell prossimoPuntoDiScelta(Cell ps) {
        Block init = ps.getBlock();
        List<Cell> celle = init.getCells();
        if(init.isSatisfied(soluzione)){
            //passa al prossimo blocco
            for(Block b : grid.getBlocks()) {
                if(!b.equals(init)){
                    for(int k = 0; k < b.getCells().size(); k++) {
                        if(!initialized[b.getCells().get(k).getRow()][b.getCells().get(k).getCol()]){
                            return b.getCells().get(k);
                        }
                    }
                }
            }
        }
        for(int k = 0; k < celle.size(); k++) {
            if(!initialized[celle.get(k).getRow()][celle.get(k).getCol()]){
                return celle.get(k);
            }
        }
        return ps; //se è l'unico elemento nel blocco ed il blocco non è soddisfatto, bisogna cambiare ps stesso
    }

    @Override
    public boolean ultimoPuntoDiScelta(Cell ps) {
            if(nonInizializzati()>0) return false;
            return true;

    }

    private int nonInizializzati(){
        int init=0;
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(!initialized[i][j]){
                    ++init;
                }
            }
        }
        return init;
    }

    @Override
    public Cell ultimoPuntoDiScelta() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(!initialized[i][j]){
                    int row = i;
                    int col = j;
                    for(Cell c : cells){
                        if(c.getRow() == row && c.getCol() == col){
                            return c;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Integer primaScelta(Cell ps) {
        return 1;
    }

    @Override
    public Integer prossimaScelta(Integer integer) {
        scelta = integer + 1;
        return scelta;

    }

    @Override
    public Integer ultimaScelta(Cell ps) {
        return size;
    }

    private void setAllCellsInValue(Block b, int value){
        for (Cell c : b.getCells()) {
            soluzione[c.getRow()][c.getCol()] = value;
        }
    }

    @Override
    public boolean assegnabile(Integer scelta, Cell puntoDiScelta) {
        int row = puntoDiScelta.getRow();
        int col = puntoDiScelta.getCol();

        // Controllo sulla riga
        for (int i = 0; i < size; i++) {
            if (soluzione[row][i] == scelta) {
                return false;
            }
        }

        // Controllo sulla colonna
        for (int i = 0; i < size; i++) {
            if (soluzione[i][col] == scelta) {
                return false;
            }
        }
        Block b = puntoDiScelta.getBlock();
        if(b.getCells().size()==1){
            if(scelta == b.getResult())
                return true;
        }
        if(b.getOperator().equals("+")){
            int sum=0;
            for (Cell x: b.getCells()) {
                //if (soluzione[x.getRow()][x.getCol()] != 0)
                sum += soluzione[x.getRow()][x.getCol()];

            }
            return sum+scelta<=b.getResult(); //ADDED

        }else if(b.getOperator().equals("-")){
            for(int j=0; j<b.getCells().size()-1; j++) {
                if(soluzione[b.getCells().get(j).getRow()][b.getCells().get(j).getCol()]==0 && soluzione[b.getCells().get(j+1).getRow()][b.getCells().get(j+1).getCol()]==0) return true;
                else if(soluzione[b.getCells().get(j).getRow()][b.getCells().get(j).getCol()]==0) return Math.abs(soluzione[b.getCells().get(j+1).getRow()][b.getCells().get(j+1).getCol()]-scelta)==b.getResult();
                else if( soluzione[b.getCells().get(j+1).getRow()][b.getCells().get(j+1).getCol()]==0) return Math.abs(soluzione[b.getCells().get(j).getRow()][b.getCells().get(j).getCol()]-scelta)==b.getResult();
            }
            return false;

        }else if(b.getOperator().equals("*")){
            int mul=1;
            for (Cell x: b.getCells()) {

                if(soluzione[x.getRow()][x.getCol()]!=0)
                    mul *= soluzione[x.getRow()][x.getCol()];
            }
            return mul*scelta<=b.getResult();

        }else if(b.getOperator().equals("/")){
            for(int j=0; j<b.getCells().size()-1; j++) {
                if(soluzione[b.getCells().get(j).getRow()][b.getCells().get(j).getCol()]==0 && soluzione[b.getCells().get(j+1).getRow()][b.getCells().get(j+1).getCol()]==0) return true;
                else if(soluzione[b.getCells().get(j).getRow()][b.getCells().get(j).getCol()]==0) return ( (soluzione[b.getCells().get(j+1).getRow()][b.getCells().get(j+1).getCol()]/scelta)==b.getResult() || (scelta/soluzione[b.getCells().get(j+1).getRow()][b.getCells().get(j+1).getCol()])==b.getResult());
                else if( soluzione[b.getCells().get(j+1).getRow()][b.getCells().get(j+1).getCol()]==0) return ((soluzione[b.getCells().get(j).getRow()][b.getCells().get(j).getCol()]/scelta)==b.getResult() || (scelta/soluzione[b.getCells().get(j).getRow()][b.getCells().get(j).getCol()])==b.getResult());
            }
            return false;

        }

        return false;
    }


    @Override
    public void assegna(Integer scelta, Cell puntoDiScelta) {
        soluzione[puntoDiScelta.getRow()][puntoDiScelta.getCol()] = scelta;
        initialized[puntoDiScelta.getRow()][puntoDiScelta.getCol()]=true;

    }

    @Override
    public void deassegna(Integer scelta, Cell puntoDiScelta) {
        soluzione[puntoDiScelta.getRow()][puntoDiScelta.getCol()]=0;
        initialized[puntoDiScelta.getRow()][puntoDiScelta.getCol()]=false;
    }

    @Override
    public Cell precedentePuntoDiScelta(Cell puntoDiScelta) {
        List<Cell> celle = puntoDiScelta.getBlock().getCells();
        if(celle.isEmpty() || celle.size()==1){
            for(Block b : grid.getBlocks()) {
                if(!b.equals(puntoDiScelta.getBlock())){
                    for(int k = 0; k < celle.size(); k++) {
                        if(initialized[celle.get(k).getRow()][celle.get(k).getCol()]){
                            initialized[celle.get(k).getRow()][celle.get(k).getCol()]=false;
                            return celle.get(k);
                        }
                    }
                }
            }
        }

        for(int k = 0; k < celle.size(); k++) {
            Cell curr = celle.get(k);
            if(initialized[curr.getRow()][curr.getCol()] && ! curr.equals(puntoDiScelta)){
                initialized[curr.getRow()][curr.getCol()]=false;
                return curr;
            }
        }

        //if(index == celle.size()) return prossimoPuntoDiScelta(puntoDiScelta);
        return puntoDiScelta;
    }

    @Override
    public Integer ultimaSceltaAssegnataA(Cell puntoDiScelta) {
        return soluzione[puntoDiScelta.getRow()][puntoDiScelta.getCol()];
    }

    @Override
    public void scriviSoluzione(int nr_sol) {
        System.out.println("");
        System.out.println("-----INIZIO SOLUZIONE----");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("["+i+"]["+j+"]: "+ soluzione[i][j]);
                System.out.print(" ,");
            }
            System.out.println();
        }
        Grid griglia = new Grid(size);
        griglia.setGrid(soluzione);
        solList.add(griglia);
        System.out.println("-----END SOLUZIONE----");
    }

    public static void main(String[] args) {
        Grid g = new Grid(3);
        Block block = Block.createBlock("+", 3);
        Block block2 = Block.createBlock("/", 2);
        Block block3 = Block.createBlock("+", 4);
        g.addBlock(block);
        g.addBlock(block2);
        g.addBlock(block3);
        g.createRandomBlocks();
        for(Block b : g.getBlocks()){
            System.out.println("Blocco: "+b.toString());
            for(Cell cell : b.getCells()){
                System.out.println("Celle: "+cell.toString());
            }
        }
        Backtracking back = new Backtracking(g);
        back.risolvi(3);
    }
}
