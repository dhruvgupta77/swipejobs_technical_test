package com.swipejobs.integration.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.swipejobs.dto.Job;
import com.swipejobs.integration.service.JobService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

	@Value(value = "${jobResourceUrl}")
	private String jobUrl;

	private RestTemplate restTemplate;

	public JobServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public List<Job> getJobs() {

		ResponseEntity<List<Job>> entity = null;
		List<Job> jobs = null;
		try {
			entity = restTemplate.exchange(jobUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>() {
			});
			if (!HttpStatus.OK.equals(entity.getStatusCode())) {
				log.error("Failed to get proper response from Worker Service. StatusCode={}", entity.getStatusCode());
				return getDefaultResponse();
			}

			jobs = entity.getBody();

			return jobs;
		} catch (HttpClientErrorException ex) {
			log.error("++++++REACHED HERE: Exception Occured while retrieving jobs");

			int rawStatusCode = ex.getRawStatusCode();
			if (rawStatusCode == HttpStatus.UNAUTHORIZED.value() || rawStatusCode == HttpStatus.FORBIDDEN.value()) {
				log.error("++++++REACHED HERE: unaouthorized/forbidden: " + rawStatusCode);
			}
			ex.printStackTrace();
		}

		if (entity == null) {
			log.error("Failed to get Response from Worker service");
			return getDefaultResponse();
		} else

			return getDefaultResponse();
	}

	private List<Job> getDefaultResponse() {
		return Collections.<Job>emptyList();
	}

}
