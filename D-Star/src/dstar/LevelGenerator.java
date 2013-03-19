package dstar;

import java.io.IOException;
import java.util.Random;

public class LevelGenerator {
    private static String[] entities = { 
        "empty", "brick", "target", "hunter", "swapper"
    };
    private static Random rnd = new Random();

    private static boolean getNext( int[] num, int n, int r ) {
        int target = r - 1;
        num[target]++;
        if ( num[target] > ((n - (r - target)) + 1) ) {
            while ( num[target] > ((n - (r - target))) ) {
                target--;
                if (target < 0) {
                    break;
                }
            }
            if ( target < 0 ) {
                return true;
            }
            num[target]++;
            for ( int i = target + 1; i < num.length; i++ ) {
                num[i] = num[i - 1] + 1;
            }
        }
        return false;
    }
        
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
        
        int targets[][] = new int[5][2];
        while ( level.targets != 5 ) {
            while ( !level.field[row][col].equals( entities[0] ) ) {
                row = rnd.nextInt( level.field.length );
                col = rnd.nextInt( level.field[row].length );
            }
            level.targets++;
            level.field[row][col] = entities[2];
            targets[level.targets - 1][0] = col;
            targets[level.targets - 1][1] = row;
        }
        
        int l = 0;
        int[][] n = new int[9 * 12 - 7][2];
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 12; j++ ) {
                if ( (level.hunter_x != j || level.hunter_y != i) &&
                     (level.swapper_x != j || level.swapper_y != i) &&
                     (targets[0][0] != j || targets[0][1] != i) &&
                     (targets[1][0] != j || targets[1][1] != i) &&
                     (targets[2][0] != j || targets[2][1] != i) &&
                     (targets[3][0] != j || targets[3][1] != i) &&
                     (targets[4][0] != j || targets[4][1] != i) ) {
                    n[l][0] = i;
                    n[l][1] = j;
                    l++;
                }
            }
        }
        
        int[] res = new int[10];
        for (int i = 0; i < res.length * 7; i += 7) {
            res[i / 7] = i + rnd.nextInt( i / 7 + 7 );
        }
        boolean done = false;
        while ( !done ) {
            for ( int i = 0; i < res.length; i++ ) {
                level.field[n[res[i]][0]][n[res[i]][1]] = entities[1];
            }
            /*if ( feasible( level, 10 ) ) {
                break;
            }*/
            break;
            
            /*for ( int i = 0; i < res.length; i++ ) {
                level.field[n[res[i]][0]][n[res[i]][1]] = entities[0];
            }
            done = getNext( res, 9 * 12 - 8, 10 );*/
        } 
                
        return level;
    }
    
    private boolean feasible( Level level, int remain_depth ) throws IOException {
        if ( remain_depth > 0 ) {
            for ( int step = 0; step < 5; step++ ) {
                Level step_level = new Level( level );
                if ( step == 4 ) {
                    step_level.swapHunter();
                    if ( feasible( step_level, --remain_depth ) ) {
                        return true;
                    }
                } else if( step_level.moveHunter( Level.dir.get( step ) ) ) {
                    if ( step_level.targets == 0 ) {
                        return true;
                    } else if ( feasible( step_level, --remain_depth ) ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
