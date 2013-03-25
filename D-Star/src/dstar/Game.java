/*
 * Class where user input is turned into actions.
 */
package dstar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel {
    private Level level;
    private int current_level;
    private boolean game_completed;
    private static String[] level_names;
    private static Map<Integer, Level.Direction> dir;
    private static LevelGenerator level_generator;
    private static long steps;
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
                    steps++;
                    Game.this.repaint();
                } else if ( e.getKeyCode() == KeyEvent.VK_SPACE ) {
                    steps++;
                    level.swapHunter();
                    Game.this.repaint();
                } else if ( e.getKeyCode() == KeyEvent.VK_N ) {
                    level.targets = 0;
                }
                
                levelCompleted();
            }
        });
        
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent e ) {
                if ( e.getButton() == MouseEvent.BUTTON3 ) {
                    steps++;
                    level.swapHunter();
                    Game.this.repaint();
                    return;
                }
                
                int x = level.hunter_x * 64 + 32;
                int y = level.hunter_y * 64 + 32;
                if ( e.getX() - x > 32 && Math.abs(e.getY() - y) < 32 ) {
                    if ( level.moveHunter( Level.Direction.RIGHT ) ) {
                        steps++;
                        Game.this.repaint();
                    }
                } else if ( x - e.getX() > 32 && Math.abs(e.getY() - y) < 32 ) {
                    if ( level.moveHunter( Level.Direction.LEFT ) ) {
                        steps++;
                        Game.this.repaint();
                    }
                } else if ( y - e.getY() > 32 && Math.abs(e.getX() - x) < 32 ) {
                    if ( level.moveHunter( Level.Direction.UP ) ) {
                        steps++;
                        Game.this.repaint();
                    }
                } else if ( e.getY() - y > 32 && Math.abs(e.getX() - x) < 32 ) {
                    if ( level.moveHunter( Level.Direction.DOWN ) ) {
                        steps++;
                        Game.this.repaint();
                    }
                }
                
                levelCompleted();
            }
        });
        JButton left = new JButton( "←" );
        left.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.moveHunter( Level.Direction.LEFT );
                steps++;
                Game.this.repaint();
                levelCompleted();
            }
        });
        left.setFocusable( false );
        add( left );
        JButton right = new JButton( "→" );
        right.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.moveHunter( Level.Direction.RIGHT );
                steps++;
                Game.this.repaint();
                levelCompleted();
            }
        });
        right.setFocusable( false );
        add( right );
        JButton up = new JButton( "↑" );
        up.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.moveHunter( Level.Direction.UP );
                steps++;
                Game.this.repaint();
                levelCompleted();
            }
        });
        up.setFocusable( false );
        add( up );
        JButton down = new JButton( "↓" );
        down.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level.moveHunter( Level.Direction.DOWN );
                steps++;
                Game.this.repaint();
                levelCompleted();
            }
        });
        down.setFocusable( false );
        add( down );
        
        JButton sw = new JButton( "▒Switch" );
        sw.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                steps++;
                level.swapHunter();
                Game.this.repaint();
            }
        });
        sw.setFocusable( false );
        add( sw );
        
        setBackground( Color.decode( "0x7DF9FF" ) );
        setPreferredSize( new Dimension( 758, 630 ) );
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
        g.drawLine(0, 63, 767, 63);
    }
    
    /**
     * This method is called, when user ate all targets.
     */
    public void levelCompleted() {
        if ( level.targets == 0 ) {
            long diff = (System.nanoTime() - start_time) / 1000000;
            JOptionPane.showMessageDialog(Game.this, 
                    "steps: " + steps + "\n" + diff + " ms.");
            
            current_level++;
            if ( current_level < 100 ) {
                try {
                    level = level_generator.generate();
                    steps = 0;
                    start_time = System.nanoTime();
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
}