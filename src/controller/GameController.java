package controller;

import model.Block;
import model.Grid;
import view.GridView;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;

public class GameController implements Serializable {
    private Grid grid; //modello
    private GridView gridView; //vista
    private static int id;

    public GameController(Grid grid, GridView gridView) {
        this.grid = grid;
        this.gridView = gridView;
        this.gridView.setController(this);

    }

    public void uploadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("games/lastID.txt"));
            id = Integer.parseInt(br.readLine());
            id++;
            PrintWriter pw = new PrintWriter(new FileOutputStream("games/lastID.txt"), true);
            pw.println(id);

            String path = "games/number_" + id + "_GridSize_" + grid.getSize();
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(grid);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createNewGameClicked() {
        gridView.loadSecondPage();
    }

    public void createNewGameFile(File f) {
        //carica grid
        downloadFile(f);

        //ripristino la vista di gioco
        gridView.loadGamePage();
    }

    private void downloadFile(File nomeFile) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile));
            this.grid = (Grid) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            return;
        }
    }

    public void generateBlock(JTextField operando, JTextField risultato) {
        Block block = Block.createBlock(operando.getText(), Integer.parseInt(risultato.getText()));
        grid.addBlock(block);
    }

    public void initializeBlocks() {
        grid.createRandomBlocks();
        gridView.loadGamePage();

    }

    public void setConstraints(JLayeredPane p) {
        gridView.highlightBlocks(grid.getBlocks(), p);
    }

    public boolean check() {
        for (Block b : grid.getBlocks()) {
            if (!b.isSatisfied(this.grid.getGrid())) {
                grid.clear();
                return false;
            }
        }
        return true;
    }

    public void setGridValue(int row, int col, int value) {
        grid.setGridValue(row, col, value);
    }

    public void resetGrid() {
        grid.clear();
    }


    public LinkedList<Grid> solve(int max) {
        return this.grid.risolvi(max);

    }
}
