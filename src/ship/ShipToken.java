package ship;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * Created by Brian on 12/17/2015.
 */
public class ShipToken extends Rectangle {

    public static double SMALL_SHIP_TEMPLATE_WIDTH_MM = 40.0;
    public static double LARGE_SHIP_TEMPLATE_WIDTH_MM = 80.0;

    public ShipToken(String fileName, ShipSize shipSize) {
        double templateWidth = getShipTemplateWidth(shipSize);
        this.setX(-templateWidth/2.0);
        this.setY(-templateWidth/2.0);
        this.setWidth(templateWidth);
        this.setHeight(templateWidth);
        this.setArcHeight(5);
        this.setArcWidth(5);
        this.setStroke(Color.GRAY);
        this.setStrokeWidth(3.0);
        this.setFill(new ImagePattern(new Image("file:" + fileName)));
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


    /*
    public MeshView getShipMeshView(String fileName) {

        float[] points = {
                -50f,   50f,  0.0f,
                -50f,  -50f,  0.0f,
                50f,   50f,  0.0f,
                50f,  -50f,  0.0f,
        };

        float[] texCoords = {
                0, 1,
                0, 0,
                1, 1,
                1, 0
        };
        // p0, t0, p1, t1, etc
        int[] faces = {
                2, 2, 1, 1, 0, 0,
                2, 2, 3, 3, 1, 1
        };

        final TriangleMesh shipMesh = new TriangleMesh();
        shipMesh.getPoints().setAll(points);
        shipMesh.getTexCoords().setAll(texCoords);
        shipMesh.getFaces().setAll(faces);

        PhongMaterial imageMaterial = new PhongMaterial();
        imageMaterial.setDiffuseMap(new Image("file:"+fileName));
        MeshView meshView = new MeshView(shipMesh);
        meshView.setMaterial(imageMaterial);
        return meshView;
    }
     */
}
