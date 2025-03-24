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
        public ArrayList<Coordinates> calculateValidMoves(ChessBoard board)
        {
            ArrayList<Coordinates> moves = new ArrayList<Coordinates>();

            int col, row;

            Color color = super.getColor();

            //Up
            col = super.getCol(); row = super.getRow() - 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Coordinates(col, row));
                row--;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Coordinates(col, row));
            }

            //Up and to the right
            col = super.getCol() + 1; row = super.getRow() - 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Coordinates(col, row));
                col++; row--;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Coordinates(col, row));
            }

            //Right
            col = super.getCol() + 1; row = super.getRow();
            while(canMoveThere(board, col, row))
            {
                moves.add(new Coordinates(col, row));
                col++;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Coordinates(col, row));
            }

            //Down and to the right
            col = super.getCol() + 1; row = super.getRow() + 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Coordinates(col, row));
                col++; row++;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Coordinates(col, row));
            }

            //Down
            col = super.getCol(); row = super.getRow() + 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Coordinates(col, row));
                row++;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Coordinates(col, row));
            }

            //Down and to the left
            col = super.getCol() - 1; row = super.getRow() + 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Coordinates(col, row));
                col--; row++;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Coordinates(col, row));
            }

            //Left
            col = super.getCol() - 1; row = super.getRow();
            while(canMoveThere(board, col, row))
            {
                moves.add(new Coordinates(col, row));
                col--;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Coordinates(col, row));
            }

            //Up and to the left
            col = super.getCol() - 1; row = super.getRow() - 1;
            while(canMoveThere(board, col, row))
            {
                moves.add(new Coordinates(col, row));
                col--; row--;
            }
            if(canEatThere(board, col, row, color))
            {
                moves.add(new Coordinates(col, row));
            }

            return moves;
        }
}
