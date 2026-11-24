package com.peakprosys.institute.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String courseName;

    @Column(nullable = false, unique = true, length = 20)
    private String courseCode;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer durationInWeeks;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal courseFee;

    @Column(nullable = false)
    private Boolean active = true;

    public Course() {
    }

    public Course(String courseName, String courseCode, String description,
                  Integer durationInWeeks, BigDecimal courseFee, Boolean active) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
        this.courseFee = courseFee;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getCourseName() { return courseName; }
    public String getCourseCode() { return courseCode; }
    public String getDescription() { return description; }
    public Integer getDurationInWeeks() { return durationInWeeks; }
    public BigDecimal getCourseFee() { return courseFee; }
    public Boolean getActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public void setDescription(String description) { this.description = description; }
    public void setDurationInWeeks(Integer durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }
    public void setCourseFee(BigDecimal courseFee) { this.courseFee = courseFee; }
    public void setActive(Boolean active) { this.active = active; }
}