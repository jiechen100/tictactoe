package com.jie.h2;

import org.springframework.transaction.annotation.Transactional;

public interface EmployeeDao {
	@Transactional(readOnly = false)
	void save(Employee tt);

	@Transactional(readOnly = false)
	void update(Employee tt);

	@Transactional(readOnly = false)
	void delete(Employee tt);

	Employee findByName(String name);
}
