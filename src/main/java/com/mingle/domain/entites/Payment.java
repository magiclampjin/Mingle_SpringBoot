package com.mingle.domain.entites;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Payment {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="party_registration_id")
	private Long partyRegistrationId;
	
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="date")
	@CreationTimestamp
    private Timestamp date;
	
	@Column(name="service_id")
	private Long serviceId;
	
	@Column(name="payment_type_id")
	private String paymentTypeId;
	
	@Column(name="price")
	private Long price;
	
	@Column(name="used_mingle_money")
	private Long usedMingleMoney;
	
	@ManyToOne
	@JoinColumn(name="service_id", referencedColumnName = "id",insertable = false, updatable = false)
	private Service service;
	
}
