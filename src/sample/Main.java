package sample;

import com.sun.javafx.fxml.builder.TriangleMeshBuilder;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;


public class Main extends Application {

    public static double SHIP_TEMPLATE_WIDTH = 100;
    private BooleanProperty firingArcVisibile = new SimpleBooleanProperty(true);

    private  TemplateTransition templateTransition;

    private Path path;
    private double x1, y1;

    public Parent createContent(Stage stage) throws Exception {

        final Rectangle shipToken = getShipToken("resources/ywing.jpg");
        MovementTemplate movementTemplate = getHardTurnTemplate(200, 200, 20, 100);
        Path firingArc = getFiringArc(400);

      //  Group ship = new Group();
     //   ship.getChildren().add(shipToken);
      //  ship.getChildren().add(firingArc);

        // TO DO -- look into this one!
        //ship.contains(0, 0);




        firingArc.translateXProperty().bind(shipToken.translateXProperty());
        firingArc.translateYProperty().bind(shipToken.translateYProperty());
        shipToken.setTranslateX(200);
        shipToken.setTranslateY(200);


        /*
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(movementTemplate.movementPath);
        pathTransition.setNode(ship);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        */

        templateTransition = new TemplateTransition(Duration.millis(4000), shipToken, firingArc);
        //templateTransition.setPath(movementTemplate.movementPath);
        //templateTransition.setNode(ship);
        //templateTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        templateTransition.setCycleCount(1);
        templateTransition.setAutoReverse(false);
        templateTransition.play();

        // Identity matrix
       // Affine transformMatrix = new Affine();
      //  ship.getTransforms().setAll(transformMatrix);

        /*
        final KeyValue kvx = new KeyValue(ship.translateXProperty(), 500);
        final KeyValue kvy = new KeyValue(ship.translateYProperty(), 500);
        final KeyFrame kfx = new KeyFrame(Duration.millis(1000), kvx);
        final KeyFrame kfy = new KeyFrame(Duration.millis(1000), kvy);
        timeline.getKeyFrames().add(kfx);
        timeline.getKeyFrames().add(kfy);
        timeline.play();              */

        // Build the Scene Graph
        Group root = new Group();
        root.getChildren().add(movementTemplate.movementTemplate);
        root.getChildren().add(firingArc);
        root.getChildren().add(shipToken);

        root.setOnMousePressed(event -> {
            if (event.getTarget() instanceof Rectangle) {
                ((Rectangle) event.getTarget()).setStroke(Color.YELLOW);
            }
        });

        root.setOnMouseReleased(event -> {
            if (event.getTarget() instanceof Rectangle) {
                ((Rectangle) event.getTarget()).setStroke(Color.GRAY);
            }
        });



        root.addEventHandler(MouseEvent.ANY, e -> {
            if(e.getTarget() instanceof Node ) {
                System.out.println("Mouse Event = " + e.getEventType());
                Node node = (Node) e.getTarget();
                if (e.getEventType() == MouseEvent.MOUSE_ENTERED_TARGET) {
                    if (!node.translateXProperty().isBound()) {
                        node.setEffect(new DropShadow(20, Color.WHITESMOKE));
                    }
                }
                else if (e.getEventType() == MouseEvent.MOUSE_EXITED_TARGET) {
                    node.setEffect(null);
                }
                else if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    x1 = e.getX();
                    y1 = e.getY();
                }
                else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    // translate node
                    if (!node.translateXProperty().isBound()) {
                        node.setTranslateX(e.getX() - x1 + node.getTranslateX());
                    }
                    if (!node.translateYProperty().isBound()) {
                        node.setTranslateY(e.getY() - y1 + node.getTranslateY());
                    }
                    x1 = e.getX();
                    y1 = e.getY();
                }
                else if (e.getButton() == MouseButton.SECONDARY) {
                    // right-click over the path to move it to its original position
                    if (!node.translateXProperty().isBound()) {
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    }
                }
            }
        });


        SubScene scene = createScene(root, stage);

        Group group = new Group();
        group.getChildren().add(scene);
        return group;
    }


    private SubScene createScene(Group rootGroup, Stage stage) {
        Image texture = new Image("file:resources/starfield.jpg");
        ImagePattern imagePattern = new ImagePattern(texture);

        ParallelCamera parallelCamera = new ParallelCamera();

        SubScene subScene = new SubScene(rootGroup, 500, 500, true, null);
        subScene.setFill(imagePattern);
        subScene.setCamera(parallelCamera);
        subScene.heightProperty().bind(stage.heightProperty());
        subScene.widthProperty().bind(stage.widthProperty());

        return subScene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        Scene scene = new Scene(createContent(primaryStage));

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    templateTransition.play();
                    break;
                case F:
                    firingArcVisibile.set(!firingArcVisibile.get());
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public Rectangle getShipToken(String fileName) {
        final Rectangle shipToken = new Rectangle (-50, -50, 100, 100);
        shipToken.setArcHeight(5);
        shipToken.setArcWidth(5);
        shipToken.setRotate(90);
        shipToken.setStroke(Color.GRAY);
        shipToken.setStrokeWidth(3.0);
        shipToken.setFill(new ImagePattern(new Image("file:"+fileName)));
        return shipToken;
    }

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

    public static class MovementTemplate {
        Path movementPath;
        Path movementTemplate;

        public MovementTemplate(Path movementPath, Path movementTemplate) {
            this.movementPath = movementPath;
            this.movementTemplate = movementTemplate;
        }
    }

    public Path getFiringArc(double firingRadius) {
        Path firingArc = new Path();

        double x1 = firingRadius * Math.cos(45.0/180.0*Math.PI);
        double y1 = -firingRadius * Math.sin(45.0/180.0*Math.PI);

        double x2 = firingRadius * Math.cos(45.0/180.0*Math.PI);
        double y2 = firingRadius * Math.sin(45.0/180.0*Math.PI);

        firingArc.getElements().add(new MoveTo(0, 0));
        firingArc.getElements().add(new LineTo(x1, y1));
        firingArc.getElements().add(new ArcTo(firingRadius, firingRadius, 90, x2, y2, false, true));
        firingArc.getElements().add(new LineTo(0, 0));
        firingArc.setFill(Color.rgb(255, 0, 0, 0.5));
        firingArc.setStroke(Color.rgb(255, 0, 0, 1.0));
        firingArc.setFillRule(FillRule.NON_ZERO);
        firingArc.visibleProperty().bind(firingArcVisibile);

        return firingArc;
    }

    public MovementTemplate getHardTurnTemplate(double x, double y, double width, double radius) {
        Path movementPath = new Path();
        Path movementTemplate = new Path();

        double outerRadius = radius + width;
        double innerRadius = radius - width;

        double x0 = x;
        double y0 = y-width;

        double x1 = x0 + outerRadius;
        double y1 = y0 + outerRadius;

        double x2 = x1 - width*2;
        double y2 = y1;

        double x3 = x2 - innerRadius;
        double y3 = y2 - innerRadius;

        movementTemplate.getElements().add(new MoveTo(x0, y0));
        movementTemplate.getElements().add(new ArcTo(outerRadius, outerRadius, 90, x1, y1, false, true));
        movementTemplate.getElements().add(new LineTo(x2, y2));
        movementTemplate.getElements().add(new ArcTo(innerRadius, innerRadius, 90, x3, y3, false, false));
        movementTemplate.getElements().add(new LineTo(x0, y0));
        movementTemplate.setFill(Color.DARKGREEN);
        movementTemplate.setStroke(Color.WHITE);
        movementTemplate.setFillRule(FillRule.NON_ZERO);

        movementPath.getElements().add(new MoveTo(x - SHIP_TEMPLATE_WIDTH/2.0, y));
        movementPath.getElements().add(new LineTo(x, y));
        movementPath.getElements().add(new ArcTo(radius, radius, 90, x+radius, y+radius, false, true));
        movementPath.getElements().add(new LineTo(x+radius, y+radius + SHIP_TEMPLATE_WIDTH/2.0));
        movementPath.setStroke(Color.WHITE);
        movementPath.getStrokeDashArray().setAll(5d, 5d);

        return new MovementTemplate(movementPath, movementTemplate);
    }

    /**
     * Java main for when running without JavaFX launcher
     Perspective Camera
     Camera 3-5
     */
    public static void main(String[] args) {
        launch(args);
    }
}
