package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entites.Department;

public class DepartamentService {
	
	public List<Department> findAll(){
	  List<Department> list = new ArrayList<>();
	  list.add(new Department(1 ,"BOOKS"));
	  list.add(new Department(2,"COMPUTERS"));
	  list.add(new Department(3, "ELETRONICS"));
	  
	  return list;
		
	}

}
