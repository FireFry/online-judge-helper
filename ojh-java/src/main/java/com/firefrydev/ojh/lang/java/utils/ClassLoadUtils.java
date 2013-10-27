package com.firefrydev.ojh.lang.java.utils;

import com.firefrydev.ojh.local.Source;
import org.apache.commons.io.FileUtils;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class ClassLoadUtils {

    public static final File TEMP_DIR = new File(new File(System.getProperty("user.home"), ".ffd"), "temp");

    public static Class load(Source source) throws Exception {
        File tempDir = TEMP_DIR;
        FileUtils.forceMkdir(tempDir);
        File file = new File(tempDir, source.getClassName() + ".java");
        if (file.exists()) {
            FileUtils.forceDelete(file);
        }
        file.createNewFile();
        FileUtils.writeStringToFile(file, source.getSourceCode());
        return load(tempDir, source.getClassName());
    }

    public static Class load(File location, String className) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        File javaFile = new File(location, className.replace(".", File.separator) + ".java");
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(javaFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();

        fileManager.close();

        URL classUrl = new URL("file:///" + location.getAbsolutePath() + "/");
        URL[] classUrls = { classUrl };
        URLClassLoader ucl = new URLClassLoader(classUrls);

        Class testClass = ucl.loadClass(className);

        File classFile = new File(location, className.replace(".", File.separator) + ".class");
        FileUtils.forceDelete(classFile);

        return testClass;
    }

}
