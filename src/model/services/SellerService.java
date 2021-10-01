package model.services;

import java.util.List;

import model.DAO.DaoFactory;
import model.DAO.SellerDao;
import model.entites.Seller;

public class SellerService {
	
	private SellerDao dao = DaoFactory.createSellerDao();
	
	public List<Seller> findAll(){
	
	  
	  return dao.findAll();
		
	}
	public void saveOrUpdate(Seller obj) {
		if(obj.getId() == null) {
		 dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Seller obj) {
		dao.deleteByid(obj.getId());
	}

}
