package player;
import player.Player;
import player.playlist;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Audio
{
	public Player obj;
	private Clip clip; // a clip-sound effect that is played
	
	/*public Audio(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		//AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
		AudioFormat baseFormat = ais.getFormat();
		AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
				baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
		
		//Decoded ais
		AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
		clip = AudioSystem.getClip();
		clip.open(dais);
	}
	*/
	// Konstruktor
	public Audio(String path)
	{
		try 
		{
		//AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));		
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
				baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			
			//Decoded ais
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
		clip.open(dais);
			
		} 
		catch (UnsupportedAudioFileException e) 
		{		
			e.printStackTrace();
			
	        JOptionPane.showMessageDialog(obj, "This file cannot be opened"," Error!", JOptionPane.ERROR_MESSAGE);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(obj, "This file cannot be opened"," Error!", JOptionPane.ERROR_MESSAGE);
			
		}
		catch (LineUnavailableException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(obj, "This file cannot be opened"," Error!", JOptionPane.ERROR_MESSAGE);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(obj, "This file cannot be opened"," Error!", JOptionPane.ERROR_MESSAGE);
			
		}
	}
	
	// Method starting SFX 
	public void play(float volume, int framePos)
	{
		playClip(volume, framePos);
		clip.start();
	}
	
	// A method that stops SFX
	public void stop()
	{
		if(clip.isRunning())
			clip.stop();
	}
	
	// Method that closes SFX
	
	
	public void playAfterPause(int frame)
	{
		clip.setFramePosition(frame);
		clip.start();
	}
	
	public int getFramePosition()
	{
		return clip.getFramePosition();
	}
	
	public boolean getIsRunning()
	{
		return clip.isRunning();
	}
	public int getFrameLen()
	{
		return clip.getFrameLength();
	}
	// A method with the aid of which the user can change the volume
	public void changeVolume(float volume)
	{
		volumeControl(volume);
	}
	
	// A method used to adjust the volume of a sound effect
	private void playClip(float volume, int framePos)
	{
		if(clip == null)
			return;
		
		stop();
		volumeControl(volume);
		
		clip.setFramePosition(framePos);
	}
	
	private void volumeControl(float volume)
	{
		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		try
		{
			gainControl.setValue(volume);
			//System.out.println(volume);
		}
		catch(IllegalArgumentException e)
		{
			//e.printStackTrace();
			if(volume > gainControl.getMaximum())
				volume = gainControl.getMaximum();
			else if(volume < gainControl.getMinimum())
				volume = gainControl.getMinimum();
		}	
	}
}
