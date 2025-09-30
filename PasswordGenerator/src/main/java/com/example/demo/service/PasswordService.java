package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PasswordService {

    private static final char[] LOWER = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] NUM = "0123456789".toCharArray();
    private static final char[] SYM = "!@#$%^&*()-_=+[]{};:,.?/".toCharArray();

    private final SecureRandom random = new SecureRandom();

    public String generate(int length, boolean lower, boolean upper, boolean number, boolean symbol){
        if (length < 4 || length > 64) throw new IllegalArgumentException("length 4~64");
        List<char[]> pools = new ArrayList<>();
        if (lower) pools.add(LOWER);
        if (upper) pools.add(UPPER);
        if (number) pools.add(NUM);
        if (symbol) pools.add(SYM);
        if (pools.isEmpty()) throw new IllegalArgumentException("옵션을 하나 이상 선택하세요.");

        List<Character> chars = new ArrayList<>();
        for(char[] p : pools) chars.add(p[random.nextInt(p.length)]);

        while(chars.size() < length) {
            char[] p = pools.get(random.nextInt(pools.size()));
            chars.add(p[random.nextInt(p.length)]);
        }

        Collections.shuffle(chars, random);

        StringBuffer sb = new StringBuffer(chars.size());
        for(char c : chars) sb.append(c);
        return sb.toString();
    }
}
