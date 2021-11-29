package com.swipejobs.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_EMPTY)
public class Worker {
	private int rating;
	private Boolean isActive;
	private List<String> certificates;
	private List<String> skills;
	private JobSearchAddress jobSearchAddress;
	private String transportation;
	private Boolean hasDriversLicense;
	private List<Availability> availability;
	private String phone;
	private String email;
	private Name name;
	private int age;
	private String guid;
	private String userId;
}