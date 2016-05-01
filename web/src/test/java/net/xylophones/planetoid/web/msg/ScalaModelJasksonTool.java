package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import net.xylophones.planetoid.game.maths.Vector2D;
import net.xylophones.planetoid.game.model.*;
import org.junit.Before;
import org.junit.Test;
import scala.Enumeration;
import scala.Option;
import scala.collection.immutable.Set;
import scala.collection.immutable.Set$;

public class ScalaModelJasksonTool {

    private ObjectMapper jacksonObjectMapper;

    @Before
    public void setUp() throws Exception {
        jacksonObjectMapper = new ObjectMapper();
        jacksonObjectMapper.registerModule(new DefaultScalaModule());
    }

    @Test
    public void checkGameStartRequestCanBeSerialisedToJsonThenDeserialised() throws Exception {
        Enumeration.Value value = GameEvent.withName("PlayerLoseLife");
        Set<Enumeration.Value> scalaSet = (Set<Enumeration.Value>) Set$.MODULE$.newBuilder().$plus$eq(value).result();

        GameModelUpdateResult result = new GameModelUpdateResult(
                new GameModel(
                        new Planet(new Vector2D(1D, 1D), 10),
                        null,
                        null, Winner.withName("Draw"), new RoundCountdownTimer(1, 10L, 20L),
                        Option.empty()//new Some(new RoundCountdownTimer(1, 10L, 20L)),
                ), scalaSet
        );

        // when
        String json = jacksonObjectMapper.writeValueAsString(result);
        System.out.println(json);
        System.out.println(GameModelUpdateResult.class.getSimpleName());
    }
}