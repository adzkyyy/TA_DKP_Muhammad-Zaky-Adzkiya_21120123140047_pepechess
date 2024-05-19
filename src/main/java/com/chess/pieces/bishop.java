package com.chess.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

public class bishop extends piece {
    public bishop(boolean is_good, int row, int col, board board, char name){
        super(is_good, row, col, board, name);
        try {
            this.sprite = this.scaled_image(
                    is_good ?
                            ImageIO.read(getClass().getResourceAsStream("/images/WBishop.png")):
                            ImageIO.read(getClass().getResourceAsStream("/images/BBishop.png")),
                    board.get_tilesize(),
                    board.get_tilesize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean valid_move(int row, int col){
        return Math.abs(col - this.col) == Math.abs(row - this.row);
    }
    public boolean collision(int row, int col){
        if (Math.abs(this.row - row) == Math.abs(this.col - col)){

            if (this.row < row && this.col > col){
                for (int i = 1; i < Math.abs(this.col - col); i++) {
                    if (board.get_piece(row - i, col + i) != null){
                        return true;
                    }
                }
            }

            if (this.row > row && this.col > col){
                for (int i = 1; i < Math.abs(this.col - col); i++) {
                    if (board.get_piece(row + i, col + i) != null){
                        return true;
                    }
                }
            }

            if (this.row < row && this.col < col) {
                for(int i = 1; i < Math.abs(this.col - col); i++) {
                    if(board.get_piece(row - i, col - i) != null) {
                        return true;
                    }
                }
            }

            if (this.row > row && this.col < col) {
                for(int i = 1; i < Math.abs(this.col - col); i++) {
                    if(board.get_piece(row + i, col - i) != null) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
