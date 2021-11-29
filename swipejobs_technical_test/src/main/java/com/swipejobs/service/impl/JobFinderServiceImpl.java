package com.swipejobs.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.swipejobs.dto.Job;
import com.swipejobs.dto.Worker;
import com.swipejobs.exception.BadRequestException;
import com.swipejobs.exception.NoJobFoundException;
import com.swipejobs.exception.NoWorkerFoundException;
import com.swipejobs.integration.service.impl.JobServiceImpl;
import com.swipejobs.integration.service.impl.WorkerServiceImpl;
import com.swipejobs.service.JobFinderService;
import com.swipejobs.util.DistanceCalculator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobFinderServiceImpl implements JobFinderService {

	private WorkerServiceImpl workerService;
	private JobServiceImpl jobService;
	@Value(value = "${maxJobSearchResults}")
	private Long maxJobSearchResults;

	public JobFinderServiceImpl(WorkerServiceImpl workerService, JobServiceImpl jobService) {
		super();
		this.workerService = workerService;
		this.jobService = jobService;
	}

	@Override
	public Set<Job> findJobsForWoker(String workerId)
			throws BadRequestException, NoWorkerFoundException, NoJobFoundException {
		
		if (workerId == null || "".equals(workerId) || !StringUtils.isNumeric(workerId))
			throw new BadRequestException();
		
		Set<Job> workerMatchedJobs = null;

		List<Worker> workers = workerService.getWorkers();

		if (workers == null || workers.isEmpty()) {
			log.error("No workes found....");
			throw new NoWorkerFoundException();
		} else {
			Optional<Worker> workerOpt = workers.stream().filter(w -> w.getUserId().equals(workerId)).findAny();
			Worker worker = null;
			if (workerOpt.isPresent())
				worker = workerOpt.get();
			else {
				log.error("Given Worker {} not found in the system", workerId);
				throw new NoWorkerFoundException();
			}
			List<Job> jobs = jobService.getJobs();
			if (jobs == null || jobs.isEmpty()) {
				log.error("No jobs were found....");
				throw new NoJobFoundException();
			} else {
				workerMatchedJobs = filterWorkerJobs(jobs, worker);
			}
		}
		return workerMatchedJobs;
	}

	private Set<Job> getDefaultResponse() {
		return Collections.emptySet();
	}

	private Set<Job> filterWorkerJobs(List<Job> jobs, Worker worker) {

		log.info("Total Jobs: ", jobs.size());
		Set<Job> matchedJobs = null;

		// Driver License Filter
		matchedJobs = jobs.stream().filter(job -> job.isDriverLicenseRequired() == worker.getHasDriversLicense())
				.collect(Collectors.toSet());

		// Skills Filter
		matchedJobs = matchedJobs.stream().filter(job -> worker.getSkills().contains(job.getJobTitle()))
				.collect(Collectors.toSet());

		// Certificate Filter
		matchedJobs = matchedJobs.stream().filter(job -> isWorkerhasAtLeastOneCertificate(job, worker))
				.collect(Collectors.toSet());

		// Distance Filter: Done at end to do distance computation for necessary job
		// list only
		matchedJobs = matchedJobs.stream()
				.filter(job -> DistanceCalculator.calculateDistance(worker.getJobSearchAddress().getLatitude(),
						job.getLocation().getLatitude(), worker.getJobSearchAddress().getLongitude(),
						job.getLocation().getLongitude(),
						worker.getJobSearchAddress().getUnit()) <= worker.getJobSearchAddress().getMaxJobDistance())
				.collect(Collectors.toSet());
		
		//Limit the job search result to max limit
		matchedJobs = matchedJobs.stream().limit(maxJobSearchResults).collect(Collectors.toSet());

		log.info("Number of matched jobs: ", matchedJobs.size());

		return matchedJobs;
	}

	private boolean isWorkerhasAtLeastOneCertificate(Job job, Worker worker) {
		Set<String> matchingCert = job.getRequiredCertificates().stream().distinct()
				.filter(worker.getCertificates()::contains).collect(Collectors.toSet());
		return !matchingCert.isEmpty();
	}

}