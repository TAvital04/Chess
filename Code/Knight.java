import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Knight extends Cell
{
    //Declare variables
        private static final File LIGHT_IMAGE_FILE = new File("Assets\\Light_Kight.png");
        private static final File DARK_IMAGE_FILE = new File("Assets\\Dark_Kight.png");
    
    //Constructor
        public Knight(Coordinates pos, Color color, ChessBoard board)
        {
            super(pos, board);

            super.setColor(color);
            super.setType(Type.KNIGHT);
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
        public ArrayList<Move> calculateValidMoves(ChessBoard board)
        {
            ArrayList<Move> moves = new ArrayList<Move>();

            int col = super.getCol(), row = super.getRow();
            Color color = super.getColor();

            //Up 2 right 1
            if(canMoveAndEatThere(board, col + 1, row - 2, color))
            {
                moves.add(new Move(col + 1, row - 2, Move.Type.NORMAL));
            }

            //Up 1 right 2
            if(canMoveAndEatThere(board, col + 2, row - 1, color))
            {
                moves.add(new Move(col + 2, row - 1, Move.Type.NORMAL));
            }
            
            //Down 1 right 2
            if(canMoveAndEatThere(board, col + 2, row + 1, color))
            {
                moves.add(new Move(col + 2, row + 1, Move.Type.NORMAL));
            }

            //Down 2 right 1
            if(canMoveAndEatThere(board, col + 1, row + 2, color))
            {
                moves.add(new Move(col + 1, row + 2, Move.Type.NORMAL));
            }

            //Down 2 left 1
            if(canMoveAndEatThere(board, col - 1, row + 2, color))
            {
                moves.add(new Move(col - 1, row + 2, Move.Type.NORMAL));
            }

            //Down 1 left 2
            if(canMoveAndEatThere(board, col - 2, row + 1, color))
            {
                moves.add(new Move(col - 2, row + 1, Move.Type.NORMAL));
            }

            //Up 1 left 2
            if(canMoveAndEatThere(board, col - 2, row - 1, color))
            {
                moves.add(new Move(col - 2, row - 1, Move.Type.NORMAL));
            }

            //Up 2 left 1
            if(canMoveAndEatThere(board, col - 1, row - 2, color))
            {
                moves.add(new Move(col - 1, row - 2, Move.Type.NORMAL));
            }

            return moves;
        }
}
