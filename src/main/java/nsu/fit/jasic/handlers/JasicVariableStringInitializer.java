package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableDescriptor;

public class JasicVariableStringInitializer extends AbstractVariableInitializer {
    @Override
    public void setStringData(String data) {
        this.variable = data.substring(1, data.length() - 1);
    }

    @Override
    public String getStringData() {
        return (String) this.variable;
    }

    @Override
    public VariableDescriptor getDescriptor() {
        return VariableDescriptor.STRING;
    }
}
