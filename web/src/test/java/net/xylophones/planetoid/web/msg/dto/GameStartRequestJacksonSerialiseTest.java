package net.xylophones.planetoid.web.msg.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameStartRequestJacksonSerialiseTest {

    private ObjectMapper jacksonObjectMapper;

    @Before
    public void setUp() throws Exception {
        jacksonObjectMapper = new ObjectMapper();
    }

    @Test
    public void checkGameStartRequestCanBeSerialisedToJsonThenDeserialised() throws Exception {
        // given
        GameStartRequest cameStartRequest = new GameStartRequest("1234", "Will Robertson");

        // when
        String json = jacksonObjectMapper.writeValueAsString(cameStartRequest);
        GameStartRequest deserialised = jacksonObjectMapper.readValue(json, GameStartRequest.class);

        // then
        assertThat(cameStartRequest).isEqualTo(deserialised);
    }
}