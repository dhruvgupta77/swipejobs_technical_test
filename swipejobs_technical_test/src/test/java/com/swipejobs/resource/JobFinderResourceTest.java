package com.swipejobs.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class JobFinderResourceTest {

	@Autowired
	private MockMvc mvc;
	

	@Test
	void shouldReturn400WhenNoRecordFoundForGivenWorkerId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/jobfinder/200"))
				.andExpect(status().isInternalServerError());
	}

	@Test
	void shouldReturn200WhenRecordIsFoundForGivenWorkerId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/jobfinder/1"))
				.andExpect(status().isOk())
				.andExpect(content().json("[\r\n" + "    {\r\n" + "        \"driverLicenseRequired\": true,\r\n"
						+ "        \"requiredCertificates\": [\r\n"
						+ "            \"Calm in the Eye of the Storm\",\r\n"
						+ "            \"Healthy Living Promoter\",\r\n"
						+ "            \"The Behind the Scenes Wonder\"\r\n" + "        ],\r\n"
						+ "        \"location\": {\r\n" + "            \"longitude\": \"14.316602\",\r\n"
						+ "            \"latitude\": \"50.022868\"\r\n" + "        },\r\n"
						+ "        \"billRate\": \"$17.79\",\r\n" + "        \"workersRequired\": 2,\r\n"
						+ "        \"startDate\": \"2015-11-10\",\r\n"
						+ "        \"about\": \"Minim commodo amet elit Lorem fugiat non fugiat irure irure. Fugiat aute aliqua ea veniam amet qui tempor elit nisi Lorem commodo aliquip cillum. Id eiusmod ea deserunt adipisicing mollit et. Do quis laboris cupidatat occaecat aute aliqua culpa non ea reprehenderit tempor eu. Duis cillum voluptate pariatur eu eu ullamco laboris. Sit ut cillum ipsum enim aute quis ea eu laborum do ipsum. Eu adipisicing eiusmod eiusmod nostrud aute aliquip magna ad fugiat incididunt.\",\r\n"
						+ "        \"jobTitle\": \"Chief Troublemaker\",\r\n" + "        \"company\": \"Uxmox\",\r\n"
						+ "        \"guid\": \"562f66aa60c4be83d4b592ec\",\r\n" + "        \"jobId\": 25\r\n"
						+ "    }\r\n" + "]"));
	}

	@Test
	void shouldReturn404WhenWorkedIdIsInvalid() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/jobfinder/null")).andExpect(status().isBadRequest());

	}

}
