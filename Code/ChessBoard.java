import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

public class ChessBoard 
{
    //Declare variables
        private static final int GRID_SIZE = 8;
            //represents the fact that a chessboard ALWAYS has an 8x8 grid

        private Cell[][] chessBoard;
            /*
            * This is THE chessboard.
            * The squares and the other stuff are visuals for humans or lists for keeping track
            * But when the computer opens its eyes, this is what it is looking at
            */

    //Constructor
        public ChessBoard(int boardSize)
        {
            chessBoard = new Cell[GRID_SIZE][GRID_SIZE];

            Cell.setCellSize(boardSize / GRID_SIZE);

            instantiateTheCells();
        }

    //Instantiation methods
        public void instantiateTheCells()
        {
            for(int i = 0; i < GRID_SIZE; i++)
            {
                for(int j = 0; j < GRID_SIZE; j++)
                {
                    chessBoard[i][j] = new Cell(new Coordinates(j, i), this);
                }
            }
        }
    
    //Drawing methods (game instantiation)
        public void paintTheBoard(Graphics graphic)
        //Is called upon during the instantiation of graphic in paintComponent()
        {
            fillInTheSquares(graphic);
            instantiateTheChessPieces(graphic);
            repaintTheBoard(graphic);
        }

        public void repaintTheBoard(Graphics graphic)
        //Is called upon when repaint() recalls paintComponent() after the board has been set up
        {
            fillInTheSquares(graphic);
            for(Cell[] row: chessBoard)
            {
                for(Cell cell: row)
                {
                    cell.draw(graphic);
                }
            }
        }

        public void fillInTheSquares(Graphics graphic)
        //Is responsible for the checkerboard pattern
        {
            for(int row = 0; row < GRID_SIZE; row++)
            //For every row of cells
            {
                if(row % 2 == 0)
                //If the row number is even, it starts with a light square
                {
                    for(int col = 0; col < GRID_SIZE; col++)
                    //For every column in a row
                    {
                        if(col % 2 == 0)
                        //If the column number is even, set the cell to light
                        {
                            graphic.setColor(Color.WHITE);
                        }
                        else
                        {
                            graphic.setColor(Color.BLACK);
                        }

                        //Print the cell
                        graphic.fillRect(col * Cell.getCellSize(), row * Cell.getCellSize(), Cell.getCellSize(), Cell.getCellSize());
                    }
                }
                else
                //If the row number is odd, it starts with a dark square
                {
                    for(int col = 0; col < GRID_SIZE; col++)
                    //For every column in a row
                    {
                        if(col % 2 == 0)
                        //If the column number is even, set the cell to dark
                        {
                            graphic.setColor(Color.BLACK);
                        }
                        else
                        {
                            graphic.setColor(Color.WHITE);
                        }

                        //Print the cell
                        graphic.fillRect(col * Cell.getCellSize(), row * Cell.getCellSize(), Cell.getCellSize(), Cell.getCellSize());
                    }
                }
            }
        }

        public void instantiateTheChessPieces(Graphics graphic)
        /*
        *
        *  8 [R][B][N][Q][K][N][B][R]
        *  7 [P][P][P][P][P][P][P][P]
        *  6 [ ][ ][ ][ ][ ][ ][ ][ ]
        *  5 [ ][ ][ ][ ][ ][ ][ ][ ]
        *  4 [ ][ ][ ][ ][ ][ ][ ][ ]
        *  3 [ ][ ][ ][ ][ ][ ][ ][ ]
        *  2 [P][P][P][P][P][P][P][P]
        *  1 [R][B][N][Q][K][N][B][R]
        *     A  B  C  D  E  F  G  H
        *
        * Places all the pieces on the board
        * Only runs once during startup
        *
        */
        {
            instantiateThePawns(graphic, Cell.Color.LIGHT);
            instantiateTheHeavies(graphic, Cell.Color.LIGHT);

            instantiateThePawns(graphic, Cell.Color.DARK);
            instantiateTheHeavies(graphic, Cell.Color.DARK);
        }
        public void instantiateThePawns(Graphics graphic, Cell.Color color)
        {
            //Determine what row to fill the pawns with
            int row = getPawnRow(color) - 1; //-1 because arrays start at 0 but the board starts at 1

            //Fill that row with pawns, then draw them on the screen
            for(int col = 0; col < GRID_SIZE; col++)
            {
                chessBoard[row][col] = new Pawn(new Coordinates(col, row), color, this);

                chessBoard[row][col].draw(graphic);
            }
        }
        public int getPawnRow(Cell.Color color)
        {
            if(color == Cell.Color.LIGHT)
            //[First player, light, white] pawns are on row two
            {
                return 7;
            }
            else
            //[Second player, dark, black] pawns are on row 7
            {
                return 2;
            }
        }
        public void instantiateTheHeavies(Graphics graphic, Cell.Color color)
        {
            int row = getHeavyRow(color) - 1; //-1 because arrays start at 0 but the board starts at 1

            chessBoard[row][0] = new Rook(new Coordinates(0, row), color, this); //Official board positions are hard-coded
            chessBoard[row][0].draw(graphic);

            chessBoard[row][1] = new Knight(new Coordinates(1, row), color, this);
            chessBoard[row][1].draw(graphic);

            chessBoard[row][2] = new Bishop(new Coordinates(2, row), color, this);
            chessBoard[row][2].draw(graphic);

            chessBoard[row][3] = new Queen(new Coordinates(3, row), color, this);
            chessBoard[row][3].draw(graphic);

            chessBoard[row][4] = new King(new Coordinates(4, row), color, this);
            chessBoard[row][4].draw(graphic);
            
            chessBoard[row][5] = new Bishop(new Coordinates(5, row), color, this);
            chessBoard[row][5].draw(graphic);

            chessBoard[row][6] = new Knight(new Coordinates(6, row), color, this);
            chessBoard[row][6].draw(graphic);

            chessBoard[row][7] = new Rook(new Coordinates(7, row), color, this);
            chessBoard[row][7].draw(graphic);
        }
        public int getHeavyRow(Cell.Color color)
        {
            if(color == Cell.Color.LIGHT)
            //[First player, light, white] heavies are on row 1
            {
                return 8;
            }
            else
            //[Second player, dark, black] heavies are on row 8
            {
                return 1;
            }
        }

