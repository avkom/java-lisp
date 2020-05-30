package com.github.avkomq.lisp;

import java.util.Scanner;

public class App
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Reader reader = new Reader();
        Evaluator evaluator = new Evaluator();
        Printer printer = new Printer();
        Environment globalEnvironment = new Environment();
        initializeGlobalEnvironment(globalEnvironment);

        System.out.println("Lisp Interpreter");

        while (true) {
            System.out.print("> ");

            try {
                String input = scanner.nextLine();
                Object ast = reader.parse(input);
                Object result = evaluator.evaluate(ast, globalEnvironment);
                String output = printer.print(result);
                System.out.println(output);
            }
            catch (Exception exception) {
                exception.printStackTrace(System.out);
            }
        }
    }

    private static void initializeGlobalEnvironment(Environment environment) {
        setEnvironmentLambda(environment,"+", args -> (Double)args[0] + (Double)args[1]);
    }

    private static void setEnvironmentLambda(Environment environment, String symbol, Lambda lambda) {
        environment.set(new Symbol(symbol), lambda);
    }
}