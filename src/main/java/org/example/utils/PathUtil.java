package org.example.utils;

import java.io.File;

/**
 * 路径工具类
 */
public class PathUtil {
    public static String getCompilerPath(String Path) {
        String res = "target" + File.separator
                + "test-classes" + File.separator
                + "cases" + File.separator
                + "answers" + File.separator
                + Path.substring(0, Path.lastIndexOf("/")) + File.separator
                + Path.substring(Path.lastIndexOf("/") + 1);
        return res;
    }

    public static String getClassPath(){
        return "target" + File.separator
                + "test-classes" + File.separator
                + "cases" + File.separator
                + "answers" + File.separator
                + "code-answers" + File.separator;
    }

}
