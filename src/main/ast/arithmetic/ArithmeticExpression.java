package main.ast.arithmetic;

import main.ast.Node;
import main.ast.language.Expression;
import main.interpreter.RunTime;

import java.util.ArrayList;
import java.util.Arrays;

public class ArithmeticExpression extends Node implements Expression {




    public static enum ExpressionType {
        PLUS_EXPRESSION,
        MINUS_EXPRESSION,
        SINGLE_EXPRESSION
    }


    public ArrayList<ArithmeticExpression> expressions;
    public ExpressionType expressionType;

    public ArithmeticExpression(ExpressionType expressionType, ArithmeticExpression... expressions) {
        this.expressionType = expressionType;

        this.expressions = new ArrayList<ArithmeticExpression>();

        this.expressions.addAll(Arrays.asList(expressions));

    }

    // for convention
    public ArithmeticExpression() {
        expressionType = null;
        expressions = null;
    }

    @Override
    public Object eval(RunTime runTime) {
        if (expressionType.equals(ExpressionType.PLUS_EXPRESSION) || expressionType.equals(ExpressionType.MINUS_EXPRESSION)) {
            ArithmeticExpression firstExpression = (ArithmeticExpression) expressions.get(0);
            ArithmeticExpression secondExpression = (ArithmeticExpression) expressions.get(1);

            Object firstObject = firstExpression.eval(runTime);
            Object secondObject = secondExpression.eval(runTime);

            assert firstObject instanceof Integer;
            assert secondObject instanceof Integer;

            Integer first = (Integer) firstObject;
            Integer second = (Integer) secondObject;

            if (expressionType.equals(ExpressionType.PLUS_EXPRESSION)) return first + second;
            else return first - second;

        }

        else {
            ArithmeticExpression firstExpression = (ArithmeticExpression) expressions.get(0);
            Object firstObject = firstExpression.eval(runTime);
            assert firstObject instanceof Integer;

            return (Integer) firstObject;

        }
    }



    @Override
    public String toString() {
        if (expressionType.equals(ExpressionType.PLUS_EXPRESSION)) {
            return expressions.get(0).toString() + " + " + expressions.get(1).toString();
        }

        if (expressionType.equals(ExpressionType.MINUS_EXPRESSION)) {
            return expressions.get(0).toString() + " - " + expressions.get(1).toString();
        }

        if (expressionType.equals(ExpressionType.SINGLE_EXPRESSION)) {
            return expressions.get(0).toString();
        }

        return "";
    }




}
