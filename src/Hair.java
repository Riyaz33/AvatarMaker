import javafx.scene.paint.Color;


public class Hair {
    private Color color;
    private String style;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    Hair(String style, Color color){
        this.style = style;
        this.color = color;
    }


}
