package nsu.fit.jasic.variable;

import org.objectweb.asm.Opcodes;

public enum VariableDescriptor {
    NUMBER(Opcodes.ILOAD, Opcodes.ISTORE, "I"),
    STRING(Opcodes.ALOAD, Opcodes.ASTORE, "Ljava/lang/String;");

    public int getCode;
    public int saveCode;
    public String descriptor;

    VariableDescriptor(int getCode, int saveCode, String descriptor) {
        this.getCode = getCode;
        this.saveCode = saveCode;
        this.descriptor = descriptor;
    }
}
