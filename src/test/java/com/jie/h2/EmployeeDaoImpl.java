package com.jie.h2;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDaoImpl extends HibernateDaoSupport implements EmployeeDao {

	HibernateTemplate hibernateTemplate() {
		HibernateTemplate ht = getHibernateTemplate();
		ht.setCheckWriteOperations(false);
		return ht;
	}

	@Override
	public void save(Employee tt) {
		HibernateTemplate ht = hibernateTemplate();
		ht.save(tt);
		ht.flush();
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Employee tt) {
		// Session session = getSessionFactory().openSession();
		// Transaction tx = session.beginTransaction();
		// session.persist(tt);
		// tx.commit();
		// session.close();
		// getSessionFactory().getSessionFactoryOptions().toString();
		HibernateTemplate ht = hibernateTemplate();
		ht.update(tt);
		ht.flush();
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(Employee tt) {
		HibernateTemplate ht = hibernateTemplate();
		ht.delete(tt);
		ht.flush();
	}

	public Employee findByName(String name) {
		// from Employee where ... the Employee is the java class name, not
		// table name, case sensitive
		List<?> list = getHibernateTemplate().find(
				"from Employee where name=?", name);
		if (list != null && list.size() > 0)
			return (Employee) list.get(0);
		else
			return null;
	}

}
