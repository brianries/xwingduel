package rendering.obstacles;

import base.Player;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Brian on 3/6/2016.
 */
public class ObstacleFactory {

    private HashMap<Player, HashSet<ObstacleType>> playerObstacles;

    public ObstacleFactory() {
        this.playerObstacles = new HashMap<>();
        playerObstacles.put(Player.PLAYER_ONE, new HashSet<>());
        playerObstacles.put(Player.PLAYER_TWO, new HashSet<>());
    }

    public Obstacle createObstacle(Player player, ObstacleType type) {
        HashSet<ObstacleType> playerSet = playerObstacles.get(player);
        if (playerSet == null) {
            throw new RuntimeException("Unknown player " + player);
        }
        if (!playerSet.contains(type)) {
            playerSet.add(type);
            switch (type) {
                case ASTEROID_BASE_CORE_0:
                case ASTEROID_BASE_CORE_1:
                case ASTEROID_BASE_CORE_2:
                case ASTEROID_BASE_CORE_3:
                case ASTEROID_BASE_CORE_4:
                case ASTEROID_BASE_CORE_5:
                case ASTEROID_TFA_CORE_0:
                case ASTEROID_TFA_CORE_1:
                case ASTEROID_TFA_CORE_2:
                case ASTEROID_TFA_CORE_3:
                case ASTEROID_TFA_CORE_4:
                case ASTEROID_TFA_CORE_5:
                    return new Asteroid(type);
                case DEBRIS_0:
                case DEBRIS_1:
                case DEBRIS_2:
                case DEBRIS_3:
                case DEBRIS_4:
                case DEBRIS_5:
                    return new Debris(type);
                default:
                    throw new RuntimeException("Unknown obstacle type " + type);
            }
        }
        else {
            throw new RuntimeException("Cannot have duplicate obstacle for the same player!");
        }
    }


    /*
    public Image loadObstacleImage(ObstacleType type) {

    }
    */
}

