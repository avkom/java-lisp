package com.github.avkomq.lisp;

import java.util.ArrayList;
import java.util.Arrays;

public class Core {
    public static void main(String[] args) {
        String input = "(begin (define r 10) (* pi (* r r)))";
        String[] tokens = tokenize(input);
        System.out.println(Arrays.toString(tokens));
    }

    private static String[] tokenize(String input) {
        return input
                .replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .trim()
                .split("\\s+");
    }

    private static Object parse(String input) {
        String[] tokens = tokenize(input);
        return readFromTokens(tokens, 0);
    }

    private static Object readFromTokens(String[] tokens, int position) {
        for (int i = position; i < tokens.length; i++) {
            if ("(".equals(tokens[i])) {
                ArrayList list = new ArrayList();
                i++;
                while (!")".equals(tokens[i])) {
                    list.add(readFromTokens(tokens, ++i));
                }
                return list;
            }
            if (")".equals(tokens[i])) {
                throw new Error("Unexpected )");
            }
            try {
                return Integer.parseInt(tokens[i]);
            }
            catch (Error intError)
            {
                try {
                    return Double.parseDouble(tokens[i]);
                }
                catch (Error doubleError)
                {
                    return tokens[i];
                }
            }

        }
    }
}
