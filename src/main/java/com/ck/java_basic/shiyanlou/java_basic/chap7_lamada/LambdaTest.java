package com.ck.java_basic.shiyanlou.java_basic.chap7_lamada;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/5/14
 * @Content:
 */
/*
* 一个 Lambda 表达式具有下面这样的语法特征。
* 它由三个部分组成：
* 第一部分为一个括号内用逗号分隔的参数列表，参数即函数式接口里面方法的参数；
* 第二部分为一个箭头符号：->；
* 第三部分为方法体，可以是表达式和代码块。语法如下：
* */

/*
* 可以得到以下结论：

可访问 static 修饰的成员变量，如果是 final static 修饰，不可再次赋值，只有 static 修饰可再次赋值；
可访问表达式外层的 final 局部变量（不用声明为 final，隐性具有 final 语义），不可再次赋值。
* */
public class LambdaTest {
    final static String salutation = "Hello "; //正确，不可再次赋值
    //static String salutation = "Hello "; //正确，可再次赋值
//    String salutation = "Hello "; //报错
//    final String salutation = "Hello "; //报错

    public static void main(String args[]){
        LambdaTest tester = new LambdaTest();

        // 带有类型声明的表达式
        MathOperation addition = (int a, int b) -> a + b;

        // 没有类型声明的表达式
        MathOperation subtraction = (a, b) -> a - b;

        // 带有大括号、带有返回语句的表达式
        MathOperation multiplication = (int a, int b) -> { return a * b; };

        // 没有大括号和return语句的表达式
        MathOperation division = (int a, int b) -> a / b;

        // 输出结果
        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // 没有括号的表达式
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        // 有括号的表达式
        GreetingService greetService2 = (message) ->
                System.out.println("Hello " + message);

        // 调用sayMessage方法输出结果
        greetService1.sayMessage("Shiyanlou");
        greetService2.sayMessage("Classmate");


        //final String salutation = "Hello "; //正确，不可再次赋值
        //String salutation = "Hello "; //正确，隐性为 final , 不可再次赋值

        // salution = "welcome to "
        GreetingService greetService3 = message ->
                System.out.println(salutation + message);
        greetService3.sayMessage("Shiyanlou");
    }

    // 下面是定义的一些接口和方法

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }
}