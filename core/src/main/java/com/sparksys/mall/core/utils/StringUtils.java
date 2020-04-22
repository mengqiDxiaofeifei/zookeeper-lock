package com.sparksys.mall.core.utils;

import java.util.StringTokenizer;

/**
 * 中文类名:
 * 中文描述:
 *
 * @author zhouxinlei
 * @date 2019-11-20 15:57:30
 */
public class StringUtils {
    /**
     * 子字符串个数统计函数
     *
     * @param source    源字符串
     * @param separator 字符串的分割符号
     * @return int 子字符串个数
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:59:17
     */
    public static int countString(String source, String separator) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(source)){
            return 0;
        }
        StringTokenizer token = new StringTokenizer(source, separator);
        return token.countTokens();
    }

    /**
     * 数字转换为带分隔符的字符串，及在百位、十万。。等
     * 前一位加上"," 如source="1234567" return "1,234,567"
     *
     * @param source
     * @return java.lang.String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 16:01:30
     */
    public static String autoAddComma(String source) {
        String numeric = "";
        if (source != "" && source.length() > 3) {
            int neg = 0;
            if (source.indexOf("-") == 0) {
                source = source.replace("-", "");
                neg = 1;
            }
            int digit = source.length();
            int residue = digit % 3;// 取出前面还有几位数据
            int divisor = digit / 3;// 得到总共有几对3位数字

            if (residue != 0) {
                numeric = source.substring(0, residue);
                source = source.substring(residue, digit);
            }
            for (int i = 1; i <= divisor; i++) {
                String data = source.substring(3 * (i - 1), 3 * i);
                if (residue == 0 && i == 1) {
                    numeric = numeric.concat(data);
                } else {
                    numeric = numeric.concat(",").concat(data);
                }
            }
            if (neg == 1) {
                numeric = "-" + numeric;
            }
        } else {
            numeric = source;
        }
        return numeric;
    }

    /**
     * 数字转换为大写数字
     *
     * @param value
     * @return java.lang.String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 16:05:52
     */
    public static String changeToBig(double value) {
        char[] hint = {'拾', '佰', '仟'}; // 段内位置表示
        char[] vanity = {'万', '亿'}; // 段名表示
        char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'}; // 数字表示
        long midVal = (long) (value * 100 + 0.5); // 转化成整形,四舍五入
        String valStr = String.valueOf(midVal); // 转化成字符串
        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        if (valStr.equals("0")) {
            prefix = "";
            suffix = "零";
        } else {
            String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
            String rail = valStr.substring(valStr.length() - 2); // 取小数部分
            // 处理小数点后面的数
            if (rail.equals("00")) { // 如果小数部分为0
                suffix = "整";
            } else {
                suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
            }
            // 处理小数点前面的数
            char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
            boolean preZero = false; // 标志当前位的上一位是否为有效0位（如万位的0对千位无效）
            byte zeroSerNum = 0; // 连续出现0的次数
            for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
                int idx = (chDig.length - i - 1) % 4; // 取段内位置
                int vidx = (chDig.length - i - 1) / 4; // 取段位置

                if (chDig[i] == '0') { // 如果当前字符是0
                    preZero = true;
                    zeroSerNum++; // 连续0次数递增
                    if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                        prefix += vanity[vidx - 1];
                        preZero = false; // 不管上一位是否为0，置为无效0位
                    }
                } else {
                    zeroSerNum = 0; // 连续0次数清零
                    if (preZero) { // 上一位为有效0位
                        prefix += digit[0]; // 只有在这地方用到'零'
                        preZero = false;
                    }
                    prefix += digit[chDig[i] - '0']; // 转化该数字表示
                    if (idx > 0) {
                        prefix += hint[idx - 1];
                    }
                    if (idx == 0 && vidx > 0) {
                        prefix += vanity[(vidx + 1) % 2];
                        for (int j = 0; j < (vidx + 1) / 2 - 1; j++) {
                            prefix += vanity[1];
                        }
                    }
                }
            }
            if (prefix.length() > 0) {
                prefix += '元'; // 如果整数部分存在,则有元的字样
            }
        }
        return prefix + suffix; // 返回正确表示
    }
}
