/*
 * Starting point of the game.
 */
package dstar;

import java.io.IOException;
import javax.swing.JFrame;

public class DStar {
    public static void main( String[] args ) throws IOException {
        Game game = new Game();
        Screen screen = new Screen( game );
        screen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        screen.setSize( 500, 500 ); // Better set it in game.
        screen.setVisible( true );
        screen.setTitle( "D-Star" );
    }
}