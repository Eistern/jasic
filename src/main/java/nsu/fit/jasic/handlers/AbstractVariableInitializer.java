package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableDescriptor;
import nsu.fit.jasic.variable.VariableStorage;
import org.objectweb.asm.MethodVisitor;

public abstract class AbstractVariableInitializer extends AbstractVariableHandler {
    protected Object variable;

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void handle(MethodVisitor visitor) {
        VariableDescriptor descriptor = VariableStorage.getDescriptor(this.variableName);
        Integer label = VariableStorage.getLocalNumber(this.variableName);
        if (descriptor == VariableDescriptor.STRING) {
            throw new IllegalArgumentException();
        }
        if (descriptor == null) {
            VariableStorage.updateVariable(this.variableName, VariableDescriptor.NUMBER);
            descriptor = VariableDescriptor.NUMBER;
        }
        visitor.visitLdcInsn(variable);
        visitor.visitVarInsn(descriptor.saveCode, label);
    }
}
