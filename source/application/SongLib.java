
package application;

/*Zalak Shingala(zs238)
Jasmine Buttar(jb1620)
*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.SongController;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Lib.fxml"));
			GridPane rootLayout = (GridPane) loader.load();

			SongController controller = loader.getController();
			controller.start(primaryStage);

			Scene scene = new Scene(rootLayout, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Song Library by Zalak and Jasmine");
			primaryStage.show();
			stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
