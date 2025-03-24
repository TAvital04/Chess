import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class Bishop extends Cell
{
    //Declare variables
        private static final File LIGHT_IMAGE_FILE = new File("Assets\\Light_Bishop.png");
        private static final File DARK_IMAGE_FILE = new File("Assets\\Dark_Bishop.png");

    //Constructor
        public Bishop(Coordinates pos, Cell.Color color, ChessBoard board)
        {
            super(pos, board);

            super.setColor(color);
            super.setType(Type.BISHOP);
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
                else if(super.getColor() == Cell.Color.DARK)
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
        public ArrayList<Move> calculateValidMoves(ChessBoard board)
        {
            ArrayList<Move> moves = new ArrayList<Move>();

            int col, row;
            Cell.Color color = super.getColor();

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

            return moves;
        }
}