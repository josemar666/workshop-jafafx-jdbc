package model.services;

import java.util.List;

import model.DAO.DaoFactory;
import model.DAO.DepartmentDao;
import model.entites.Department;

public class DepartamentService {
	
	private DepartmentDao dao = DaoFactory.creteDeaprtmentDao();
	
	public List<Department> findAll(){
	
	  
	  return dao.findAll();
		
	}

}
