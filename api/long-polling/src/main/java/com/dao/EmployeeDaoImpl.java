package com.dao;

import java.util.List;

import com.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl implements EmployeeDaoInterface {

	private final SessionFactory sessionFactory;

	@Autowired
	public EmployeeDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Employee> getAllEmployees() {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Employee> theQuery = currentSession.createQuery(
				"from Employee order by name", Employee.class);
		return theQuery.getResultList();
	}

	@Override
	public void saveEmployee(Employee theEmployee) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theEmployee);
	}

	@Override
	public Employee getEmployee(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Employee> employeeQuery = currentSession.createQuery(
				"from Employee where employee_id = :employeeID ", Employee.class);
		employeeQuery.setParameter("employeeID", theId);
		Employee emp = employeeQuery.getSingleResult();

		String teamName = (String) currentSession.createQuery(
				"select name from Team t where team_id = :teamID ")
				.setParameter("teamID", emp.getTeam_id())
				.uniqueResult();
		emp.setTeamName(teamName);

		return emp;
	}

	@Override
	public void deleteEmployee(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery =
				currentSession.createQuery("delete from Employee where employee_id=:employeeId");
		theQuery.setParameter("employeeId", theId);
		theQuery.executeUpdate();		
	}

}











