package com.servervaultapp.servervaultapp.controllers;

import com.servervaultapp.servervaultapp.constants.PathConstants;
import com.servervaultapp.servervaultapp.entities.Server;
import com.servervaultapp.servervaultapp.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ServerController {
	
	@Autowired
	private ServerService serverService;


	// to quick check if the server is up and running or not
	@GetMapping("/check")
	public String checkingServer() {
		return "Server is running...";
	}


	// This endpoint returns a list of all servers in the system.
	@RequestMapping(method = RequestMethod.GET, value = PathConstants.GET_ALL_SERVERS)
	public ResponseEntity<List<Server>> getAllServers() {
		return serverService.getAllServers();
	}


	// This endpoint returns a single server by its ID.
	@RequestMapping(method = RequestMethod.GET, value = PathConstants.GET_SERVER_BY_ID)
	public ResponseEntity<Server> getServerById(@PathVariable String serverId) {
		return serverService.getServerById((serverId));
	}


	// This endpoint returns a single server by its name.
	@RequestMapping(method = RequestMethod.GET, value = PathConstants.GET_SERVER_BY_NAME)
	public ResponseEntity<List<Server>> getServersByName(@PathVariable String name) {
		return serverService.getServerByName(name);
	}


	// This endpoint adds a new server to the system.
	@RequestMapping(method = RequestMethod.POST, value = PathConstants.ADD_SERVER)
	public ResponseEntity<Server> addServer(@RequestBody Server server) {
		return serverService.addServer(server);
	}


	// This endpoint updates an existing server.
	@RequestMapping(method = RequestMethod.PUT, value = PathConstants.UPDATE_SERVER)
	public ResponseEntity<Server> updateServer(@RequestBody Server server) {
		return serverService.updateServer(server);
	}


	// This endpoint deletes a server by its ID.
	@RequestMapping(method = RequestMethod.DELETE, value = PathConstants.DELETE_SERVER)
	public ResponseEntity<String> deleteServer(@PathVariable String serverId) {
		return serverService.deleteServerById(serverId);
	}

}
