package com.github.avkomq.lisp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {
    private final static String REGEXP = "\\(|" + // (
            "\\)|" + // )
            "[-+]?[0-9]+[.]?[0-9]*([eE][-+]?[0-9]+)?|Inf|-Inf|NaN|" + // number
            "\\\"([^\\\\\\\"]|\\\\.)*\\\"|" + // string
            "[^\\\"\\(\\)\\s]+|" + // symbol
            "#t|#f|" + // boolean
            "nil"; // nil
    private final static Pattern pattern = Pattern.compile(REGEXP);

    public Object parse(String input) {
        ArrayList<String> tokens = getTokens(input);
        Enumerator<String> enumerator = new Enumerator(tokens);
        enumerator.moveNext();
        return getAst(enumerator);
    }

    ArrayList<String> getTokens(String input) {
        Matcher matcher = pattern.matcher(input);
        ArrayList<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    Object getAst(Enumerator<String> enumerator) {
        String token = enumerator.getCurrent();

        if ("(".equals(token)) {
            ArrayList list = new ArrayList();
            while (enumerator.moveNext() && !")".equals(enumerator.getCurrent())) {
                list.add(getAst(enumerator));
            }

            if (!")".equals(enumerator.getCurrent())) {
                throw new SyntaxErrorException(") expected");
            }

            return list;
        }

        if (")".equals(token)) {
            throw new SyntaxErrorException("Unexpected )");
        }

        if ("#t".equals(token)) {
            return true;
        }

        if ("#f".equals(token)) {
            return false;
        }

        if ("nil".equals(token)) {
            return null;
        }

        if (token.startsWith("\"")) {
            return token.substring(1, token.length() - 1);
        }

        if ("Inf".equals(token)) {
            return Double.POSITIVE_INFINITY;
        }

        if ("-Inf".equals(token)) {
            return Double.NEGATIVE_INFINITY;
        }

        try {
            return Long.parseLong(token);
        } catch (Exception longException) {
            try {
                return Double.parseDouble(token);
            } catch (Exception doubleException) {
                return new Symbol(token);
            }
        }
    }
}
