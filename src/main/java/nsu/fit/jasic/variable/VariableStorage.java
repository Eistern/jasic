package nsu.fit.jasic.variable;

import org.objectweb.asm.Label;

import java.util.HashMap;
import java.util.Map;

public class VariableStorage {
    private static final Map<String, Integer> localNumberMap = new HashMap<>();
    private static final Map<String, VariableDescriptor> descriptorMap = new HashMap<>();
    private static final Map<String, Label> labelMap = new HashMap<>();
    private static int labelCounter = 1;

    public static void saveVariable(String variableName) {
        localNumberMap.put(variableName, labelCounter++);
    }

    public static void updateVariable(String variableName, VariableDescriptor variableType) {
        descriptorMap.put(variableName, variableType);
    }

    public static void saveLabel(String name, Label instance) {
        labelMap.put(name, instance);
    }

    public static Label getLabel(String name) {
        return labelMap.get(name);
    }

    public static VariableDescriptor getDescriptor(String variableName) {
        return descriptorMap.get(variableName);
    }

    public static Integer getLocalNumber(String variableName) {
        return localNumberMap.get(variableName);
    }
}
