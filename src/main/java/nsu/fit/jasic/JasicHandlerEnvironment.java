package nsu.fit.jasic;

import nsu.fit.jasic.handlers.JasicElementHandler;

import java.util.LinkedList;
import java.util.List;

public class JasicHandlerEnvironment {
    private static final List<JasicElementHandler> treeBranch = new LinkedList<>();
    private static JasicElementHandler treeRoot = null;

    private JasicHandlerEnvironment() {}

    public static void addTreeRoot(JasicElementHandler treeNode) {
        if (treeBranch.isEmpty() && treeRoot == null) {
            treeBranch.add(treeNode);
            treeRoot = treeNode;
        } else {
            treeBranch.get(0).addChild(treeNode);
            treeBranch.add(0, treeNode);
        }
    }

    public static void treeRootUp() {
        treeBranch.remove(0);
    }

    public static void addCommand(JasicElementHandler treeLeaf) {
        treeBranch.get(0).addChild(treeLeaf);
    }

    public static JasicElementHandler getResult() {
        return treeRoot;
    }
}
