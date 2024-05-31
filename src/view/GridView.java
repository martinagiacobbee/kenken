package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Random;

public class GridView extends JFrame {


    private int gridSize;
    private JTextField[][] constrainedGrid;
    private GameController controller;
    private int[][] grid;
    private int constraints; //numero di blocchi scelto dall'utente
    private Random rand = new Random();


    public GridView(int[][] grid) {
        this.gridSize = grid.length;
        this.grid = grid;
        setTitle("KenKen Solver");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGamePage();
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void initGamePage() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setVisible(true);
        add(panel);
        JLabel scelta = new JLabel("Seleziona la modalità di gioco:");
        scelta.setBounds(380, 200, 400, 100);
        panel.add(scelta);

        JButton createButton = new JButton("Crea nuovo");
        createButton.setForeground(Color.BLUE);
        createButton.setBounds(250, 300, 200, 80);
        createButton.setBorderPainted(false);
        createButton.setBackground(new Color(64, 181, 231));
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.createNewGameClicked();
            }
        });
        panel.add(createButton);

        JButton fileButton = new JButton("Carica da file");
        fileButton.setForeground(Color.RED);
        fileButton.setBounds(500, 300, 200, 80);
        fileButton.setBorderPainted(false);
        fileButton.setBackground(new Color(225, 196, 255));
        fileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                File f = fileChooser.getSelectedFile();
                controller.createNewGameFile(f);
            }
        });
        panel.add(fileButton);
        setVisible(true);
    }

    public void loadSecondPage() {
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
                    if (num >= gridSize*gridSize || num < 1) {
                        JOptionPane.showMessageDialog(p, "Inserisci un numero di vincoli compreso tra 1 e " + (gridSize-1), "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        numberBlocks.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyTyped(KeyEvent e) {
                                if (e.getKeyChar() == '\n') {
                                    e.consume(); // Consuma l'evento "Invio" per prevenire la sua inserzione nel campo di testo
                                    numberBlocks.setEditable(false);
                                }
                                constraints = num;
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
        numberBlocks.setBounds(250, 100, 400, 80);
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


    public void loadGamePage() {
        //Finestra di gioco

        getContentPane().removeAll(); // Rimuovi tutto il contenuto attuale dalla finestra
        JLayeredPane p = new JLayeredPane();
        p.setVisible(true);

        constrainedGrid = new JTextField[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JTextField casella = new JTextField();
                constrainedGrid[i][j] = casella;
                int row= i;
                int col = j;
                casella.setFont(new Font(casella.getFont().getName(), Font.PLAIN, 20));
                casella.setHorizontalAlignment(JTextField.CENTER);
                casella.addActionListener(new ActionListener() {
                                              @Override
                                              public void actionPerformed(ActionEvent e) {
                                                  try {
                                                      int num = Integer.parseInt(casella.getText());
                                                      casella.setEditable(false);

                                                      //imposto i valori sulla griglia della view e ne chiamo l'aggiunta su quella del model tramite controller
                                                      grid[row][col] = Integer.parseInt(casella.getText());
                                                      controller.setGridValue(row,col, grid[row][col]);
                                                  } catch (NumberFormatException ex) {
                                                      JOptionPane.showMessageDialog(p, "Inserisci un numero valido.", "Dialog",
                                                              JOptionPane.ERROR_MESSAGE);
                                                  }
                                              }
                                          });
                casella.setBounds(100 - (gridSize * 2) + j * 300 / gridSize, 160 - (gridSize * 2) + i * 300 / gridSize, 300 / gridSize, 300 / gridSize);
                p.add(casella, JLayeredPane.DEFAULT_LAYER);
            }
        }
        controller.setConstraints(p);

        JButton check = new JButton("check");
        check.setBounds(600, 180, 100, 50);
        Color checkColor = check.getBackground();
        check.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(controller.check()){
                    check.setBackground(Color.GREEN);
                    revalidate();
                    repaint();

                }else{
                    check.setBackground(Color.RED);
                    revalidate();
                    repaint();
                    controller.resetGrid(); //elimino tutto: ricomincia
                }
            }

        });

        JButton pulisci = new JButton("clear all");
        pulisci.setBounds(600, 240, 100, 50);
        pulisci.addMouseListener(new MouseAdapter() {
                                     @Override
                                     public void mouseClicked(MouseEvent e) {
                                         resetGrid();
                                         check.setBackground(checkColor);
                                         controller.resetGrid();
                                     }
                                 });

        JButton soluzione = new JButton("solve");
        soluzione.setBounds(600, 300, 100, 50);
        soluzione.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetGrid(); //elimino tutta la griglia: va risolto da zero
                controller.resetGrid();
                int [][] soluzione = controller.solve();
                if(!(soluzione==null)) showSolution(soluzione);
                else{
                    JOptionPane.showMessageDialog(p, "Nessuna soluzione disponibile", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton upload = new JButton("Save");
        upload.setBounds(600, 360, 100, 50);
        upload.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        controller.uploadFile();
                                    }
                                });

        add(upload);
        add(check);
        add(pulisci);
        add(soluzione);
        add(p);
        revalidate();
        repaint();
        setVisible(true);
    }

    public void loadViewFromFile(Grid g){

        getContentPane().removeAll(); // Rimuovi tutto il contenuto attuale dalla finestra
        JLayeredPane p = new JLayeredPane();
        p.setVisible(true);

        constrainedGrid = new JTextField[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JTextField casella = new JTextField();
                constrainedGrid[i][j] = casella;
                int row= i;
                int col = j;
                casella.setFont(new Font(casella.getFont().getName(), Font.PLAIN, 20));
                casella.setHorizontalAlignment(JTextField.CENTER);
                casella.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int num = Integer.parseInt(casella.getText());
                            casella.setEditable(false);

                            //imposto i valori sulla griglia della view e ne chiamo l'aggiunta su quella del model tramite controller
                            grid[row][col] = Integer.parseInt(casella.getText());
                            controller.setGridValue(row,col, grid[row][col]);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(p, "Inserisci un numero valido.", "Dialog",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                casella.setBounds(100 - (gridSize * 2) + j * 300 / gridSize, 160 - (gridSize * 2) + i * 300 / gridSize, 300 / gridSize, 300 / gridSize);
                p.add(casella, JLayeredPane.DEFAULT_LAYER);
            }
        }
        highlightBlocks(g.getBlocks(),p); //non c'è bisogno di far mediare il controller

        JButton check = new JButton("check");
        check.setBounds(600, 180, 100, 50);
        Color checkColor = check.getBackground();
        check.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(controller.check()){
                    check.setBackground(Color.GREEN);
                    revalidate();
                    repaint();

                }else{
                    check.setBackground(Color.RED);
                    revalidate();
                    repaint();
                    controller.resetGrid(); //elimino tutto: ricomincia
                }
            }

        });

        JButton pulisci = new JButton("clear all");
        pulisci.setBounds(600, 240, 100, 50);
        pulisci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetGrid();
                check.setBackground(checkColor);
                controller.resetGrid();
            }
        });

        JButton soluzione = new JButton("solve");
        soluzione.setBounds(600, 300, 100, 50);
        soluzione.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetGrid(); //elimino tutta la griglia: va risolto da zero
                controller.resetGrid();
                int [][] soluzione = controller.solve();
                if(!(soluzione==null)) showSolution(soluzione);
                else{
                    JOptionPane.showMessageDialog(p, "Nessuna soluzione disponibile", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(check);
        add(pulisci);
        add(soluzione);
        add(p);
        revalidate();
        repaint();
        setVisible(true);
    }

    public void showSolution(int[][] soluzione) {

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(gridSize, gridSize));
        JFrame frame = new JFrame("Soluzione");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
        JLabel[][] sol = new JLabel[gridSize][gridSize];
        for(int i=0;i<gridSize;i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
                sol[i][j] = new JLabel();
                sol[i][j].setText(soluzione[i][j]+"");
                sol[i][j].setFont(new Font(sol[i][j].getFont().getName(), Font.PLAIN, 20));
                sol[i][j].setHorizontalAlignment(JTextField.CENTER);
                p.add(sol[i][j]);
            }
        }

        frame.add(p);
        revalidate();
        repaint();
    }

    public void highlightBlocks(List<Block> blocks, JLayeredPane p) {
        for (Block block : blocks) {
            Cell first = block.getCells().getFirst();

            Color blockColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

            JLabel vincolo = new JLabel(block.getConstraint());
            JTextField casella = constrainedGrid[first.getRow()][first.getCol()];
            vincolo.setBounds(casella.getX() + 3, casella.getY() - (40 - (gridSize - 2) * 6), casella.getWidth(), casella.getHeight());
            p.add(vincolo, JLayeredPane.PALETTE_LAYER);

            for (Cell cell : block.getCells()) {
                JTextField cella = constrainedGrid[cell.getRow()][cell.getCol()];
                cella.setBackground(blockColor);
            }
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
        if (round > 0) {
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
        if (round <= 0) {
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

    public void resetGrid(){
            for (int i = 0; i < gridSize; i++)
                for (int j = 0; j < gridSize; j++) {
                    constrainedGrid[i][j].setText("");
                    constrainedGrid[i][j].setEditable(true);
                }
            }


    public static void main(String[] args) {

        Grid grid = new Grid(2);
        GridView gridView = new GridView(grid.getGrid());
        GameController controller = new GameController(grid, gridView);
    }
}



