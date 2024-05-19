package com.chess.pieces;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class piece {
    public char name;
    private final boolean is_good_frogo;
    public int row, col, x, y;
    board board;
    public Image sprite;

    public piece(boolean is_good_frogo, int row, int col, board board, char name){
        this.is_good_frogo = is_good_frogo;
        this.name = name;
        this.row = row;
        this.col = col;
        this.board = board;
        this.x = board.get_tilesize() * this.row;
        this.y = board.get_tilesize() * this.col;
        this.board = board;
    }
    public Image scaled_image(Image src, int w, int h){
        BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(src, 0,0, w, h, null);
        g2d.dispose();
        return resized;
    }
    public void draw(Graphics graphic){
        graphic.drawImage(sprite, x, y, null);
    }
    public boolean valid_move(int row, int col){
        return false;
    }

    public boolean collision(int row, int col){
        return false;
    }
    public boolean is_good(){
        return is_good_frogo;
    }
    public boolean alliance(piece piece){
        return piece.is_good_frogo == this.is_good_frogo;
    }
}
