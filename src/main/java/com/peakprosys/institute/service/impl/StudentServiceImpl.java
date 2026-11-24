package com.peakprosys.institute.service.impl;

import com.peakprosys.institute.dto.request.StudentRequest;
import com.peakprosys.institute.dto.response.StudentResponse;
import com.peakprosys.institute.entity.Student;
import com.peakprosys.institute.exception.StudentNotFoundException;
import com.peakprosys.institute.repository.StudentRepository;
import com.peakprosys.institute.service.StudentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponse registerStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Student student = new Student(
            request.fullName(),
            request.email(),
            request.phoneNumber(),
            request.course()
        );

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        return mapToResponse(findStudent(id));
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = findStudent(id);

        if (!student.getEmail().equalsIgnoreCase(request.email())
                && studentRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        student.setFullName(request.fullName());
        student.setEmail(request.email());
        student.setPhoneNumber(request.phoneNumber());
        student.setCourse(request.course());

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.delete(findStudent(id));
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException(id));
    }

    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(
            student.getStudentId(),
            student.getFullName(),
            student.getEmail(),
            student.getPhoneNumber(),
            student.getCourse()
        );
    }
}