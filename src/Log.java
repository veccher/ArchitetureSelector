import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Log {
	String logName;
	String log;
	public Log (String logName)
	{
		this.logName=logName;
		log = new String();
	}
	public void addLine (String logLine)
	{
		if (!log.isEmpty())
			log+="\n";
		log+=logLine;
	}
	public void save() throws IOException
	{
		FileOutputStream f=new FileOutputStream("/logs/"+logName);
		f.write(log.getBytes());
		f.close();
	}
}
