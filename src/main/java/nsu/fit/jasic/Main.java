package nsu.fit.jasic;

import nsu.fit.jasic.handlers.JasicElementHandler;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.parboiled.Node;
import org.parboiled.Parboiled;
import org.parboiled.common.FileUtils;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: <executable.jsc>");
            return;
        }
        String input = FileUtils.readAllText(args[0]);
        String outputFileName = args[0].split("\\.jcs$")[0];

        ParserDescriptor parser = Parboiled.createParser(ParserDescriptor.class);
        ParseRunner<Object> parserRunner = new ReportingParseRunner<>(parser.jasicRunnable());
        ParsingResult<Object> parsingResult = parserRunner.run(input);
        if (parsingResult.hasErrors()) {
            System.err.println("Parse Error " + parsingResult.parseErrors.get(0).getStartIndex());
            return;
        }
        System.out.println(ParseTreeUtils.printNodeTree(parsingResult));
        List<Node<Object>> allDeclarations = new ArrayList<>();
        ParseTreeUtils.collectNodes(parsingResult.parseTreeRoot, (Node<Object> node) -> node.getLabel().equals("variableCreate"), allDeclarations);
        JasicElementHandler resultHandler = JasicHandlerEnvironment.getResult();

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, outputFileName, null, "java/lang/Object", null);

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
                        null, new String[]{"java/lang/Throwable"});
        methodVisitor.visitCode();
        resultHandler.handle(methodVisitor);
        methodVisitor.visitInsn(Opcodes.RETURN);

        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
        classWriter.visitEnd();

        FileOutputStream outputStream = new FileOutputStream(String.format("%s.class", outputFileName));
        outputStream.write(classWriter.toByteArray());
        outputStream.close();
    }
}
