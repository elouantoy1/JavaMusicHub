package musichub.server;

import java.io.*;
import java.net.*;
import musichub.business.*;

public class ServerInstance extends Thread {
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	private MusicHubServer theHubServer;
	
	public ServerInstance(Socket s, MusicHubServer theHubServer) {
		this.socket = s;
		this.theHubServer = theHubServer;
	}
	
	public void run () {
		try {
			this.input = new ObjectInputStream(socket.getInputStream());
			this.output = new ObjectOutputStream(socket.getOutputStream());
			
			String inputReader = (String)input.readObject();  //read the object received through the stream and deserialize it
			System.out.println("server received a text:" + inputReader);
			
			
			switch(inputReader) {
			case "GETAUDIOELEMENTS":
				this.sendAudioElements();
				break;
			case "GETALBUMS":
				this.sendAlbums();
				break;
			case "GETPLAYLISTS":
				this.sendPlaylists();
				break;
			case "SENDSONG":
				this.getSong();
				break;
			}
				
		} catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	private void sendAudioElements() {
		try {
			this.output.writeObject(theHubServer.getAudioElements());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void sendAlbums() {
		try {
			this.output.writeObject(theHubServer.getAlbums());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void sendPlaylists() {
		try {
			this.output.writeObject(theHubServer.getPlaylists());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void getSong() {
		try {
			this.output.writeObject("OK");
			Song receivedSong = (Song)input.readObject();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
	}
}
