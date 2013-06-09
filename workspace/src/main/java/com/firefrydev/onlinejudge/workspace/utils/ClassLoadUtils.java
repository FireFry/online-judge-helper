package com.firefrydev.onlinejudge.workspace.utils;

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

    public static Class load(String location, String className) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        File javaFile = new File(location + File.separator + className.replace(".", File.separator) + ".java");
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(javaFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();

        fileManager.close();

        URL classUrl;
        classUrl = new URL("file:///" + location + (!location.endsWith("/") ? "/" : ""));
        URL[] classUrls = { classUrl };
        URLClassLoader ucl = new URLClassLoader(classUrls);

        Class testClass = ucl.loadClass(className);

        File classFile = new File(location + File.separator + className.replace(".", File.separator) + ".class");
        FileUtils.forceDelete(classFile);

        return testClass;
    }

}
