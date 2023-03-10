package main.ast.language;

import main.ast.Node;
import main.ast.booleanAlgebra.BooleanLiteral;
import main.interpreter.RunTime;
import main.interpreter.Printer;
import main.interpreter.SignalCode;

public class ConditionalBlock extends Node implements BlockOrStatement {

    public static enum ConditionalBlockType {
        IF,
        IF_ELSE
    }


    IfBlock ifBlock;
    ElseBlock elseBlock;

    ConditionalBlockType type;



    public ConditionalBlock (ConditionalBlockType type, IfBlock ifBlock) {
        assert type.equals(ConditionalBlockType.IF);
        this.type = type;
        this.ifBlock = ifBlock;
    }

    public ConditionalBlock (ConditionalBlockType type, IfBlock ifBlock, ElseBlock elseBlock) {
        assert type.equals(ConditionalBlockType.IF_ELSE);
        this.type = type;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(ifBlock);

        if (type.equals(ConditionalBlockType.IF_ELSE)) {
            builder.append("\n").append(elseBlock);
        }

        return builder.toString();
    }

    @Override
    public SignalCode execute(RunTime runTime) {
        BooleanLiteral condition = ifBlock.condition;

        if (condition.eval(runTime).equals(true)) {
            if (ifBlock.execute(runTime).equals(SignalCode.TERMINATE)) {
                return SignalCode.TERMINATE;
            }
        }
        else if (type.equals(ConditionalBlockType.IF_ELSE)) {
            if (elseBlock.execute(runTime).equals(SignalCode.TERMINATE)) {
                return SignalCode.TERMINATE;
            }
        }
        return SignalCode.NONE;
    }

    @Override
    public void print(Printer printer) {
        ifBlock.print(printer);
        if (type.equals(ConditionalBlockType.IF_ELSE)) {
            elseBlock.print(printer);
        }
    }


}
