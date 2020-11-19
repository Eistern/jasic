package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableStorage;
import org.objectweb.asm.MethodVisitor;

public class JasicVariableDefinitionHandler extends AbstractVariableHandler {
    @Override
    public void handle(MethodVisitor visitor) {
        VariableStorage.saveVariable(this.variableName);
    }
}
