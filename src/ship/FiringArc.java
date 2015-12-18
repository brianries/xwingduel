package ship;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Created by Brian on 12/17/2015.
 */
public class FiringArc extends Path {

    public FiringArc(ShipSize shipSize) {
        double[] lengths = new double[] { 100.0, 200.0, 300.0};
        for (double length : lengths) {
            init(length, ShipToken.getShipTemplateWidth(shipSize));
        }
    }

    private void init(double firingRadius, double templateWidth) {
        double x0 = templateWidth/2.0;
        double y0 = -templateWidth/2.0;

        double x5 = templateWidth/2.0;
        double y5 = templateWidth/2.0;

        double x1 = x0 + firingRadius * Math.cos(45.0/180.0*Math.PI);
        double y1 = y0 - firingRadius * Math.sin(45.0/180.0*Math.PI);

        double x2 = x0 + firingRadius;
        double y2 = y0;

        double x3 = x5 + firingRadius;
        double y3 = y5;

        double x4 = x5 + firingRadius * Math.cos(45.0/180.0*Math.PI);
        double y4 = y5 + firingRadius * Math.sin(45.0/180.0*Math.PI);

        this.getElements().add(new MoveTo(x0, y0));
        this.getElements().add(new LineTo(x1, y1));
        this.getElements().add(new ArcTo(firingRadius, firingRadius, 45, x2, y2, false, true));
        this.getElements().add(new LineTo(x3, y3));
        this.getElements().add(new ArcTo(firingRadius, firingRadius, 45, x4, y4, false, true));
        this.getElements().add(new LineTo(x5, y5));
        this.getElements().add(new LineTo(x0, y0));
        this.setFill(Color.rgb(255, 0, 0, 0.35));
        this.setStroke(Color.rgb(255, 0, 0, 0.5));
        this.setFillRule(FillRule.NON_ZERO);
    }
}
