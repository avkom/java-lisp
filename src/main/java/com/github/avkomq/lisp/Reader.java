package com.github.avkomq.lisp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {
    private final static String REGEXP = "\\(|" + // (
            "\\)|" + // )
            "[-+]?[0-9]+[.]?[0-9]*([eE][-+]?[0-9]+)?|\\+Inf|-Inf|NaN|" + // number
            "\\\"([^\\\\\\\"]|\\\\.)*\\\"|" + // string
            "[^\\\"\\(\\)\\s]+|" + // symbol
            "#t|#f|" + // boolean
            "nil"; // nil
    private final static Pattern pattern = Pattern.compile(REGEXP);

    public Object parse(String input) {
        ArrayList<String> tokens = getTokens(input);
        return readFromTokens(tokens.iterator());
    }

    ArrayList<String> getTokens(String input) {
        Matcher matcher = pattern.matcher(input);
        ArrayList<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    private Object readFromTokens(Iterator<String> iterator) {
        while (iterator.hasNext()) {
            String token = iterator.next();
            if ("(".equals(token)) {
                ArrayList list = new ArrayList();
                if (iterator.hasNext()) {
                    token = iterator.next();
                    while (iterator.hasNext() && !")".equals(token)) {
                        list.add(readFromTokens(iterator));
                    }
                }
                return list;
            }
            if (")".equals(token)) {
                throw new RuntimeException("Unexpected )");
            }
            try {
                return Integer.parseInt(token);
            } catch (Exception intException) {
                try {
                    return Double.parseDouble(token);
                } catch (Exception doubleException) {
                    return token;
                }
            }
        }
        return null;
    }
}
