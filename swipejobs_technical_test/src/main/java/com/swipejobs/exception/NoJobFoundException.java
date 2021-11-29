package com.swipejobs.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoJobFoundException extends Exception {

	private static final long serialVersionUID = 8173121904014587980L;

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("no jobs found in system").toString();
	}
}
