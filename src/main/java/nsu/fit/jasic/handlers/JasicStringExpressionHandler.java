package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableDescriptor;
import org.apache.commons.lang3.NotImplementedException;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.LinkedList;
import java.util.List;

public class JasicStringExpressionHandler extends AbstractVariableInitializer {
    private final List<JasicElementHandler> children = new LinkedList<>();

    @Override
    public void setStringData(String data) {
        throw new NotImplementedException("String data not supported for root elements");
    }

    @Override
    public String getStringData() {
        throw new NotImplementedException("String data not supported for root elements");
    }

    @Override
    public VariableDescriptor getDescriptor() {
        return VariableDescriptor.STRING;
    }

    @Override
    public void addChild(JasicElementHandler child) {
        children.add(child);
    }

    @Override
    public void handle(MethodVisitor visitor) {
        if (children.size() == 1) {
            children.get(0).handle(visitor);
        } else {
            visitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            visitor.visitInsn(Opcodes.DUP);
            visitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            for (JasicElementHandler child : children) {
                child.handle(visitor);
                String descr = ((AbstractVariableInitializer) child).getDescriptor().descriptor;
                visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", String.format("(%s)Ljava/lang/StringBuilder;", descr), false);
            }
            visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        }
    }
}
