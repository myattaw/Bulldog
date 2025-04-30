package me.yattaw.bulldog.reflection;

import me.yattaw.bulldog.core.players.Player;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

public final class ReflectionHelper {

    /**
     * Dynamically discovers all available Player types in the package.
     * @return List of available player type names (e.g., ["ai", "human", "random"])
     */
    public static List<String> getAvailablePlayerTypes() {
        String packageName = "me.yattaw.bulldog.core.players.types";
        List<String> types = new ArrayList<>();

        try {
            List<Class<?>> playerClasses = getClassesInPackage(packageName).stream()
                    .filter(cls -> Player.class.isAssignableFrom(cls) && !cls.equals(Player.class))
                    .toList();

            for (Class<?> playerClass : playerClasses) {
                String className = playerClass.getSimpleName();
                String typeName = className.replace("Player", "").toLowerCase();
                types.add(typeName);
            }
        } catch (Exception e) {
            System.err.println("Error scanning player types: " + e.getMessage());
        }
        return types;
    }

    /**
     * Creates a Player instance by type name (e.g., "ai", "random").
     * @param playerType The type of player to create (case-insensitive).
     * @return New Player instance, or null if invalid.
     */
    public static Player createPlayerInstance(String playerType) {
        String packageName = "me.yattaw.bulldog.core.players.types";
        try {
            Optional<Class<?>> matchedClass = ReflectionHelper.getClassesInPackage(packageName).stream()
                    .filter(Player.class::isAssignableFrom)
                    .filter(cls -> cls.getSimpleName().equalsIgnoreCase(playerType + "Player"))
                    .findFirst();

            if (!matchedClass.isPresent()) {
                return null;
            }

            Class<?> playerClass = matchedClass.get();
            Constructor<?> constructor = playerClass.getConstructor(String.class);

            String playerName = playerClass.getSimpleName().replace("Player", "");
            return (Player) constructor.newInstance(playerName);
        } catch (Exception e) {
            return null;
        }
    }


    // Helper: Scans package for classes
    public static List<Class<?>> getClassesInPackage(String packageName) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);

        List<Class<?>> classes = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                classes.addAll(findClasses(new File(resource.getFile()), packageName));
            }
        }
        return classes;
    }

    // Helper: Recursively finds classes in a directory
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) return classes;

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

}