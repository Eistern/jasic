package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableDescriptor;
import nsu.fit.jasic.variable.VariableStorage;
import org.objectweb.asm.MethodVisitor;

public class JasicVariableLoadHandler extends AbstractVariableHandler {
    @Override
    public void handle(MethodVisitor visitor) {
        VariableDescriptor descriptor = VariableStorage.getDescriptor(this.variableName);
        Integer label = VariableStorage.getLabel(this.variableName);
        assert descriptor != null && label != null;
        visitor.visitVarInsn(descriptor.getCode, label);
    }
}
