/*
 * Class where user input is turned into actions.
 */
package dstar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel {
    private Level level;
    private int current_level;
    private boolean game_completed;
    private static String[] level_names;
    private static Map<Integer, Level.Direction> dir;
    private static LevelGenerator level_generator;
    private static long start_time;
    
    public Game() throws IOException {
        dir = new HashMap<>();
        dir.put( KeyEvent.VK_UP, Level.Direction.UP );
        dir.put( KeyEvent.VK_DOWN, Level.Direction.DOWN );
        dir.put( KeyEvent.VK_LEFT, Level.Direction.LEFT );
        dir.put( KeyEvent.VK_RIGHT, Level.Direction.RIGHT );
        
        level_names = new String[2];
        level_names[0] = "level1.txt";
        level_names[1] = "level2.txt";
        
        current_level = 0;
        level_generator = new LevelGenerator();
        level = level_generator.generate();
        
        start_time = System.nanoTime();
        
        addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e ) {
                if ( dir.containsKey( e.getKeyCode() ) &&
                     level.moveHunter( dir.get( e.getKeyCode() ) ) 
                ) {
                    Game.this.repaint();
                } else if ( e.getKeyCode() == KeyEvent.VK_SPACE ) {
                    level.swapHunter();
                    Game.this.repaint();
                }
                
                if ( level.targets == 0 ) {
                    long diff = (System.nanoTime() - start_time) / 1000000;
                    JOptionPane.showMessageDialog(Game.this, diff + " ms.");
                    levelCompleted();
                }
            }
        });
        
        setBackground( Color.decode( "0x7DF9FF" ) );
        setPreferredSize( new Dimension( 374, 278 ) );
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
        if ( game_completed ) {
            Font font = new Font( "Cooper black", Font.BOLD, 14 );
            g.setFont( font );
            g.drawString( "Game completed!", 130, 130 );
        } else {
            level.drawLevel( g );
        }
    }
    
    /**
     * This method is called, when user ate all targets.
     */
    public void levelCompleted() {
        current_level++;
        if ( current_level < 100 ) {
            try {
                level = level_generator.generate();
                //level.loadLevel( level_names[current_level] );
            } catch ( IOException e ) {
                System.out.println( e.getMessage() );
            }
        } else {
            game_completed = true;
        }
        repaint();
    }
}