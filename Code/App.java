//Imports
import javax.swing.JFrame;

//Class
public class App 
{
    //Declare variables
        private static final int BOARD_SIZE = 800;

        private static JFrame frame;

    //Main
        public static void main(String[] args)
        {
            //Create the frame
            frame = new JFrame("Chess");

            //Configure the frame
            frame.setVisible(true);
            frame.setSize(BOARD_SIZE + BOARD_SIZE/20, BOARD_SIZE + BOARD_SIZE/20);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Start the game
            ChessGame chess = new ChessGame(BOARD_SIZE);
            frame.add(chess);
        }
}
