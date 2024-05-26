package controller;

import model.Block;
import model.Cell;
import model.Grid;
import view.GridView;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Grid grid; //modello
    private GridView gridView; //vista

    public GameController(Grid grid, GridView gridView) {
        this.grid = grid;
        this.gridView = gridView;
        this.gridView.setController(this);
        this.grid.addObserver(gridView); //potenzialmente inutile: il controller fa tutto
    }

    public void uploadFile(File f){

    }

    public void downloadFile(){

    }

    public void createNewGameClicked(){
            gridView.loadSecondPage();
    }

    public void generateBlock(JTextField operando, JTextField risultato) {
        Block block = Block.createBlock(operando.getText(), Integer.parseInt(risultato.getText()));
        grid.addBlock(block);
    }

    public void initializeBlocks(){
        grid.createRandomBlocks();
        System.out.println(this.grid.toString());
        gridView.loadGamePage();
        //grid.notifyObservers();
    }

    public void addCellInBlock(Cell cell, Block block){
        for(Block b : grid.getBlocks()){
            if(b.equals(block)){
                b.addCell(cell);
            }
        }
    }

    public void addBlockForCell(Cell cell, Block block){
        cell.setBlock(block);
    }

    public void setConstraints(JLayeredPane p){
        gridView.highlightBlocks(grid.getBlocks(),p);
    }

    public boolean check(){
        //Il controllo ha effetto solo se tutte le celle dei blocchi sono state riempite
        for(Block b : grid.getBlocks()){
            System.out.println(b.toString());
            if(!b.isSatisfied(this.grid.getGrid())){
                grid.clear();
                System.out.println("Non soddisfatto");
                return false;
            }
        }
        return true;
    }

    public void setGridValue(int row, int col, int value){
        grid.setGridValue(row, col, value);
        System.out.println("Valore impostato sulla griglia: "+value+" in ("+row+","+col+")");
    }

    public void resetGrid(){
        grid.clear();
    }

    public void solve(){
        this.grid.risolvi();
    }

    public void reset(){
        for(Block b : grid.getBlocks()){
            b.removeAll();
        }
        grid.clear();
        this.gridView.resetGrid();
    }
}
