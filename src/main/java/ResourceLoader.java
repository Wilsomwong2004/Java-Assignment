import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceLoader {
    public static ImageIcon loadIcon(String path) {
        // Try loading as a resource first (works in both IDEs when running from JAR)
        URL url = ResourceLoader.class.getResource(path);
        if (url != null) {
            return new ImageIcon(url);
        }

        // If resource loading fails, try file system (useful for VSCode during development)
        File file = new File("src/main/resources" + path);
        if (file.exists()) {
            return new ImageIcon(file.getAbsolutePath());
        }

        System.err.println("Resource not found: " + path);
        return null; // Or return a default icon
    }
}
