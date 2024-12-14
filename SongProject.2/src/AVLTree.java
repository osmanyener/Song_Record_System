/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author osman
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author osman
 * @param <Item>
 */

  public class AVLTree<Item> {
public static ArrayList<Song> listOfSongs = new ArrayList<Song>();

    AVLNode<Item> root;

    public AVLTree() {
        root = null;
    }

    public int height(AVLNode<Item> d) {
        if (d == null) {
            return 0;
        } else {
            return d.height;
        }
    }

    public AVLNode<Item> rotateMyLeft(AVLNode<Item> focus) {
        AVLNode<Item> k = focus.left;
        focus.left = k.right;
        k.right = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }

    public AVLNode<Item> rotateMyRight(AVLNode<Item> focus) {
        AVLNode<Item> k = focus.right;
        focus.right = k.left;
        k.left = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }
    
    public AVLNode<Item> doubleRotateLeftSide(AVLNode focus) {
        focus.left = rotateMyRight(focus.left);
        return rotateMyLeft(focus);
    }

    public AVLNode<Item> doubleRotateRightSide(AVLNode focus) {
        focus.right = rotateMyLeft(focus.right);
        return rotateMyRight(focus);
    }

    int getBalanceLR(AVLNode<Item> focus) {
        if (focus == null) {
            return 0;
        }
        return height(focus.left) - height(focus.right);
    }
    int getBalanceRL(AVLNode<Item> focus) {
        if (focus == null) {
            return 0;
        }
        return height(focus.right) - height(focus.left);
    }
    
    public AVLNode<Item> insert(AVLNode focus, Item key, int index) {
        if (focus == null) {
            focus = new AVLNode(key, index);
        } else if (index < focus.index) {
            focus.left = insert(focus.left, key, index);
            if (getBalanceLR(focus) == 2) {
                if (index < focus.left.index) {
                    focus = rotateMyLeft(focus);
                } else {
                    focus = doubleRotateLeftSide(focus);
                }
            }
        } else if (index > focus.index) {
            focus.right = insert(focus.right, key, index);
            if (getBalanceRL(focus) == 2) {
                if (index > focus.right.index) {
                    focus = rotateMyRight(focus);
                } else {
                    focus = doubleRotateRightSide(focus);
                }
            }
        } else {
            focus.key = key;
        }
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        return focus;
    }
    public void insert(Item key, int index) {
        root = insert(root, key, index);
    }
    
    public void traverseLevelOrder(AVLNode focus) {
        if (focus == null) {
            focus = root;
        }
        java.util.LinkedList<AVLNode> que = new java.util.LinkedList<AVLNode>();
        que.add(focus);
        while (!que.isEmpty()) {
            AVLNode d = que.removeFirst();
            if (d.left != null) {
                que.addLast(d.left);
            }
            if (d.right != null) {
                que.addLast(d.right);
            }
            System.out.println(d);
        }
    }

    AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
     AVLNode deleteNode(AVLNode focus, int ind) {
        
        if (focus == null) {
            return focus;
        }
        if (ind < focus.index) {
            focus.left = deleteNode(focus.left, ind);
        }else if (ind > focus.index) {
            focus.right = deleteNode(focus.right, ind);
        }else {
            
            if ((focus.left == null) || (focus.right == null)) {
                AVLNode temp = null;
                if (null == focus.left) {
                    temp = focus.right;
                } else {
                    temp = focus.left;
                }
                if (temp == null) {
                    temp = focus;
                    focus = null;
                } else{
                    focus = temp;
                }                               
            } else { 
                AVLNode temp = minValueNode(focus.right);
                focus.key = temp.key;
                focus.index = temp.index;
                focus.right = deleteNode(focus.right, focus.index);
            }
        }
        if (focus == null) {
            return focus;
        }
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        int balance = getBalanceLR(focus);
        if (balance > 1 && getBalanceRL(focus.left) >= 0) {
            return rotateMyLeft(focus);
        }
        if (balance > 1 && getBalanceLR(focus.left) < 0) {
            focus.left = rotateMyRight(focus.left);
            return rotateMyLeft(focus);
        }
        if (balance < -1 && getBalanceRL(focus.right) <= 0) {
            return rotateMyRight(focus);
        }
        if (balance < -1 && getBalanceLR(focus.right) > 0) {
            focus.right = rotateMyLeft(focus.right);
            return rotateMyRight(focus);
        }
        return focus;
    }
 
    public AVLNode<Item> Search(Item key){
        return searchRecursive(this.root,key);
      }
    
    public AVLNode<Item> searchRecursive(AVLNode focus, Item key) {
     AVLNode parent = this.root; 
        if (focus == null) {
            return null;
        }
        if (focus.key.equals(key)){
          return focus;
        }
        if(focus.left != null && focus.right != null){
        
            if(focus.left.key.equals(key)){
           return focus.left;
            }else if(focus.right.key.equals(key)){
           return focus.right;
           
            }else{
                if(focus.left.left != null || focus.left.right != null){
                return searchRecursive(parent.left, key);      
                }else if(focus.right.right != null || focus.right.left != null){
                   return searchRecursive(parent.right, key); 
                }
            }   
               return null;
        }
          return null;
    }
      public void searchBetween(AVLNode focus, Integer lower, Integer upper) {

          ArrayList<Integer> findings = new ArrayList<>();
          int F = (Integer) focus.key;
          while (lower <= upper) {
              AVLNode L = Search((Item) lower);
              if (L != null) {

                  findings.add(L.index);
              }
              lower = lower + 1;
          }
          for (int i = 0; i < findings.size(); i++) {
              System.out.println(listOfSongs.get(findings.get(i)));
          }
      }
      
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner input = new Scanner(System.in);
       

        File sng = new File("Songs.txt");
        Scanner reader = new Scanner(sng);
        
        AVLTree<Integer> tree1 = new AVLTree();
        AVLTree<String> tree2 = new AVLTree();
        AVLTree<String> tree3 = new AVLTree();
        
    while(reader.hasNextLine()){
        String str = reader.nextLine();
        String[] strSongs = str.split(";");
        Song s1 = new Song(strSongs[0], strSongs[1], Integer.parseInt(strSongs[2]), strSongs[3], Integer.parseInt(strSongs[4]));
        listOfSongs.add(s1);
    }reader.close();
    
        for (int i = 0; i < listOfSongs.size(); i++) {
            tree1.insert(listOfSongs.get(i).ID, i);
        }
         for (int i = 0; i < listOfSongs.size(); i++) {
            tree2.insert(listOfSongs.get(i).songName, i);
        }
        for (int i = 0; i < listOfSongs.size(); i++) {
            tree3.insert(listOfSongs.get(i).artist, i);
        } 
        
       System.out.println("MENU");
       System.out.println("Welcome to song recording program..");
       int x = 0;
      
        
       while (x != 4) {
            System.out.println("Please choose one of the options below that you want to choose");
            System.out.println("1> Search for song");
            System.out.println("2> Delete the song");
            System.out.println("3> Print all songs");
            System.out.println("4> Exit the program");
            System.out.println("Enter a number:");
            x = input.nextInt();
            switch (x) {
                
                case 1:
                    System.out.println("How do you want to search for the song?");
                    System.out.println("1> With ID of song");
                    System.out.println("2> With name of song");
                    System.out.println("3> With Artist of song");
                    System.out.println("4> With range of two ID");
                    System.out.print("Enter a number: ");
                    int h = input.nextInt();
                    
                    switch (h) {
                        
                        case 1: {
                            System.out.println("Which ID do you want search?");
                            int y = input.nextInt();
                            if(tree1.searchRecursive(tree1.root, y) != null){
                             System.out.println(listOfSongs.get(tree1.searchRecursive(tree1.root, y).index));   
                            }else{
                                System.out.println("There is no song with ID " + y);
                            }
                            
                            break;
                        }
                        case 2: {
                            System.out.println("Which song name do you want to search?");
                            String y = input.next();
                            if(tree2.searchRecursive(tree2.root, y) != null){
                                System.out.println(listOfSongs.get(tree2.searchRecursive(tree2.root, y).index));
                            }else{
                                System.out.println("There is no song with song name " + y);
                            }
                            break;
                        }
                        case 3:
                            System.out.println("Which artist do you want to search?");
                            String y = input.next();
                            if(tree3.searchRecursive(tree3.root, y) != null){
                                System.out.println(listOfSongs.get(tree3.searchRecursive(tree3.root, y).index));
                            }else{
                                System.out.println("There is no song with artist name " + y);
                            }
                            break;
                        case 4:{
                            System.out.println("What range do you want to search for songs?");
                            System.out.print("Lower ID: ");
                            int low = input.nextInt();
                            System.out.print("Upper ID: ");
                            int upp = input.nextInt();
                            tree1.searchBetween(tree1.root, low, upp);
                            break;
                        }
                        default:
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Which ID do you want to delete?");
                    Integer d = input.nextInt();
                    int indx = tree1.Search(d).index;
                    System.out.println(indx);
                    tree1.root = tree1.deleteNode(tree1.root, indx);
                    tree2.root = tree2.deleteNode(tree2.root, indx);
                    tree3.root = tree3.deleteNode(tree3.root, indx);
                    
                    break;
                case 3:
                    System.out.println("Choose the option to print songs");
                    System.out.println("1> Print from memory (array)");
                    System.out.println("2> Print from program (tree)");
                    int opt = input.nextInt();
                    
                    if(opt == 1){
                     System.out.println("The available songs are:");
                    
                    for (int i = 0; i < listOfSongs.size(); i++) {
                        System.out.println(listOfSongs.get(i));
                    }
                    }else if(opt == 2){
                        
                        System.out.println("ID tree Level-Order");
                        tree1.traverseLevelOrder(tree1.root);
                        System.out.println("");
                        
                        System.out.println("Song Name tree Level-Order");
                        tree2.traverseLevelOrder(tree2.root);
                        System.out.println("");
                        
                        System.out.println("Artist Name tree Level-Order");
                        tree3.traverseLevelOrder(tree3.root);
                        System.out.println("");
                    }
                    
                    break;
                case 4:
                    System.out.println("Exited the program,Thank you.");
                
                default:
                    break;
            }
        }
    }
}
