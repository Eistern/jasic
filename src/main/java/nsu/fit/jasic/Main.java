package nsu.fit.jasic;

import nsu.fit.jasic.handlers.JasicElementHandler;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        InputStream input = new FileInputStream(new File(args[0]));

        ParserDescriptor parser = Parboiled.createParser(ParserDescriptor.class);
        ParseRunner<Object> parserRunner = new ReportingParseRunner<Object>(parser.jasicRunnable());
        String input = "print a";
        ParsingResult<Object> parsingResult = parserRunner.run(input);
        System.out.println(ParseTreeUtils.printNodeTree(parsingResult));
        JasicElementHandler resultHandler = JasicHandlerEnvironment.getResult();

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Main", null, "java/lang/Object", null);

        //Default <init>
        MethodVisitor initVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        initVisitor.visitCode();
        initVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        initVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        initVisitor.visitInsn(Opcodes.RETURN);
        initVisitor.visitMaxs(1, 1);
        initVisitor.visitEnd();

        //main method
        MethodVisitor methodVisitor = classWriter
                .visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V",
                        null, null);
        methodVisitor.visitCode();
        resultHandler.handle(methodVisitor);
        methodVisitor.visitInsn(Opcodes.RETURN);

        methodVisitor.visitEnd();
        classWriter.visitEnd();

        FileOutputStream outputStream = new FileOutputStream("Main.class");
        outputStream.write(classWriter.toByteArray());
        outputStream.close();
    }
}
