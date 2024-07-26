package iut.prj2024; 


import java.io.IOException;

import iut.prj2024.view.JeuController;
import iut.prj2024.view.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AkariApp extends Application {

    private BorderPane bPane;
    private Stage primaryStage;

	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadMainMenu();
    }

    public void loadMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AkariApp.class.getResource("view/MainPage.fxml"));
        
            this.bPane = loader.load();

            MainPageController ctrl = loader.getController();
            ctrl.setAkariApp(this);

            Scene scene = new Scene(bPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Araki 2024 : Création de la partie ");
            primaryStage.show();

        } catch (IOException e ) {
            System.exit(1);
        }
    }


    public void loadGame(String type, String diff) {
        try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( AkariApp.class.getResource("view/Jeu.fxml"));
			
			BorderPane vueSaisie = loader.load();
			
			Scene scene = new Scene(vueSaisie);
			
			primaryStage.setTitle("Araki 2024 : En Jeu !");
			primaryStage.setScene(scene);
			
			JeuController ctrl = loader.getController();
			ctrl.setDialogStage(primaryStage, scene);
            ctrl.initialzeGame(type, diff);
            ctrl.setAkariApp(this);

			primaryStage.show();
						
		} catch (IOException e) {
			System.out.println("Ressource FXML non disponible : SaisieMembres");
			System.exit(1);
		}	
    }

    public static void main2(String[] args) {
        Application.launch(args);
    }

}