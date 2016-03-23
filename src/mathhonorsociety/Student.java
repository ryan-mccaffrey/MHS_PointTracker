package mathhonorsociety;

/**
 * All members are created as type student. Students contain the member's properties like name, email, grade, etc.
 * Contains methods that are performed on the members.
 * @author ry543
 */
import java.io.*;
import java.util.*;

public class Student {
    private String studentFirstName;
    private String studentLastName;
    private ArrayList<Integer> meetingPoints;
    private ArrayList<String> date;
    private ArrayList<Integer> eventPoints;
    private ArrayList<String> events;
    private String studentEmail;
    private boolean tShirtOrder;
    private int totalPoints;
    private int numberOfMeetings;
    private int numberOfEvents;
    private int currentGrade;
    
    /*
    * Creates a student object for a new member.
    * @param firstName First name of new member.
    * @param lastName Last name of new member.
    * @param email Email address of new member.
    * @param grade Grade level of new member.
    * @param shirt Boolean value for if new member owns t-shirt.
    */
    public Student(String firstName, String lastName, String email, int grade, boolean shirt)
    {
        studentFirstName = firstName;
        studentLastName = lastName;
        studentEmail = email;
        tShirtOrder = shirt;
        totalPoints = 0;
        numberOfMeetings = 0;
        currentGrade = grade;
        meetingPoints = new ArrayList<Integer>();
        date = new ArrayList<String>();
        eventPoints = new ArrayList<Integer>();
        events = new ArrayList<String>();
        numberOfEvents = 0;
    }
    
    /*
    * Creates a student object for a member read from file.
    * @param firstName First name of new member.
    * @param lastName Last name of new member.
    * @param email Email address of new member.
    * @param grade Grade level of new member.
    * @param point Total points member has.
    * @param days Number of meetings member has.
    * @param totalEvents Number of events member has.
    * @param shirt Boolean value for if new member owns t-shirt.
    */
    public Student(String firstName, String lastName, String email, int grade, int point, int days, int totalEvents, boolean shirt, ArrayList<String> datesStuffs, ArrayList<Integer> pointStuffs, ArrayList<String> eventsStuffs, ArrayList<Integer> pointStuffs2)
    {
        studentFirstName = firstName;
        studentLastName = lastName;
        studentEmail = email;
        totalPoints = point;
        numberOfMeetings = days;
        tShirtOrder = shirt;
        date = datesStuffs;
        meetingPoints = pointStuffs;
        currentGrade = grade;
        numberOfEvents = totalEvents;
        eventPoints = pointStuffs2;
        events = eventsStuffs;
    }

    /**
     * Add date of certain event and how many points member earned
     * Event will be added into a new slot of its respective ArrayList (end of the list)
     * Points will be added into a new slot of its respective ArrayList (end of the list)
     * @param point
     * @param day
     * @param shirt
     */
    public int compareTo (Student student1)
    {
        return studentLastName.compareTo(student1.getLastName());
    }
    
    public void addMeetingPoints(int point, String day, boolean shirt)
    {
        if (shirt == true)
        {
            point = point + 1;
        }
        meetingPoints.add(point);
        date.add(day);
        calcTotalPoints();
        calcNumberOfMeetings();
    }
    
    public void addMeeting(String dayOfMeeting, int number)
    {
        date.add(dayOfMeeting);
        meetingPoints.add(number);
        numberOfMeetings++;
    }
    
    public void addEvent(String eventTitle, int number)
    {
        events.add(eventTitle);
        eventPoints.add(number);
        numberOfEvents++;
    }
    
    public void removeLastMeeting()
    {
        date.remove(date.size()-1);
        meetingPoints.remove(meetingPoints.size()-1);
        numberOfMeetings--;
    }
    
    public void removeLastEvent()
    {
        events.remove(events.size()-1);
        eventPoints.remove(eventPoints.size()-1);
        numberOfEvents--;
    }
    
