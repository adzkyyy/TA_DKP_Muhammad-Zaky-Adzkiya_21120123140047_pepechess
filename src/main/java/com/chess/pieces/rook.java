package com.chess.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

public class rook extends piece {
    public rook(boolean is_good, int row, int col, board board, char name) {
        super(is_good, row, col, board, name);
        try {
            this.sprite = this.scaled_image(
                    is_good ?
                            ImageIO.read(getClass().getResourceAsStream("/images/WRook.png")):
                            ImageIO.read(getClass().getResourceAsStream("/images/BRook.png")),
                    board.get_tilesize(),
                    board.get_tilesize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean valid_move(int row, int col) {
        return this.col == col || this.row == row;
    }

    public boolean collision(int row, int col) {

        if (this.row == row || this.col == col) {
            if (this.col > col) {
                for (int i = col + 1; i < this.col; i++) {
                    if (board.get_piece(this.row, i) != null) {
                        return true;
                    }
                }
            }

            if (this.col < col) {
                for (int i = col - 1; i > this.col; i--) {
                    if (board.get_piece(this.row, i) != null) {
                        return true;
                    }
                }
            }

            if (this.row < row) {
                for (int i = row - 1; i > this.row; i--) {
                    if (board.get_piece(i, this.col) != null) {
                        return true;
                    }
                }
            }

            if (this.row > row) {
                for (int i = row + 1; i < this.row; i++) {
                    if (board.get_piece(i, this.col) != null) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
}

