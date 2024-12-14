/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author osman
 */
public class Song {
    
    String songName;
    String artist;
    int ID;
    String genre;
    int year;

    public Song() {
    }

    
    public Song(String songName, String artist, int ID, String genre, int year) {
        this.songName = songName;
        this.artist = artist;
        this.ID = ID;
        this.genre = genre;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Song name: " + songName + ", Artist: " + artist + ", ID: "  + ID + ", Genre: " + genre + ", year: " + year;
    }
}
