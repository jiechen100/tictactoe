package com.jie.h2;

public class EmployeeBoImpl implements EmployeeBo {
	EmployeeDao employeeDao;

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public void save(Employee tt) {
		employeeDao.save(tt);
	}

	public void update(Employee tt) {
		employeeDao.update(tt);
	}

	public void delete(Employee tt) {
		employeeDao.delete(tt);
	}

	public Employee findByName(String name) {
		return employeeDao.findByName(name);
	}
}
