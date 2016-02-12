package network;

/**
 * Created by Brian on 2/11/2016.
 * Enumeration for the type of command being sent to the server / clients
 */
public enum Command {
    SHIP_PLACEMENT,
    DECLOAK,
    MANEUVER,
    ACTION,
    DECLARE_TARGET,
    ROLL_DICE,
    REROLL_DICE,
    MODIFY_DICE,
}
