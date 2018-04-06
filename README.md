# edgar-analytics
This is the solution of Insight Fellows Programs Coding Project.

# Dependencies

This solution is based on Java 8, without any other external libiaries. You could run this program if you installed JDK 1.8.

# Run the program

To run the program, just switch the `edgar-analytics` directory and run the `run.sh` file.

# Description of each class

Here are six classes in total, each class has it's own methods to finish the job.

## Main

The entry of the whole program, takes arguments of input and output file path, finishes the read and write jobs.

## Record

Deals with each line of input file, including find each entry's position and extract entries we needed.

## SingleUserSession

Creates session for each user ip, and update each user's session. Also provides formatted output for output file.

## AllUsersSessions

Collects all users' sessions and finds expired sessions.

##  CustomException

Customized exception, throws it when encountered with some exception we expected.

## Utils

Common utils class, provides some common methods for all the other classes.