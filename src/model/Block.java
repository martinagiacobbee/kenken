package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Block implements Serializable {
    public abstract boolean isSatisfied(int[][] grid);
    public abstract int getResult();
    public abstract void setResult(int result);
    public abstract List<Cell> getCells();
    public abstract void setCells(List<Cell> cells);
    public abstract String getConstraint();
    public abstract void addCell(Cell cell);
    public abstract void removeCell(Cell cell);
    public abstract void removeAll();

    public boolean equals(Block b) {
        if(!( b instanceof Block)) return false;
        Block b2 = (Block) b;
        for(Cell c : getCells()) {
            if(!b2.getCells().contains(c)) return false;
        }
        return true;
    }

    //Factory method
    public static Block createBlock(String operator, int result) {
        String constraint;
        switch (operator) {
            case "+":
                constraint = result+"+";
                return new SumBlock(result, constraint);
            case "-":
                constraint = result+"-";
                return new SubBlock(result, constraint);
            case "*":
                constraint = result+"*";
                return new MulBlock(result, constraint);
            case "/":
                constraint = result+"/";
                return new DivBlock(result, constraint);
            default:
                throw new IllegalArgumentException("Operatore non valido");
        }
    }


}
