package net.xylophones.planetoid.web.msg.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class GameControl {

    private final String userId;

    private final String gameId;

    private final boolean fireMissile;

    private final boolean left;

    private final boolean right;

    private final boolean thrust;

    private final boolean reverseThrust;

    @JsonCreator
    public GameControl(@JsonProperty("userId") String userId,
                       @JsonProperty("gameId") String gameId,
                       @JsonProperty("fireMissile") boolean fireMissile,
                       @JsonProperty("left") boolean left,
                       @JsonProperty("right") boolean right,
                       @JsonProperty("thrust") boolean thrust,
                       @JsonProperty("reverseThrust") boolean reverseThrust) {

        this.userId = userId;
        this.gameId = gameId;
        this.fireMissile = fireMissile;
        this.left = left;
        this.right = right;
        this.thrust = thrust;
        this.reverseThrust = reverseThrust;
    }

    public String getUserId() {
        return userId;
    }

    public String getGameId() {
        return gameId;
    }

    public boolean isFireMissile() {
        return fireMissile;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isThrust() {
        return thrust;
    }

    public boolean isReverseThrust() {
        return reverseThrust;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof GameControl)) {
            return false;
        }

        GameControl that = (GameControl) other;
        return fireMissile == that.fireMissile &&
                left == that.left &&
                right == that.right &&
                thrust == that.thrust &&
                reverseThrust == that.reverseThrust &&
                Objects.equal(userId, that.userId) &&
                Objects.equal(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, gameId, fireMissile, left, right, thrust, reverseThrust);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("gameId", gameId)
                .add("fireMissile", fireMissile)
                .add("left", left)
                .add("right", right)
                .add("thrust", thrust)
                .add("reverseThrust", reverseThrust)
                .toString();
    }
}
