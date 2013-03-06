/*
 * Class where user input is turned into actions.
 */
package dstar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class Game extends JPanel {
    private Level level;
    private int current_level;
    private static String[] level_names;
    private static Map<Integer, Level.Direction> dir;
    
    public Game() throws IOException {
        dir = new HashMap<>();
        dir.put( KeyEvent.VK_UP, Level.Direction.UP);
        dir.put( KeyEvent.VK_DOWN, Level.Direction.DOWN);
        dir.put( KeyEvent.VK_LEFT, Level.Direction.LEFT);
        dir.put( KeyEvent.VK_RIGHT, Level.Direction.RIGHT);
        
        level_names = new String[1];
        level_names[0] = "level1.txt";
        
        current_level = 0;
        level = new Level( level_names[current_level] );
        
        addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e ) {
                if ( dir.containsKey( e.getKeyCode() ) &&
                     level.moveHunter( dir.get( e.getKeyCode() ) ) )
                    Game.this.repaint();
            }
        });
        
        setBackground( Color.decode( "0x7DF9FF" ) );
        setPreferredSize( new Dimension( 374, 278 ) );
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
        level.drawLevel( g );
    }
}
