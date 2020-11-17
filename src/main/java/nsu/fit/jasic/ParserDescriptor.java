package nsu.fit.jasic;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Characters;
import org.parboiled.support.Chars;

@SuppressWarnings("InfiniteRecursion")
@BuildParseTree
public class ParserDescriptor extends BaseParser<Object> {
    protected int test = 1;

    public Rule jasicRunnable() {
        return OneOrMore(jasicCommand(), Optional(commandSeparator()));
    }

    public Rule jasicCommand() {
        return FirstOf(variableDeclaration(), printCommand(), labelCommand(), gotoCommand());
    }



    public Rule gotoCommand() {
        return Sequence("goto ", variableName().label("labelCreation"));
    }

    public Rule labelCommand() {
        return Sequence("label ", variableName().label("labelCreation"));
    }

    public Rule printCommand() {
        return Sequence("print ", variableName().label("variableAccess"));
    }

    public Rule variableDeclaration() {
        return Sequence("let ", variableName().label("variableCreate"), Optional(variableInitialization()));
    }

    public Rule variableInitialization() {
        return Sequence(optionalSpacesForChar('='), FirstOf(numberConst(), stringExpression(), variableName().label("variableAccess")));
    }

    public Rule variableName() {
        return OneOrMore(FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), '_', '-'));
    }

    public Rule stringExpression() {
        return Sequence(stringLiteral(), Optional(optionalSpacesForChar('+'), stringExpression()));
    }

    public Rule optionalSpacesForChar(char c) {
        return Optional(Sequence(Optional(' '), c, Optional(' ')));
    }

    public Rule stringLiteral() {
        return Sequence("\"", OneOrMore(AnyOf(Characters.ALL.remove('"').remove(Chars.EOI))).label("stringConst"), "\"");
    }

    public Rule numberConst() {
        return Sequence(CharRange('1', '9'), ZeroOrMore(CharRange('0', '9')));
    }

    public Rule commandSeparator() {
        return Ch('\n');
    }
}
