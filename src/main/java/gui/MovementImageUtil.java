package gui;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.*;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import movement.MovementDifficulty;
import movement.MovementType;

import java.util.HashMap;

/**
 * Created by Brian on 8/17/2016.
 */
public class MovementImageUtil {

    private HashMap<String, HashMap<MovementDifficulty, Image>> images;

    public MovementImageUtil() {
        loadImages();
    }

    private void loadImages() {
        images = new HashMap<>();
        images.put("Straight", createDifficultyImages(new Image("file:resources/maneuvers/straight.jpg")));
        images.put("Bank", createDifficultyImages(new Image("file:resources/maneuvers/bank_turn.jpg")));
        images.put("Hard", createDifficultyImages(new Image("file:resources/maneuvers/hard_turn.jpg")));
        images.put("KTurn", createDifficultyImages(new Image("file:resources/maneuvers/k_turn.jpg")));
    }

    public HashMap createDifficultyImages(Image sourceImage) {
        HashMap<MovementDifficulty, Image> images = new HashMap<>();
        for (MovementDifficulty difficulty : MovementDifficulty.values()) {
            images.put(difficulty, getImageColored(sourceImage, getColor(difficulty)));
        }
        return images;
    }

    public Color getColor(MovementDifficulty difficulty) {
        switch (difficulty) {
            case RED:
                return Color.RED;
            case GREEN:;
                return Color.GREEN;
            case WHITE:
            default:
                return Color.WHITE;
        }
    }

    public Image getImageColored(Image sourceImage, Color color) {
        int width = (int)sourceImage.getWidth();
        int height = (int)sourceImage.getHeight();
        PixelReader pixelReader = sourceImage.getPixelReader();

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color sourceColor = pixelReader.getColor(i, j);
                double grayScale = toGrayScale(sourceColor);
                if (grayScale > 0.25) {
                    writer.setColor(i, j, color);
                }
                else {
                    writer.setColor(i, j, sourceColor);
                }
            }
        }
        return writableImage;
    }

    public double toGrayScale(Color color) {
        return 0.2989 * color.getRed() + 0.5870 * color.getGreen() + 0.1140 * color.getBlue();
    }

    public ImageView getImageView(MovementType type, MovementDifficulty difficulty, double width, double height) {
        Image image;
        switch (type) {
            case FORWARD_1:
            case FORWARD_2:
            case FORWARD_3:
            case FORWARD_4:
            case FORWARD_5:
                image = images.get("Straight").get(difficulty);
                break;
            case LEFT_HARD_1:
            case LEFT_HARD_2:
            case LEFT_HARD_3:
            case RIGHT_HARD_1:
            case RIGHT_HARD_2:
            case RIGHT_HARD_3:
                image = images.get("Hard").get(difficulty);
                break;
            case LEFT_BANK_1:
            case LEFT_BANK_2:
            case LEFT_BANK_3:
            case RIGHT_BANK_1:
            case RIGHT_BANK_2:
            case RIGHT_BANK_3:
                image = images.get("Bank").get(difficulty);
                break;
            case K_TURN_1:
            case K_TURN_2:
            case K_TURN_3:
            case K_TURN_4:
            case K_TURN_5:
                image = images.get("KTurn").get(difficulty);
                break;
            default:
                image = images.get("Straight").get(difficulty);
                break;
        }

        ImageView imageView = new ImageView(image);

        // Handle mirroring it if its a left turn
        switch (type) {
            case LEFT_HARD_1:
            case LEFT_HARD_2:
            case LEFT_HARD_3:
            case LEFT_BANK_1:
            case LEFT_BANK_2:
            case LEFT_BANK_3:
                imageView.setScaleX(-1);
                break;
        }

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setSmooth(true);
        imageView.setCache(true);

        return imageView;
    }
}
