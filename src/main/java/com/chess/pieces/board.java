package com.chess.pieces;

import com.chess.controller.*;
import com.chess.gui.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class board {
    private boolean good_frogo_turn;
    public boolean check_mate_bad, check_mate_good, stale_mate;
    private final int tilesize = 80;
    private final int row = 8;
    private final int col = 8;
    private final gameController controller;
    private final piece[][] pieces = new piece[row][col];
    private final LinkedList<piece> taken_good_piece = new LinkedList<>();
    private final LinkedList<piece> taken_bad_piece = new LinkedList<>();

    public  piece piece_to_move;
    pieces pieces_panel;

    public board(pieces pieces_panel){
        this.pieces_panel = pieces_panel;
        pieces_panel.board = this;
        controller = new gameController(this);
        this.good_frogo_turn = false;
        this.check_mate_bad = false;
        this.check_mate_good = false;
        this.stale_mate = false;
        init_pieces();
    }

    public void init_pieces(){
        pieces[0][0] = new rook(true, 0, 0, this, 'R');
        pieces[1][0] = new knight(true, 1, 0, this, 'H');
        pieces[2][0] = new bishop(true, 2, 0, this, 'B');
        pieces[3][0] = new queen( true, 3, 0, this, 'Q');
        pieces[4][0] = new king(true, 4, 0, this, 'K');
        pieces[5][0] = new bishop(true, 5, 0, this, 'B');
        pieces[6][0] = new knight(true, 6, 0, this, 'H');
        pieces[7][0] = new rook(true, 7, 0, this, 'R');

        pieces[0][7] = new rook( false, 0, 7, this, 'R');
        pieces[1][7] = new knight(false, 1, 7, this, 'H');
        pieces[2][7] = new bishop(false, 2, 7, this, 'B');
        pieces[3][7] = new queen(false, 3, 7, this, 'Q');
        pieces[4][7] = new king(false, 4, 7, this, 'K');
        pieces[5][7] = new bishop(false, 5, 7, this, 'B');
        pieces[6][7] = new knight(false, 6, 7, this, 'H');
        pieces[7][7] = new rook(false, 7, 7, this, 'R');

        for(int i = 0; i<8; i++) {
            pieces[i][1] = new pawn(true, i, 1, this, 'P');
            pieces[i][6] = new pawn(false, i, 6, this, 'P');
        }

    }
    public void draw(Graphics g) {
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if((i+j) % 2 == 0) {
                    g.setColor(new Color(249, 172, 113));
                }else {
                    g.setColor(new Color(103, 51, 20));
                }
                g.fillRect(i*tilesize, j*tilesize, tilesize, tilesize);
            }
        }
        if(piece_to_move != null) {
            for(int i = 0; i < row; i++) {
                for (int j = 0; j < row; j++) {
                    if (is_valid_move(piece_to_move,i,j)) {
                        g.setColor(new Color(86, 218, 86, 140));
                        g.fillRect(i*tilesize, j*tilesize, tilesize, tilesize);
                    }
                }
            }
        }
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (pieces[i][j] != null) {
                    pieces[i][j].draw(g);
                }
            }
        }
    }

    public boolean is_valid_move(piece piece, int row, int col) {

        if((piece.is_good() && !good_frogo_turn) || (!piece.is_good() && good_frogo_turn)) return false;

        if(piece.row == row && piece.col == col) return false;

        if(pieces[row][col]!=null) if(pieces[row][col].alliance(piece)) return false;

        if(!piece.valid_move(row, col)) return false;

        if(piece.collision(row, col)) return false;

        return !controller.king_in_check(piece, row, col);
    }

    public void make_move(piece p, int row, int col){
        piece g_king = get_king(true);
        piece b_king = get_king(false);
        if(p.name == 'K' && Math.abs(g_king.row - row) == 2 && p.is_good()) {

            pieces[2][0] = pieces[0][0];
            pieces[0][0] = null;

            pieces[2][0].row = 2;
            pieces[2][0].col = 0;

            pieces[2][0].x = 2 * tilesize;
            pieces[2][0].y = 0 * tilesize;

        } else if (p.name == 'K' && Math.abs(b_king.row - row) == 2 && !p.is_good()) {

            pieces[2][7] = pieces[0][7];
            pieces[0][7] = null;

            pieces[2][7].row = 2;
            pieces[2][7].col = 7;

            pieces[2][7].x = 2 * tilesize;
            pieces[2][7].y = 7 * tilesize;
        }

        if(pieces[row][col] != null) {
            if(pieces[row][col].is_good()) {
                taken_good_piece.add(pieces[row][col]);
                pieces_panel.repaint();
            }else {
                taken_bad_piece.add(pieces[row][col]);
                pieces_panel.repaint();
            }
        }

        pieces[row][col] = pieces[p.row][p.col];
        pieces[p.row][p.col] = null;

        p.row = row;
        p.col = col;

        p.x = row * tilesize;
        p.y = col * tilesize;

        piece_to_move = null;

        if (p.name == 'P' && p.is_good() && col == 7) {
            pieces[row][col] = null;
            pieces[row][col] = new queen(true, row, col, this, 'Q');
        } else if(p.name == 'P' && !p.is_good() && col == 0) {
            pieces[row][col] = null;
            pieces[row][col] = new queen(false, row, col, this, 'Q');
        }

        good_frogo_turn = !good_frogo_turn;

        controller.check_or_stale();
    }

    public piece get_king(boolean good) {
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (pieces[i][j] != null && pieces[i][j].name == 'K' && pieces[i][j].is_good() == good) {
                    return pieces[i][j];
                }
            }
        }
        return null;
    }

    public int get_tilesize(){
        return tilesize;
    }
    public piece get_piece(int row, int col){
        return pieces[row][col];
    }
    public int taken_good_pieces_len(){
        return taken_good_piece.size();
    }
    public int taken_bad_pieces_len(){
        return taken_bad_piece.size();
    }
    public piece get_taken_good_pieces(int i){
        return taken_good_piece.get(i);
    }
    public piece get_taken_bad_pieces(int i){
        return taken_bad_piece.get(i);
    }
    public boolean turn() {return  good_frogo_turn;}
}
