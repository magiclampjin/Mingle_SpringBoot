package com.mingle.domain.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_file")
@Setter
@Getter
@NoArgsConstructor
public class PostFile {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ori_name", nullable = false)
	private String oriName;
	
	@Column(name = "sys_name", nullable = false)
	private String sysName;
	
	@Column(name = "post_id", nullable = false)
	private Long postId;
	
	
	@Builder
	public PostFile(Long id, String oriName, String sysName, Long postId) {
		super();
		this.id = id;
		this.oriName = oriName;
		this.sysName = sysName;
		this.postId = postId;
	}
	
	

}
