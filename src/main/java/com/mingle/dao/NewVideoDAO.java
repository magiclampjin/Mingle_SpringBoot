package com.mingle.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mingle.dto.NewVideoDTO;

@Repository
public class NewVideoDAO {
	
	@Autowired
	private SqlSession db;
	
	
	public List<NewVideoDTO> selectLikestVideosDuringLatestOneMonth(){
		return db.selectList("NewVideo.selectLikestVideosDuringLatestOneMonth");
	}

}
