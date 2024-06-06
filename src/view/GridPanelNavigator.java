package view;

import model.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GridPanelNavigator {

    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private LinkedList<Grid> soluzioni;

    public GridPanelNavigator(LinkedList<Grid> soluzioni) {

        this.soluzioni = soluzioni;
        frame = new JFrame("Soluzioni");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //istanzio i diversi gridPanel
        for (int i = 0; i < soluzioni.size(); i++) {
            JLabel title = new JLabel("Soluzione n. "+(i+1)+"->");
            title.setFont(new Font("Arial", Font.BOLD, 20));
            cardPanel.add(title,"Card"+ i);
            JPanel gridPanel = createGridPanel(i);

            cardPanel.add(gridPanel, "Card" + i);

        }

        JPanel controlPanel = new JPanel();
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.previous(cardPanel);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(cardPanel);
            }
        });

        controlPanel.add(prevButton);
        controlPanel.add(nextButton);

        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createGridPanel(int panelNumber) {
        Grid grid = soluzioni.get(panelNumber);
        int gridSize = grid.getSize();
        int[][] griglia = grid.getGrid();

        JPanel panel = new JPanel(new GridLayout(gridSize, gridSize));


        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JLabel number = new JLabel(griglia[i][j] + "");
                number.setFont(new Font(number.getFont().getName(), Font.PLAIN, 20));
                number.setHorizontalAlignment(JTextField.CENTER);
                panel.add(number);
            }
        }
        return panel;
    }

}
