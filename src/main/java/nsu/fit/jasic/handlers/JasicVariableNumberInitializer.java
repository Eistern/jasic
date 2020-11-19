package nsu.fit.jasic.handlers;

public class JasicVariableNumberInitializer extends AbstractVariableInitializer {

    @Override
    public void setStringData(String data) {
        this.variable = Integer.parseInt(data);
    }

    @Override
    public String getStringData() {
        return variable.toString();
    }
}
