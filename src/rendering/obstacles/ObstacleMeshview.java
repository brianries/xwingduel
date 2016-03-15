package rendering.obstacles;

import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;

/**
 * Created by Brian on 3/14/2016.
 */
public class ObstacleMeshView extends MeshView implements ObstacleTokenPart {

    public ObstacleMeshView(Mesh mesh) {
        super(mesh);
    }

    @Override
    public ObstacleToken getObstacleToken() {
        return (ObstacleToken) this.getParent();
    }

}