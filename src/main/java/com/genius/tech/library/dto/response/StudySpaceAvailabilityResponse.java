package com.genius.tech.library.dto.response;

public class StudySpaceAvailabilityResponse {
    private Long id;
    private String spaceName;
    private Integer capacity;
    private String roomType;
    private String location;
    private Integer floor;

    public StudySpaceAvailabilityResponse() {
    }

    public StudySpaceAvailabilityResponse(Long id, String spaceName, Integer capacity, String roomType, String location, Integer floor) {
        this.id = id;
        this.spaceName = spaceName;
        this.capacity = capacity;
        this.roomType = roomType;
        this.location = location;
        this.floor = floor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
