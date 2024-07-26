package iut.prj2024.type;

import javafx.scene.control.Button;

public class ButtonParam {
    private Button b;
    private Boolean activateCG;
    private Boolean activateCD;
    private int PosX;
    private int PosY;

    public ButtonParam() {
        this.b = null;
        this.activateCG = false;
        this.activateCD = false;
        this.PosX = 0;
        this.PosY = 0;
    }


    public ButtonParam(Button b) {
        this.b = b;
        this.activateCG = false;
        this.activateCD = false;
        this.PosX = 0;
        this.PosY = 0;
    }

    public Button getButton() {
        return this.b;
    }

    public Boolean getStateLeft() {
        return this.activateCG;
    }

    public Boolean getStateRight() {
        return this.activateCD;
    }

    public int getX() {
        return this.PosX;
    }

    public int getY() {
        return this.PosY;
    }

    public void setStateLeft(Boolean s) {
        this.activateCG = s;
    }

    public void setStateRight(Boolean s) {
        this.activateCD = s;
    }

    public void setX(int x) {
        this.PosX = x;
    }

    public void setY(int y) {
        this.PosY = y;
    }

}
