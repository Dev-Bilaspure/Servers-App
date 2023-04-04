package com.servervaultapp.servervaultapp.services;

import com.servervaultapp.servervaultapp.controllers.ServerController;
import com.servervaultapp.servervaultapp.entities.Server;
import com.servervaultapp.servervaultapp.repositories.ServerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ServerServiceImpl implements ServerService {

	@Autowired
	private ServerRepository serverRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);

	/**
	 * Retrieve a list of all server objects currently stored in the system.
	 *
	 * @return a list of all Server objects in the system
	 */
	@Override
	public ResponseEntity<List<Server>> getAllServers()  {
		try {
			List<Server> servers = serverRepository.findAll();
			if (!servers.isEmpty()) {
				LOGGER.info("Returning the list of servers with success status code");
				return ResponseEntity.ok(servers); // Return the list of Server objects with a success status code
			} else {
				LOGGER.info("No servers found, returning failure status code");
				return ResponseEntity.notFound().build(); // Return a failure status code if no servers are found
			}
		} catch (Exception e) {
			// handling exception
			LOGGER.error("An error occurred while retrieving the list of servers: {}", e.getMessage(), e);
			System.out.println("An error occurred while retrieving the list of servers: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return an error status code
		}
	}


	/**
	 * Retrieve a server object from the system with the specified server ID.
	 *
	 * @param serverId a String value indicating the ID of the server to retrieve
	 * @return the Server object with the specified ID, or null if no such server exists
	 */
	@Override
	public ResponseEntity<Server> getServerById(String serverId) {
		try {
			LOGGER.info("Retrieving the server with ID {}", serverId);
			Server server = serverRepository.findById(serverId).orElse(null);
			if (server != null) {
				LOGGER.info("Returning the server with ID {} and success status code", serverId);
				return ResponseEntity.ok(server); // Return the Server object with a success status code
			} else {
				LOGGER.info("No server found with ID {}, returning failure status code", serverId);
				return ResponseEntity.notFound().build(); // Return a failure status code if no server with the specified ID is found
			}
		} catch (Exception e) {
			// handling exception
			LOGGER.error("An error occurred while retrieving the server with ID {}: {}", serverId, e.getMessage(), e);
			System.out.println("An error occurred while retrieving the server: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return an error status code
		}
	}


	/**
	 * Add a new server object to the system.
	 *
	 * @param server a Server object containing the data for the server to add
	 * @return the newly added Server object
	 */
	@Override
	public ResponseEntity<Server> addServer(Server server) {
		try {
			LOGGER.info("Adding a new server with name '{}'", server.getName());
			Server addedServer = serverRepository.save(server); // Add the Server object to the repository and return it
			LOGGER.info("New server added with ID '{}'", addedServer.getServerId());
			return ResponseEntity.ok(addedServer);
		} catch (Exception e) {
			// handling exception
			LOGGER.error("An error occurred while adding the server: {}", e.getMessage(), e);
			System.out.println("An error occurred while adding the server: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}



	/**
	 * Update an existing server in the system with the given server object.

	 * @param server the updated Server object to be saved
	 * @return the updated Server object that has been saved
	 */
	@Override
	public ResponseEntity<Server> updateServer(Server server) {
		Server existingServer = null; // Declare a variable to store the existing server object
		try {
			// Try to find the existing server object in the repository by its ID
			LOGGER.info("Updating server with ID '{}'", server.getServerId());
			existingServer = serverRepository.findById(server.getServerId()).orElse(null);
			if (existingServer != null) { // If the existing server is found, update its properties
				existingServer.setName(server.getName());
				existingServer.setFramework(server.getFramework());
				existingServer.setLanguage(server.getLanguage());
				serverRepository.save(existingServer); // Save the updated server object to the repository
				LOGGER.info("Server with ID '{}' updated", server.getServerId());
				return ResponseEntity.ok(existingServer);
			} else {
				LOGGER.warn("Server with ID '{}' not found", server.getServerId());
				return ResponseEntity.notFound().build();
			}
		} catch(Exception e) { // If an error occurs, catch it and log a message
			// handle exception
			LOGGER.error("An error occurred while updating the server with ID '{}': {}", server.getServerId(), e.getMessage(), e);
			System.out.println("An error occurred while updating the server: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}



	/**
	 * Delete the server object with the specified ID from the system.
	 *
	 * @param serverId a String value indicating the ID of the server to delete
	 * @return a String message indicating the status of the operation
	 */
	@Override
	public ResponseEntity<String> deleteServerById(String serverId) {
		try {
			Optional<Server> existingServer = serverRepository.findById(serverId); // Retrieve the Server object with the specified ID from the repository
			if (existingServer.isPresent()) {
				serverRepository.delete(existingServer.get()); // Delete the Server object from the repository
				LOGGER.info("Server with ID {} has been deleted", serverId);
				return ResponseEntity.ok("Server with ID " + serverId + " has been deleted"); // Return a success message
			} else {
				LOGGER.warn("Server with ID {} not found", serverId);
				return ResponseEntity.notFound().build(); // Return a failure message
			}
		} catch (Exception e) {
			// handling exception
			LOGGER.error("An error occurred while deleting the server with ID {}", serverId, e);
			System.out.println("An error occurred while deleting the server: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Retrieves all the servers with the given name from the data source.
	 *
	 * @param name The name of the server to retrieve.
	 * @return The List of servers with the given name, or null if no such server exists.
	 */
	@Override
	public ResponseEntity<List<Server>> getServerByName(String name) {
		try {
			// Retrieve all Server objects with the specified name from the repository
			LOGGER.info("Retrieving servers with name: {}", name);
			List<Server> servers = serverRepository.findAllByName(name);
			if (!servers.isEmpty()) {
				LOGGER.info("Servers with name {} retrieved successfully", name);
				return ResponseEntity.ok(servers);
			} else {
				LOGGER.info("No servers found with name: {}", name);
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			// Handle any exceptions
			LOGGER.error("An error occurred while retrieving the server: {}", e.getMessage());
			System.err.println("An error occurred while retrieving the server: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
