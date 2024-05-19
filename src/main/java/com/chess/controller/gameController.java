package com.chess.controller;

import com.chess.pieces.*;
public class gameController {
    board board;

    public gameController(board board) {
        this.board = board;
    }
    public boolean king_in_check(piece piece, int row, int col) {

        if (board.turn()) {

            piece king = board.get_king(true);

            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    if(board.get_piece(i, j) != null && !board.get_piece(i, j).is_good()) {

                        switch (board.get_piece(i, j).name) {
                            case 'H':
                                if(check_by_knight(piece, king, row, col, i, j)) return true;
                                break;

                            case 'R':
                                if(check_by_rook(piece, king, row, col, i, j)) return true;
                                break;

                            case 'B':
                                if(check_by_bishop(piece, king, row, col, i, j)) return true;
                                break;

                            case 'Q':
                                if(
                                        check_by_bishop(piece, king, row, col, i, j)
                                        || check_by_rook(piece, king, row, col, i, j)
                                ) {
                                    return true;
                                }
                                break;

                            case 'K':
                                if(check_by_king(piece, king, row, col, i, j)) return true;
                                break;

                            case 'P':
                                if(check_by_pawn(piece, king, row, col, i, j)) return true;
                                break;
                        }
                    }
                }
            }

        } else {

            piece king = board.get_king(false);
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    if(board.get_piece(i, j) != null && board.get_piece(i, j).is_good()) {

                        switch (board.get_piece(i, j).name) {

                            case 'H':
                                if(check_by_knight(piece, king, row, col, i, j)) return true;
                                break;

                            case 'R':
                                if(check_by_rook(piece, king, row, col, i, j)) return true;
                                break;

                            case 'B':
                                if(check_by_bishop(piece, king, row, col, i, j)) return true;
                                break;

                            case 'Q':
                                if(
                                        check_by_bishop(piece, king, row, col, i, j)
                                        || check_by_rook(piece, king, row, col, i, j)
                                ) return true;
                                break;

                            case 'K':
                                if(check_by_king(piece, king, row, col, i, j)) return true;
                                break;

                            case 'P':
                                if(check_by_pawn(piece, king, row, col, i, j)) return true;
                                break;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean check_by_rook(piece piece, piece king, int row, int col, int i, int j) {
        if(piece.name == 'K') {
            if((col == j || row == i) && !(row == i && col == j)) {
                if (j > col) {
                    for(int m = col+1; m < j; m++) {
                        if(board.get_piece(i, m) != null) {
                            if(board.get_piece(i, m).name != 'K') {
                                return false;
                            }
                        }
                    }
                    return true;
                }

                if (j < col) {
                    for(int m = col-1; m > j; m--) {
                        if(board.get_piece(i, m) != null) {
                            if(board.get_piece(i, m).name != 'K') {
                                return false;
                            }
                        }
                    }
                    return true;
                }

                if (i < row) {
                    for(int m = row-1; m > i; m--) {
                        if(board.get_piece(m, j) != null) {
                            if(board.get_piece(m, j).name != 'K') {
                                return false;
                            }
                        }
                    }
                    return true;
                }

                if (i > row) {
                    for(int m = row+1; m < i; m++) {
                        if(board.get_piece(m, j) != null) {
                            if(board.get_piece(m, j).name != 'K') {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        } else {

            if((king.col == j || king.row == i) && !(row == i && col == j)) {

                if (j > king.col) {
                    for(int m = king.col+1; m < j; m++) {
                        if(
                                (row == i && col == m)
                                || (board.get_piece(i, m) != null
                                && (piece != board.get_piece(i, m)))
                        ) {
                            return false;
                        }
                    }
                    return true;
                }

                if (j < king.col) {
                    for(int m = king.col-1; m > j; m--) {
                        if(
                                (row == i && col == m)
                                || (board.get_piece(i, m) != null
                                && (piece != board.get_piece(i, m)))
                        ) {
                            return false;
                        }
                    }
                    return true;
                }

                if (i < king.row) {
                    for(int m = king.row-1; m > i; m--) {
                        if(
                                (row == m && col == j)
                                || (board.get_piece(m, j) != null
                                && (piece != board.get_piece(m, j)))
                        ) {
                            return false;
                        }
                    }
                    return true;
                }

                if (i > king.row) {
                    for(int m = king.row+1; m < i; m++) {
                        if(
                                (row == m && col == j)
                                || (board.get_piece(m, j) != null
                                && (piece != board.get_piece(m, j)))
                        ) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public boolean check_by_knight(piece piece, piece king, int row, int col, int i, int j) {

        if(piece.name == 'K') {
            return Math.abs(col - j) * Math.abs(row - i) == 2 && !(row == i && col == j);

        }else {
            return (Math.abs(king.col - j) * Math.abs(king.row - i) == 2) && !(row == i && col == j);
        }
    }

    public boolean check_by_bishop(piece piece, piece king, int row, int col, int i, int j) {

        if(piece.name == 'K') {
            
            if((Math.abs(col - j) == Math.abs(row - i)) && !(row == i && col == j)) {

                if (i < row && j > col ) {
                    for(int m = 1; m < Math.abs(j - col); m++) {
                        if(board.get_piece(row - m, col + m) != null) {
                            if(board.get_piece(row - m, col + m).name != 'K') {
                                return false;
                            }
                        }
                    }
                    return true;
                }

                if (i > row && j > col) {
                    for(int m = 1; m < Math.abs(j - col); m++) {
                        if(board.get_piece(row + m, col + m) != null) {
                            if(board.get_piece(row + m, col + m).name != 'K') {
                                return false;
                            }
                        }
                    }
                    return true;
                }

                if (i < row && j < col) {
                    for(int m = 1; m < Math.abs(j - col); m++) {
                        if(board.get_piece(row - m, col - m) != null) {
                            if(board.get_piece(row - m, col - m).name != 'K') {
                                return false;
                            }
                        }
                    }
                    return true;
                }

                if (i > row && j < col) {
                    for(int m = 1; m < Math.abs(j - col); m++) {
                        if(board.get_piece(row + m, col - m) != null) {
                            if(board.get_piece(row + m, col - m).name != 'K') {
                                return false;
                            }
                        }
                    }
                    return true;
                }

            }
            
        } else {

            if((Math.abs(king.col - j) == Math.abs(king.row - i)) && !(row == i && col == j)) {

                if (i < king.row && j > king.col ) {
                    for(int m = 1; m < Math.abs(j - king.col); m++) {
                        if(
                                (row == king.row - m && col == king.col + m)
                                || (board.get_piece(king.row - m, king.col + m) != null
                                && (piece != board.get_piece(king.row - m, king.col + m)))
                        ) {
                            return false;
                        }
                    }
                    return true;
                }

                if (i > king.row && j > king.col) {
                    for(int m = 1; m < Math.abs(j - king.col); m++) {
                        if(
                                (row == king.row + m && col == king.col + m)
                                || (board.get_piece(king.row + m, king.col + m) != null
                                && (piece != board.get_piece(king.row + m, king.col + m)))
                        ) {
                            return false;
                        }
                    }
                    return true;
                }

                if (i < king.row && j < king.col) {
                    for(int m = 1; m < Math.abs(j - king.col); m++) {
                        if(
                                (row == king.row - m && col == king.col - m)
                                || (board.get_piece(king.row - m, king.col - m) != null
                                && (piece != board.get_piece(king.row - m, king.col - m)))
                        ) {
                            return false;
                        }
                    }
                    return true;
                }

                if (i > king.row && j < king.col) {
                    for(int m = 1; m < Math.abs(j - king.col); m++) {
                        if(
                                (row == king.row + m && col == king.col - m)
                                || (board.get_piece(king.row + m, king.col - m) != null
                                && (piece != board.get_piece(king.row + m, king.col - m)))) {
                            return false;
                        }
                    }
                    return true;
                }
            }

        }
        return false;
    }

    public boolean check_by_king(piece piece, piece king, int row, int col, int i, int j) {

        if(piece.name == 'K') {

            return (i == row && (Math.abs(j - col) == 1))
                    || (Math.abs(i - row) == 1 && j == col)
                    || (Math.abs(i - row) == 1 && Math.abs(j - col) == 1);

        }else {

            return (i == king.row && (Math.abs(j - king.col) == 1))
                    || (Math.abs(i - king.row) == 1 && j == king.col)
                    || (Math.abs(i - king.row) == 1 && Math.abs(j - king.col) == 1);
        }

    }

    public boolean check_by_pawn(piece piece, piece king, int row, int col, int i, int j) {

        if(piece.name == 'K') {

            if(
                    !board.get_piece(i, j).is_good()
                    && j > col && Math.abs(row - i) == 1
                    && Math.abs(col - j) == 1
            ) {
                return true;
            }

            return board.get_piece(i, j).is_good() && j < col
                    && Math.abs(row - i) == 1
                    && Math.abs(col - j) == 1;

        }else {

            if(
                    !board.get_piece(i, j).is_good() && j > king.col
                    && Math.abs(king.row - i) == 1
                    && Math.abs(king.col - j) == 1
                    && !(row == i && col == j)
            ) {
                return true;
            }

            return board.get_piece(i, j).is_good() && j < king.col
                    && Math.abs(king.row - i) == 1
                    && Math.abs(king.col - j) == 1
                    && !(row == i && col == j);

        }
    }

    public void check_or_stale() {

        if(board.turn()) {

            piece dummy = board.get_king(true);

            if(king_in_check(dummy, dummy.row, dummy.col)) {
                if(invalid_move(true)) {
                    board.check_mate_good = true;
                }

            }else {
                if(invalid_move(true)) {
                    board.stale_mate = true;
                }
            }

        }else {
            piece dummy = board.get_king(false);

            if(king_in_check(dummy, dummy.row, dummy.col)) {
                if(invalid_move(false)) {
                    board.check_mate_bad = true;
                }

            }else {
                if(invalid_move(false)) {
                    board.stale_mate = true;
                }
            }
        }
    }

    public boolean invalid_move(boolean isWhite) {

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                piece dummy = board.get_piece(i, j);
                if(dummy != null) {
                    if(dummy.is_good() == isWhite) {
                        for(int m = 0; m < 8; m++) {
                            for(int n = 0; n < 8; n++) {
                                if(board.is_valid_move(dummy, m, n)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}