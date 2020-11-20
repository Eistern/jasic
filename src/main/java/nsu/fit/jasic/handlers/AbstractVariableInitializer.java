package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableDescriptor;
import org.objectweb.asm.MethodVisitor;

public abstract class AbstractVariableInitializer extends AbstractVariableHandler {
    protected Object variable;

    @Override
    public abstract void setStringData(String data);

    @Override
    public abstract String getStringData();

    public abstract VariableDescriptor getDescriptor();

    @Override
    public void handle(MethodVisitor visitor) {
        visitor.visitLdcInsn(variable);
    }
}
