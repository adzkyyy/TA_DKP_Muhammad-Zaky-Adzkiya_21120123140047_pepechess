package com.chess.gui;

import javax.swing.*;

public class frame extends JFrame {
    game panel;
    pieces pieces_panel;

    public frame() {
        
        pieces_panel = new pieces();			
        panel = new game(pieces_panel);	

        setSize(655, 678);

        setTitle("PepeChess (frogo)");	

        setLocationRelativeTo(null);

        setLayout(null);

        add(panel);	

        setVisible(true);

        setResizable(true);	

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
