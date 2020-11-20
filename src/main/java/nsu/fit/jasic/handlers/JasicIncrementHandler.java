package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableStorage;
import org.objectweb.asm.MethodVisitor;

public class JasicIncrementHandler extends AbstractTreeRootHandler {
    @Override
    public void handle(MethodVisitor visitor) {
        assert this.children.size() == 1;
        JasicElementHandler loadVariableHandler = this.children.get(0);
        String variableName = loadVariableHandler.getStringData();
        Integer localNumber = VariableStorage.getLocalNumber(variableName);
        visitor.visitIincInsn(localNumber, 1);
    }
}
