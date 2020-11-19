package nsu.fit.jasic.handlers;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JasicPrintHandler extends AbstractTreeRootHandler {
    @Override
    public void handle(MethodVisitor visitor) {
        assert this.children.size() == 1;
        JasicElementHandler loadVariableHandler = this.children.get(0);
        assert loadVariableHandler instanceof JasicVariableLoadHandler;

        visitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        String variableSignature = ((JasicVariableLoadHandler) loadVariableHandler).getVariableSignature();
        assert variableSignature != null;
        loadVariableHandler.handle(visitor);
        visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", String.format("(%s)V", variableSignature), false);
    }
}
