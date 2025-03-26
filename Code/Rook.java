import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Rook extends Cell
{
    //Declare variables
        private static final File LIGHT_IMAGE_FILE = new File("Assets\\Light_Rook.png");
        private static final File DARK_IMAGE_FILE = new File("Assets\\Dark_Rook.png");

        private int age = 0;

    //Constructor
        public Rook(Coordinates pos, Color color, ChessBoard board)
        {
            super(pos, board);

            super.setColor(color);
            super.setType(Type.ROOK);
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

            return moves;
        }

        public boolean isAtSpawn()
        {
            if((this.getRow() == 0 || this.getRow() == 7) && (this.getCol() == 0 || this.getCol() == 7))
            //There is no edge case where this leaves a fault. If you thought there was... so did I.
                //The one most probable edge case would involve the king moving which means castling still wouldn't be possible.
            {
                return true;
            }

            return false;
        }

    //Getters/setters
        public int getAge()
        {
            return this.age;
        }
        public void setAge(int n)
        {
            this.age = n;
        }

    //Retrieve positions
    public static Coordinates spawnRookUpLeft()
    {
        return new Coordinates(0, 0);
    }
    public static Coordinates spawnRookUpRight()
    {
        return new Coordinates(7, 0);
    }
    
    public static Coordinates spawnRookDownLeft()
    {
        return new Coordinates(0, 7);
    }
    public static Coordinates spawnRookDownRight()
    {
        return new Coordinates(7, 7);
    }
}
