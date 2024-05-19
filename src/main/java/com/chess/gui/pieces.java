package com.chess.gui;
import com.chess.pieces.*;
import javax.swing.*;
import java.awt.*;

public class pieces extends JPanel {
    boolean reset;
    public board board;
    Image image;
    Graphics graphics;

    public pieces(){
        reset = false;
        this.setLayout(null);
        this.setSize(10, 10);
        this.setFocusable(true);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g){
        if (reset || board == null){
            return;
        }

        int x = 10;
        int y = 0;
        for (int i = 0; i < board.taken_bad_pieces_len(); i++) {
            if (i != 0 && i % 4 == 0){
                y += 50;
                x = 10;
            }
            g.drawImage(board.get_taken_bad_pieces(i).sprite, x, y, 40, 40, null);
            x += 48;
        }
        x = 10;
        y += 50;

        for (int i = 0; i < board.taken_good_pieces_len(); i++) {
            if (i != 0 && i % 4 == 0) {
                y += 50;
                x = 10;
            }
            g.drawImage(board.get_taken_good_pieces(i).sprite, x, y, 40, 40, null);
            x += 48;
        }
    }
}
