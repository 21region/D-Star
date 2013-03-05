/*
 * Cell represents a part of the 9 x 12 field.
 * It contains its type and the corresponding picture.
 */
package dstar;

public class Cell {
    public String type;
    
    public Cell() {
    }
    
    public Cell( String type_name ) {
        type = type_name;
    }
}
