package com.mingle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceDTO {
	private Long id;
	private String name;
	private String englishName;
	private Long price;
	private Long maxPeopleCount;
	private Long commission;
	private String serviceCategoryId;
}
