package nsu.fit.jasic.handlers;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractTreeRootHandler implements JasicElementHandler {
    protected final List<JasicElementHandler> children = new LinkedList<>();

    @Override
    public void addChild(JasicElementHandler child) {
        children.add(child);
    }
}
