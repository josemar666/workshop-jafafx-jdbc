package model.DAO;

import java.util.List;

import model.entites.Department;
import model.entites.Seller;

public interface SellerDao {

	void insert(Seller obj);
	void update(Seller obj);
	void deleteByid(Integer id);
	Seller findByid(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department dp);
	
	
	
}
