package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableDescriptor;
import nsu.fit.jasic.variable.VariableStorage;
import org.objectweb.asm.MethodVisitor;

public class JasicVariableLoadHandler extends AbstractVariableInitializer {
    @Override
    public void setStringData(String data) {
        this.variableName = data;
    }

    @Override
    public String getStringData() {
        return this.variableName;
    }

    @Override
    public VariableDescriptor getDescriptor() {
        return VariableStorage.getDescriptor(this.variableName);
    }

    @Override
    public void handle(MethodVisitor visitor) {
        VariableDescriptor descriptor = VariableStorage.getDescriptor(this.variableName);
        Integer label = VariableStorage.getLocalNumber(this.variableName);
        assert descriptor != null && label != null;
        visitor.visitVarInsn(descriptor.getCode, label);
    }
}
