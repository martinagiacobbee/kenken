package controller;

import model.Block;
import model.Grid;
import view.GridView;

import javax.swing.*;
import java.io.File;
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

    public void setConstraints(JLayeredPane p){
        this.gridView.highlightBlocks(grid.getBlocks(),p);
    }
}
