package com.shop.config;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass  //부모 클래스를 상속받는 자식 클래스의 매핑 정보만 제공함
@Getter @Setter
public abstract class BaseTimeEntity {
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime regTime;
	
	@LastModifiedDate
	private LocalDateTime updateTime;
}
