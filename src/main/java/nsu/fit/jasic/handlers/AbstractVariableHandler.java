package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableStorage;
import org.apache.commons.lang3.NotImplementedException;

public abstract class AbstractVariableHandler implements JasicElementHandler {
    protected String variableName;

    @Override
    public void setStringData(String data) {
        this.variableName = data;
    }

    @Override
    public String getStringData() {
        return this.variableName;
    }

    @Override
    public void addChild(JasicElementHandler child) {
        throw new NotImplementedException("Leaf elements not supporting children");
    }

    public String getVariableSignature() {
        return VariableStorage.getDescriptor(variableName).descriptor;
    }
}
