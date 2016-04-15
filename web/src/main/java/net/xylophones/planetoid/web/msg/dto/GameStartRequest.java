package net.xylophones.planetoid.web.msg.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class GameStartRequest {

    private final String id;

    private final String name;

    @JsonCreator
    public GameStartRequest(@JsonProperty("id") String id,
                            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object that) {
        if (! (that instanceof GameStartRequest)) {
            return false;
        }

        GameStartRequest other = (GameStartRequest) that;

        return Objects.equal(id, other.id) &&
                Objects.equal(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}
