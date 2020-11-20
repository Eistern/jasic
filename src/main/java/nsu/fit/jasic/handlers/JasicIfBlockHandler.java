package nsu.fit.jasic.handlers;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JasicIfBlockHandler extends AbstractTreeRootHandler {
    private boolean invert = false;

    @Override
    public void setStringData(String data) {
        invert = data.equals("not ");
    }

    @Override
    public void handle(MethodVisitor visitor) {
        JasicElementHandler leftSide = this.children.get(0);
        JasicElementHandler rightSide = this.children.get(1);
        JasicElementHandler codeBlock = this.children.get(2);
        Label skipBlock = new Label();
        leftSide.handle(visitor);
        rightSide.handle(visitor);
        if (invert) {
            visitor.visitJumpInsn(Opcodes.IF_ICMPLE, skipBlock);
        } else {
            visitor.visitJumpInsn(Opcodes.IF_ICMPGE, skipBlock);
        }
        codeBlock.handle(visitor);
        visitor.visitLabel(skipBlock);
    }
}
