//Import required classes
import java.awt.*;
import javax.swing.*;
import java.util.*;

class Clock extends JFrame implements Runnable
{
//Declare global objects
Thread runner;
Font clockFont;

// my stuff
public int startYear = 2016;
public int startDayOfYear = 12;
public int startHour = 13;
public int startMin = 8;
public int startSec = 00;


public Clock()
{
//create the window
super("Java Clock");
setSize(300,100);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);

//create the Font instance
clockFont = new Font("Serif",Font.BOLD,40);

//Add the components
Container contentArea = getContentPane();
ClockPanel timeDisplay = new ClockPanel();
contentArea.add(timeDisplay);
setContentPane(contentArea);

// my stuff
startMin = 59 - startMin;
startHour = 23 - startHour;

//Start the thread running
start();
}

//Create a clock component
class ClockPanel extends JPanel
{
public void paintComponent (Graphics painter)
{
painter.setColor(Color.white);
//painter.fillRect(0,0,30,100);
painter.fillRect(0, 0, this.getWidth(), this.getHeight());
painter.setFont(clockFont);
painter.setColor(Color.black);
painter.drawString( timeNow(),20,40);
}
}

//Get the current time
// after the end of the year this needs recoded.
public String timeNow()
{
Calendar now = Calendar.getInstance();
int hrs = now.get(Calendar.HOUR_OF_DAY);
int min = now.get(Calendar.MINUTE);
int sec = now.get(Calendar.SECOND);


// my stuff
int yer = now.get(Calendar.YEAR);
int r1 =0;
int r2 = 0;
int r3 = 0;
int aday = 0;
int dday = now.get(Calendar.DAY_OF_YEAR);
if (yer > startYear)
{
	if (dday <= startDayOfYear)
	{
		aday = (365-startDayOfYear) + dday + ((yer - startYear - 1)*365);
	}
	else
	{
		aday = (dday - startDayOfYear) + ((yer - startYear)*365);
	}
}
else
{
	aday = dday - startDayOfYear - 1;
}
sec += startSec;
if(sec>= 60)
{
	r1 = 1;
	sec = sec - 60;
}
min += r1;
min += startMin;
if(min>=60)
{
	r2 = 1;
	min = min - 60;
}
hrs += r2;
hrs += startHour;
if(hrs>=24)
{
	r3 = 1;
	hrs = hrs - 24;
}
aday += r3;
r1 = 0;
r2 = 0;
r3 = 0;

String time =  aday + "  " + zero(hrs) + ":" + zero(min) + ":" + zero(sec);
//String time =  (aday*24*60*60 + (hrs*60*60) + (min*60) + (sec)   ) +  " "; //+ "  " + zero(hrs) + ":" + zero(min) + ":" + zero(sec);

return time;

}

//Add leading zero if required
public String zero (int num)
{
String number = (num<10)?("0"+num) : (""+num);
return number;
}

//Method to start thread
public void start()
{
if (runner == null)
runner = new Thread(this);
runner.start();
}

//Define the thread task
public void run()
{
while (runner == Thread.currentThread())
{
repaint();
try
{
Thread.sleep(1000);
}
catch (InterruptedException e) {}
}
}

//Programs main method
public static void main (String [] args)
{
Clock eg = new Clock();
}
}
