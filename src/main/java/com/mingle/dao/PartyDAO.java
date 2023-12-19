package com.mingle.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PartyDAO {
	@Autowired
	private SqlSession db;
	
	public List<Map<String,Object>> selectCountUserByService(){
		return db.selectList("Service.selectCountUserByService");
	}
}
