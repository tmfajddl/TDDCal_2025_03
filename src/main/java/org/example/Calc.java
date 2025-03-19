package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {

    public static int run(String exp) {
        // 괄호 제거
        exp = stripOuterBrackets(exp);

        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }

        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");

        boolean needToCompound = needToPlus && needToMulti;

        if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));

            return run(newExp);
        }

        if (needToPlus) {
            exp = exp.replace("- ", "+ -");

            String[] bits = exp.split(" \\+ ");

            int sum = 0;

            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }

            return sum;
        } else if (needToMulti) {
            String[] bits = exp.split(" \\* ");

            int sum = 1;

            for (int i = 0; i < bits.length; i++) {
                sum *= Integer.parseInt(bits[i]);
            }

            return sum;
        }

        throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다");
    }

    private static String stripOuterBrackets(String exp) {
        int outerBracketsCount = 0;
        int i = 0;
        int j = exp.length()-1;
        while (i < exp.length()) {
            while (j > ) {
                while (exp.charAt(i) == '(' && exp.charAt(j) == ')') {
                    outerBracketsCount++;
                }
                j--;
            }
            i++;
        }

        if (outerBracketsCount == 0) return exp;

        else{
            String sub = exp.substring(i + 1, j);
            String[] arr = sub.split(" \\+ ");
            int a = 0;

            for (int n = 0; n < arr.length; n++) {
                a += Integer.parseInt(arr[n]);
            }
            return exp.replace(sub, String.valueOf(a));
        }
    }

}