/*
 * Level contains the field and knows where the hunter can move.
 * It reacts, when hunter crosses a target or encounters a brick.
 */
package dstar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Level {
    public enum Direction { UP, DOWN, LEFT, RIGHT }
    public Cell[][] field;
    
    public Level() {
        field = new Cell[9][12];
    }
    
    public Level( String file_name ) throws IOException {
        this();
        loadLevel( file_name );
    }
    
    /**
     * Load the level.
     * @param file_name 
     */
    public void loadLevel( String file_name ) throws IOException {
        BufferedReader br = new BufferedReader( new FileReader( file_name ) );
        String line = br.readLine();
        
        int cell_pos_x = 0, cell_pos_y = 0;
        while ( line != null ) {
            for ( String cell_type : line.split( "\\s+" ) ) {
                switch ( cell_type ) {
                    case "e":
                        field[cell_pos_y][cell_pos_x] = new Cell( "empty" );
                        break;
                    case "b":
                        field[cell_pos_y][cell_pos_x] = new Cell( "brick" );
                        break;
                    case "t":
                        field[cell_pos_y][cell_pos_x] = new Cell( "target" );
                        break;
                    case "s":
                        field[cell_pos_y][cell_pos_x] = new Cell( "swapper" );
                        break;
                    case "h":
                        field[cell_pos_y][cell_pos_x] = new Cell( "hunter" );
                        break;
                        
                    default:
                        throw new IOException( "Illegal symbol in " + file_name );
                }
                cell_pos_x++;
            }
            cell_pos_y++;
            cell_pos_x = 0;
            line = br.readLine();
        }
        br.close();
    }
    
    /**
     * Moves the hunter in the direction dir.
     * @param dir
     * @return true if hunter was moved
     */
    public boolean moveHunter( Direction dir ) {
        return false;
    }
    
    /**
     * Swaps the hunter with the swap_brick.
     */
    public void swapHunter() {
    }
}
