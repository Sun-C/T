package com.jt.dubbo;

public class Return {
    public static void main(String[] args) {
        int a = ret();
        System.out.println(a);
    }

    private static int ret() {
        int b = 0;
        try {
            b=1;
            //return b;
        }catch (Exception e){

        }finally {
            b=2;
        }
        return b;
    }
}
