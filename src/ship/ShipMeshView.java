package ship;

import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;

/**
 * Created by Brian on 2/2/2016.
 */
public class ShipMeshView extends MeshView implements ShipTokenPart {

    public ShipMeshView(Mesh mesh) {
        super(mesh);
    }

    @Override
    public ShipToken getShipToken() {
        return (ShipToken) this.getParent();
    }
}
