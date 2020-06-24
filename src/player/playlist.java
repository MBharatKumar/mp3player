package player;
import player.Player;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class playlist{
	
	public static Player obj1;


    static ArrayList<String> playname = new ArrayList<String>();
    static ArrayList<String> playpath = new ArrayList<String>();
  
    public static void addsong(String name1,String path1)
    {
        playname.add(name1);
        playpath.add(path1);

    }

    public void deletesong()
    {
        playname.remove(0);
        playpath.remove(0);
    }

    public ArrayList<String> getnames()
    {
        return playname;

    }

    
}