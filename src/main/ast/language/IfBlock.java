package main.ast.language;

import main.ast.Node;
import main.ast.booleanAlgebra.BooleanLiteral;
import main.interpreter.Color;
import main.interpreter.RunTime;
import main.interpreter.Printer;
import main.interpreter.SignalCode;

import java.util.ArrayList;

public class IfBlock extends Node {

    BooleanLiteral condition;
    ArrayList<BlockOrStatement> blocks;


    public IfBlock(BooleanLiteral condition, ArrayList<BlockOrStatement> blocks) {
        this.condition = condition;
        this.blocks = blocks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("if (").append(condition).append(") {\n");

        for (BlockOrStatement b: blocks) {
            builder.append("     ").append(b).append("\n");
        }

        builder.append("}");


        return builder.toString();
    }


    public SignalCode execute(RunTime runTime) {
        for (BlockOrStatement b: blocks) {
            if (b.execute(runTime).equals(SignalCode.TERMINATE)) {
                return SignalCode.TERMINATE;
            }

        }
        return SignalCode.NONE;
    }

    /*

    while (true) {
        if (true) {
            terminate;

        }

        else if () {

        }

        else {

        }
    }

    */

    @Override
    public void print(Printer printer) {
        printer.addWithNoIndentation(Color.addColor(Color.BLUE_BOLD));
        printer.addRaw("if (");

        printer.addWithNoIndentation(Color.removeColor());


        printer.addWithNoIndentation(condition);


        printer.addWithNoIndentation(Color.addColor(Color.BLUE_BOLD));
        printer.addWithNoIndentation(")");

        printer.addWithNoIndentation(" {\n");

        printer.addWithNoIndentation(Color.removeColor());

        printer.addIndentation();
        for (BlockOrStatement block: blocks) {
            block.print(printer);
            printer.add("\n");
        }
        printer.removeIndentation();

        printer.addWithNoIndentation(Color.addColor(Color.BLUE_BOLD));

        printer.add("}");

        printer.addWithNoIndentation(Color.removeColor());
    }
}
