/*
 * Level contains the field and knows where the hunter can move.
 * It reacts, when hunter crosses a target or encounters a brick.
 */
package dstar;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Level {
    public static enum Direction { UP, DOWN, LEFT, RIGHT }
    public static final int WIDTH = 12;
    public static final int HEIGHT = 9;
    public static final int CELL_SIZE = 32;
    
    public int targets;
    public int hunter_x;
    public int hunter_y;
    public int swapper_x;
    public int swapper_y;
    public String[][] field;
    
    private static Map<String, BufferedImage> images;
    
    public Level() throws IOException {
        field = new String[HEIGHT][WIDTH];
        if ( images == null ) {
            images = new HashMap<>();
            for ( String key : new String[] { 
                "empty", "brick", "target", "hunter", "swapper" 
            } ) {
                String relative_path = "resources/" + key + ".png";
                images.put( key, ImageIO.read(
                    Level.class.getResourceAsStream( relative_path ) ) );
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
        InputStream is =
            Level.class.getResourceAsStream( "resources/" + file_name );
        BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
        String line = br.readLine();
        
        int cell_pos_x = 0, cell_pos_y = 0;
        while ( line != null ) {
            for ( String cell_type : line.split( "\\s+" ) ) {
                switch ( cell_type ) {
                    case "e":
                        field[cell_pos_y][cell_pos_x] = "empty";
                        break;
                    case "b":
                        field[cell_pos_y][cell_pos_x] = "brick";
                        break;
                    case "t":
                        targets++;
                        field[cell_pos_y][cell_pos_x] = "target";
                        break;
                    case "s":
                        swapper_x = cell_pos_x;
                        swapper_y = cell_pos_y;
                        field[cell_pos_y][cell_pos_x] = "swapper";
                        break;
                    case "h":
                        hunter_x = cell_pos_x;
                        hunter_y = cell_pos_y;
                        field[cell_pos_y][cell_pos_x] = "hunter";
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
        int x = 0, y = 0;
        for ( String[] row : field ) {
            for ( String cell_name : row ) {
                g.drawImage( images.get( cell_name ),
                             x, y, CELL_SIZE, CELL_SIZE, null);
                x += CELL_SIZE;
            }
            x = 0;
            y += CELL_SIZE;
        }
    }
    
    /**
     * Moves the hunter in the direction dir.
     * @param dir - direction in which hunter should be moved.
     * @return true if hunter was moved
     */
    public boolean moveHunter( Direction dir ) {
        boolean changed = false;
        switch ( dir ) {
            case UP:
                while ( targets > 0 && hunter_y > 0 &&
                        !field[hunter_y - 1][hunter_x].equals( "brick" ) &&
                        !field[hunter_y - 1][hunter_x].equals( "swapper" ) ) {
                    hunter_y--;
                    field[hunter_y + 1][hunter_x] = field[hunter_y][hunter_x];
                    field[hunter_y][hunter_x] = "hunter";
                    
                    if ( field[hunter_y + 1][hunter_x].equals( "target" ) ) {
                        targets--;
                        field[hunter_y + 1][hunter_x] = "empty";
                    }
                    changed = true;
                }
                break;
            case DOWN:
                while ( targets > 0 && hunter_y < HEIGHT - 1 &&
                        !field[hunter_y + 1][hunter_x].equals( "brick" ) &&
                        !field[hunter_y + 1][hunter_x].equals( "swapper" ) ) {
                    hunter_y++;
                    field[hunter_y - 1][hunter_x] = field[hunter_y][hunter_x];
                    field[hunter_y][hunter_x] = "hunter";
                    
                    if ( field[hunter_y - 1][hunter_x].equals( "target" ) ) {
                        targets--;
                        field[hunter_y - 1][hunter_x] = "empty";
                    }
                    changed = true;
                }
                break;
            case LEFT:
                while ( targets > 0 && hunter_x > 0 &&
                        !field[hunter_y][hunter_x - 1].equals( "brick" ) &&
                        !field[hunter_y][hunter_x - 1].equals( "swapper" ) ) {
                    hunter_x--;
                    field[hunter_y][hunter_x + 1] = field[hunter_y][hunter_x];
                    field[hunter_y][hunter_x] = "hunter";
                    
                    if ( field[hunter_y][hunter_x + 1].equals( "target" ) ) {
                        targets--;
                        field[hunter_y][hunter_x + 1] = "empty";
                    }
                    changed = true;
                }
                break;
            case RIGHT:
                while ( targets > 0 && hunter_x < WIDTH - 1 &&
                        !field[hunter_y][hunter_x + 1].equals( "brick" ) &&
                        !field[hunter_y][hunter_x + 1].equals( "swapper" ) ) {
                    hunter_x++;
                    field[hunter_y][hunter_x - 1] = field[hunter_y][hunter_x];
                    field[hunter_y][hunter_x] = "hunter";
                    
                    if ( field[hunter_y][hunter_x - 1].equals( "target" ) ) {
                        targets--;
                        field[hunter_y][hunter_x - 1] = "empty";
                    }
                    changed = true;
                }
                break;
        }
        
        return changed;
    }
    
    /**
     * Swaps the hunter with the swapper.
     */
    public void swapHunter() {
        field[swapper_y][swapper_x] = "hunter";
        field[hunter_y][hunter_x] = "swapper";
        
        int tmp = swapper_x;
        swapper_x = hunter_x;
        hunter_x = tmp;
        
        tmp = swapper_y;
        swapper_y = hunter_y;
        hunter_y = tmp;
    }
}
