package ship;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Brian on 12/19/2015.
 */
public class ShipOutlineToken extends Rectangle {

    public static double SMALL_SHIP_TEMPLATE_WIDTH_MM = 40.0;
    public static double LARGE_SHIP_TEMPLATE_WIDTH_MM = 80.0;

    public ShipOutlineToken(ShipSize shipSize) {
        double templateWidth = getShipTemplateWidth(shipSize);
        this.setX(-templateWidth/2.0);
        this.setY(-templateWidth/2.0);
        this.setWidth(templateWidth);
        this.setHeight(templateWidth);
        this.setArcHeight(5);
        this.setArcWidth(5);
        this.setFill(Color.rgb(0, 0, 0, 0));
        this.setStroke(Color.rgb(255, 255, 255, 0.5));
        this.setStrokeWidth(1.0);
        this.getStrokeDashArray().addAll(3d, 3d);
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
