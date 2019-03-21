package view;

/*Zalak Shingala(zs238)
Jasmine Buttar(jb1620)
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Song;

public class SongController {
	@FXML
	Button add;
	@FXML
	Button edit;
	@FXML
	Button delete;
	@FXML
	Text songList;
	@FXML
	ListView<Song> listView;
	private ObservableList<Song> items;
	@FXML
	TextField name;
	@FXML
	TextField artist;
	@FXML
	TextField album;
	@FXML
	TextField year;
    @FXML
	Text details;

	public void start(Stage primaryStage) {
		items = FXCollections.observableArrayList(read("source/songs.txt"));
		listView.setItems(items);

		if (listView != null) {
			listView.getSelectionModel().selectFirst();
		}

		showSongDetails();
		listView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> showSongDetails());
		// TODO Auto-generated method stub
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent win) {
				PrintWriter writer;
				try {
					File file = new File("source/songs.txt");
					file.createNewFile();
					writer = new PrintWriter(file);
					for (Song s : items) {
						writer.println(s.getTitle());
						writer.println(s.getArtist());
						writer.println(s.getAlbum());
						writer.println(s.getYear());

					}
					writer.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		primaryStage.close();

	}

	// Add Button
	public void add(ActionEvent e) {

		String sName = name.getText();
		name.clear();
		String sArtist = artist.getText();
		artist.clear();
		String sAlbum = album.getText();
		album.clear();
		String sYear = year.getText();
		year.clear();
		listView.getSelectionModel().selectFirst();
		if (sName.equals("") || sArtist.equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Cannot leave Song Title or Song Artist empty.");
			alert.showAndWait();
		} else {
			if (!(MatchFound(items, sName, sArtist))) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Adding New Item");
				alert.setContentText("Are you sure you want to add this item?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					items.add(new Song(sName, sArtist, sAlbum, sYear));
					FXCollections.sort(items, new SongCompare());
					listView.setItems(items);
					listView.getSelectionModel().selectFirst();
				}

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Same title/artist combination already exists in file");
				alert.showAndWait();
			}
			showSongDetails();
		}
	}

	// Delete Button
	public void delete(ActionEvent e) {

		final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
		if (items.size() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Nothing is selected to delete");
			alert.showAndWait();
		} else {
			if ((selectedIdx==(items.size()-1))) {
				listView.getSelectionModel().selectPrevious();
			}

			listView.getSelectionModel().selectNext();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Deleting Item");
			alert.setContentText("Are you sure you want to delete this item?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				items.remove(selectedIdx);
				listView.setItems(items);
			}
           }
	}

	// Edit button
	public void edit(ActionEvent e) {

		if (items.size() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Nothing is selected to edit");
			alert.showAndWait();
		} else {
			final int selectedIdx = listView.getSelectionModel().getSelectedIndex();

			String sName = name.getText();
			String sArtist = artist.getText();
			String sAlbum = album.getText();
			String sYear = year.getText();

			if (sName.equals("") || sArtist.equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Cannot leave Song Title or Song Artist empty.");
				alert.showAndWait();
			} else {

				if (!(MatchFound(items, sName, sArtist))) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText("Edit Item");
					alert.setContentText("Are you sure you want to edit this item?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						items.set(selectedIdx, new Song(sName, sArtist, sAlbum, sYear));
						FXCollections.sort(items, new SongCompare());
						listView.setItems(items);
						listView.getSelectionModel().selectFirst();

					}

				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("WARNING");
					alert.setHeaderText("Same title/artist combination already exists in file");
					alert.showAndWait();
				}
			}
			showSongDetails();
		}

	}

	// Method to check if the Song title and Song Artist combination already
	// exists in the library
	public boolean MatchFound(ObservableList<Song> items, String sName, String sArtist) {
		for (int i = 0; i < items.size(); i++) {
			if ((items.get(i).getTitle().toLowerCase().equals(sName.toLowerCase()))) {
				if (items.get(i).getArtist().toLowerCase().equals(sArtist.toLowerCase())) {
					return true;
				}
            }

		}
		return false;
	}

	// show Song Details
	private void showSongDetails() {
		String nm, art, alb, yr;
		if (listView.getSelectionModel().getSelectedIndex() < 0) {
			return;
		}

		Song song = listView.getSelectionModel().getSelectedItem();
		nm = song.getTitle();
		art = song.getArtist();
		alb = song.getAlbum();
		yr = song.getYear();
		details.setText("TITLE: " + nm + "\n\nARTIST: " + art + "\n\nALBUM: " + alb + "\n\nYEAR: " + yr);
	}

	// read from external txt file
	private ArrayList<Song> read(String filePathName) {
		ArrayList<Song> songs = new ArrayList<Song>();
		BufferedReader br;
		Path filePath = Paths.get(filePathName);
		try {

			if (!new File(filePathName).exists()) {
				return songs;
			}
			br = Files.newBufferedReader(filePath);
			String line = br.readLine();

			while (line != null) {
				String title = line;
				line = br.readLine();
				String artist = line;
				line = br.readLine();
				String album = line;
				line = br.readLine();
				String year = line;
				Song temp = new Song(title, artist, album, year);
				songs.add(temp);
				line = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		Collections.sort(songs, new SongCompare());
		return songs;
	}
}
