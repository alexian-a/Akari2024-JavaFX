package iut.prj2024.view;

import java.net.URL;
import java.util.ResourceBundle;

import iut.prj2024.AkariApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class MainPageController implements Initializable {

    private AkariApp getAkariApp;
    private String TypeSelected;
    private String DiffSelected;

    @FXML
    private RadioButton rBut3x3;

    @FXML
    private RadioButton rBut5x5;
    
    @FXML
    private RadioButton rBut6x4;
    
    @FXML
    private RadioButton rBut7x7;
    
    @FXML
    private RadioButton rBut10x10;
    
    @FXML
    private RadioButton rBut14x14;

    @FXML
    private RadioButton rButDifFacile;
    
    @FXML
    private RadioButton rButDifMoyen;
    
    @FXML
    private RadioButton rButDifDifficile;

    @FXML
    private Button bJouer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}


    /////////// Action sur les boutons pour selection le type //////////
    @FXML
    private void selectType3x3() {
        rButDifFacile.setDisable(false);
        rButDifMoyen.setDisable(true);
        rButDifDifficile.setDisable(true);

        rButDifFacile.setSelected(true);
        this.DiffSelected = "easy";
        bJouer.setDisable(false);

        this.TypeSelected = "3x3";
    }

    @FXML
    private void selectType5x5() {
        rButDifFacile.setDisable(false);
        rButDifMoyen.setDisable(false);
        rButDifDifficile.setDisable(false);

        rButDifFacile.setSelected(true);
        this.DiffSelected = "easy";
        bJouer.setDisable(false);

        this.TypeSelected = "5x5";

    }

    @FXML
    private void selectType6x4() {
        rButDifFacile.setDisable(false);
        rButDifMoyen.setDisable(true);
        rButDifDifficile.setDisable(true);

        rButDifFacile.setSelected(true);
        this.DiffSelected = "easy";
        bJouer.setDisable(false);

        this.TypeSelected = "6x4";
    }

    @FXML
    private void selectType7x7() {
        rButDifFacile.setDisable(false);
        rButDifMoyen.setDisable(false);
        rButDifDifficile.setDisable(false);

        rButDifFacile.setSelected(true);
        this.DiffSelected = "easy";
        bJouer.setDisable(false);

        this.TypeSelected = "7x7";

    }

    @FXML
    private void selectType10x10() {
        rButDifFacile.setDisable(false);
        rButDifMoyen.setDisable(false);
        rButDifDifficile.setDisable(true);

        rButDifFacile.setSelected(true);
        this.DiffSelected = "easy";
        bJouer.setDisable(false);

        this.TypeSelected = "10x10";
    }

    @FXML
    private void selectType14x14() {
        rButDifFacile.setDisable(false);
        rButDifMoyen.setDisable(true);
        rButDifDifficile.setDisable(false);

        rButDifFacile.setSelected(true);
        this.DiffSelected = "easy";
        bJouer.setDisable(false);

        this.TypeSelected = "14x14";
    }


    ///// Action sur les boutons dif /////
    @FXML
    private void selectDiffFacile() {
        this.DiffSelected = "easy";
        bJouer.setDisable(false);
    }

    @FXML
    private void selectDiffMoyen () {
        this.DiffSelected = "hard";
        bJouer.setDisable(false);
    }

    @FXML
    private void selectDiffDifficile() {
        this.DiffSelected = "tricky";
        bJouer.setDisable(false);
    }

    @FXML
    private void buttonJouer() {
        this.getAkariApp.loadGame(TypeSelected, DiffSelected);
    }

    public void setAkariApp(AkariApp app) {
        this.getAkariApp = app;
    }
    
}
