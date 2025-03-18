package org.example;

public class Calc {

    public static int run(String exp) {
        exp = exp.replace("- ", "+ -");
        exp = exp.replace("/ ", "* 1/");

        if(exp.contains("+") || exp.contains("-")) {
            String[] bits = exp.split(" \\+ ");
            int a = 0;

                for(int i = 0; i < bits.length; i++) {
                    a += Integer.parseInt(bits[i]);
            }
            return a;
        }
        if(exp.contains("*")||exp.contains("/")) {
            String[] bits = exp.split(" \\* ");
            int a = 1;

                for(int i = 0; i < bits.length; i++) {
                    a *= Integer.parseInt(bits[i]);
            }
            return a;
        }
        throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다");
    }

}