package nsu.fit.jasic.handlers;

import org.apache.commons.lang3.NotImplementedException;
import org.objectweb.asm.MethodVisitor;

public interface JasicElementHandler {
    default void setStringData(String data) {
        throw new NotImplementedException("String data not supported for root elements");
    }

    default String getStringData() {
        throw new NotImplementedException("String data not supported for root elements");
    }

    void addChild(JasicElementHandler child);

    void handle(MethodVisitor visitor);
}
