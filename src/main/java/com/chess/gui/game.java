package com.chess.gui;

import com.chess.pieces.*;
import com.chess.controller.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class game extends JPanel implements Runnable{
    private static final int PANEL_WIDTH = 650;
    private static final int PANEL_HEIGHT = 650;
    private boolean gameStart;
    private final boolean gameOver;
    private int gameEndCode;

    Thread gameThread;
    Graphics graphics;
    Image image;
    board board;
    Image whiteKing;
    Image blackKing;
    mouseController mousehandler;
    pieces pieces_panel;

    public game(pieces pieces_panel) {

        gameStart = false;
        gameOver = false;

        whiteKing = new ImageIcon(getClass().getResource("/images/WKing.png")).getImage();
        blackKing = new ImageIcon(getClass().getResource("/images/BKing.png")).getImage();

        this.pieces_panel = pieces_panel;

        setLayout(null);
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setFocusable(true);
        setVisible(true);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g) {

        if(gameStart) {
            pieces_panel.reset = false;
            board.draw(g);

        } else if(gameOver) {
            board = null;
            pieces_panel.repaint();
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            if(gameEndCode == 0) {
                g.drawImage(blackKing, 180, 230, null);
                g.drawString("Black wins!", 280, 300);

            } else if(gameEndCode == 1) {
                g.drawImage(whiteKing, 180, 230, null);
                g.drawString("White wins!", 280, 300);

            } else {
                g.drawImage(whiteKing, 180, 230, null);
                g.drawString("Draw!", 280, 300);
                g.drawImage(blackKing, 440, 230, null);
            }
        }
    }

    public void run() {

        long last = System.nanoTime();
        double fps = 120.0;
        double ns = 1000000000 / fps;
        double delta = 0;

        while (true) {

            long now = System.nanoTime();
            delta += (now - last) / ns;
            last = now;

            if (delta >= 1) {
                repaint();

                if (board == null) {
                    board = new board(pieces_panel);
                    mousehandler = new mouseController(board);
                    this.addMouseListener(mousehandler);
                    this.addMouseMotionListener(mousehandler);
                }
                
                gameStart = true;
                if (board.check_mate_good || board.check_mate_bad || board.stale_mate) {

                    pieces_panel.reset = true;
                    if (board.check_mate_good) {
                        gameEndCode = 0;
                    } else if (board.check_mate_bad) {
                        gameEndCode = 1;
                    } else {
                        gameEndCode = 2;
                    }
                    board.check_mate_bad = false;
                    board.check_mate_good = false;
                }
                delta--;
            }
        }
    }
}
