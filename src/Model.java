import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Model {
    public Hair hair;
    public String eyes;
    public String eyebrows;
    public String skin;
    public String mouth;
    public Clothes clothes;
    public Controller controller;
    public Color background;
    public Integer eyeSize;
    public Integer eyebrowOffset;

    //init model with default Avatar values
    public Model(){
        this.hair = new Hair("long", Color.color(Math.random(), Math.random(), Math.random()));
        this.eyes = "eyes_default";
        this.skin = "brown";
        this.eyebrows = "brows_default";
        this.eyeSize = -1; //todo
        this.eyebrowOffset = -1; // todo
        this.mouth = "mouth_default";
        this.clothes = new Clothes();
        this.clothes.setJacket(Color.color(Math.random(), Math.random(), Math.random()));
        this.clothes.setLeftLapel(Color.color(Math.random(), Math.random(), Math.random()));
        this.clothes.setRightLapel(Color.color(Math.random(), Math.random(), Math.random()));
        this.clothes.setTshirt(Color.color(Math.random(), Math.random(), Math.random()));
        this.clothes.setTshirtNeck(Color.color(Math.random(), Math.random(), Math.random()));
        this.background = Color.WHITE;
    }

    public void setHair(Color color, String style){
        this.hair = new Hair(style, color);
    }

    public void updateView(){
         controller.updateAvatar();
    }


}
