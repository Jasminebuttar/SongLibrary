package view;

/*Zalak Shingala(zs238)
Jasmine Buttar(jb1620)
*/
import java.util.Comparator;

import model.Song;

public class SongCompare implements Comparator<Song> {

	@Override
	public int compare(Song s1, Song s2) {
		// TODO Auto-generated method stub
		return s1.getTitle().toLowerCase().compareTo(s2.getTitle().toLowerCase());

	}

}
