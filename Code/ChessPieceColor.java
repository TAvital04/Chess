public enum ChessPieceColor 
{
    LIGHT, DARK, NULL;

    public static boolean isOpposite(ChessPieceColor one, ChessPieceColor two)
    {
        if(one == ChessPieceColor.LIGHT && two == ChessPieceColor.DARK)
        {
            return true;
        }
        else if(one == ChessPieceColor.DARK && two == ChessPieceColor.LIGHT)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}