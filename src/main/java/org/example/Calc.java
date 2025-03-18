package org.example;

public class Calc {

    public static int run(String exp) {
        exp = exp.replace("- ", "+ -");
        String[] bits = exp.split(" \\+ | \\* ");
        if(exp.contains("*")) {
            if(exp.contains("+") || exp.contains("-")){
                int b= 1;
                for(int i = 1; i < bits.length; i++)
                    b *= Integer.parseInt(bits[i]);
                int a = Integer.parseInt(bits[0]);
                return a + b;
            }
            else{
                int a = 1;

                for(int i = 0; i < bits.length; i++) {
                    a *= Integer.parseInt(bits[i]);
                }
                return a;

            }
        }
        else if(exp.contains("+") || exp.contains("-")) {
                int a = 0;

                for(int i = 0; i < bits.length; i++) {
                    a += Integer.parseInt(bits[i]);
                }
                return a;


        }
        throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다");
    }

}