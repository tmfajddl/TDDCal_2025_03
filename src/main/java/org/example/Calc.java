package org.example;

public class Calc {

    public static int run(String exp) {
        exp = exp.replace("- ", "+ -");
        exp = exp.replace("/ ", "* 1/");

        if(exp.contains("+") || exp.contains("-")) {
            String[] bits = exp.split(" \\+ ");
            int a = Integer.parseInt(bits[0]);
            int b = Integer.parseInt(bits[1]);
            int c = 0;

            if (bits.length > 2) {
                for(int i = 2; i < bits.length; i++) {
                    c += Integer.parseInt(bits[i]);
                }
            }
            return a + b + c;
        }
        else if(exp.contains("*")||exp.contains("/")) {
            String[] bits = exp.split(" \\* ");
            int a = Integer.parseInt(bits[0]);
            int b = Integer.parseInt(bits[1]);
            int c = 0;

            if (bits.length > 2) {
                for(int i = 2; i < bits.length; i++) {
                    c += Integer.parseInt(bits[i]);
                }
            }
            return a * b * c;
        }
        throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다");
    }

}