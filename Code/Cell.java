import java.awt.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class Cell
//Qualities that all cells share
{
    //Declare variables
        private static int cellSize; 

        private Coordinates pos;

        private Color color;
        private Type type;

        private final String CELL_SELECTED_PATH = "Assets\\Cell_Selected.png";
        private final String CELL_VALIDSELECT_PATH = "Assets\\Cell_Valid.png";
        private boolean selected = false;
        private boolean validSelect = false;

        private int age;
        
    //Constructor
        public Cell(Coordinates pos, ChessBoard board)
        {
            start(pos, board);
        }
        public Cell(int col, int row, ChessBoard board)
        {
            start(new Coordinates(col, row), board);
        }

        public void start(Coordinates pos, ChessBoard board)
        {
            this.pos = pos;

            this.color = Color.NULL;
            this.type = Type.NULL;

            this.age = -1;
        }

    //Enums
        public enum Color 
        {
            LIGHT, DARK, NULL;
        
            public static boolean isOpposite(Color one, Color two)
            {
                if(one == Color.LIGHT && two == Color.DARK)
                {
                    return true;
                }
                else if(one == Color.DARK && two == Color.LIGHT)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        public enum Type 
        {
            NULL, PAWN, BISHOP, KNIGHT, ROOK, QUEEN, KING;
        }

    //Drawing methods
        public void draw(Graphics graphic)
        {
            if(this.selected == true)
            {
                try
                {
                    graphic.drawImage(ImageIO.read(new File(CELL_SELECTED_PATH)), this.pos.getX() * cellSize, this.pos.getY() * cellSize, null);

                }
                catch(Exception e)
                {
                    System.out.println("Could not load cell selected");
                }
            }

            if(this.validSelect == true)
            {
                try
                {
                    graphic.drawImage(ImageIO.read(new File(CELL_VALIDSELECT_PATH)), this.pos.getX() * cellSize, this.pos.getY() * cellSize, null);
                }
                catch(Exception e)
                {
                    System.out.println("Could not load cell valid select");
                }
            }
        }

        public void select()
        {
            this.selected = true;
        }
        public void validSelect()
        {
            this.validSelect = true;
        }

        public void deselect()
        {
            this.selected = false;
            this.validSelect = false;
        }

        public ArrayList<Move> calculateValidMoves(ChessBoard board)
        {
            return new ArrayList<Move>();
        }

    //Other methods
        public boolean canMoveAndEatThere(ChessBoard board, int col, int row, Color color)
        //This tests if the piece can move and eat somewhere, works for most pieces
        {
            if(board.inBounds(col, row))
            {
                if(Color.isOpposite(board.getPiece(col, row).getColor(), color))
                {
                    return true;
                }
                else if(board.getPiece(col, row).getType() == Type.NULL)
                {
                    return true;
                }
            }
            
            return false;
        }
        public boolean canMoveThere(ChessBoard board, int col, int row)
        //Applied for where a rook, for example, must stop before eating the first piece it sees
        {
            if(board.inBounds(col, row))
            {
                if(board.getPiece(col, row).getType() == Type.NULL)
                {
                    return true;
                }
            }
            
            return false;
        }
        public boolean canEatThere(ChessBoard board, int col, int row, Color color)
        //Applied for where a rook, row example, may stop to eat
        {
            if(board.inBounds(col, row))
            {
                if(Color.isOpposite(board.getPiece(col, row).getColor(), color))
                {
                    return true;
                }
            }
            return false;
        }

        public boolean inEnemySight(ChessBoard board)
        {


            return false;
        }

    //Getters/setters
        //Cell size
        public static int getCellSize()
        {
            return cellSize;
        }
        public static void setCellSize(int cs)
        {
            cellSize = cs;
        }

        //Position
        public Coordinates getPos()
        {
            return this.pos;
        }
        public void setPos(int col, int row, ChessBoard board)
        {
            this.setCol(col);
            this.setRow(row);
        }
        public void setPos(Coordinates destination, ChessBoard board)
        {
            this.pos = destination;
        }

        public int getCol()
        {
            return pos.getX();
        }
        public void setCol(int col)
        {
            this.pos.setX(col);
        }

        public int getRow()
        {
            return pos.getY();
        }
        public void setRow(int row)
        {
            this.pos.setY(row);
        }
        
        //Selected
        public boolean getSelected()
        {
            return this.selected;
        }
        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }

        //Color
        public Color getColor()
        {
            return color;
        }
        public void setColor(Color color)
        {
            this.color = color;
        }

        //Type
        public Type getType()
        {
            return type;
        }
        public void setType(Type type)
        {
            this.type = type;
        }

        //Age
        public int getAge()
        {
            return this.age;
        }
        public void setAge(int n)
        {
            this.age = n;
        }
}