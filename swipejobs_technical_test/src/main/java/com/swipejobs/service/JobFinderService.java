package com.swipejobs.service;

import java.util.Set;

import com.swipejobs.dto.Job;
import com.swipejobs.exception.BadRequestException;
import com.swipejobs.exception.NoJobFoundException;
import com.swipejobs.exception.NoWorkerFoundException;

public interface JobFinderService {

	public Set<Job> findJobsForWoker(String workerId) throws BadRequestException, NoWorkerFoundException, NoJobFoundException;

}
