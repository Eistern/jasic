package nsu.fit.jasic.handlers;

import nsu.fit.jasic.variable.VariableDescriptor;

public class JasicVariableNumberInitializer extends AbstractVariableInitializer {

    @Override
    public void setStringData(String data) {
        this.variable = Integer.parseInt(data);
    }

    @Override
    public String getStringData() {
        return variable.toString();
    }

    @Override
    public VariableDescriptor getDescriptor() {
        return VariableDescriptor.NUMBER;
    }
}
