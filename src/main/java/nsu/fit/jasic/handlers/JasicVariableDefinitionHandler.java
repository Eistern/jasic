package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableDescriptor;
import nsu.fit.jasic.variable.VariableStorage;
import org.objectweb.asm.MethodVisitor;

public class JasicVariableDefinitionHandler extends AbstractVariableHandler {
    @Override
    public void handle(MethodVisitor visitor) {
        VariableStorage.saveVariable(this.variableName);
    }

    public void saveVariableOnStack(MethodVisitor visitor, VariableDescriptor variableDescriptor) {
        VariableDescriptor descriptor = VariableStorage.getDescriptor(this.variableName);
        Integer label = VariableStorage.getLocalNumber(this.variableName);
        if (descriptor == null) {
            VariableStorage.updateVariable(this.variableName, variableDescriptor);
        }
        visitor.visitVarInsn(variableDescriptor.saveCode, label);
    }
}
