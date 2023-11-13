package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger lengthThreeCounter = new AtomicInteger(0);
    private static final AtomicInteger lengthFourCounter = new AtomicInteger(0);
    private static final AtomicInteger lengthFiveCounter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        List<Thread> threads = new ArrayList<>();
        Thread checkSymbolOrder = new Thread(() ->
        {
            for (var str : texts) {
                boolean isBeauty = true;
                char[] chars = str.toCharArray();
                for (int i = 1; i < chars.length; i++) {
                    if (chars[i] != chars[i - 1] || chars[i] > chars[i - 1]) {
                        isBeauty = false;
                        break;
                    }
                }
                if (isBeauty) {
                    if(str.length() == 3){
                        lengthThreeCounter.incrementAndGet();
                    }
                    if(str.length() == 4){
                        lengthFourCounter.incrementAndGet();
                    }
                    if(str.length() == 5){
                        lengthFiveCounter.incrementAndGet();
                    }
                }
            }
        });
        threads.add(checkSymbolOrder);
        Thread palindrome = new Thread(()->
        {
            for(var str: texts){
                boolean isBeauty = true;
                char[] chars = str.toCharArray();
                for (int i = 0; i < (chars.length / 2); i++) {
                    if (chars[chars.length - (i + 1)] != chars[i]) {
                        isBeauty = false;
                        break;
                    }
                }
                if (isBeauty) {
                    if(str.length() == 3){
                        lengthThreeCounter.incrementAndGet();
                    }
                    if(str.length() == 4){
                        lengthFourCounter.incrementAndGet();
                    }
                    if(str.length() == 5){
                        lengthFiveCounter.incrementAndGet();
                    }
                }
            }
        });
        threads.add(palindrome);
        for(Thread th: threads){
            th.start();
            th.join();
        }
        System.out.println("Красивых слов с длиной 3: " + lengthThreeCounter.get());
        System.out.println("Красивых слов с длиной 4: " + lengthFourCounter.get());
        System.out.println("Красивых слов с длиной 5: " + lengthFiveCounter.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }



}