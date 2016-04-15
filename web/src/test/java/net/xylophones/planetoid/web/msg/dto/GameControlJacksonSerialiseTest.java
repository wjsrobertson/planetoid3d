package net.xylophones.planetoid.web.msg.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GameControlJacksonSerialiseTest {

    private ObjectMapper jacksonObjectMapper;

    @Before
    public void setUp() throws Exception {
        jacksonObjectMapper = new ObjectMapper();
    }

    @Test
    public void checkGameStartRequestCanBeSerialisedToJsonThenDeserialised() throws Exception {
        // given
        GameControl gameControl = new GameControl("user id", "game id", true, false, true, false);

        // when
        String json = jacksonObjectMapper.writeValueAsString(gameControl);
        GameControl deserialised = jacksonObjectMapper.readValue(json, GameControl.class);

        // then
        assertThat(gameControl).isEqualTo(deserialised);
    }

}