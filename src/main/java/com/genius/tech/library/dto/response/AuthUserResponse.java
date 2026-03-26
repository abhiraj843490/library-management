package com.genius.tech.library.dto.response;

import com.genius.tech.library.enums.Role;

public class AuthUserResponse {
    private Long id;
    private Long studentId;
    private String userCode;
    private String name;
    private String email;
    private Role role;
    private Boolean active;
    private String gender;
    private String seatSection;
    private String seatNumber;

    public AuthUserResponse() {
    }

    public AuthUserResponse(Long id, Long studentId, String userCode, String name, String email, Role role,
                            Boolean active, String gender, String seatSection, String seatNumber) {
        this.id = id;
        this.studentId = studentId;
        this.userCode = userCode;
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = active;
        this.gender = gender;
        this.seatSection = seatSection;
        this.seatNumber = seatNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeatSection() {
        return seatSection;
    }

    public void setSeatSection(String seatSection) {
        this.seatSection = seatSection;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
