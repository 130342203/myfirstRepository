package com.ck.java_basic.java_sdk_8;

public class FilterEmployeeByAge implements MyPredicate<Employee> {

	@Override
	public boolean test(Employee t) {
		return t.getAge() > 18;
	}

}
