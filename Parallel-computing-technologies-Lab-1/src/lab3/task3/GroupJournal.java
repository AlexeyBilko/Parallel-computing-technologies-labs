package lab3.task3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GroupJournal {
    private final int NUM_GROUPS = 3;
    private final int NUM_LECTURERS = 1;
    private final int NUM_ASSISTANTS = 3;
    private final int NUM_STUDENTS_IN_GROUP = 10;
    private final int MAX_GRADE = 100;

    private int[][] journal;
    private Lock[][] groupLocks;

    public GroupJournal() {
        journal = new int[NUM_GROUPS][NUM_STUDENTS_IN_GROUP];
        groupLocks = new Lock[NUM_GROUPS][NUM_LECTURERS + NUM_ASSISTANTS];
        for (int i = 0; i < NUM_GROUPS; i++) {
            for (int j = 0; j < NUM_LECTURERS + NUM_ASSISTANTS; j++) {
                groupLocks[i][j] = new ReentrantLock();
            }
        }
    }

    private void assignGradesForStaffMember(int staffMember, int weekNumber) {
        for (int gradeIndex = 0; gradeIndex < NUM_STUDENTS_IN_GROUP; gradeIndex++) {
            boolean gradeAssigned = false;
            int groupIndex = 0;
            while (!gradeAssigned && groupIndex < NUM_GROUPS) {
                for (int studentIndex = 0; studentIndex < journal[groupIndex].length; studentIndex++) {
                    //int studentIndex = (int) (Math.random() * journal[groupIndex].length);
                    if (groupLocks[groupIndex][staffMember].tryLock() && journal[groupIndex][studentIndex] == 0) {
                        try {
                            if (journal[groupIndex][studentIndex] == 0) {
                                int grade = (int) (Math.random() * MAX_GRADE);
                                journal[groupIndex][studentIndex] = grade;
                                if (staffMember < NUM_LECTURERS) {
                                    System.out.printf("Assigned grade %d to student %d in group %d by lecturer\n", grade, studentIndex + 1, groupIndex, studentIndex + 1);
                                } else {
                                    System.out.printf("Assigned grade %d to student %d in group %d by assistant %d\n", grade, studentIndex + 1, groupIndex, staffMember - NUM_LECTURERS + 1);
                                }
                                gradeAssigned = true;
                            }
                        } finally {
                            groupLocks[groupIndex][staffMember].unlock();
                        }
                    }
                }
                groupIndex++;
            }
        }
        if (staffMember < NUM_LECTURERS) {
            System.out.printf("-> All grades assigned for lecturer, week %d\n", weekNumber);
        } else {
            System.out.printf("-> All grades assigned for assistant %d, week %d\n", staffMember, weekNumber);
        }
    }

    public void assignGrades(int weekNumber) throws InterruptedException {
        Thread[] threads = new Thread[NUM_LECTURERS + NUM_ASSISTANTS];
        for (int i = 0; i < NUM_LECTURERS + NUM_ASSISTANTS; i++) {
            final int staffMemberIndex = i;
            threads[i] = new Thread(() -> assignGradesForStaffMember(staffMemberIndex, weekNumber));
            threads[i].start();
        }

        for (int i = 0; i < NUM_LECTURERS + NUM_ASSISTANTS; i++) {
            threads[i].join();
        }

        System.out.printf("--> All grades assigned, week %d", weekNumber);
    }

    public static void main(String[] args) throws InterruptedException {
        GroupJournal journal = new GroupJournal();
        int weekNumber = 1;
        journal.assignGrades(weekNumber);
    }
}
