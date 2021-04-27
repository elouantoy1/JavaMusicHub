package musichub.business;

import java.io.File;
import java.util.Scanner;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

public class MusicHandler {
	
	private Clip clip;
	
	public MusicHandler(String filepath) {
		try {
			File file = new File(filepath);
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);

			clip = AudioSystem.getClip();
			clip.open(stream);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error : can't load music");
		}
	}
	
	public void play() {
		try {
			clip.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void pause() {
		try {
			clip.stop();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void reset() {
		try {
			clip.setMicrosecondPosition(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void close() {
		try {
			clip.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void Music(String filepath){
	
		try{
			Scanner scanner = new Scanner(System.in);

			File file = new File(filepath);

			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

			Clip clip = AudioSystem.getClip();

			clip.open(audioStream);
			String response = "";

			while(!response.equals("Q")) {

				System.out.println("P = play, S = Stop, R = Reset, Q = Quit");
				System.out.print("Enter your choice: ");

				response = scanner.next();
				response = response.toUpperCase();

				switch(response) {

					case ("P"): clip.start();
					break;

					case ("S"): clip.stop();
					break;

					case ("R"): clip.setMicrosecondPosition(0);
					break;
				
					case ("Q"): clip.close();
					break;
				
					default: System.out.println("Not a valid response");
				}
			}
			scanner.close();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error can't play music");
		}
	}
}