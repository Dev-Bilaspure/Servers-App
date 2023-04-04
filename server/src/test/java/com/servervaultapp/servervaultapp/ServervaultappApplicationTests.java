package com.servervaultapp.servervaultapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servervaultapp.servervaultapp.constants.PathConstants;
import com.servervaultapp.servervaultapp.entities.Server;
import com.servervaultapp.servervaultapp.repositories.ServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.*;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class servervaultappApplicationTests {
	ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	@MockBean
	private ServerRepository serverRepository;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void addServerTest() throws Exception {
		// Create a new Server object
		Server server = new Server();
		server.setName("Inventory1");
		server.setLanguage("java");
		server.setFramework("spring");

		// Convert the Server object to a JSON string
		String jsonRequest = new ObjectMapper().writeValueAsString(server);

		// Configure the mock repository to return the server object when save() is called
		when(serverRepository.save(server)).thenReturn(server);

		// Perform a POST request to the addServer endpoint with the JSON request body
		MvcResult result = mockMvc.perform(post(PathConstants.ADD_SERVER).content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		System.out.println(result);

		// Get the response content as a string
		String resultContent = result.getResponse().getContentAsString();

		// Deserialize the response content to a Server object
		Server addedServer = new ObjectMapper().readValue(resultContent, Server.class);

		// Check that the addedServer object has the same values as the original server object
		assertEquals(server.getName(), addedServer.getName());
		assertEquals(server.getLanguage(), addedServer.getLanguage());
		assertEquals(server.getFramework(), addedServer.getFramework());

		// Verify that the response status is 200 OK
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	public void getAllServersTest() throws Exception {
		// Create a list of Server objects to return from the mock repository
		List<Server> servers = new ArrayList<>();

		Server server1 = new Server();
		server1.setFramework("express");
		server1.setName("Server11");
		server1.setLanguage("javascript");

		Server server2 = new Server();
		server2.setFramework("spring");
		server2.setName("Server12");
		server1.setLanguage("java");

		servers.add(server1);
		servers.add(server2);


		// Configure the mock repository to return the list of servers
		when(serverRepository.findAll()).thenReturn(servers);

		// Perform a GET request to the getAllServers endpoint
		MvcResult result = mockMvc.perform(get(PathConstants.GET_ALL_SERVERS)).andExpect(status().isOk()).andReturn();

		// Get the response content as a string
		String resultContent = result.getResponse().getContentAsString();

		// Deserialize the response content to a list of Server objects
		List<Server> returnedServers = new ObjectMapper().readValue(resultContent, new TypeReference<List<Server>>() {
		});

		// Verify that the returnedServers list has the same contents as the original servers list
		assertEquals(servers.size(), returnedServers.size());
		for (int i = 0; i < servers.size(); i++) {
			assertEquals(servers.get(i).getName(), returnedServers.get(i).getName());
			assertEquals(servers.get(i).getLanguage(), returnedServers.get(i).getLanguage());
			assertEquals(servers.get(i).getFramework(), returnedServers.get(i).getFramework());
		}
	}

	@Test
	public void getServerByIDTest() throws Exception {
		// Create a server object to add to the repository
		Server server = new Server();
		server.setName("Inventory1");
		server.setLanguage("java");
		server.setFramework("spring");

		// Configure the mock repository to return the server object when save() is called
		when(serverRepository.save(server)).thenReturn(server);

		// Save the server to the repository and get its ID
		Server savedServer = serverRepository.save(server);
		String serverId = savedServer.getServerId();
		System.out.println("serverID: " + serverId);

		// Configure the mock repository to return the saved server when findById() is called with the server ID
		when(serverRepository.findById(serverId)).thenReturn(Optional.of(savedServer));

		// Perform a GET request to the getServerByID endpoint with the server ID as a path variable
		MvcResult result = mockMvc.perform(get("/api/servers/" + serverId).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		// Get the response content as a string
		String resultContent = result.getResponse().getContentAsString();

		// Deserialize the response content to a Server object
		Server returnedServer = new ObjectMapper().readValue(resultContent, Server.class);

		// Verify that the returned server matches the saved server
		assertEquals(savedServer, returnedServer);
	}


	@Test
	public void getServerByName() throws Exception {
		// Create a server object to add to the repository
		Server server = new Server();
		server.setName("Inventory1");
		server.setLanguage("java");
		server.setFramework("spring");

		List<Server> serverList = new ArrayList<>();
		serverList.add(server);

		// Configure the mock repository to return the server object when save() is called
		when(serverRepository.save(server)).thenReturn(server);

		// Save the server to the repository and get its ID
		Server savedServer = serverRepository.save(server);
		String serverName = savedServer.getName();

		// Configure the mock repository to return the saved server when findById() is called with the server ID
		when(serverRepository.findAllByName(serverName)).thenReturn(serverList);

		// Perform a GET request to the getServerByID endpoint with the server ID as a path variable
		MvcResult result = mockMvc.perform(get("/api/servers/name/" + serverName).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		// Get the response content as a string
		String resultContent = result.getResponse().getContentAsString();

		// Deserialize the response content to a Server object
		List<Server> returnedServers = new ObjectMapper().readValue(resultContent, new TypeReference<List<Server>>() {
		});

		// Verify that the returnedServers list has the same contents as the original servers list
		assertEquals(serverList.size(), returnedServers.size());
		for (int i = 0; i < serverList.size(); i++) {
			assertEquals(serverList.get(i).getName(), returnedServers.get(i).getName());
			assertEquals(serverList.get(i).getLanguage(), returnedServers.get(i).getLanguage());
			assertEquals(serverList.get(i).getFramework(), returnedServers.get(i).getFramework());
		}
	}


	@Test
	public void updateServerTest() throws Exception {
		// Create a server object to add to the repository
		Server server = new Server();
		server.setName("Inventory1");
		server.setLanguage("java");
		server.setFramework("spring");


		// Configure the mock repository to return the server object when save() is called
		when(serverRepository.save(server)).thenReturn(server);

		// Save the server to the repository and get its ID
		Server savedServer = serverRepository.save(server);

		savedServer.setName("Inventory-New-Named");

		String requestBody = new ObjectMapper().writeValueAsString(savedServer);

		MvcResult result = mockMvc.perform(put("/api/servers/").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody)).andExpect(status().isOk()).andReturn();

		// Get the response content as a string
		String resultContent = result.getResponse().getContentAsString();

		// Deserialize the response content to a Server object
		Server returnedServer = new ObjectMapper().readValue(resultContent, Server.class);

		// Verify that the returnedServer object has been updated
		assertEquals(savedServer.getName(), returnedServer.getName());
		assertEquals(savedServer.getLanguage(), returnedServer.getLanguage());
		assertEquals(savedServer.getFramework(), returnedServer.getFramework());
	}


	@Test
	public void deleteServerByIdTest() throws Exception {
		// Create a server object to add to the repository
		Server server = new Server();
		server.setName("Inventory1");
		server.setLanguage("java");
		server.setFramework("spring");


		// Configure the mock repository to return the server object when save() is called
		when(serverRepository.save(server)).thenReturn(server);

		// Save the server to the repository and get its ID
		Server savedServer = serverRepository.save(server);

		// Configure the mock repository to return the server object when findById() is called with the server ID
		when(serverRepository.findById(savedServer.getServerId())).thenReturn(Optional.of(server));

		// Perform a DELETE request to the deleteServerById endpoint with the server ID as a path variable
		MvcResult result = mockMvc.perform(delete("/api/servers/" + savedServer.getServerId()).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
	}
}
