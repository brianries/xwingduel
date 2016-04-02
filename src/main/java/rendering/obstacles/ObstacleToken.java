package rendering.obstacles;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import util.Highlightable;
import util.Selectable;

/**
 * Created by Brian on 3/7/2016.
 */
public class ObstacleToken extends Group implements Selectable, Highlightable {

    // Vassal stores a 113x113 pixel image for the 40mm x 40mm ship token -- everything is normalized to this
    public static float MM_TO_PIXEL_RATIO = 40.0f / 113.0f;

    private PhongMaterial material = new PhongMaterial();
    private PhongMaterial outlineMaterial = new PhongMaterial();
    private boolean selected = false;
    private boolean highlighted = false;

    private ObstacleMeshView obstacleMeshView;
    private MeshView outlineMeshView;

    public ObstacleToken(ObstacleType type) {
        super();

        Image image = new Image("file:"+getObstacleFileName(type));
        material.setDiffuseMap(image);

        float width = MM_TO_PIXEL_RATIO * (float)image.getWidth();
        float height = MM_TO_PIXEL_RATIO * (float)image.getHeight();

        Mesh obstacleMesh = getRectangleMesh(width, height);
        obstacleMeshView = new ObstacleMeshView(obstacleMesh);
        obstacleMeshView.setMaterial(material);
        obstacleMeshView.setTranslateZ(-0.01);

        Image outlineImage = new Image("file:"+getObstacleOutlineFileName(type));
        outlineMaterial.setDiffuseMap(outlineImage);

        float outLineWidth = MM_TO_PIXEL_RATIO * (float)outlineImage.getWidth();
        float outLineHeight = MM_TO_PIXEL_RATIO * (float)outlineImage.getHeight();

        Mesh outlineMesh = getRectangleMesh(outLineWidth, outLineHeight);
        outlineMeshView = new MeshView(outlineMesh);
        outlineMeshView.setMaterial(outlineMaterial);
        outlineMeshView.setTranslateZ(0.0);
        outlineMeshView.setDisable(true);
        outlineMaterial.setDiffuseColor(Color.rgb(0, 0, 0, 0));

        this.getChildren().addAll(obstacleMeshView, outlineMeshView);
    }

    private static String getObstacleFileName(ObstacleType type) {
        return "resources/obstacles/" + type.toString().toLowerCase() + ".png";
    }

    private static String getObstacleOutlineFileName(ObstacleType type) {
        return "resources/obstacles/" + type.toString().toLowerCase() + "_outline.png";
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
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
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
            outlineMaterial.setDiffuseColor(Color.rgb(255, 255, 255, 1.0));
            outlineMeshView.setDisable(false);
            material.setDiffuseColor(Color.WHITE);
        }
        else {
            outlineMaterial.setDiffuseColor(Color.rgb(0, 0, 0, 0.0));
            outlineMeshView.setDisable(true);
            if (highlighted) {
                material.setDiffuseColor(Color.GREEN);
            }
            else {
                material.setDiffuseColor(Color.WHITE);
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
}
