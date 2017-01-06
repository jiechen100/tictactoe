package com.jie.h2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmployeeHibernateApp {

	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"config/BeanLocations.xml");

		EmployeeBo employeeBo = (EmployeeBo) appContext.getBean("employeeBo");

		/** insert **/
		Employee t;
		t = employeeBo.findByName("Hello");
		System.out.println(t == null ? "cannot find hello" : t);

		// t = new Employee();
		// t.setId(4);
		// t.setName("first");
		// employeeBo.save(t);

		/** select **/
		// t = employeeBo.findByName("first");
		// System.out.println(t);

		/** update **/
		t.setName("second");
		employeeBo.update(t);
		System.out.println(t);
		// t = employeeBo.findByName("first");
		// System.out.println(t == null ? "cannot find first" : "find first");
		// t = employeeBo.findByName("second");
		// System.out.println(t);
		//
		// /** delete **/
		// employeeBo.delete(t);

		t = employeeBo.findByName("second");
		System.out.println(t == null ? "cannot find second" : "find second");

		System.out.println("Done");
	}

}