    /**
     * Update the points of an already existing event (maybe for when point count is wrong).
     * Input the date of the event needed to be fixed and how many points were actually earned.
     * @param index
     * @param pointValue
     */
    public void changeMeetingPoints(int index, int pointValue)
    {
        meetingPoints.set(index, pointValue);
    }
    
    public void changeMeetingTitle(int index, String newTitle)
    {
        date.set(index, newTitle);
    }
    
    public void changeEventPoints(int index, int eventPointValue)
    {
        eventPoints.set(index, eventPointValue);
    }
    
    public void changeEventTitle(int index, String newTitle)
    {
        events.set(index, newTitle);
    }
    
    public void updatePoints(int point, String day)
    {
        int event = -1;
        for (int i = 0; i < meetingPoints.size(); i++)
        {
            if (day.equals(date.get(i)))
            {
                event = i;
            }
        }
        meetingPoints.set(event, point);
        calcTotalPoints();
        calcNumberOfMeetings();
        calcNumberOfEvents();
    }
   
    public void calcTotalPoints()
    {
        int total = 0;
        for (int i = 0; i < meetingPoints.size(); i++)
        {
            total = total + meetingPoints.get(i);
        }
        for (int j = 0; j < eventPoints.size(); j++)
        {
            total = total + eventPoints.get(j);
        }
        totalPoints = total;
    }
    
    public int getTotalPoints()
    {
        return totalPoints;
    }
    
    public void calcNumberOfMeetings()
    {
        if ((date == null) || date.isEmpty()) 
        {
            numberOfMeetings = 0;
        } 
        else 
        {
            numberOfMeetings = date.size();
        }
        
    }
    
    public int getNumberOfMeetings()
    {
        calcNumberOfMeetings();
        return numberOfMeetings;
    }
    
    public void calcNumberOfEvents()
    {
        if ((events == null) || events.isEmpty())
        {
            numberOfEvents = 0;
        }
        else
        {
            numberOfEvents = events.size();
        }
    }
    
    public int getNumberOfEvents()
    {
        calcNumberOfEvents();
        return numberOfEvents;
    }
    /**
     * Checks if member attended event.  Input is the date of the event and the method outputs a boolean
     * where true = attended event, false = did not attend.
     */
    public boolean attendanceAtEvent(String day)
    {
        int event = -1;
        int eventPoints = 0;
        for (int i = 0; i < meetingPoints.size(); i++)
        {
            if (day.equals(date.get(i)))
            {
                event = i;
            }
        }
        if (event != -1)
        {
            eventPoints = meetingPoints.get(event);
        }
        if (eventPoints != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Determines if the student has ordered a t-shirt.
     */
    public void changeTshirt(boolean shirt)
    {
        tShirtOrder = shirt;
    }
    
    public boolean tShirt()
    {
        return tShirtOrder;
    }
    
    public void changeFirstName(String name)
    {
        studentFirstName = name;
    }
    
    public String getFirstName()
    {
        return studentFirstName;
    }
    
    public void changeLastName(String name)
    {
        studentLastName = name;
    }
    
    public String getLastName()
    {
        return studentLastName;
    }
    
    public void changeEmail(String email)
    {
        studentEmail = email;
    }
    
    public String getEmail()
    {
        return studentEmail;
    }
    
    public void changeGrade(int level)
    {
        currentGrade = level;
    }
    
    public int getGrade()
    {
        return currentGrade;
    }
    
    public ArrayList<Integer> getMeetingPointList()
    {
        return meetingPoints;
    }
    
    public ArrayList<String> getDateList()
    {
        return date;
    }
    
    public ArrayList<Integer> getEventPointList()
    {
        return eventPoints;
    }
    
    public ArrayList<String> getEventList()
    {
        return events;
    }
    
    public void updateStudent()
    {
        calcNumberOfMeetings();
        calcNumberOfEvents();
        calcTotalPoints();
    }
}
