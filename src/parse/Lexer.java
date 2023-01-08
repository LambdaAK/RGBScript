package parse;

import java.util.LinkedList;
import java.util.Queue;

public class Lexer {

    public Queue<Token> tokens = new LinkedList<Token>();

    private Token prevToken;


    private final String numbers = "1234567890";


    private String input;

    public Lexer(String input) {

        this.input = input;

        lexAll();


    }

    private void add(Token token) {
        prevToken = token;
        tokens.add(token);
    }


    private void lexAll() {
        while (input.length() > 0) {
            lexOneToken();
        }
    }

    private void lexOneToken() {

        // get rid of whitespace and newlines

        while (input.charAt(0) == ' ' || input.charAt(0) == '\n') {
            input = input.substring(1);
        }

        System.out.println(input);
        System.out.println(tokens);
        System.out.println();

        // parsing standard tokens


        for (TokenType tokenType: TokenType.values()) {
            if (!tokenType.tokenLexType.equals(TokenLexType.STANDARD)) continue;

            String tokenRepresentation = tokenType.getRepresentation();

            if (input.indexOf(tokenRepresentation) == 0) {
                // match, so lex the token and add it to the token queue
                add(new Token(tokenType));
                input = input.substring(tokenRepresentation.length());
                return; // finish parsing the token
            }
        }






        /*

        at this point we either want to lex a variable name or a function
        this depends on what the last token was
        if the previous token is a variable type, we lex a variable name
        otherwise, we will lex a function token


        */


        char c = input.charAt(0);
        if (numbers.indexOf(c) != -1) lexInt();


        else if (prevToken.type.getCategory().equals(TokenCategory.TYPE)) lexVariableNameToken();

        else lexFunctionToken();


    }

    private void lexInt() {
        char c = input.charAt(0); // used for looking at the first character in the input string

        System.out.println("NEXT : " + c);



            int num = 0;

            while (numbers.indexOf(c) != -1) {
                num *= 10;
                num += Integer.parseInt(String.valueOf(c));

                input = input.substring(1);

                if (input.length() != 0) {
                    c = input.charAt(0);
                }
                else {
                    break;
                }

            }

            add(new Token.NumToken(num));


    }

    private void lexVariableNameToken() {
        // find the index of the next assignment operator
        // take the substring

        String name = input.substring(0, input.indexOf(TokenType.ASSIGNMENT_OPERATOR.toString()));

        input = input.substring(input.indexOf(TokenType.ASSIGNMENT_OPERATOR.toString()));

        while (name.charAt(name.length() - 1) == ' ') {
            name = name.substring(0, name.length() - 1); // remove the last character
        }

        add(new Token.VariableNameToken(name));

    }

    private void lexFunctionToken() {
        /*

        look for the first left paren
        take the substring from the start to the left paren
        trim the whitespace
        that is the function name
        push the function token to the token queue

        */

        int indexOfLeftParen = input.indexOf('(');
        String functionName = input.substring(0, indexOfLeftParen);
        // remove ending whitespace in the function name


        while (functionName.charAt(functionName.length() - 1) == ' ') {
            functionName = functionName.substring(0, functionName.length() - 1); // remove the last character
        }

        add(new Token.FunctionToken(functionName));

        input = input.substring(indexOfLeftParen); // remove characters up to and not including the left paren
    }



}
