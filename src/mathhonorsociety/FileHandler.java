package mathhonorsociety;

/**
 * Class that contains methods for writing and reading the file 'members.txt'
 * @author Ryan McCaffrey
 */
import java.io.*;
import java.util.*;

public class FileHandler {

    /*
    * Method to write all data to file. Saves properties of each member that exists in the honor society
    * @param studentList This is the member list of the honor society
    * @exception IOException On file error.
    */
    public static void ToFile(ArrayList<Student> studentList) throws IOException {
        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("members.txt")));
       //FileWriter fw = new FileWriter("members.txt", true);
        //BufferedWriter output = new BufferedWriter(fw);

        //outputs "FirstName,LastName,email,"
        for (int i = 0; i < MainOperations.getPassword().length; i++) {
            output.print(MainOperations.getPassword()[i]);
        }
        output.print(",");
        output.print(MainOperations.getPointsPerMeeting() + "," + MainOperations.getPointsPerShirt());
        output.print("\n");

        for (int j = 0; j < studentList.size(); j++) {
            output.print(studentList.get(j).getFirstName() + "," + studentList.get(j).getLastName() + "," + studentList.get(j).getEmail() + ",");
            //outputs Total#ofPoints,#Meetings
            output.print(studentList.get(j).getTotalPoints() + "," + studentList.get(j).getNumberOfMeetings() + "," + studentList.get(j).getNumberOfEvents() + ",");
            output.print(studentList.get(j).getGrade() + ",");

            //outputs 1, for true or 0, for false
            if (studentList.get(j).tShirt() == true) {
                output.write("1,");
            } else {
                output.write("0,");
            }

            //outputs date,pts,date,pts.....
            int i = 0;
            while (i < studentList.get(j).getNumberOfMeetings()) {
                output.print(studentList.get(j).getDateList().get(i) + ",");
                output.print(studentList.get(j).getMeetingPointList().get(i) + ",");
                i++;
            }

            i = 0;
            while (i < studentList.get(j).getNumberOfEvents()) {
                output.print(studentList.get(j).getEventList().get(i) + ",");
                output.print(studentList.get(j).getEventPointList().get(i));
                if (i < studentList.get(j).getNumberOfEvents() - 1) {
                    output.print(",");
                }
                i++;
            }

            output.write("\n");
        }

        output.close();
    }

    /*
    * Method to read data from file. Adds members to member list after each read.
    * @exception IOException On file error.
    */
    public static void ReadFile() throws IOException {
        String tempInput = "";
        File memberInfo = new File("members.txt");
        Scanner sf = new Scanner(memberInfo);
        String stuff = sf.nextLine();
        String currentPassword = stuff.substring(0, stuff.indexOf(","));
        String leftover = stuff.substring(stuff.indexOf(",") + 1);
        int meetingPoints = Integer.parseInt(leftover.substring(0, leftover.indexOf(",")));
        int shirtPoints = Integer.parseInt(leftover.substring(leftover.indexOf(",") + 1));
        //String currentPassword = sf.nextLine();
        //System.out.println(currentPassword);

        boolean status = sf.hasNext();
        while (status) {
            tempInput = sf.nextLine();
            StringTokenizer stok = new StringTokenizer(tempInput, ",");

            String nameFirst = stok.nextToken();
            //System.out.println(nameFirst);

            String nameLast = stok.nextToken();
            //System.out.println(nameLast);

            String tempEmail = stok.nextToken();
            //System.out.println(tempEmail);

            int tempTotalPoints = Integer.parseInt(stok.nextToken());
            //System.out.println(tempTotalPoints);

            int tempNumberMeetings = Integer.parseInt(stok.nextToken());
            //System.out.println(tempNumberMeetings);

            int tempNumberEvents = Integer.parseInt(stok.nextToken());
            //System.out.println(tempNumberEvents);

            int gradeLevel = Integer.parseInt(stok.nextToken());
            //System.out.println(gradeLevel);
            int tShirtBinary = Integer.parseInt(stok.nextToken());
            boolean tShirtOrder;
            if (tShirtBinary == 1) {
                tShirtOrder = true;
            } else {
                tShirtOrder = false;
            }

            //System.out.println(tShirtOrder);
            ArrayList<String> tempDates = new ArrayList<String>();
            ArrayList<Integer> tempMeetingPoints = new ArrayList<Integer>();
            int i = 0;
            while (i < tempNumberMeetings) {
                tempDates.add(stok.nextToken());
                tempMeetingPoints.add(Integer.parseInt(stok.nextToken()));
                i++;
            }

            ArrayList<String> tempEvents = new ArrayList<String>();
            ArrayList<Integer> tempEventPoints = new ArrayList<Integer>();
            int j = 0;
            while (j < tempNumberEvents) {
                tempEvents.add(stok.nextToken());
                tempEventPoints.add(Integer.parseInt(stok.nextToken()));
                j++;
            }

            MainOperations.addMember(nameFirst, nameLast, tempEmail, gradeLevel, tempTotalPoints, tempNumberMeetings, tempNumberEvents, tShirtOrder, tempDates, tempMeetingPoints, tempEvents, tempEventPoints);

            status = sf.hasNext();
        }

        char[] existingPassword = new char[currentPassword.length()];
        for (int i = 0; i < currentPassword.length(); i++) {
            existingPassword[i] = currentPassword.charAt(i);
        }
        MainOperations.setPassword(existingPassword);
        MainOperations.changePointsPerMeeting(meetingPoints);
        MainOperations.changePointsPerShirt(shirtPoints);

        sf.close();
    }

}
