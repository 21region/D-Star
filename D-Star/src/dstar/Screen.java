/*
 * Screen is a class that holds the main frame
 * and retransmits messages from the user to the game.
 */
package dstar;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JFrame {
    public Screen( JPanel event_processor ) {
        add( event_processor );
        event_processor.setFocusable( true );
    }
}