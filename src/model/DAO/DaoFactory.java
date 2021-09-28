package model.DAO;

import db.DB;
import model.DAO.impl.DepartmentDaoJDBC;
import model.DAO.impl.SellerDAOjdbc;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDAOjdbc(DB.getConnection());
	}
	
	public static DepartmentDao creteDeaprtmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
}
