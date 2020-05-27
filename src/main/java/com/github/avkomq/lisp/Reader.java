package com.github.avkomq.lisp;

import java.util.ArrayList;
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
        Enumerator<String> enumerator = new Enumerator(tokens);
        enumerator.moveNext();
        return readFromTokens(enumerator);
    }

    ArrayList<String> getTokens(String input) {
        Matcher matcher = pattern.matcher(input);
        ArrayList<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    Object readFromTokens(Enumerator<String> enumerator) {
        String token = enumerator.getCurrent();

        if ("(".equals(token)) {
            ArrayList list = new ArrayList();
            while (enumerator.moveNext() && !")".equals(enumerator.getCurrent())) {
                list.add(readFromTokens(enumerator));
            }
            return list;
        }

        if (")".equals(token)) {
            throw new RuntimeException("Unexpected )");
        }

        if (token.startsWith("\"")) {
            return token.substring(1, token.length() - 1);
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
}
