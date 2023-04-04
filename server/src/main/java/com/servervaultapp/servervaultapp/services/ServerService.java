package com.servervaultapp.servervaultapp.services;

import com.servervaultapp.servervaultapp.entities.Server;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServerService {

	public ResponseEntity<List<Server>> getAllServers();
	public ResponseEntity<Server> getServerById(String serverId);
	public ResponseEntity<Server> addServer(Server server);
	public ResponseEntity<Server> updateServer(Server server);
	public ResponseEntity<String> deleteServerById(String serverId);
	public ResponseEntity<List<Server>> getServerByName(String name);

}
