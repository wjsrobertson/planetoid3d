package net.xylophones.planetoid.web.msg.model;

import java.util.Optional;

// TODO - get rid of the prefixes and just use the enum constants
public enum IncomingMessageType {

    GameStartRequest("greq:"), GameControl("ctrl:");

    IncomingMessageType(String prefix) {
        this.prefix = prefix;
    }

    public final String prefix;

    public static Optional<IncomingMessageType> getMessageType(String message) {
        for (IncomingMessageType messageType : values()) {
            if (message.startsWith(messageType.prefix)) {
                return  Optional.of(messageType);
            }
        }

        return Optional.empty();
    }
}
