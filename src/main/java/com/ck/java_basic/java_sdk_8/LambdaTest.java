/*
package com.ck.java_basic.java_sdk_8;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;


public class LambdaTest {

    //原来匿名内部类
    @Test
    public void test1() {
        //比较器
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        TreeSet<Integer> ts = new TreeSet<>(com);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("adbc");
        System.out.println(stringBuilder.reverse());
    }

    //现在的 Lambda 表达式
    @Test
    public void test2() {
        //比较器
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

        TreeSet<Integer> ts = new TreeSet<>(com);
    }

    List<Employee> employees = Arrays.asList(
            new Employee("佟刚", 18, '男', 0),
            new Employee("宋红康", 20, '女', 5000),
            new Employee("菜菜老师", 18, '女', 8000),
            new Employee("李立超", 22, '男', 9000),
            new Employee("封捷", 30, '男', 11000)
    );

    //需求：获取尚硅谷所有老师，工资大于 5000 的员工的信息
    public List<Employee> filterEmployeeBySalary(List<Employee> employees) {
        List<Employee> list = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getSalary() >= 5000) {
                list.add(employee);
            }
        }

        return list;
    }

    //需求：获取尚硅谷所有老师，年龄大于 18  的员工的信息
    public List<Employee> filterEmployeeByAge(List<Employee> employees) {
        List<Employee> list = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getAge() > 18) {
                list.add(employee);
            }
        }

        return list;
    }

    @Test
    public void test3() {
        List<Employee> list = filterEmployeeBySalary(employees);

        for (Employee employee : list) {
            System.out.println(employee);
        }

        System.out.println("---------------------------------");

        List<Employee> list2 = filterEmployeeByAge(employees);

        for (Employee employee : list2) {
            System.out.println(employee);
        }
    }

    //优化方式一：策略设计模式
    public List<Employee> filterEmployees(List<Employee> employees, MyPredicate<Employee> mp) {
        List<Employee> list = new ArrayList<>();

        for (Employee employee : employees) {
            if (mp.test(employee)) {
                list.add(employee);
            }
        }

        return list;
    }

    @Test
    public void test4() {
        List<Employee> list = filterEmployees(employees, new FilterEmployeeBySalary());

        for (Employee employee : list) {
            System.out.println(employee);
        }

        System.out.println("---------------------------------");

        List<Employee> list2 = filterEmployees(employees, new FilterEmployeeByAge());

        for (Employee employee : list2) {
            System.out.println(employee);
        }
    }

    //优化方式二：策略设计模式 + 匿名内部类
    @Test
    public void test5() {
        List<Employee> list = filterEmployees(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee t) {
                return t.getSalary() >= 5000;
            }
        });

        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    //优化方式三：策略设计模式 + Lambda 表达式
    @Test
    public void test6() {
        List<Employee> list = filterEmployees(employees, (e) -> e.getSalary() >= 5000);
        list.forEach(System.out::println);
    }

    //优化方式四:Stream API
    @Test
    public void test7() {
        employees.stream()
                .filter((e) -> e.getSalary() >= 5000)
                .forEach(System.out::println);

        System.out.println("---------------------------------------");

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }
}
*/
