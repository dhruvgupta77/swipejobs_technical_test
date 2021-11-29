package com.swipejobs.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipejobs.dto.Job;
import com.swipejobs.dto.Worker;
import com.swipejobs.exception.BadRequestException;
import com.swipejobs.exception.NoJobFoundException;
import com.swipejobs.exception.NoWorkerFoundException;
import com.swipejobs.integration.service.impl.JobServiceImpl;
import com.swipejobs.integration.service.impl.WorkerServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={JobFinderServiceImpl.class})
@TestPropertySource(locations="classpath:custom.properties")
class JobFinderServiceImplTest {

	@Autowired
	private JobFinderServiceImpl jobFinderServiceImpl;
	@MockBean
	private WorkerServiceImpl workerService;
	@MockBean
	private JobServiceImpl jobService;

	private List<Job> jobs;
	private List<Worker> workers;
	private static final String DEFAULT_WORKER_ID = "1";

	@BeforeEach
	public void init() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		jobs = mapper.readValue(ResourceUtils.getFile("classpath:jobs.json"), new TypeReference<List<Job>>() {
		});
		workers = mapper.readValue(ResourceUtils.getFile("classpath:workers.json"), new TypeReference<List<Worker>>() {
		});
	}

	@Test
	void matchJobsForWorker() throws BadRequestException, NoWorkerFoundException, NoJobFoundException {
		when(jobService.getJobs()).thenReturn(jobs);
		when(workerService.getWorkers()).thenReturn(workers);

		Set<Job> jobs = jobFinderServiceImpl.findJobsForWoker(DEFAULT_WORKER_ID);
		assertEquals(1, jobs.size());
	}

}
