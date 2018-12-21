package com.ck.java_basic.java_sdk_8;

public class FilterEmployeeBySalary implements MyPredicate<Employee>{

	@Override
	public boolean test(Employee t) {
		return t.getSalary() >= 5000;
	}

}
