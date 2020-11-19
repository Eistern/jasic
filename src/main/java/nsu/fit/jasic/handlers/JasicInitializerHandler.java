package nsu.fit.jasic.handlers;

import org.objectweb.asm.MethodVisitor;

public class JasicInitializerHandler extends AbstractTreeRootHandler {

    @Override
    public void handle(MethodVisitor visitor) {
        JasicVariableDefinitionHandler variableDefinition = (JasicVariableDefinitionHandler) this.children.get(0);
        variableDefinition.handle(visitor);
        if (this.children.size() == 2) {
            AbstractVariableInitializer variableInitializer = (AbstractVariableInitializer) this.children.get(1);
            variableInitializer.setVariableName(variableDefinition.getStringData());
            variableInitializer.handle(visitor);
        }
    }
}
