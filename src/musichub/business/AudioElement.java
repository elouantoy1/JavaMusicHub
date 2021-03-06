package musichub.business;

import java.util.*;
import java.io.Serializable;
import org.w3c.dom.*;

public abstract class AudioElement implements Serializable {
	protected String  	title;
	protected String 	artist;
	protected int    	lengthInSeconds;
	protected UUID    	uuid;
	protected String	content;
	protected Format	format;
	
	public AudioElement (String title, String artist, int lengthInSeconds, String id, String content, String format) {
		this.title = title;
		this.artist = artist;
		this.lengthInSeconds = lengthInSeconds;
		this.uuid = UUID.fromString(id);
		this.content = content;
		this.setFormat(format);
	}

	public AudioElement (String title, String artist, int lengthInSeconds, String content, String format) {
		this.title = title;
		this.artist = artist;
		this.lengthInSeconds = lengthInSeconds;
		this.content = content;
		this.uuid =  UUID.randomUUID();
		this.setFormat(format);
	}
	
	public AudioElement (Element xmlElement)  throws Exception
	{
		try {
			title = xmlElement.getElementsByTagName("title").item(0).getTextContent();
			artist = xmlElement.getElementsByTagName("artist").item(0).getTextContent();
			lengthInSeconds = Integer.parseInt(xmlElement.getElementsByTagName("length").item(0).getTextContent());
			content = xmlElement.getElementsByTagName("content").item(0).getTextContent();
			String uuid = null;
			String format = null;
			try {
				uuid = xmlElement.getElementsByTagName("UUID").item(0).getTextContent();
			}
			catch (Exception ex) {
				System.out.println ("Empty element UUID, will create a new one");
			}
			try {
				format = xmlElement.getElementsByTagName("format").item(0).getTextContent();
			}
			catch (Exception ex) {
				System.out.println ("Unknow format, will set noformat by default.");
			}
			if ((uuid == null)  || (uuid.isEmpty()))
				this.uuid = UUID.randomUUID();
			else this.uuid = UUID.fromString(uuid);
			
			if ((format == null)  || (format.isEmpty()))
				this.format = Format.NOFORMAT;
			else setFormat(format);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private void setFormat(String format) {
		switch (format.toLowerCase()) {
		case "mp3":
			this.format = Format.MP3;
			break;
		case "wav":
			this.format = Format.WAV;
			break;
		case "noformat":
		default:
			this.format = Format.NOFORMAT;
			break;
	}
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public String getArtist() {
		return this.artist;
	}

	public String getTitle() {
		return this.title;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getFormat() {
		if(this.format == null)
			return "noformat";
		else
			return this.format.getFormat();
	}
	
	public String toString() {
		return "Title = " + this.title + ", Artist = " + this.artist + ", Length = " + this.lengthInSeconds + ", Content = " + this.content;
	}

	public void createXMLElement(Document document, Element parentElement)
	{
		Element nameElement = document.createElement("title");
        nameElement.appendChild(document.createTextNode(title));
        parentElement.appendChild(nameElement);
		
		Element artistElement = document.createElement("artist");
        artistElement.appendChild(document.createTextNode(artist));
        parentElement.appendChild(artistElement);
		
		Element lengthElement = document.createElement("length");
        lengthElement.appendChild(document.createTextNode(Integer.valueOf(lengthInSeconds).toString()));
        parentElement.appendChild(lengthElement);
		
		Element UUIDElement = document.createElement("UUID");
        UUIDElement.appendChild(document.createTextNode(uuid.toString()));
        parentElement.appendChild(UUIDElement);
		
		Element contentElement = document.createElement("content");
        contentElement.appendChild(document.createTextNode(content));
        parentElement.appendChild(contentElement);
        
        Element formatElement = document.createElement("format");
        formatElement.appendChild(document.createTextNode(getFormat()));
        parentElement.appendChild(formatElement);

	}
	
}