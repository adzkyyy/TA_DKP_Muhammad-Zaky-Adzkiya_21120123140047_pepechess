package com.chess.controller;
import com.chess.pieces.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class mouseController extends MouseAdapter {

    private final board board;
    int pressC,pressR, releaseC, releaseR;
    
    public mouseController(board board) {
        this.board = board;
    }
    public void mouseDragged(MouseEvent e) {

        if (board.piece_to_move != null) {
            board.piece_to_move.x = e.getX() - board.get_tilesize() / 2;
            board.piece_to_move.y = e.getY() - board.get_tilesize() / 2;
        }
    }
    public void mousePressed(MouseEvent e) {

        pressR = e.getX() / board.get_tilesize();
        pressC = e.getY() / board.get_tilesize();

        board.piece_to_move = board.get_piece(pressR, pressC);
    }
    public void mouseReleased(MouseEvent e) {

        if(e.getX() < 0 || e.getX() > 640 || e.getY() < 0 || e.getY() > 640) {
            board.piece_to_move.x = board.piece_to_move.row * board.get_tilesize();
            board.piece_to_move.y = board.piece_to_move.col * board.get_tilesize();
            board.piece_to_move = null;
            return;
        }

        releaseR = e.getX() / board.get_tilesize();
        releaseC = e.getY() / board.get_tilesize();
        if (board.piece_to_move != null) {

            if(board.is_valid_move(board.piece_to_move, releaseR, releaseC)){
                board.make_move(board.piece_to_move, releaseR, releaseC);

            } else {
                board.piece_to_move.x = board.piece_to_move.row * board.get_tilesize();
                board.piece_to_move.y = board.piece_to_move.col * board.get_tilesize();
                board.piece_to_move = null;
            }
        }
    }
}
