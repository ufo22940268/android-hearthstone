package me.biubiubiu.hearthstone.core;

public class Deck {

    public String desc;
    public Card[] cards;

    @Override
    public String toString() {
        return desc;
    }

    public class Card {
        public String pic;
        public int count;
        public String id;
        public String name;
    }
}

