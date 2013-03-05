/*
 * Level contains the field and knows where the hunter can move.
 * It reacts, when hunter crosses a target or encounters a brick.
 */
package dstar;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Level {
    public static enum Direction { UP, DOWN, LEFT, RIGHT }
    public static final int WIDTH = 12;
    public static final int HEIGHT = 9;
    public Cell[][] field;
    
    private static Map<String, BufferedImage> images;
    
    public Level() throws IOException {
        field = new Cell[HEIGHT][WIDTH];
        if ( images == null ) {
            images = new HashMap<>();
            for ( String key : new String[] { "brick", "target" } ) {
                images.put( key, ImageIO.read( new File( key + ".png" ) ) );
            }
        }
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
     * Draw the level if it was changed.
     * @param g 
     */
    public void drawLevel( Graphics g ) {
        g.drawImage( images.get( "brick" ), 0, 0, 32, 32, null);
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
