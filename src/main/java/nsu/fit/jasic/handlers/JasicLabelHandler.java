package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableStorage;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class JasicLabelHandler extends AbstractVariableHandler {

    @Override
    public void handle(MethodVisitor visitor) {
        Label label = VariableStorage.getLabel(this.variableName);
        visitor.visitLabel(label);
    }
}
