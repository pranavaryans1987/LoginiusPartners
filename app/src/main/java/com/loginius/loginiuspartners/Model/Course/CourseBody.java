package com.loginius.loginiuspartners.Model.Course;

public class CourseBody {

    String crsnm, dur, fees, type;
    int status;

    public CourseBody(String crsnm, String dur, String fees, String type) {
        this.crsnm = crsnm;
        this.dur = dur;
        this.fees = fees;
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
