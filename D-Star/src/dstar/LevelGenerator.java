package dstar;

import java.io.IOException;
import java.util.Random;

public class LevelGenerator {
    private static String[] entities = { 
        "empty", "brick", "target", "hunter", "swapper"
    };
    private static Random rnd = new Random();
        
    public Level generate() throws IOException {
        Level level = new Level();
        
        for ( int row = 0; row < level.field.length; row++ ) {
            for ( int col = 0; col < level.field[row].length; col++ ) {
                level.field[row][col] = entities[0];
            }
        }
        
        int row = rnd.nextInt( level.field.length );
        int col = rnd.nextInt( level.field[row].length );
        level.hunter_x = col;
        level.hunter_y = row;
        level.field[row][col] = entities[3];
        
        while ( !level.field[row][col].equals( entities[0] ) ) {
            row = rnd.nextInt( level.field.length );
            col = rnd.nextInt( level.field[row].length );
        }
        level.swapper_x = col;
        level.swapper_y = row;
        level.field[row][col] = entities[4];
        
        while ( level.targets != 5 ) {
            while ( !level.field[row][col].equals( entities[0] ) ) {
                row = rnd.nextInt( level.field.length );
                col = rnd.nextInt( level.field[row].length );
            }
            level.targets++;
            level.field[row][col] = entities[2];
        }
        
        int bricks_num = 0;
        final int bricks_gen = rnd.nextInt( 9 * 12 - 6 );
        while ( bricks_num != bricks_gen ) {
            while ( !level.field[row][col].equals( entities[0] ) ) {
                row = rnd.nextInt( level.field.length );
                col = rnd.nextInt( level.field[row].length );
            }
            bricks_num++;
            level.field[row][col] = entities[1];
        }
        
        return level;
    }
}
