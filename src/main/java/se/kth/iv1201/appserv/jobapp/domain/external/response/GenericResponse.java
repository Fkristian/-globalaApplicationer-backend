package se.kth.iv1201.appserv.jobapp.domain.external.response;

public class GenericResponse {
    public static final GenericResponse OK = new GenericResponse("OK");
    public static final GenericResponse UNAUTHORIZED = new GenericResponse("UNAUTHORIZED");
    private String status;

    public GenericResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