    //Drawing methods (post setup)
        public void deselect()
        {
            for(Cell[] row: chessBoard)
            {
                for(Cell piece: row)
                {
                    piece.deselect();
                }
            }
        }

        public void showValidMoves(ArrayList<Move> moves)
        {
            for(Move move: moves)
            {
                getPiece(move.getPos()).validSelect();
            }
        }

        public void flip()
        {
            for(int i = 0; i < GRID_SIZE / 2; i++)
            {
                for(int j = 0; j < GRID_SIZE; j++)
                {
                    swap(getPiece(j, i), getPiece(GRID_SIZE - 1 - j, GRID_SIZE - 1 - i));
                }
            }
        }
        public void swap(Cell one, Cell two)
        {
            //Set the game state
            chessBoard[one.getRow()][one.getCol()] = two;
            chessBoard[two.getRow()][two.getCol()] = one;

            //Set the visual state
            Coordinates temp = one.getPos();
            one.setPos(two.getPos(), this);
            two.setPos(temp, this);
        }

    //Interaction methods
        public boolean inBounds(int col, int row)
        {
            if(col >= 0 && col < GRID_SIZE)
            {
                if(row >= 0 && row < GRID_SIZE)
                {
                    return true;
                }
            }

            return false;
        }

        public void move(Cell activePiece, Move move)
        //Handles moving a piece after it has been verified. This is called in ChessGame under chooseLocation
        {
            Coordinates activePiecePos = activePiece.getPos();

            if(move.getType() == Move.Type.NORMAL || move.getType() == Move.Type.ENPASSANT)
            {
                setPiece(activePiece, move.getPos());
                setPiece(new Cell(activePiecePos, this), activePiecePos);
            }

            if(move.getType() == Move.Type.ENPASSANT)
            {
                setPiece(new Cell(move.getPos().getX(), move.getPos().getY() + 1, this), move.getPos().getX(), move.getPos().getY() + 1);
            }
        }

        public boolean cellIsSafe(Move move, Cell.Color color)
        {
            for(Cell[] row: chessBoard)
            {
                for(Cell cell: row)
                //For all the cells on the board
                {
                    if(Cell.Color.isOpposite(cell.getColor(), color))
                    //If the cell is the opposite color of the move
                    {
                        if(inEnemySight(move, cell))
                        //If the cell can see the move, the move is not safe
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
        public boolean inEnemySight(Move move, Cell attacker)
        {
            for(Move attack: attacker.calculateValidMoves(this, move.getType()))
            //For all the possible attacks
            {
                if(attack.getPos().equals(move.getPos()))
                //If the attack is at the same position of the move, the move is not safe
                {
                    return false;
                }
            }

            return true;
        }

    //Other methods
        public void age()
        //Some pieces have actions that only work when they are of some age 
            //Like pawns need to have been moved once for en passant and kings naught for castling
        {
            for(Cell[] row: chessBoard)
            {
                for(Cell cell: row)
                {
                    if(cell instanceof Pawn)
                    {
                        Pawn pawn = (Pawn)cell;
                        if(!pawn.isAtSpawn())
                        {
                            pawn.setAge(pawn.getAge() + 1);
                        }
                    }

                    if(cell instanceof King)
                    {
                        King king = (King)cell;
                        if(!king.isAtSpawn())
                        {
                            king.setAge(king.getAge() + 1);
                        }
                    }

                    if(cell instanceof Rook)
                    {
                        Rook rook = (Rook)cell;
                        if(!rook.isAtSpawn())
                        {
                            rook.setAge(rook.getAge() + 1);
                        }
                    }
                }
            }
        }

    //Getters/Setters
        public Cell getPiece(int col, int row)
        {
            return chessBoard[row][col];
        }
        public Cell getPiece(MouseEvent click)
        {
            return chessBoard[click.getY() / Cell.getCellSize()][click.getX() / Cell.getCellSize()];
        }
        public Cell getPiece(Coordinates pos)
        {
            return chessBoard[pos.getY()][pos.getX()];
        }

        public void setPiece(Cell cell, Coordinates destination)
        {
            //Set the game state
            chessBoard[destination.getY()][destination.getX()] = cell;

            //Set the visual state
            cell.setPos(destination, this);
        }
        public void setPiece(Cell cell, Cell destination)
        {
            //Set the game state
            chessBoard[destination.getRow()][destination.getCol()] = cell;

            //Set the visual state
            cell.setPos(destination.getPos(), this);
        }
        public void setPiece(Cell cell, int col, int row)
        {
            //Set the game state
            chessBoard[row][col] = cell;

            //Set the visual state
            cell.setPos(col, row, this);
        }

        public int getGRID_SIZE()
        {
            return GRID_SIZE;
        }
}
