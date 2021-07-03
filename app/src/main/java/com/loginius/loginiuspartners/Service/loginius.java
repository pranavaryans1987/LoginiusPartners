package com.loginius.loginiuspartners.Service;

import com.loginius.loginiuspartners.Model.Course.CourseBody;
import com.loginius.loginiuspartners.Model.Course.CourseNameBody;
import com.loginius.loginiuspartners.Model.Develop.DevBody;
import com.loginius.loginiuspartners.Model.Develop.DevList;
import com.loginius.loginiuspartners.Model.Login.Login;
import com.loginius.loginiuspartners.Model.Project.Payment;
import com.loginius.loginiuspartners.Model.Project.Project;
import com.loginius.loginiuspartners.Model.Project.ProjectBody;
import com.loginius.loginiuspartners.Model.Stud.CurrentStud;
import com.loginius.loginiuspartners.Model.Stud.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface loginius {

    @POST("course/addcourse.php")
    Call<CourseBody> addCourseBodyCall(@Body CourseBody courseBody);

    @POST("course/listCrs.php")
    Call<List<CourseNameBody>> getCourse(@Body CourseNameBody CourseNameBody);

    @POST("student/addStd.php")
    Call<CourseNameBody> addStudCall(@Body CourseNameBody courseNameBody);

    @POST("developer/addDev.php")
    Call<DevBody> Develop(@Body DevBody devBody);

    @POST("project/devList.php")
    Call<List<DevList>> getDev(@Body DevList devList);

    @POST("project/addProject.php")
    Call<ProjectBody> PrjAdd(@Body ProjectBody projectBody);

    @POST("project/payment.php")
    Call<List<Payment>> getPrjAmt(@Body Payment payment);

    @POST("developer/addDevPay.php")
    Call<Payment> devPayAmt(@Body Payment devAmtPay);

    @POST("Login/login.php")
    Call<Login> userChk(@Body Login login);

    @POST("student/payment.php")
    Call<List<Student>> student(@Body Student student);

    @POST("student/addStudRec.php")
    Call<Student> stdRecPay(@Body Student student);

    @POST("project/prjList.php")
    Call<List<ProjectBody>> prjOut(@Body ProjectBody projectBody);

    @POST("project/addPrjRec.php")
    Call<ProjectBody> prjRec(@Body ProjectBody projectBody);

    @POST("student/getStudent.php")
    Call<List<CurrentStud>> crntStud(@Body CurrentStud currentStud);

    @POST("student/alumniStd.php")
    Call<List<CurrentStud>> almnStud(@Body CurrentStud currentStud);

    @POST("student/updateStd.php")
    Call<CurrentStud> updCrs(@Body CurrentStud currentStud);

    @POST("project/projectWork.php")
    Call<List<ProjectBody>> listPrj(@Body ProjectBody projectBody);

    @POST("project/prjReceive.php")
    Call<List<Project>> getPrj(@Body Project project);

    @POST("project/PrjDetails.php")
    Call<List<Project>> onGoing(@Body Project project);

}
