import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class King extends Cell
{
    //Declare variables
        private final File LIGHT_IMAGE_FILE = new File("Assets\\Light_King.png");
        private final File DARK_IMAGE_FILE = new File("Assets\\Dark_King.png");

        private int age = 0;

    //Constructor
        public King(Coordinates pos, Color color, ChessBoard board)
        {
            super(pos, board);
            super.setColor(color);
            super.setType(Type.KING);
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

            int col = super.getCol(), row = super.getRow();

            Color color = super.getColor();

            //Up
            if(canMoveAndEatThere(board, col, row - 1, color))
            {
                moves.add(new Coordinates(col, row - 1));
            }

            //Up and to the right
            if(canMoveAndEatThere(board, col + 1, row - 1, color))
            {
                moves.add(new Coordinates(col + 1, row - 1));
            }

            //To the right
            if(canMoveAndEatThere(board, col + 1, row, color))
            {
                moves.add(new Coordinates(col + 1, row));
            }

            //Down and to the right
            if(canMoveAndEatThere(board, col + 1, row + 1, color))
            {
                moves.add(new Coordinates(col + 1, row + 1));
            }

            //Down
            if(canMoveAndEatThere(board, col, row + 1, color))
            {
                moves.add(new Coordinates(col, row + 1));
            }

            //Down and to the left
            if(canMoveAndEatThere(board, col - 1, row + 1, color))
            {
                moves.add(new Coordinates(col - 1, row + 1));
            }

            //To the left
            if(canMoveAndEatThere(board, col - 1, row, color))
            {
                moves.add(new Coordinates(col - 1, row));
            }

            //Up and to the left
            if(canMoveAndEatThere(board, col - 1, row - 1, color))
            {
                moves.add(new Coordinates(col - 1, row - 1));
            }

            // //Castle
            // if(this.getAge() == 0)
            // {
            //     castling(board, moves);
            // }

            return moves;
        }

        public boolean isAtSpawn()
        {
            if((super.getRow() == 0 || super.getRow() == 7) && super.getCol() == 4)
            {
                return true;
            }

            return false;
        }

    //Castling methods
        public void castling(ChessBoard board, ArrayList<Coordinates> moves)
        /*
         * The king can castle if
         *      -the king has not moved
         *      -the rook has not moved
         *      -no enemy pieces are looking at the rook, king, nor the path between them
         */
        {
            //Left
            Cell rook = board.getPiece(0, 7);

            boolean kingIsValid = this.getAge() == 0;
            boolean rookIsValid = rook.getType() == Type.ROOK && rook.getAge() == 0;
            boolean pathIsSafe = getPathIsSafe(board, 0, 4);

            if(kingIsValid && rookIsValid && pathIsSafe)
            {
                moves.add(new Coordinates(0, 7));
            }

            //Right
            rook = board.getPiece(7, 7);

            rookIsValid = rook.getType() == Type.ROOK && rook.getAge() == 0;
            pathIsSafe = getPathIsSafe(board, 4, 7);

            if(kingIsValid && rookIsValid && pathIsSafe)
            {
                moves.add(new Coordinates(7, 7));
            }
        }

        public boolean getPathIsSafe(ChessBoard board, int start, int end)
        {
            for(int i = start; i <= end; i++)
            {
                
            }

            return true;
        }

    //Getters/Setters
        public int getAge()
        {
            return this.age;
        }
        public void setAge(int n)
        {
            this.age = n;
        }
}
