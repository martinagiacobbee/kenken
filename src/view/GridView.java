package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

public class GridView extends JFrame{
    private JLabel[][] labelGrid; //DA RIMUOVERE

    private int gridSize;
    private JTextField[][] constrainedGrid;
    private GameController controller;
    private int constraints; //numero di blocchi scelto dall'utente
    private Random rand = new Random();
    private JLayeredPane pane;

    public GridView(int gridSize) {
        this.gridSize = gridSize;
        setTitle("KenKen Solver");
        setSize(900,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGamePage();
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void initGamePage(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setVisible(true);
        add(panel);
        JLabel scelta = new JLabel("Seleziona la modalità di gioco:");
        scelta.setBounds(380,200,400,100);
        panel.add(scelta);

        JButton createButton = new JButton("Crea nuovo");
        createButton.setForeground(Color.BLUE);
        createButton.setBounds(250,300,200,80);
        createButton.setBorderPainted(false);
        createButton.setBackground(new Color(64, 181, 231));
        createButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                controller.createNewGameClicked();
            }
        });
        panel.add(createButton);

        JButton fileButton = new JButton("Carica da file");
        fileButton.setForeground(Color.RED);
        fileButton.setBounds(500,300,200,80);
        fileButton.setBorderPainted(false);
        fileButton.setBackground(new Color(225, 196, 255));
        fileButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                controller.createNewGameClicked();
            }
        });
        panel.add(fileButton);
        setVisible(true);
    }

    public void loadSecondPage(){
        getContentPane().removeAll(); // Rimuovi tutto il contenuto attuale dalla finestra
        JPanel p = new JPanel(null);
        add(p);
        p.setVisible(true);
        JTextField numberBlocks = new JTextField("Inserisci il numero di vincoli e premi invio: ");
        numberBlocks.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (numberBlocks.getText().equals("Inserisci il numero di vincoli e premi invio: ")) {
                    numberBlocks.setText("");
                }
            }
        });

        numberBlocks.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               try {
                                                   int num = Integer.parseInt(numberBlocks.getText());
                                                   if (num > gridSize*gridSize || num < 1) {
                                                       JOptionPane.showMessageDialog(p, "Inserisci un numero di vincoli compreso tra 1 e " + gridSize*gridSize, "Dialog",
                                                               JOptionPane.ERROR_MESSAGE);
                                                   } else {
                                                       numberBlocks.addKeyListener(new KeyAdapter() {
                                                           @Override
                                                           public void keyTyped(KeyEvent e) {
                                                               if (e.getKeyChar() == '\n') {
                                                                   e.consume(); // Consuma l'evento "Invio" per prevenire la sua inserzione nel campo di testo
                                                                   numberBlocks.setEditable(false);
                                                               }
                                                               constraints=num;
                                                               config(num);
                                                               //creaBlocco(caselle,risultato, operando);
                                                           }
                                                       });
                                                   }
                                               } catch (NumberFormatException ex) {
                                                   JOptionPane.showMessageDialog(p, "Inserisci un numero valido.", "Dialog",
                                                           JOptionPane.ERROR_MESSAGE);

                                               }
                                           }
        });
        numberBlocks.setBounds(250,100,400,80);
        p.add(numberBlocks);
        setVisible(true);
    }

 //notify when a new block is added
    public void update(Grid grid) {
        //aggiorna la griglia
        /*for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                cells[i][j].setText(grid.getGrid()[i][j] == 0 ? "" : String.valueOf(grid.getGrid()[i][j]));
            }
        }*/
        //highlightBlocks(grid.getBlocks());
    }

    public void highlightBlocks(List<Block> blocks, JLayeredPane p) {
        for (Block block : blocks) {
            Cell first = block.getCells().getFirst();
            int res = block.getResult();
            String op;
            switch (block) {
                case SumBlock sumBlock -> op = "+";
                case SubBlock subBlock -> op = "-";
                case DivBlock divBlock -> op = "/";
                case MulBlock mulBlock -> op = "*";
                default -> {op=null;
                }
            }

            Color blockColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

            for (Cell cell : block.getCells()) {
                labelGrid[cell.getRow()][cell.getCol()].setText(block.getConstraint());
                JTextField cella =  constrainedGrid[cell.getRow()][cell.getCol()];
                cella.setBackground(blockColor);

                //p.add(labelGrid[cell.getRow()][cell.getCol()], JLayeredPane.PALETTE_LAYER);
            }

            add(p);
            //revalidate();
            //repaint();
            setVisible(true);


            //???????constrainedGrid[first.getRow()][first.getCol()] = new JTextField(res+op);

        }
    }

    private void config(int round) {
        // Inizializzazione del pannello
        JPanel p = new JPanel();
        p.setLayout(null);
        JFrame frame = new JFrame("Inizializza i blocchi");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(p);
        frame.setVisible(true);


        // Inizializzazione dei blocchi scelti dall'utente
         if (round>0){
            System.out.println(round);
            JLabel label = new JLabel("Numero di blocchi rimanenti da inizializzare: " + round);
            label.setBounds(320, 100, 400, 30);
            p.add(label);

            JTextField operando = new JTextField("inserisci l'operatore e premi invio(+,-,/ or *)");
            operando.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (operando.getText().equals("inserisci l'operatore e premi invio(+,-,/ or *)")) {
                        operando.setText("");
                    }
                }
            });
            operando.setBounds(320, 150, 245, 30);
            p.add(operando);

            operando.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String op = operando.getText();
                    if (!op.equals("+") && !op.equals("-") && !op.equals("/") && !op.equals("*")) {
                        JOptionPane.showMessageDialog(p, "Inserisci un operatore valido", "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        operando.setEditable(false);
                    }
                }
            });

            JTextField risultato = new JTextField("inserisci il risultato del blocco e premi invio");
            risultato.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (risultato.getText().equals("inserisci il risultato del blocco e premi invio")) {
                        risultato.setText("");
                    }
                }
            });
            risultato.setBounds(320, 200, 245, 30);
            p.add(risultato);

            risultato.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int num = Integer.parseInt(risultato.getText());
                        risultato.setEditable(false);
                        endBlockInit(risultato, operando, round, frame);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(p, "Inserisci un numero valido.", "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
            // Rinfresca il layout del pannello dopo aver aggiunto ogni componente
            p.revalidate();
            p.repaint();
        if(round<=0){
            JLabel label = new JLabel("Fa freddo! Ora puoi chiudere la finestra!");
            label.setBounds(320, 100, 400, 30);
            p.add(label);
            controller.initializeBlocks(); //ora ho tutti le info sui blocchi e posso inizializzarli

        }

    }

    private void endBlockInit(JTextField risultato, JTextField operando, int round, JFrame f) {

        //Il metodo chiama il controller per far generare il blocco corrispondente
        if (!operando.isEditable() && !risultato.isEditable()) {
            controller.generateBlock(operando, risultato);

            risultato.setText("inserisci il risultato del blocco e premi invio");
            risultato.setEditable(true);
            operando.setText("inserisci l'operatore e premi invio(+,-,/ or *)");
            operando.setEditable(true);
            config(--round);
            f.dispose();
        }

    }

    public void loadGamePage(){
        //Finestra di gioco
        getContentPane().removeAll(); // Rimuovi tutto il contenuto attuale dalla finestra
        JLayeredPane p = new JLayeredPane();
        p.setVisible(true);

        p.setLayout(new GridLayout(gridSize, gridSize));
        constrainedGrid = new JTextField[gridSize][gridSize];
        labelGrid = new JLabel[gridSize][gridSize];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                constrainedGrid[row][col] = new JTextField();
                labelGrid[row][col] = new JLabel();
                constrainedGrid[row][col].setHorizontalAlignment(JTextField.CENTER);
                constrainedGrid[row][col].setFont(new Font(constrainedGrid[row][col].getFont().getName(), Font.PLAIN, 20));
                //p.add(labelGrid[row][col], JLayeredPane.PALETTE_LAYER);
                add(constrainedGrid[row][col]);
                p.add(constrainedGrid[row][col], JLayeredPane.DEFAULT_LAYER);



            }
        }
        add(p);
        controller.setConstraints(p);
        //revalidate();
       // repaint();
        //setVisible(true);
    }


    public static void main(String[] args) {
        GridView gridView = new GridView(3);
        Grid grid = new Grid(3);
        GameController controller = new GameController(grid, gridView);
    }
}



