package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableStorage;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JasicGotoHandler extends AbstractVariableHandler {

    @Override
    public void handle(MethodVisitor visitor) {
        Label label = VariableStorage.getLabel(this.variableName);
        visitor.visitJumpInsn(Opcodes.GOTO, label);
    }
}
