package com.servervaultapp.servervaultapp.constants;

public class PathConstants {
    // Suppress default constructor for noninstantiability
    private PathConstants() {
        throw new AssertionError("No instances for you!");
    }

    public static final String GET_ALL_SERVERS = "/api/servers";
    public static final String GET_SERVER_BY_ID = "/api/servers/{serverId}";
    public static final String GET_SERVER_BY_NAME = "/api/servers/name/{name}";
    public static final String ADD_SERVER = "/api/servers";
    public static final String UPDATE_SERVER = "/api/servers";
    public static final String DELETE_SERVER = "/api/servers/{serverId}";
}
