package com.mingle.domain.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostType{

	@Id
	@Column(name = "id")
	private String id;
}
