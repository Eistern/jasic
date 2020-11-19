package nsu.fit.jasic.variable;

import java.util.HashMap;
import java.util.Map;

public class VariableStorage {
    private static final Map<String, Integer> labelMap = new HashMap<>();
    private static final Map<String, VariableDescriptor> descriptorMap = new HashMap<>();
    private static int labelCounter = 1;

    public static void saveVariable(String variableName, VariableDescriptor variableType) {
        labelMap.put(variableName, labelCounter++);
        descriptorMap.put(variableName, variableType);
    }

    public static VariableDescriptor getDescriptor(String variableName) {
        return descriptorMap.get(variableName);
    }

    public static Integer getLabel(String variableName) {
        return labelMap.get(variableName);
    }
}
