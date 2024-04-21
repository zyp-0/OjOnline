package org.example.utils;

public class AnswerUtil {
    /**
     * 将选项的字符串形式转换为整数形式。
     *
     * @param s 选项的字符串形式
     * @return 选项的整数形式
     */
    public static String optionIntegerToString(String s) {
        int i = Integer.parseInt(s);
        return String.valueOf((char) (i + 'A'));
    }

    /**
     * 将整数形式的选项转换为字符串形式。
     *
     * @param i 整数形式的选项
     * @return 字符串形式的选项
     */
    public static String integerToOptionString(int i) {
        return String.valueOf((char) (i + 'A'));
    }
}
