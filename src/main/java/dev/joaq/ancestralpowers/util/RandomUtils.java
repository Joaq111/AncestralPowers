package dev.joaq.ancestralpowers.util;

import java.util.Random;

public class RandomUtils {
    private static final Random rand = new Random();

    private static final String[] movements = {"Voo", "Teleporte", "Velocidade"};
    private static final String[] mains = {"Super Força", "Imortalidade", "Fireball"};
    private static final String[] intelligences = {"Burro", "Inteligente", "Gênio"};

    public static String randomMovement() { return movements[rand.nextInt(movements.length)]; }
    public static String randomMain() { return mains[rand.nextInt(mains.length)]; }
    public static String randomIntelligence() { return intelligences[rand.nextInt(intelligences.length)]; }
}