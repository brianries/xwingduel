package rendering.obstacles;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import rendering.ship.ShipMeshView;
import util.Highlightable;
import util.Selectable;

/**
 * Created by Brian on 3/7/2016.
 */
public class ObstacleToken extends Group implements Selectable, Highlightable {

    public static float OUTLINE_WIDTH_MM = 4.0f;

    private PhongMaterial material = new PhongMaterial();
    private PhongMaterial outlineMaterial = new PhongMaterial();
    private boolean selected = false;
    private boolean highlighted = false;

    public ObstacleToken(ObstacleType type) {
        super();

        Image image = new Image("file:"+getObstacleFileName(type));
        material.setDiffuseMap(image);
        outlineMaterial.setDiffuseColor(Color.rgb(0, 0, 0, 0));

        float width = 150.0f; // TODO figure this out per asteroid

        float ratio = (float)(image.getHeight() / image.getWidth());

        Mesh obstacleMesh = getRectangleMesh(width*ratio, width);
        MeshView meshView = new MeshView(obstacleMesh);
        meshView.setMaterial(material);
        meshView.setTranslateZ(-0.01);

        Mesh outlineMesh = getRectangleMesh(width+OUTLINE_WIDTH_MM, width+OUTLINE_WIDTH_MM);
        MeshView outlineMeshView = new MeshView(outlineMesh);
        outlineMeshView.setMaterial(outlineMaterial);
        outlineMeshView.setTranslateZ(0.0);

        this.getChildren().addAll(meshView, outlineMeshView);
    }

    private static String getObstacleFileName(ObstacleType type) {
        return "resources/obstacles/" + type.toString().toLowerCase() + ".png";
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

    @Override
    public void setHighlighted(boolean highlight) {

    }

    @Override
    public void setSelected(boolean selected) {

    }
}
