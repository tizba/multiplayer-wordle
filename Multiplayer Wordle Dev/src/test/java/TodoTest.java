import fr.baptiste_masoud.multiplayer_wordle.server.Server;
import fr.baptiste_masoud.multiplayer_wordle.server.game.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void Test() {
        //Todo: Test
        Game game = new Game(new Server(1234));
        assertEquals(game,game);
    }
}