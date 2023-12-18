package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.AdjectiveWithRownum;

public interface AdjectiveViewRepository extends JpaRepository<AdjectiveWithRownum, Long>{
	// 닉네임 형용사 해당 n번째 데이터 가져오기 -> 데이터를 지웠을 경우를 고려하여 뷰에서 가져옴
	@Query("SELECT a.adjective FROM AdjectiveWithRownum a WHERE a.num = :randAdjective")
	String selectAdjectiveByNum(@Param("randAdjective") int randAdjective);
}
