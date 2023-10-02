import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DeckTest {
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testDeckSize() {
        assertEquals(52, deck.getDeckSize());
    }

    @Test
    public void testDeckShuffle() {
        Deck originalDeck = new Deck();
        originalDeck.shuffle();

        // After shuffling, the order should be different
        assertNotEquals(originalDeck.draw().toString(), deck.draw().toString());
    }
}
