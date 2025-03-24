//Imports
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

//Class
public class ChessGame extends JPanel implements MouseListener
{
    //Declare variables
        private ChessBoard chessBoard;

        private int turn;

        private Cell activePiece;

    //Constructor
        public ChessGame(int boardSize)
        {
            //Instatiate variables
            this.chessBoard = new ChessBoard(boardSize);
            turn = -1;

            //Configure the JPanel
            setVisible(true);
            addMouseListener(this);
        }

    //Enums
        public enum GameState {
            SAFE, STALEMATE, CHECKMATE;
        }
    
    //Drawing methods
        @Override
        /*
         * Initiates itself upon startup
         * 
         * A JFrame opens a window,
         * a JPanel allows you to interact with a JFrame,
         * and a Graphic allows you to draw on a JPanel.
         * 
         * That Graphic is instantiated here, and is passed across every function that interacts
         * with the visual element of this game.
         * 
        */
        public void paintComponent(Graphics graphic)
        {
            if(turn == -1)
            {
                chessBoard.paintTheBoard(graphic);
                turn = 0;
            }
            else
            {
                chessBoard.repaintTheBoard(graphic);
            }
        }

    //Interaction methods
        @Override
        /*
         * Initiates itself upon startup
         *
         * The game works in cycles of four.
         * 1) Light chooses a piece
         * 2) Light chooses a location 
         * 3-4) Dark does the same
         * 
         * After each successful attempt at an implementation, the global variable turn will increase by one,
         *      which will allow for the cycle to move past that implementation (ie. light's turn to choose a location)
         */
        public void mouseClicked(MouseEvent click)
        {
            Cell selection = chessBoard.getPiece(click);

            switch(turn % 4)
            {
                case 0:
                //It is light's turn to choose a piece
                    choosePiece(selection, Cell.Color.LIGHT);
                    break;
                case 1:
                //It is light's turn to choose a location
                    chooseLocation(selection);
                    break;

                case 2:
                //It is dark's turn to choose a piece

                    choosePiece(selection, Cell.Color.DARK);
                    break;
                
                case 3:
                //It is dark's turn to choose a location
                    chooseLocation(selection);
                    break;
            }

            //Update the JPanel visual state
            super.repaint();
        }
        //Unused methods for MouseListener (required to override)
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}

        public void choosePiece(Cell selection, Cell.Color color)
        //Performs selection operation. If successful, increments turn by one, thereby navigating the switch case
            //Chose possible moves for the next step (choosing a location)
        {
            if(selection.getColor() == color && selection.calculateValidMoves(chessBoard).size() > 0)
            {
                //The selection has been verified, so select it
                selection.select();
                activePiece = selection;

                //Show the locations that the player can move to
                chessBoard.showValidMoves(activePiece.calculateValidMoves(chessBoard));

                //Increment the turn variable
                turn++;
            }
        }

        public void chooseLocation(Cell selection)
        //Possible choices are selected. The player must choose which cell to go to
        {            
            //First if the player clicked on the piece they just clicked on, deselect the whole board and let the player try again
            if(selection.getPos().equals(activePiece.getPos()))
            {
                chessBoard.deselect();
                turn--;
                return;
            }

            //Calculate the valid moves and check if the given cell is in one of them
            ArrayList<Move> moves = activePiece.calculateValidMoves(chessBoard);

            for(Move move: moves)
            {
                if(move.getPos().equals(selection.getPos()))
                {
                    //First deselect everything
                    chessBoard.deselect();
                    
                    //Next make a move
                    chessBoard.move(activePiece, move);

                    //Increment the turn variable
                    turn++;

                    //Flip the board to prepare for the other player to choose a piece
                    chessBoard.flip();

                    break;
                }
            }

            //This is a strange method which helps with en passant and castling
            chessBoard.age();
        }
}
