package com.mingle.mappers;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface GenericMapper<D,E>{
	D toDto(E e);
	E toEntity(D d);
	
	List<D> toDtoList(List<E> e);
	List<E> toEntityList(List<D> d);
	
	Set<D> toDtoSet(Set<E> e);
	Set<E> toEntitySet(Set<D> d);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntityFromDTO(D d, @MappingTarget E e);

	default Instant map(Timestamp t) {
		return t.toInstant();
	}
	
	default Timestamp map(Instant i) {
		if(i!=null)
			return Timestamp.from(i);
		else 
			return null;
	}
}
