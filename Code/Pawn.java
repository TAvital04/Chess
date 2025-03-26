import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Pawn extends Cell
{
    //Declare variables
        private static final File LIGHT_IMAGE_FILE = new File("Assets\\Light_Pawn.png");
        private static final File DARK_IMAGE_FILE = new File("Assets\\Dark_Pawn.png");

    //Constructor
        public Pawn(Coordinates pos, Color color, ChessBoard board)
        {
            super(pos, board);
            
            super.setColor(color);
            super.setType(Type.PAWN);

            super.setAge(0);
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

            int col = super.getCol(), row = super.getRow();

            Color color = super.getColor();

            //If a push forward is in bounds, add it
            if(canMoveThere(board, col, row - 1))
            {
                moves.add(new Move(col, row - 1, Move.Type.NORMAL));
            }

            //If a diagonal move is in bounds and takes a piece of the opposite color, add it
                //Up and to the left
                if(canEatThere(board, col - 1, row - 1, color))
                {
                    moves.add(new Move(col - 1, row - 1, Move.Type.NORMAL));
                }
                //Up and to the right
                if(canEatThere(board, col + 1, row - 1, color))
                {
                    moves.add(new Move(col + 1, row - 1, Move.Type.NORMAL));
                }

            //If age = 0, the piece can move forward by 2
                //I don't have to check bounds
                if(super.getAge() == 0)
                {
                    if(canMoveThere(board, col, row - 2))
                    {
                        moves.add(new Move(col, row - 2, Move.Type.NORMAL));
                    }
                }

            //En passant
                //Left
                if(canMoveAndEatThere(board, col - 1, row - 1, color))
                {
                    Cell cell = board.getPiece(col - 1, row);
                    if(cell.getType() == Type.PAWN && cell.getAge() == 1)
                    {
                        moves.add(new Move(col - 1, row - 1, Move.Type.ENPASSANT));
                    }
                }
                //Right
                if(canMoveAndEatThere(board, col + 1, row, color))
                {
                    if(board.getPiece(col + 1, row) instanceof Pawn)
                    {
                        Pawn pawn = (Pawn)board.getPiece(col + 1, row);
                        if(pawn.getAge() == 1)
                        {
                            moves.add(new Move(col + 1, row - 1, Move.Type.ENPASSANT));
                        }
                    }
                }

            return moves;
        }

        public boolean isAtSpawn()
        {
            if(this.getRow() == 1 || this.getRow() == 6)
            {
                return true;
            }

            return false;
        }

    //Retrieve positions
    public static int spawnPawnRowUp()
    {
        return 1;
    }
    
    public static int spawnPawnRowDown()
    {
        return 6;
    }
}
