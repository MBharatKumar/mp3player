package player;
import player.playlist;
import java.util.*;
import player.Audio;
import java.io.File;
import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.util.List; 
import java.util.concurrent.ExecutionException; 
  
import javax.swing.*; 

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player extends JFrame
{
	public static final int WIDTH = 500, HEIGHT = 330;
	
	public static final long serialVersionUID = 1L;
	public JPanel browsePanel, namePanel, buttonPanel;
	//public JLabel songName;
	public JButton btnBrowse, btnPlay, btnPause, btnStop, volUp, volDown,start,next;
	public String fullSongPath;
	public Audio song;
	public boolean isFirstSong = true;
	public int framePos,i,len,firsttime=1,index=0,begin=0;
	public float volume;
	public Thread textMove;
	public playlist songlist,copy;
	private JComboBox cb;
	
	//public int ind=0;
	public Player()
		{
		setTitle("Music Player");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		initWindow();
		volume = -20.0f;
		
		setVisible(true);
	}
	
	 private void startThread()  
	    { 
	  
	        SwingWorker sw1 = new SwingWorker()  
	        { 
	  
	            @Override
	            protected String doInBackground() throws Exception  
	            { 
	                // define what thread will do here 
	            	
	        		
	        		
	        		while((song.getFrameLen()-song.getFramePosition())>0)
	        		{
	        			Thread.sleep(100);
	        			//System.out.println((song.getFrameLen()-song.getFramePosition()));
	        		
	        		}
	        		
	                String res="played song " + i+1;
	                return res; 
	            } 
	  
	            @Override
	            protected void process(List chunks) 
	            { 
	                // define what the event dispatch thread  
	                // will do with the intermediate results received 
	                // while the thread is executing 
	                 
	               // songName.setText(songlist.playname.get(i));
	                
	                
	            } 
	  
	            @Override
	            protected void done()  
	            { 
	                // this method is called when the background  
	                // thread finishes execution 
	              
	            	
                   
	                	i++;
	                	begin++;
	                	//System.out.println("song ended________________----------------");
	                	cb.removeAllItems();
	                	  for(int h=begin;h<index;h++)
	                		  cb.addItem(songlist.playname.get(h));
	                	//playlist.deletesong1();
	                	
	     			 
	                	if(i<len)
	                		playSong(volume);
	                      
	               
	            } 
	        }; 
	          
	        // executes the swingworker on worker thread 
	        sw1.execute();  
	    } 
	      
	
	public void initWindow()
	{	
		browsePanel = new JPanel();
		btnBrowse = new JButton("Browse Songs");
		add(browsePanel, BorderLayout.NORTH);
		//gifPanel = new JPanel();
		
		btnBrowse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser chooser = new JFileChooser();
				
				JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);

            int option = fileChooser.showOpenDialog(Player.this);
            
            if(option == JFileChooser.APPROVE_OPTION)
            {
               File[] files = fileChooser.getSelectedFiles();
               String path = fileChooser.getCurrentDirectory().toString();
               
               for(File file: files){
            	   
            	   	index++;
					String name = file.getName();
					fullSongPath = path + "\\" + name;	
					songlist.addsong(name,fullSongPath);
					//songlist.addsong1(name,fullSongPath);
				//	copy.addsong(name, fullSongPath);
				//	System.out.println(fullSongPath);
					//ind++;
                                    }
               len=playlist.playname.size();
             //  System.out.println("This is NEW Playlist");
              // for(String str:songlist.playpath1)
            	//   System.out.println(str + "\n");
               
              //Iterator itr=songlist.playpath1.iterator();  
             // while(itr.hasNext()){  
                // System.out.println(songlist.playpath1);
              // }
            if(firsttime!=1) {
               volUp.setEnabled(true);
       		volDown.setEnabled(true);
       		btnPlay.setEnabled(false);
       		btnPause.setEnabled(true);
       		btnStop.setEnabled(true);
       		//start.setEnabled(true);
       		next.setEnabled(true);
       		cb.removeAllItems();
      	  for(int h=begin;h<index;h++)
      		  cb.addItem(songlist.playname.get(h));
       		
			  
            }
            else { //playlist.deletesong1();
            	  volUp.setEnabled(false);
             		volDown.setEnabled(false);
             		btnPlay.setEnabled(false);
             		btnPause.setEnabled(false);
             		btnStop.setEnabled(false);
             		start.setEnabled(true);
             		next.setEnabled(false);
             		//firsttime=0;
             		cb.removeAllItems();
                	  for(int h=begin;h<index;h++)
                		  cb.addItem(songlist.playname.get(h));
             		
            	
            }
			}			
		}		
	});

			
					
					
		browsePanel.add(btnBrowse);
		JLabel lab=new JLabel("SONG LIST : ");
		namePanel = new JPanel();
		add(namePanel, BorderLayout.CENTER);
		//add(gifPanel, BorderLayout.CENTER);
		cb = new JComboBox();
		cb.setBounds(250,250,250,40);
		namePanel.add(lab);
		namePanel.add(cb);
		Icon mus=new ImageIcon("res/tenor.gif");
		JLabel musi=new JLabel(mus);
		namePanel.add(musi);
		
		buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);
		btnPlay = new JButton(new ImageIcon("res/play.png"));
		btnPause = new JButton(new ImageIcon("res/pause.png"));
		btnStop = new JButton(new ImageIcon("res/stop.png"));
		volUp = new JButton(new ImageIcon("res/volUp.png"));
		volDown = new JButton(new ImageIcon("res/volDown.png"));
		start = new JButton("start");
		next = new JButton("next");
		
		btnPlay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				song.playAfterPause(framePos);
				
				btnPlay.setEnabled(false);
				btnPause.setEnabled(true);
				return;
			}
		});
		
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{  
				if(begin==index-1)
				{
					JOptionPane.showMessageDialog(Player.this, "No next Song available"," Error!", JOptionPane.ERROR_MESSAGE);
					}
				else
				{
				    song.playAfterPause(song.getFrameLen());
				}
				// System.out.println("This is NEW Playlist");
	              // for(String str:songlist.playpath1)
	            	//   System.out.println(str + "\n");
				
				
			}
		});
		
		btnPause.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				framePos = song.getFramePosition();
				song.stop();
				btnPlay.setEnabled(true);
				btnPause.setEnabled(false);
				return;
			}
		});
		
		btnStop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				song.stop();
				songlist.playname.clear();
				songlist.playpath.clear();
				//songlist.playname1.clear();
				//songlist.playpath1.clear();
				index=0;
				begin=0;
				volUp.setEnabled(false);
				volDown.setEnabled(false);
				btnPlay.setEnabled(false);
				btnPause.setEnabled(false);
				btnStop.setEnabled(false);
				start.setEnabled(false);
				next.setEnabled(false);
				
				
			}
		});
		
		volUp.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				volume += 3.5f;
				song.changeVolume(volume);
				return;
			}
		});
		
		volDown.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				volume -= 3.5f;
				song.changeVolume(volume);
				return;
			}
		});
		start.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				playSong(volume);
				firsttime=0;
				
			
				
					
			}
		});
		volUp.setEnabled(false);
		volDown.setEnabled(false);
		btnPlay.setEnabled(false);
		btnPause.setEnabled(false);
		btnStop.setEnabled(false);
		start.setEnabled(false);
		next.setEnabled(false);
		buttonPanel.add(volUp);
		buttonPanel.add(btnPlay);
		buttonPanel.add(btnPause);
		buttonPanel.add(btnStop);
		buttonPanel.add(next);
		buttonPanel.add(volDown);
		buttonPanel.add(start);
		
	}
	
	public void playSong(float volume)
	{
		
		  /*  for(String path1 : songlist.playpath) 
		    { 	
			song = new Audio(path1);
			songName.setText(songlist.playname.get(0));
			song.play(volume,0);
				while(true) {
					if(song.getIsRunning())
								{
									Thread.yield();
								}
					else
								{
									break;
								}
			//copy.deletesong();
							}
			}*/
		
		
		song = new Audio(playlist.playpath.get(i));
		
		song.play(volume,0);
		
		    startThread();
		   
		
		    volUp.setEnabled(true);
       		volDown.setEnabled(true);
       		btnPlay.setEnabled(false);
       		btnPause.setEnabled(true);
       		btnStop.setEnabled(true);
       		start.setEnabled(false);
       		next.setEnabled(true);
       		
		
			
	}
	
		
	}



