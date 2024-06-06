import backtracking.Backtracking;
import model.Block;
import model.Cell;
import model.Grid;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestResolver {

    @Test
    public void testRandomBlocks(){
        Grid grid = new Grid(3);

        Block block = Block.createBlock("+", 3);
        Block block2 = Block.createBlock("-", 2);
        grid.addBlock(block);
        grid.addBlock(block2);
        grid.createRandomBlocks();
        assertFalse(grid.getBlocks().isEmpty()); //li crea correttamente

        assertTrue(block2.getCells().size()==2); //SubBlock di dimensione sempre 2

    }

    @Test
    public void testBlock(){
        int[][] grid = new int[3][3];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }

        grid[0][0]=2;
        grid[0][1]=1;

        Block block_sum = Block.createBlock("+", 3);
        Cell c1 = new Cell(0,0);
        Cell c2 = new Cell(0,1);
        LinkedList<Cell> cell_sum = new LinkedList<>();
        cell_sum.add(c1);
        cell_sum.add(c2);
        block_sum.setCells(cell_sum);
        assertTrue(block_sum.isSatisfied(grid));

        grid[2][2]=1;
        grid[1][0]=1;

        Block block_sub = Block.createBlock("-", 0);
        Cell c1sub = new Cell(2,2);
        Cell c2sub = new Cell(1,0);
        LinkedList<Cell> cell_sub = new LinkedList<>();
        cell_sub.add(c1sub);
        cell_sub.add(c2sub);
        block_sub.setCells(cell_sub);
        assertTrue(block_sub.isSatisfied(grid));

        grid[0][2]=3;
        grid[1][1]=3;
        grid[1][2]=2;
        Block block_mul= Block.createBlock("*", 18);
        Cell c1mul = new Cell(0,2);
        Cell c2mul = new Cell(1,1);
        Cell c3mul = new Cell(1,2);
        LinkedList<Cell> cell_mul = new LinkedList<>();
        cell_mul.add(c1mul);
        cell_mul.add(c2mul);
        cell_mul.add(c3mul);
        block_mul.setCells(cell_mul);
        assertTrue(block_mul.isSatisfied(grid));

        grid[2][1]=2;
        grid[2][0]=1;
        Block block_div= Block.createBlock("/", 2);
        Cell c1div = new Cell(2,1);
        Cell c2div = new Cell(2,0);
        LinkedList<Cell> cell_div = new LinkedList<>();
        cell_div.add(c1div);
        cell_div.add(c2div);
        block_div.setCells(cell_div);
        assertFalse(block_div.isSatisfied(grid)); //false perchè l'1 è già presente nella colonna 0 (in 1,0)

    }


    @Test
    public void testResolver(){
        Grid g = new Grid(2);
        Block block = Block.createBlock("+", 3);
        Block block2 = Block.createBlock("-", 1);

        g.addBlock(block);
        g.addBlock(block2);
        g.createRandomBlocks();

        Backtracking back = new Backtracking(g);
        back.risolvi(3);
        assertTrue(!back.getSolList().isEmpty()); //esiste sicuramente almeno una soluzione a questa configurazione
    }

}
