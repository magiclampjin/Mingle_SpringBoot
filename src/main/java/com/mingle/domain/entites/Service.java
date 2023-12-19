package com.mingle.domain.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Service {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="english_name")
	private String englishName;
	
	@Column(name="price")
	private Long price;
	
	@Column(name="commission")
	private Long commission;
	
	@Column(name="plan")
	private String plan;	
	
	@Column(name="max_people_count")
	private Long maxPeopleCount;
	
	@Column(name="url")
	private String url;
	
	@Column(name="service_category_id")
	private String serviceCategoryId;
}