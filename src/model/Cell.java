package model;

import java.io.Serializable;

public class Cell implements Serializable {
    private int riga;
    private int colonna;

    public Cell(int riga, int colonna) {
        if(colonna < 0 || riga < 0) throw new IllegalArgumentException();
        this.riga = riga;
        this.colonna = colonna;
    }

    public int getRow() {
        return riga;
    }
    public int getCol() {
        return colonna;
    }

    public String toString() {
        return "(" + colonna + "," + riga + ")";
    }
}
