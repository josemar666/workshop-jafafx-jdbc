package model.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.DAO.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class SellerDAOjdbc implements SellerDao {

	private Connection com ;
	
	
	public SellerDAOjdbc(Connection com) {
		
		this.com = com;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		
		try {
			st = com.prepareStatement("INSERT INTO seller(Name ,Email , BirthDate , BaseSalary,DepartmentId) VALUES (?,?,?,?,?) " ,
					Statement.RETURN_GENERATED_KEYS);
					
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			int executelinhas = st.executeUpdate();
			
			if(executelinhas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
					
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("ERRO INESPERADO NENHUMA LINHA FOI AFETADA !!!");
			}
		}catch(SQLException e) {
			throw new DbException ("erro não foi concluida a operação !!!!");
		}
		finally {
			DB.closeStatement(st);
			
		}
		
	}

	@Override
	public void update(Seller obj) {
       PreparedStatement st = null;
		
		try {
			st = com.prepareStatement("UPDATE seller Set Name = ? , Email = ? , BirthDate = ? , BaseSalary = ? , DepartmentId = ?  WHERE Id = ?  " ,
					Statement.RETURN_GENERATED_KEYS);
					
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
			
		}catch(SQLException e) {
			throw new DbException ("erro não foi concluida a operação !!!!");
		}
		finally {
			DB.closeStatement(st);
			
		}
		
		
	}

	@Override
	public void deleteByid(Integer id) {
		 PreparedStatement st = null;
		 
		 try {
			 st = com.prepareStatement("DELETE FROM seller\r\n" + 
			 		"WHERE Id = ?");
			 
			 st.setInt(1, id);
			 st.executeUpdate();
			 
		 }catch(SQLException e) {
			 throw new DbException(e.getMessage());
		 }finally {
			 DB.closeStatement(st);
			 
			 
		 }
		
	}

	@Override
	public Seller findByid(Integer id) {
	   PreparedStatement st = null;
	   ResultSet rs = null;
	   
	   
	   try {
		   com = DB.getConnection();
		   st = com.prepareStatement("SELECT seller.*,department.Name as DepName "
		   		+ "FROM seller INNER JOIN department "
		   		+ "ON seller.DepartmentId = department.Id "
		   		+ "WHERE seller.Id = ? ");
		   
		        st.setInt(1, id);
		        rs = st.executeQuery();
		        if(rs.next()) {
		          Department dp = instantiateDepartment(rs);
		         
		          Seller ob = instatiateSeller(rs, dp);
		          
		          return ob;
		        }
		      return null;
	   }catch(SQLException e) {
		   
		   throw new DbException(e.getMessage());
	   }
	   finally {
		   DB.closeResultSet(rs);
		   DB.closeStatement(st);
		  
		   
	   }
	
	}

	private Seller instatiateSeller(ResultSet rs, Department dp) throws SQLException {
		 Seller ob =  new Seller();
         ob.setId(rs.getInt("Id"));
         ob.setName(rs.getString("Name"));
         ob.setEmail(rs.getString("Email"));
         ob.setBithDate(rs.getDate("BirthDate"));
         ob.setBaseSalary(rs.getDouble("BaseSalary"));
         ob.setDepartment(dp);
         return ob;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
		
	}

	@Override
	public List<Seller> findAll() {
		 PreparedStatement st = null;
		   ResultSet rs = null;
		   
		   
		   try {
			   com = DB.getConnection();
			   st = com.prepareStatement("SELECT seller.*,department.Name as DepName " + 
			   		"FROM seller INNER JOIN department " + 
			   		" ON seller.DepartmentId = department.Id " + 
			   		 			   		" ORDER BY Name");
			   
			       
			        rs = st.executeQuery();
			       
			        List<Seller> lista = new ArrayList<>();
			        Map<Integer , Department> map = new HashMap<>();
			        
			        
			        while(rs.next()) {
			        	
			        Department dep = map.get(rs.getInt("DepartmentId"));
			        
			           if(dep == null) {
			        	    dep = instantiateDepartment(rs);
			        	   map.put(rs.getInt("DepartmentId"), dep);
			           }
			        		        
			         
			          Seller ob = instatiateSeller(rs, dep);
			          
			          lista.add(ob);
			        }
			      return lista;
		   }catch(SQLException e) {
			   
			   throw new DbException(e.getMessage());
		   }
		   finally {
			   DB.closeResultSet(rs);
			   DB.closeStatement(st);
			  
			   
		   }
	}

	@Override
	public List<Seller> findByDepartment(Department dp) {
		 PreparedStatement st = null;
		   ResultSet rs = null;
		   
		   
		   try {
			   com = DB.getConnection();
			   st = com.prepareStatement("SELECT seller.*,department.Name as DepName " + 
			   		"FROM seller INNER JOIN department " + 
			   		" ON seller.DepartmentId = department.Id " + 
			   		" WHERE DepartmentId = ? " + 
			   		" ORDER BY Name");
			   
			        st.setInt(1, dp.getId());
			        rs = st.executeQuery();
			       
			        List<Seller> lista = new ArrayList<>();
			        Map<Integer , Department> map = new HashMap<>();
			        
			        
			        while(rs.next()) {
			        	
			        Department dep = map.get(rs.getInt("DepartmentId"));
			        
			           if(dep == null) {
			        	    dep = instantiateDepartment(rs);
			        	   map.put(rs.getInt("DepartmentId"), dep);
			           }
			        		        
			         
			          Seller ob = instatiateSeller(rs, dep);
			          
			          lista.add(ob);
			        }
			      return lista;
		   }catch(SQLException e) {
			   
			   throw new DbException(e.getMessage());
		   }
		   finally {
			   DB.closeResultSet(rs);
			   DB.closeStatement(st);
			  
			   
		   }
		
	}

	

}
