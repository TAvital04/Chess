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

        private int age;

    //Constructor
        public King(Coordinates pos, Color color, ChessBoard board)
        {
            super(pos, board);
            super.setColor(color);
            super.setType(Type.KING);

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

            //Up
            if(canMoveAndEatThere(board, col, row - 1, color))
            {
                moves.add(new Move(col, row - 1, Move.Type.NORMAL));
            }

            //Up and to the right
            if(canMoveAndEatThere(board, col + 1, row - 1, color))
            {
                moves.add(new Move(col + 1, row - 1, Move.Type.NORMAL));
            }

            //To the right
            if(canMoveAndEatThere(board, col + 1, row, color))
            {
                moves.add(new Move(col + 1, row, Move.Type.NORMAL));
            }

            //Down and to the right
            if(canMoveAndEatThere(board, col + 1, row + 1, color))
            {
                moves.add(new Move(col + 1, row + 1, Move.Type.NORMAL));
            }

            //Down
            if(canMoveAndEatThere(board, col, row + 1, color))
            {
                moves.add(new Move(col, row + 1, Move.Type.NORMAL));
            }

            //Down and to the left
            if(canMoveAndEatThere(board, col - 1, row + 1, color))
            {
                moves.add(new Move(col - 1, row + 1, Move.Type.NORMAL));
            }

            //To the left
            if(canMoveAndEatThere(board, col - 1, row, color))
            {
                moves.add(new Move(col - 1, row, Move.Type.NORMAL));
            }

            //Up and to the left
            if(canMoveAndEatThere(board, col - 1, row - 1, color))
            {
                moves.add(new Move(col - 1, row - 1, Move.Type.NORMAL));
            }

            //Castle
            if(super.getAge() == 0 && moveType != Move.Type.CASTLE && moveType != Move.Type.CHECK)
            //I'm about to check if a king that might prevent me from castling can castle, which it can't
                //This would case a very annoying infinite loop and is basically the whole reason the Move class exists
            {
                //Trying to castle
                castling(board, moves);
            }

            //Check
            if(moveType != Move.Type.CHECK)
            //If we are not checking for checks for the other king
            {
                if(!board.cellIsSafe(new Move(super.getPos(), Move.Type.CHECK), color))
                //if the king is in check
                {
                    super.filterMovesForCheck(board, moves);
                }
            }

            return moves;
        }

        public boolean isAtSpawn()
        {
            if(((super.getRow() == 0 && super.getCol() == 3) || (super.getRow() == 7 && super.getCol() == 4)))
            {
                return true;
            }

            return false;
        }

    //Castling methods
        public void castling(ChessBoard board, ArrayList<Move> moves)
        /*
         * The king can castle if
         *      -the king has not moved
         *      -the rook has not moved
         *      -no enemy pieces are looking at the rook, king, nor the path between them
         *      -nothing stands between the rook and the king
         */
        {
            //Left
            Cell rook = board.getPiece(0, 7);

            boolean kingIsValid = super.getAge() == 0;
            boolean rookIsValid = rook.getType() == Type.ROOK && rook.getAge() == 0;

            boolean pathIsClear = false, pathIsSafe = false;
            if(super.getColor() == Color.LIGHT)
            {
                pathIsClear = getPathIsClear(board, 1, 3);
                pathIsSafe = getPathIsSafe(board, 0, 4);
            }
            else if(super.getColor() == Color.DARK)
            {
                pathIsClear = getPathIsClear(board, 1, 2);
                pathIsSafe = getPathIsSafe(board, 0, 3);
            }

            if(kingIsValid && rookIsValid && pathIsClear && pathIsSafe)
            {
                moves.add(new Move(0, 7, Move.Type.CASTLE));
            }

            //Right
            rook = board.getPiece(7, 7);

            rookIsValid = rook.getType() == Type.ROOK && rook.getAge() == 0;

            if(super.getColor() == Color.LIGHT)
            {
                pathIsClear = getPathIsClear(board, 5, 6);
                pathIsSafe = getPathIsSafe(board, 4, 7);
            }
            else if(super.getColor() == Color.DARK)
            {
                pathIsClear = getPathIsClear(board, 4, 6);
                pathIsSafe = getPathIsSafe(board, 3, 7);
            }
            
            if(kingIsValid && rookIsValid && pathIsClear && pathIsSafe)
            {
                moves.add(new Move(7, 7, Move.Type.CASTLE));
            }
        }
        public boolean getPathIsClear(ChessBoard board, int start, int end)
        {
            for(int i = start; i <= end; i++)
            {
                if(!(board.getPiece(i, 7).getType() == Type.NULL))
                {
                    return false;
                }
            }

            return true;
        }
        public boolean getPathIsSafe(ChessBoard board, int start, int end)
        {
            for(int i = start; i <= end; i++)
            {
                if(!board.cellIsSafe(new Move(i, 7, Move.Type.CASTLE), super.getColor()))
                {
                    return false;
                }
            }

            return true;
        }

        public void leftCastle(ChessBoard board)
        {
            Cell rook = board.getPiece(Rook.spawnRookDownLeft());

            Color color = super.getColor();

            //Filter by color
            if(color == Color.LIGHT)
            {
                //Move the king
                board.setPiece(this, posLeftCastleLightKing());
                board.setPiece(new Cell(spawnLightKingDown(), board), spawnLightKingDown());

                //Move the rook
                board.setPiece(rook, posLeftCastleLightRook());
                board.setPiece(new Cell(Rook.spawnRookDownLeft(), board), Rook.spawnRookDownLeft());
            }
            if(color == Color.DARK)
            {
                //Move the king
                board.setPiece(this, posLeftCastleDarkKing());
                board.setPiece(new Cell(spawnDarkKingDown(), board), spawnDarkKingDown());

                //Move the rook
                board.setPiece(rook, posLeftCastleDarkRook());
                board.setPiece(new Cell(Rook.spawnRookDownLeft(), board), Rook.spawnRookDownLeft());
            }
        }
        public void rightCastle(ChessBoard board)
        {
            Cell rook = board.getPiece(Rook.spawnRookDownRight());

            Color color = super.getColor();

            //Filter by color
            if(color == Color.LIGHT)
            {
                //Move the king
                board.setPiece(this, posRightCastleLightKing());
                board.setPiece(new Cell(spawnLightKingDown(), board), spawnLightKingDown());

                //Move the rook
                board.setPiece(rook, posRightCastleLightRook());
                board.setPiece(new Cell(Rook.spawnRookDownRight(), board), Rook.spawnRookDownRight());
            }
            else if(color == Color.DARK)
            {
                //Move the king
                board.setPiece(this, posRightCastleDarkKing());
                board.setPiece(new Cell(spawnDarkKingDown(), board), spawnDarkKingDown());


                //Move the rook
                board.setPiece(rook, posRightCastleDarkRook());
                board.setPiece(new Cell(Rook.spawnRookDownRight(), board), Rook.spawnRookDownRight());
            }
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

    //Retrieve positions
        public static Coordinates spawnLightKingUp()
        {
            return new Coordinates(3, 0);
        }
        public static Coordinates spawnLightKingDown()
        {
            return new Coordinates(4, 7);
        }
        public static Coordinates spawnDarkKingUp()
        {
            return new Coordinates(4, 0);
        }
        public static Coordinates spawnDarkKingDown()
        {
            return new Coordinates(3, 7);
        }

        //Castle
            //Light castle
            public static Coordinates posLeftCastleLightKing(){return new Coordinates(2, 7);}
            public static Coordinates posLeftCastleLightRook(){return new Coordinates(3, 7);}
            public static Coordinates posRightCastleLightKing(){return new Coordinates(6, 7);}
            public static Coordinates posRightCastleLightRook(){return new Coordinates(5, 7);}

                //Dark castle
            public static Coordinates posLeftCastleDarkKing(){return new Coordinates(1, 7);}
            public static Coordinates posLeftCastleDarkRook(){return new Coordinates(2, 7);}
            public static Coordinates posRightCastleDarkKing(){return new Coordinates(5, 7);}
            public static Coordinates posRightCastleDarkRook(){return new Coordinates(4, 7);}
}
