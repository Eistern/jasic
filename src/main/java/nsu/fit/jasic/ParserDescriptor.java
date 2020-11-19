package nsu.fit.jasic;

import nsu.fit.jasic.handlers.*;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Characters;
import org.parboiled.support.Chars;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("InfiniteRecursion")
@BuildParseTree
public class ParserDescriptor extends BaseParser<Object> {

    public Rule jasicRunnable() {
        return Sequence(addHandler(JasicExecutableHandler.class, false, null), OneOrMore(jasicCommand(), Optional(commandSeparator())), closeHandler());
    }

    public Rule jasicCommand() {
        return FirstOf(variableDeclaration(), printCommand(), labelCommand(), gotoCommand());
    }

    public Rule gotoCommand() {
        return Sequence("goto ", variableName().label("labelAccess"), addHandler(JasicGotoHandler.class, true, match()));
    }

    public Rule labelCommand() {
        return Sequence("label ", variableName().label("labelCreation"), addHandler(JasicLabelHandler.class, true, match()));
    }

    public Rule printCommand() {
        return Sequence("print ", addHandler(JasicPrintHandler.class, false, null), variableName().label("variableAccess"), addHandler(JasicVariableLoadHandler.class, true, match()), closeHandler());
    }

    public Rule variableDeclaration() {
        return Sequence("let ", variableName().label("variableCreate"), addHandler(JasicInitializerHandler.class, false, null), addHandler(JasicVariableDefinitionHandler.class, true, match()), Optional(variableInitialization()), closeHandler());
    }

    public Rule variableInitialization() {
        return Sequence(optionalSpacesForChar('='), FirstOf(Sequence(numberConst(), addHandler(JasicVariableNumberInitializer.class, true, match())), stringExpression(), variableName().label("variableAccess")));
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

    protected boolean addHandler(Class<? extends JasicElementHandler> clazz, boolean isLeaf, String data) {
        try {
            JasicElementHandler jasicElementHandler = clazz.getDeclaredConstructor().newInstance();
            if (data != null) {
                jasicElementHandler.setStringData(data);
            }
            if (isLeaf) {
                JasicHandlerEnvironment.addCommand(jasicElementHandler);
            } else {
                JasicHandlerEnvironment.addTreeRoot(jasicElementHandler);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected boolean closeHandler() {
        JasicHandlerEnvironment.treeRootUp();
        return true;
    }
}
