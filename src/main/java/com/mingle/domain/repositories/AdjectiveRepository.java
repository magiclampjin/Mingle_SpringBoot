package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.NicknameAdjective;

public interface AdjectiveRepository extends JpaRepository<NicknameAdjective, Long> {
	// 닉네임 형용사 총 개수 불러오기
	@Query("select count(*) from NicknameAdjective")
	Long selectAdjectiveCount();
}
