package iut.prj2024.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;

import iut.prj2024.AkariApp;
import iut.prj2024.jeu.Cellule;
import iut.prj2024.jeu.JeuAraki;
import iut.prj2024.jeu.ReponsePlacement;
import iut.prj2024.jeu.TypeCellule;
import iut.prj2024.type.ButtonParam;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JeuController implements Initializable {

    private Stage dialoStage;
    private AkariApp getAkariApp;
    private Scene myScene;

    @FXML
    private Label details;

    @FXML
    private Label Score;
    private int scoreInt;

    @FXML
    private Label Temps;

    @FXML
    private Label Erreurs;

    @FXML
    private Button recommencer;

    @FXML
    private Button rMenu;

    @FXML
    private GridPane grilleJeu;

    @FXML
    private Menu reglesM;

    private String TradDiff;

    private String type;
    private String diff;

    private int longueur;
    private int hauteur;

    private ButtonParam[][] bTableau;

    private JeuAraki jeu;
    private boolean Win;

    private int timeS = 0;
    private int timeM = 0;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {}

    public void setDialogStage(Stage dialogStage, Scene scene) {
        this.dialoStage = dialogStage;
        this.myScene = scene;
    }

    public void setAkariApp(AkariApp app) {
        this.getAkariApp = app;
    }

    public void InitialiseTimer() {
        new Thread(() -> {
            while (!Win) {
                try {
                    timeS++;
                    if (timeS >= 60) {
                        timeS = 0;
                        timeM++;
                    }
                    if (timeM > 0) {
                        if (timeM < 10) {
                            if (timeS < 10) {
                                Platform.runLater(() -> Temps.setText("0" + timeM + ":0" + timeS));
                            } else {
                                Platform.runLater(() -> Temps.setText("0" + timeM + ":" + timeS));
                            }
                        } else {
                            if (timeS < 10) {
                                Platform.runLater(() -> Temps.setText(timeM + ":0" + timeS));
                            } else {
                                Platform.runLater(() -> Temps.setText(timeM + ":" + timeS));
                            }
                        }
                    } else {
                        if (timeS < 10) {
                            Platform.runLater(() -> Temps.setText("00:0" + timeS));
                        } else {
                            Platform.runLater(() -> Temps.setText("00:" + timeS));
                        }
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void diffTraduction() {
        if (diff == "easy") {
            TradDiff = "Facile";
            details.setText("Facile");
        } else if (diff == "tricky") {
            TradDiff = "Difficile";
            details.setText("Difficile");
        } else if (diff == "hard") {
            TradDiff = "Moyen";
            details.setText("Moyen");
        } else {
            TradDiff = "Custom";
            details.setText("Custom");
        }
    }

    public void initialzeGame(String type, String diff) {
        this.type = type;
        this.diff = diff;
        this.Win = false;

        diffTraduction();
        InitialiseTimer();

        if (type.charAt(0) == '1') {
            longueur = Integer.parseInt("" + type.charAt(0) + type.charAt(1));
        } else {
            longueur = Integer.parseInt("" + type.charAt(0));
        }
        if (type.length() > 3) {
            hauteur = Integer.parseInt("" + type.charAt(3) + type.charAt(4));
        } else {
            hauteur = Integer.parseInt("" + type.charAt(2));
        }

        // Dimensionnement et chargement de la grille

        RowConstraints rc = new RowConstraints();
        rc.setPrefHeight(1000);
        rc.setVgrow(Priority.ALWAYS);
        rc.setFillHeight(true);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPrefWidth(1000);
        cc.setHgrow(Priority.ALWAYS);
        cc.setFillWidth(true);

        grilleJeu.getColumnConstraints().set(0, cc);
        grilleJeu.getRowConstraints().set(0, rc);

        this.jeu = new JeuAraki(longueur, hauteur);

        this.bTableau = new ButtonParam[longueur + 1][hauteur + 1];

        if (type == "6x4") {
            int random = (int) ((Math.random() * (3 - 1)) + 1);
            jeu.chargerGrilleFromStream(
                    AkariApp.class.getResourceAsStream("jeu/dataset/" + type + "/" + diff + random + ".txt"));
        } else {
            jeu.chargerGrilleFromStream(
                    AkariApp.class.getResourceAsStream("jeu/dataset/" + type + "/" + diff + ".txt"));
        }

        // Création des boutons sur la grille

        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < hauteur; j++) {

                Cellule c = jeu.getCellule(i, j);
                Button button = new Button();
                ButtonParam b = new ButtonParam(button);

                b.setX(i);
                b.setY(j);

                if (c.getType() == TypeCellule.MUR) {
                    if (c.getNombreAmpoulesNecessaires() != -1) {
                        button.setText("" + c.getNombreAmpoulesNecessaires());
                    } else {
                        button.setText("#");
                    }
                    button.setStyle("-fx-background-color: black");
                    button.setTextFill(Color.WHITE);
                } else {
                    button.setText(" ");
                    button.setStyle("-fx-border-color: black ; -fx-border-width: 0.5px ; -fx-background-color: white");

                    button.setOnMouseClicked((e) -> {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            actionClickDroit(b);
                        } else {
                            actionClickGauche(b);
                        }
                    });

                    button.setOnMouseEntered((e) -> myScene.setCursor(Cursor.HAND));
                    button.setOnMouseExited((e) -> myScene.setCursor(Cursor.DEFAULT));
                }

                button.setPrefWidth(1000);
                button.setPrefHeight(1000);
                grilleJeu.add(button, i, j);

                bTableau[b.getX()][b.getY()] = b;


            }
        }
    }

    private void actionClickGauche(ButtonParam b) {

        this.scoreInt++;
        Score.setText("" + scoreInt);

        ReponsePlacement rep = jeu.placerAmpoule(b.getX(), b.getY());

        if (rep == ReponsePlacement.AJOUTE_AMPOULE) {
            b.setStateLeft(true);
            eclairerCase();
            Erreurs.setText("");
            if (jeu.verifierVictoire()) {
                finDePartie();
            }
        } else if (rep == ReponsePlacement.SUPPRIME_AMPOULE) {
            b.setStateLeft(false);
            eclairerCase();
            Erreurs.setText("");
        } else if (rep == ReponsePlacement.DEJA_ECLAIREE) {
            Erreurs.setText("Cet emplacement est déjà éclairé");
        } else if (rep == ReponsePlacement.SUR_MUR) {
            Erreurs.setText("Cet emplacement est un mur");
        } else if (rep == ReponsePlacement.NON_CONFORME_NOMBRE) {
            Erreurs.setText("Les murs adjacents ont trop de lumières");
        }
    }

    private void actionClickDroit(ButtonParam b) {
        if (b.getStateRight() == false) {
            b.setStateRight(true);
            b.getButton().setText("M");
            b.getButton().setStyle("-fx-border-color: black ; -fx-border-width: 0.5px ; -fx-background-color: lightblue");
        } else {
            b.setStateRight(false);
            b.getButton().setText("");
            eclairerCase();
        }
    }

    private void eclairerCase() {
        for (int i = 0; i < jeu.getLargeur(); i++) {
            for (int j = 0; j < jeu.getHauteur(); j++) {

                Cellule c = jeu.getCellule(i, j);
                ButtonParam b = bTableau[i][j];
                if (b.getButton().getText() != "M") {
                    if (c.getType() == TypeCellule.AMPOULE) {
                        b.getButton().setText("*");
                        b.getButton().setStyle("-fx-border-color: black ; -fx-border-width: 0.5px ; -fx-background-color: orange");
                    } else if (c.getType() == TypeCellule.ILLUMINEE) {
                        b.getButton().setText("-");
                        b.getButton().setStyle("-fx-border-color: black ; -fx-border-width: 0.5px ; -fx-background-color: yellow");
                    } else if (c.getType() == TypeCellule.VIDE) {
                        b.getButton().setText(" ");
                        b.getButton().setStyle("-fx-border-color: black ; -fx-border-width: 0.5px ; -fx-background-color: white");
                    }
                }
            }
        }
    }


    private void finDePartie() {
        this.Win = true;

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Félicitation !");
        alert.setHeaderText("Vous avez gagné !\nVoulez-vous refaire une partie ?");
        alert.setContentText("Difficulté : " + TradDiff + "\nNombre de coups : " + Score.getText() + "\nTemps : "
                + timeM + " min " + timeS + " sec\n");
        alert.initOwner(dialoStage);

        ButtonType quitter = new ButtonType("Quitter");

        alert.getButtonTypes().setAll(quitter, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> rep = alert.showAndWait();

        if (rep.orElse(null) == ButtonType.YES) {
            getAkariApp.loadGame(type, diff);
        } else if (rep.orElse(null) == ButtonType.NO) {
            getAkariApp.loadMainMenu();
        } else {
            dialoStage.close();
        }
    }

    @FXML
    private void recommencerAct() {
        getAkariApp.loadGame(type, diff);
    }

    @FXML
    private void retourMenuAct() {
        getAkariApp.loadMainMenu();
    }

    @FXML
    private void touches() {
        Alert reg = new Alert(AlertType.NONE);
        reg.setTitle("Touches");
        reg.setHeaderText("Voici les touches du jeu Araki 2024");
        reg.setContentText("(Clique gauche) - Placer une ampoule\n(Clique droit) - Marquer une case pour aider à la réflexion");
        reg.initOwner(dialoStage);

        reg.getButtonTypes().setAll(ButtonType.YES);

        Optional<ButtonType> rep = reg.showAndWait();

        if (rep.orElse(null) == ButtonType.YES) {
            reg.close();
        }
    }

    @FXML
    private void regles() {

        Alert reg = new Alert(AlertType.NONE);
        reg.setTitle("Règles");
        reg.setHeaderText("Voici les règles du jeu Araki 2024");
        reg.setContentText(
                "1 - Il faut remplir toutes les cases de lumière.\n2 - Vous ne pouvez pas poser de lampe sur un mur\n3 - Certain murs contienent des numéros, c'est le nombre de lampes qui doivent être à côter.\n4 - Vous ne pouvez pas placer de lampe ou il y a déjà de la lumière");
        reg.initOwner(dialoStage);

        reg.getButtonTypes().setAll(ButtonType.YES);

        Optional<ButtonType> rep = reg.showAndWait();

        if (rep.orElse(null) == ButtonType.YES) {
            reg.close();
        }
    }

}