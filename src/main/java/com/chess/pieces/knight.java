package com.chess.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

public class knight extends piece {
    public knight(boolean is_good, int row, int col, board board, char name){
        super(is_good, row, col, board, name);
        try {
            this.sprite = this.scaled_image(
                    is_good ?
                            ImageIO.read(getClass().getResourceAsStream("/images/WKnight.png")):
                            ImageIO.read(getClass().getResourceAsStream("/images/BKnight.png")),
                    board.get_tilesize(),
                    board.get_tilesize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean valid_move(int row, int col){
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }
}
