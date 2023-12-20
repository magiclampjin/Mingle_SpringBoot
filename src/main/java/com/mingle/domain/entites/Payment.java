package com.mingle.domain.entites;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@IdClass(PaymentId.class)
public class Payment {

	@Id
	@Column(name="party_registration_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long partyRegistrationId;
	
	@Id
	@Column(name="member_id")
	private String memberId;
	
	@Id
	@Column(name="date")
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
