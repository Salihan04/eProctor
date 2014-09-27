/*
 * 
 * This class will help record the data and send the data in bytep[].
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioManager {

	private Recorder recorder;
	private Thread recordingThread;
	private RecordingDispatcher recordingDispatcher;

	private class RecordingDispatcher implements Runnable {

		private volatile boolean isRunning = true;
		
		public void stop() {
			isRunning = false;
		}
		
		@Override
		public void run() {
			try {
				DataLine.Info dataLineInfo = new DataLine.Info( TargetDataLine.class , getAudioFormat() ) ;
				TargetDataLine targetDataLine = (TargetDataLine)AudioSystem.getLine( dataLineInfo  ) ;
				targetDataLine.open( getAudioFormat() );
				targetDataLine.start();
				byte tempBuffer[] = new byte[10000];

				while(targetDataLine.read(tempBuffer, 0, tempBuffer.length) >0 && isRunning) {
					recorder.record(tempBuffer);
				}

			}
			catch(Exception e ) {
				e.printStackTrace();
			}
		}

	}

	public AudioManager(Recorder recorder) {
		this.recorder = recorder;
		recordingDispatcher = new RecordingDispatcher();
		recordingThread = new Thread(recordingDispatcher);
	}
	
	public void stop() {
		recordingDispatcher.stop();
	}

	public void record() {
		recordingThread.start();
	}

	public static AudioFormat getAudioFormat(){
		float sampleRate = 8000.0F;
		//8000,11025,16000,22050,44100
		int sampleSizeInBits = 8;
		//8,16
		int channels = 1;
		//1,2
		boolean signed = true;
		//true,false
		boolean bigEndian = false;
		//true,false
		return new AudioFormat( sampleRate, sampleSizeInBits, channels, signed, bigEndian );
	}

	public static void play( byte soundbytes[] )
	{
		try{  
			DataLine.Info dataLineInfo = new DataLine.Info( SourceDataLine.class , getAudioFormat() ) ;
			SourceDataLine sourceDataLine = (SourceDataLine)AudioSystem.getLine( dataLineInfo );
			sourceDataLine.open( getAudioFormat() ) ;
			sourceDataLine.start();
			sourceDataLine.write(soundbytes , 0, soundbytes.length );
			sourceDataLine.drain() ;
			sourceDataLine.close() ;
		}
		catch(Exception e )
		{
			System.out.println("not working in speakers " ) ;
		}

	}

}
