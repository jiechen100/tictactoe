package com.jie.h2;

public interface EmployeeBo {
	void save(Employee tt);

	void update( Employee tt);

	void delete(Employee tt );
	
	Employee findByName(String name);
}
