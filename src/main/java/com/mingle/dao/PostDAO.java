package com.mingle.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostDAO {
	@Autowired
	private SqlSession db;
	
	public List<Map<String,Object>> selectByNoticeTrue(){
		return db.selectList("Post.selectByNoticeTrue");
	}
	
	public List<Map<String,Object>> selectByNoticeFalse(){
		return db.selectList("Post.selectByNoticeFalse");
	}
	
	public List<Map<String,Object>> selectByNoticeTrueTop10(){
		return db.selectList("Post.selectByNoticeTrueTop10");
	}
	
	public List<Map<String,Object>> selectByNoticeFalseTop10(){
		return db.selectList("Post.selectByNoticeFalseTop10");
	}
}
