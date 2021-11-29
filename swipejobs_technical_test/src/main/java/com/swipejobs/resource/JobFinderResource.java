package com.swipejobs.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swipejobs.exception.BadRequestException;
import com.swipejobs.exception.NoJobFoundException;
import com.swipejobs.exception.NoWorkerFoundException;
import com.swipejobs.service.JobFinderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/jobfinder")
public class JobFinderResource {

	private JobFinderService jobFinderService;

	public JobFinderResource(JobFinderService jobFinderService) {
		this.jobFinderService = jobFinderService;
	}

	@GetMapping(path = "/{workerId}")
	public ResponseEntity<?> findJobs(@PathVariable(required = true) String workerId) throws BadRequestException {
		log.info("Going to find jobs for worker id {}", workerId);
		try {
			return new ResponseEntity<>(jobFinderService.findJobsForWoker(workerId), HttpStatus.OK);
		} catch (BadRequestException be) {
			final String errorMsg = "Invalid Request: Bad worker id passed: " + workerId;
			log.error(errorMsg);
			return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
		} catch (NoWorkerFoundException e) {
			final String errorMsg = "No worker found in system";
			log.error(errorMsg);
			return new ResponseEntity<>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoJobFoundException e) {
			final String errorMsg = "No job found in system";
			log.error(errorMsg);
			return new ResponseEntity<>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}