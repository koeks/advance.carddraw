package main.java;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Enum to represent the possible poker hand ranks
enum Rank {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH
}

// Class to represent a playing card
class Card {
    private final String suit;
    private final String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getRankValue() {
        // Get the numeric value of the card's rank (e.g., "A" is 14, "K" is 13)
        try {
            return Integer.parseInt(rank);
        } catch (NumberFormatException e) {
            return 0; // Non-numeric ranks are assigned a value of 0
        }
    }

    @Override
    public String toString() {
        return rank + suit;
    }
}

// Class to represent a deck of cards
class Deck {
    // Unicode for card suits not supported on all terminals, so using letters (S, H, D, C)
    private final String[] suits = {"S", "H", "D", "C"};
    private final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        // Initialize the deck by creating all possible card combinations
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        // Shuffle the deck to randomize card order
        Collections.shuffle(cards);
    }

    public Card draw() {
        // Remove and return the top card from the deck
        return cards.remove(cards.size() - 1);
    }
}

// Class to represent a player's hand
class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        // Add a card to the player's hand
        cards.add(card);
    }

    public List<Card> getCards() {
        // Get the cards in the player's hand
        return cards;
    }

    public Rank evaluateHand() {
        // Evaluate the player's hand to determine its rank

        if (isStraightFlush()) {
            return Rank.STRAIGHT_FLUSH;
        } else if (isFourOfAKind()) {
            return Rank.FOUR_OF_A_KIND;
        } else if (isFullHouse()) {
            return Rank.FULL_HOUSE;
        } else if (isFlush()) {
            return Rank.FLUSH;
        } else if (isStraight()) {
            return Rank.STRAIGHT;
        } else if (isThreeOfAKind()) {
            return Rank.THREE_OF_A_KIND;
        } else if (isTwoPair()) {
            return Rank.TWO_PAIR;
        } else if (isOnePair()) {
            return Rank.ONE_PAIR;
        } else {
            return Rank.HIGH_CARD;
        }
    }

    // Private methods to check specific poker hand ranks
    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    private boolean isFourOfAKind() {
        Map<String, Long> rankCount = cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCount.values().stream().anyMatch(count -> count == 4);
    }

    private boolean isFullHouse() {
        Map<String, Long> rankCount = cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCount.values().contains(3L) && rankCount.values().contains(2L);
    }

    private boolean isFlush() {
        Set<String> suits = cards.stream().map(Card::getSuit).collect(Collectors.toSet());
        return suits.size() == 1;
    }

    private boolean isStraight() {
        List<Integer> rankValues = cards.stream()
                .map(Card::getRankValue)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return rankValues.size() == 5 && rankValues.get(4) - rankValues.get(0) == 4;
    }

    private boolean isThreeOfAKind() {
        Map<String, Long> rankCount = cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCount.values().stream().anyMatch(count -> count == 3);
    }

    private boolean isTwoPair() {
        Map<String, Long> rankCount = cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCount.values().stream().filter(count -> count == 2).count() == 2;
    }

    private boolean isOnePair() {
        Map<String, Long> rankCount = cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        return rankCount.values().stream().anyMatch(count -> count == 2);
    }
}

// Main class for the poker game
public class PokerGame {
    public static void main(String[] args) {
        // Create a deck, shuffle it, and draw a player's hand
        Deck deck = new Deck();
        deck.shuffle();

        Hand playerHand = new Hand();
        for (int i = 0; i < 5; i++) {
            playerHand.addCard(deck.draw());
        }

        // Display the player's hand and its rank
        System.out.println("Shuffling... Shuffling... Shuffling...");
        System.out.print("Your hand: ");
        for (Card card : playerHand.getCards()) {
            System.out.print(card + " ");
        }

        Rank handRank = playerHand.evaluateHand();
        System.out.println("\nYou have: " + handRank);
    }
}
