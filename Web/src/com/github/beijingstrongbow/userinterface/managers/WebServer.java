package com.github.beijingstrongbow.userinterface.managers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpContext;

public class WebServer {
	
	private final int port;
	
	/**
	 * Creates a web server
	 * 
	 * @param port The port on which to open the server
	 * @param handlers The list of handlers for different requests and their corresponding path strings
	 */
	public WebServer(int port, HashMap<String, HttpHandler> handlers) {
		this.port = port;
		try {
			InetSocketAddress socket = new InetSocketAddress(InetAddress.getLocalHost(), port);
			
			HttpServer server = HttpServer.create(socket, 5);
			
			for(String path : handlers.keySet()) {
				server.createContext(path, handlers.get(path));
			}
			
			System.out.println("Starting server on " + server.getAddress());
			server.start();
			System.out.println("Server started!");
		} 
		catch (UnknownHostException ex) {
			ex.printStackTrace();
		} 
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}