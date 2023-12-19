package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.NounWithRownum;


public interface NounViewRepository extends JpaRepository<NounWithRownum, Long>{
	// 닉네임 명사 해당 n번째 데이터 가져오기 -> 데이터를 지웠을 경우를 고려하여 뷰에서 가져옴
	@Query("SELECT a.noun FROM NounWithRownum a WHERE a.num = :randNoun")
	String selectNounByNum(@Param("randNoun") int randNoun);
}
