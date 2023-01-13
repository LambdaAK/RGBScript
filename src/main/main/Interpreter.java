package main.main;

import main.ast.booleanAlgebra.BooleanFactor;
import main.ast.booleanAlgebra.BooleanLiteral;
import main.ast.language.Expression;
import main.ast.language.Program;
import main.parse.Lexer;
import main.parse.Parser;
import main.parse.PostLexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Interpreter {

    public static void main(String[] args) {

        String input = "";

        try {
            input = read(args[0]);
        }

        catch (IOException e) {
            System.out.println("IOException");
            System.exit(1);
        }

        // lex

        Lexer lexer = new Lexer(input);

        PostLexer postLexer = new PostLexer(lexer);

        Parser parser = new Parser(lexer.tokens);

        postLexer.postLex();

        System.out.println(lexer.tokens);

        Expression p = parser.parseExpression();

        System.out.println("-----------");
        System.out.println(p);
    }

    public static String read(String dir) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dir));
        StringBuilder builder = new StringBuilder();

        String line = null;
        String lineSeperator = System.getProperty("line.separator");

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(lineSeperator);
        }

        // delete the last new line separator
        builder.deleteCharAt(builder.length() - 1);
        reader.close();

        return builder.toString();
    }




}