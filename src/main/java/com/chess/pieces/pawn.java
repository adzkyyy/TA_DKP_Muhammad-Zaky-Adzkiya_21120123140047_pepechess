package com.chess.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

public class pawn extends piece {

    public pawn(boolean is_good, int row, int col, board board, char name) {
        super(is_good, row, col, board, name);
        try {
            this.sprite = this.scaled_image(
                    is_good ?
                            ImageIO.read(getClass().getResourceAsStream("/images/WPawn.png")):
                            ImageIO.read(getClass().getResourceAsStream("/images/BPawn.png")),
                    board.get_tilesize(),
                    board.get_tilesize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean valid_move(int row, int col) {

        if(this.is_good()) {

            if(this.col == 1) {
                if(
                        col - this.col <= 2
                        && col - this.col > 0
                        && row == this.row
                        && board.get_piece(row, col) == null
                ) {
                    return col - this.col != 2 || board.get_piece(this.row, this.col + 1) == null;
                }

            } else {
                if(
                        col - this.col == 1
                        && row == this.row
                        && board.get_piece(row, col) == null
                ) {
                    return true;
                }
            }
            return col > this.col && board.get_piece(row, col) != null
                    && Math.abs(this.row - row) == 1
                    && Math.abs(this.col - col) == 1;

        }else {

            if(this.col == 6) {

                if(
                        this.col - col <= 2
                        && this.col - col > 0
                        && row == this.row
                        && board.get_piece(row, col) == null
                ) {
                    return this.col - col != 2 || board.get_piece(this.row, this.col - 1) == null;
                }

            } else {
                if(
                        this.col - col == 1
                        && row == this.row
                        && board.get_piece(row, col) == null
                ) {
                    return true;
                }
            }
            return col < this.col
                    && board.get_piece(row, col) != null
                    && Math.abs(this.row - row) == 1
                    && Math.abs(this.col - col) == 1;
        }
    }
}