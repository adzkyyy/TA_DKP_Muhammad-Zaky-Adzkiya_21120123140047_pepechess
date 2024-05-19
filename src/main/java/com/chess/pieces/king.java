package com.chess.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

public class king extends piece {
    public king(boolean is_good, int row, int col, board board, char name){
        super(is_good, row, col, board, name);
        try {
            this.sprite = this.scaled_image(
                    is_good ?
                            ImageIO.read(getClass().getResourceAsStream("/images/WKing.png")):
                            ImageIO.read(getClass().getResourceAsStream("/images/BKing.png")),
                    board.get_tilesize(),
                    board.get_tilesize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean valid_move(int row, int col) {

        if(
                (this.row == row && Math.abs(this.col - col) == 1)
                || (Math.abs(this.row - row) == 1 && this.col == col)
                || (Math.abs(this.row - row) == 1 && Math.abs(this.col - col) == 1)
        ) return true;

        if(this.is_good()) {
            if(
                    this.col == 0
                    && this.row == 3
                    && board.get_piece(this.row - 3, this.col) != null
                    && board.get_piece(this.row - 3, this.col).name == 'R'
            ) {
                if(
                        board.get_piece(this.row - 2, this.col) == null
                        && board.get_piece(this.row - 1, this.col) == null
                ) return row == 1 && col == 0;
            }

        } else {
            if(
                    this.col == 7
                    && this.row == 3
                    && board.get_piece(this.row - 3, this.col) != null
                    && board.get_piece(this.row - 3, this.col).name == 'R'
            ) {
                if(
                        board.get_piece(this.row - 2, this.col) == null
                        && board.get_piece(this.row - 1, this.col) == null
                ) return row == 1 && col == 7;
            }
        }
        return false;
    }
}
