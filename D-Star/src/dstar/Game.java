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
import javax.swing.JPanel;

public class Game extends JPanel {
    private Level level;
    private int current_level;
    private String[] level_names;
    
    public Game() throws IOException {
        level_names = new String[1];
        level_names[0] = "level1.txt";
        
        current_level = 0;
        level = new Level( level_names[current_level] );
        
        addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e ) {
                // Some action
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
