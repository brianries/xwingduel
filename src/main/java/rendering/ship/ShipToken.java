package rendering.ship;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.TriangleMesh;
import ship.ShipSize;
import util.Highlightable;
import util.Selectable;

/**
 * Created by Brian on 12/17/2015.
 */
public class ShipToken extends Group implements Selectable, Highlightable {

    public static double SMALL_SHIP_TEMPLATE_WIDTH_MM = 40.0;
    public static double LARGE_SHIP_TEMPLATE_WIDTH_MM = 80.0;
    public static float OUTLINE_WIDTH_MM = 4.0f;

    private PhongMaterial shipTextureMaterial = new PhongMaterial();
    private PhongMaterial outlineMaterial = new PhongMaterial();
    private boolean selected = false;
    private boolean highlighted = false;

    public ShipToken(String fileName, ShipSize shipSize) {
        super();

        Image image = new Image("file:"+fileName);
        shipTextureMaterial.setDiffuseMap(image);
        outlineMaterial.setDiffuseColor(Color.rgb(0,0,0,0));

        float shipWidth = (float)getShipTemplateWidth(shipSize);

        float ratio = (float)(image.getHeight() / image.getWidth());

        Mesh shipMesh = getRectangleMesh(shipWidth * ratio, shipWidth);
        ShipMeshView shipMeshView = new ShipMeshView(shipMesh);
        shipMeshView.setMaterial(shipTextureMaterial);
        shipMeshView.setTranslateZ(-0.01);

        Mesh outlineMesh = getRectangleMesh(shipWidth*ratio+OUTLINE_WIDTH_MM, shipWidth+OUTLINE_WIDTH_MM);
        ShipMeshView outlineMeshView = new ShipMeshView(outlineMesh);
        outlineMeshView.setMaterial(outlineMaterial);
        outlineMeshView.setTranslateZ(0.0);

        this.getChildren().addAll(shipMeshView, outlineMeshView);
    }

    private static Mesh getRectangleMesh(float width, float height) {
        float[] points = {
                -width/2.0f,  height/2.0f, 0.0f,
                -width/2.0f, -height/2.0f, 0.0f,
                 width/2.0f,  height/2.0f, 0.0f,
                 width/2.0f, -height/2.0f, 0.0f
        };

        // The x-axis is forward -- and [0,0] is upper left of texture
        float[] textureCoordinates = {
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
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



    private void updateEffects() {
        if (selected) {
            outlineMaterial.setDiffuseColor(Color.YELLOW);
        }
        else {
            if (highlighted) {
                outlineMaterial.setDiffuseColor(Color.WHITE);
            }
            else {
                outlineMaterial.setDiffuseColor(Color.rgb(0,0,0,0));
            }
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        updateEffects();
    }

    @Override
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        updateEffects();
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
