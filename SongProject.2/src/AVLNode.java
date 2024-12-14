/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author osman
 * @param <Item>
 */
public class AVLNode<Item> {
    public int index; 
    public Item key;
    public AVLNode left;
    public AVLNode right;
    public int height = 1; 
    
    public AVLNode(Item key, int index){
        this.key= key;
        this.index = index;
        left = null;
        right = null;
        height = 1;
    }
    
    @Override
    public String toString() {
        return ("Node(index= " + index + ", key= " + key.toString() + ")");
    }
    
}
