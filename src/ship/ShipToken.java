package ship;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * Created by Brian on 12/17/2015.
 */
public class ShipToken extends MeshView {

    public static double SMALL_SHIP_TEMPLATE_WIDTH_MM = 40.0;
    public static double LARGE_SHIP_TEMPLATE_WIDTH_MM = 80.0;

    PhongMaterial texturedMaterial = new PhongMaterial();
    private boolean selected = false;
    private boolean highlighted = false;

    private static Mesh getRectangleMesh(float width, float height) {
        float[] points = {
                -width/2.0f,  height/2.0f, 0.0f,
                -width/2.0f, -height/2.0f, 0.0f,
                 width/2.0f,  height/2.0f, 0.0f,
                 width/2.0f, -height/2.0f, 0.0f
        };
        float[] textureCoordinates = {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f
        };
        int[] faces = {
                2, 2, 1, 1, 0, 0,
                2, 2, 3, 3, 1, 1
        };

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().setAll(points);
        mesh.getTexCoords().setAll(textureCoordinates);
        mesh.getFaces().setAll(faces);
        return mesh;
    }

    public ShipToken(String fileName, ShipSize shipSize) {
        super();

        Mesh mesh = getRectangleMesh((float) getShipTemplateWidth(shipSize), (float) getShipTemplateWidth(shipSize));
        this.setMesh(mesh);
        this.setMaterial(texturedMaterial);

        Image image = new Image("file:"+fileName);
        texturedMaterial.setDiffuseMap(image);
        texturedMaterial.setDiffuseColor(Color.WHITE);

        this.setEffect(new DropShadow(20, Color.WHITESMOKE));
    }

    private void setColor() {
        if (selected) {
           texturedMaterial.setDiffuseColor(Color.YELLOW);
        }
        else {
            if (highlighted) {
                texturedMaterial.setDiffuseColor(Color.GREEN);
            }
            else {
                texturedMaterial.setDiffuseColor(Color.WHITE);
            }
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        setColor();
    }

    public void setHightlighted(boolean highlighted) {
        this.highlighted = highlighted;
        setColor();
    }

    public static double getShipTemplateWidth(ShipSize shipSize) {
        switch (shipSize) {
            case SMALL:
                return SMALL_SHIP_TEMPLATE_WIDTH_MM;
            case LARGE:
                return LARGE_SHIP_TEMPLATE_WIDTH_MM;
            case HUGE:
            default:
                throw new RuntimeException("Ship template size " + shipSize.name() + " not implemented!");
        }
    }
}
