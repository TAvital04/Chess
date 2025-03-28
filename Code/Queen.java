import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Queen extends Cell
{
    //Declare variables
        private static final File LIGHT_IMAGE_FILE = new File("Assets\\Light_Queen.png");
        private static final File DARK_IMAGE_FILE = new File("Assets\\Dark_Queen.png");

    //Constructor
        public Queen(Coordinates pos, Color color, ChessBoard board)
        {
            super(pos, board);

            super.setColor(color);
            super.setType(Type.QUEEN);
        }

    //Methods
        @Override
        public void draw(Graphics graphic)
        {
            try
            {
                if(super.getColor() == Color.LIGHT)
                {
                    graphic.drawImage(ImageIO.read(LIGHT_IMAGE_FILE), super.getCol() * Cell.getCellSize(), super.getRow() * Cell.getCellSize(), null);
                }
                else if(super.getColor() == Color.DARK)
                {
                    graphic.drawImage(ImageIO.read(DARK_IMAGE_FILE), super.getCol() * Cell.getCellSize(), super.getRow() * Cell.getCellSize(), null);
                }
            }
            catch(IOException exception)
            {
                System.out.println("Could not load image");
            }
            super.draw(graphic);
        }

        @Override
        public ArrayList<Move> calculateValidMoves(ChessBoard board, Move.Type moveType)
        {
            ArrayList<Move> moves = new ArrayList<Move>();

            int col, row;

            Color color = super.getColor();

            //Up
            col = super.getCol(); row = super.getRow() - 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
                row--;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
            }

            //Up and to the right
            col = super.getCol() + 1; row = super.getRow() - 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
                col++; row--;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
            }

            //Right
            col = super.getCol() + 1; row = super.getRow();
            while(canMoveThere(board, col, row))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
                col++;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
            }

            //Down and to the right
            col = super.getCol() + 1; row = super.getRow() + 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
                col++; row++;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
            }

            //Down
            col = super.getCol(); row = super.getRow() + 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
                row++;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
            }

            //Down and to the left
            col = super.getCol() - 1; row = super.getRow() + 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
                col--; row++;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
            }

            //Left
            col = super.getCol() - 1; row = super.getRow();
            while(canMoveThere(board, col, row))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
                col--;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
            }

            //Up and to the left
            col = super.getCol() - 1; row = super.getRow() - 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
                col--; row--;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Move(col, row, Move.Type.NORMAL));
            }

            //Check
            if(moveType != Move.Type.CHECK)
            //If we are not checking for checks for the other king
            {
                if(!board.cellIsSafe(new Move(board.getKing(super.getColor()).getPos(), Move.Type.CHECK), color))
                //if the king is in check
                {
                    super.filterMovesForCheck(board, moves);
                }
            }

            return moves;
        }
}
