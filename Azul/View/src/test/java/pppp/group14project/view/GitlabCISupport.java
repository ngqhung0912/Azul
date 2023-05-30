package pppp.group14project.view;

public class GitlabCISupport {

    public static void headless() {
        System.setProperty("prism.verbose", "true"); // optional
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "native");
        System.setProperty("testfx.setup.timeout", "5000");
    }
}

