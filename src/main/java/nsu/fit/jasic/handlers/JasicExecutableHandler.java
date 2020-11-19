package nsu.fit.jasic.handlers;

import org.objectweb.asm.MethodVisitor;

public class JasicExecutableHandler extends AbstractTreeRootHandler {
    @Override
    public void handle(MethodVisitor visitor) {
        for (JasicElementHandler child : this.children) {
            child.handle(visitor);
        }
    }
}
