package com.peakprosys.institute.service;

import com.peakprosys.institute.dto.request.StudentRequest;
import com.peakprosys.institute.dto.response.StudentResponse;
import java.util.List;

public interface StudentService {

    StudentResponse registerStudent(StudentRequest request);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    StudentResponse updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);
}