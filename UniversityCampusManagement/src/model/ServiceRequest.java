package model;

/**
 * A student service request (e.g. "Transcript Request") waiting in the service queue.
 */
public class ServiceRequest {

    private final String requestId;
    private final String studentId;
    private final String requestDescription;

    public ServiceRequest(String requestId, String studentId, String requestDescription) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.requestDescription = requestDescription;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    /** Prints the request as a labelled block. */
    public void printDetails() {
        System.out.println("Request ID   : " + requestId);
        System.out.println("Student ID   : " + studentId);
        System.out.println("Description  : " + requestDescription);
    }

    @Override
    public String toString() {
        return requestId + " | Student: " + studentId + " | Request: " + requestDescription;
    }
}
