
package model;

/*Zalak Shingala(zs238)
Jasmine Buttar(jb1620)
*/
public class Song {
	private String title;
	private String artist;
	private String album;
	private String year;

	public Song(String t, String art, String alb, String y) {
		this.title = t;
		this.artist = art;
		this.album = alb;
		this.year = y;
	}

	public Song(String t, String art) {
		this(t, art, "", "");
	}

	public String getTitle() {
		return this.title;
	}

	public String getArtist() {
		return this.artist;
	}

	public String getAlbum() {
		return this.album;
	}

	public String getYear() {
		return this.year;
	}

	public void setAlbum(String a) {
		this.album = a;
	}

	public void setYear(String y) {
		this.year = y;
	}

	public String toString() {
		return this.title;
	}
	
}