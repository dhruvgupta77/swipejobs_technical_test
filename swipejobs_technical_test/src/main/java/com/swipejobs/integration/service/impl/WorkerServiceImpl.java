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

import com.swipejobs.dto.Worker;
import com.swipejobs.integration.service.WorkerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkerServiceImpl implements WorkerService {

	@Value(value = "${workerResourceUrl}")
	private String workerUrl;
	private RestTemplate restTemplate;

	public WorkerServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public List<Worker> getWorkers() {

		ResponseEntity<List<Worker>> entity = null;
		List<Worker> workers = null;
		try {
			entity = restTemplate.exchange(workerUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Worker>>() {
					});
			if (!HttpStatus.OK.equals(entity.getStatusCode())) {
				log.error("Failed to get proper response from Worker Service. StatusCode = {}", entity.getStatusCode());
				return getDefaultResponse();
			}

			workers = entity.getBody();

			return workers;
		} catch (HttpClientErrorException ex) {
			log.error("++++++REACHED HERE: Exception Occured while retrieving workers");
			int rawStatusCode = ex.getRawStatusCode();
			if (rawStatusCode == HttpStatus.UNAUTHORIZED.value() || rawStatusCode == HttpStatus.FORBIDDEN.value()) {
				log.error("++++++REACHED HERE: unaouthorized/forbidden: " + rawStatusCode);
			} else {
				log.error("++++++REACHED HERE: preparing auth response: " + rawStatusCode);
			}
			ex.printStackTrace();
		}

		if (entity == null) {
			log.error("Failed to get Response from Worker service");
			return getDefaultResponse();
		} else

			return getDefaultResponse();
	}

	private List<Worker> getDefaultResponse() {
		return Collections.<Worker>emptyList();
	}

}
