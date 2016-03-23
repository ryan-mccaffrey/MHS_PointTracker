package mathhonorsociety;

import java.io.*;
import java.util.*;

/*
 * Contains all variables of stored information and contains methods for altering members.
 * @author Ryan McCaffrey
 */
public class MainOperations {

    private static ArrayList<Student> memberList = new ArrayList<Student>();
    private static int numberOfMembers = 0;
    private static char[] userPassword;
    private static int pointsPerMeeting;
    private static int pointsPerShirt;

    /*
    * Adds a new member to the existing member list.
    * @param firstName1 First name of new member.
    * @param lastName1 Last name of new member.
    * @param email1 Email address of new member.
    * @param gradeLevel Grade level of new member.
    * @param shirt1 Boolean value for if new member owns t-shirt.
    */
    public static void addMember(String firstName1, String lastName1, String email1, int gradeLevel, boolean shirt1) {
        int index = -1;
        int nameFrequency = 1;
        for (int i = 0; i < numberOfMembers; i++) {
            int lasts = lastName1.compareTo(memberList.get(i).getLastName());
            int firsts = firstName1.compareTo(memberList.get(i).getFirstName());
            if (lasts < 0) {
                if (index == -1) {
                    index = i;
                }
            } else if (lasts == 0) {
                if (firsts < 0) {
                    if (index == -1) {
                        index = i;
                    }
                } else if (firsts == 0) {
                    if (index == -1) {
                        index = i + 1;
                        nameFrequency++;
                    }
                }
            }

        }

        if (index != -1) {
            memberList.add(index, new Student(firstName1, lastName1, email1, gradeLevel, shirt1));
            numberOfMembers++;
        } else {
            memberList.add(new Student(firstName1, lastName1, email1, gradeLevel, shirt1));
            numberOfMembers++;
        }
    }

    /*
    * Used to add members to member list when the file is being read.
    * @param firstName1 First name of member.
    * @param lastName1 Last name of member.
    * @param email1 Email address of member.
    * @param gradeLevel Grade level of member.
    * @param point1 Total number of points member has.
    * @param day1 Total number of meetings member has.
    * @param event1 Total number of events member has.
    * @param shirt1 Boolean value for if member owns t-shirt.
    * @param datesStuffs1 ArrayList of dates of all meetings member has.
    * @param pointStuffs1 ArrayList of points member earned for each meeting.
    * @param eventStuffs1 ArrayList of all events member has.
    * @param eventPointStuffs1 ArrayList of points member earned for each event.
    */
    public static void addMember(String firstName1, String lastName1, String email1, int gradeLevel, int point1, int day1, int event1, boolean shirt1, ArrayList<String> datesStuffs1, ArrayList<Integer> pointStuffs1, ArrayList<String> eventStuffs1, ArrayList<Integer> eventPointStuffs1) {
        memberList.add(new Student(firstName1, lastName1, email1, gradeLevel, point1, day1, event1, shirt1, datesStuffs1, pointStuffs1, eventStuffs1, eventPointStuffs1));
        numberOfMembers++;
    }

    /*
    * Changes the amount of points a member earns for attending a meeting
    * @param num The new point value a member receives for attending a meeting.
    */
    public static void changePointsPerMeeting(int num) {
        pointsPerMeeting = num;
    }

    /*
    * Returns the number of points a member earns for attending a meeting.
    * @return int This is the number of points a member earns for attending a meeting.
    */
    public static int getPointsPerMeeting() {
        return pointsPerMeeting;
    }

    /*
    * Changes the amount of points a member earns for wearing an honor society shirt.
    * @param num The new point value a member receives for wearing an honor society shirt..
    */
    public static void changePointsPerShirt(int num) {
        pointsPerShirt = num;
    }

    /*
    * Returns the amount of points a member earns for wearing an honor society shirt.
    * @return int The point value a member receives for wearing an honor society shirt.
    */
    public static int getPointsPerShirt() {
        return pointsPerShirt;
    }

    /*
    * Returns the number of members in the Math Honor Society.
    * @return int The number of members in the honor society.
    */
    public static int getNumberOfMembers() {
        return numberOfMembers;
    }

    /*
    * Returns the list of all members in the Math Honor Society.
    * @return ArrayList<Student> The list of members in the honor society.
    */
    public static ArrayList<Student> getMemberList() {
        return memberList;
    }

    /*
    * Removes a specific member from the honor society.
    * @param position This is the index at which the member is located in the ArrayList of all members.
    */
    public static void RemoveMember(int position) {
        memberList.remove(position);
        numberOfMembers--;
    }

    /*
    * Changes the current password.
    * @param tempPass This is a character array of each character in the password.
    */
    public static void setPassword(char[] tempPass) {
        userPassword = tempPass;
    }

    /*
    * Returns the current password.
    * @return char[] This is a character array of each character in the password.
    */
    public static char[] getPassword() {
        return userPassword;
    }

    /*
    * Writes to file before closing program completely.
    * @exception IOException On file error.
    */
    public static void closeProgram() throws IOException {
        for (int i = 0; i < memberList.size(); i++) {
            FileHandler.ToFile(memberList);
        }
    }

    //old method for alphabetizing names
    /*public static void alphabetize() {
        ArrayList<Student> sortedArray = new ArrayList<Student>(); // Make a variable string to compare 
        int index = -1;

        Student student1 = memberList.get(0);
        while (memberList.size() != sortedArray.size()) {
            index = memberList.size()-1;
            for (int x = 0; x < memberList.size(); x++) {
                if (memberList.get(x).compareTo(student1) < 0) {
                    student1 = memberList.get(x);
                    index = x;
                }
            }
            
            memberList.remove(index);
                
            sortedArray.add(student1);
        }
        memberList = sortedArray;
    }*/

    /*
    * Finds the index of a member in the member ArrayList.
    * @param nameFirst This is the first name of the member being looked for.
    * @param nameLast This is the last name of the member being looked for.
    * @return int This returns the index at which the member is located.
    */
    public static int findIndexOfMember(String nameFirst, String nameLast) {
        int index = -1;
        for (int i = 0; i < MainOperations.getNumberOfMembers(); i++) {
            String memberLastName = MainOperations.getMemberList().get(i).getLastName();
            String memberFirstName = MainOperations.getMemberList().get(i).getFirstName();
            if (nameLast.equals(memberLastName) && nameFirst.equals(memberFirstName)) {
                index = i;
            }
        }
        return index;
    }

}
