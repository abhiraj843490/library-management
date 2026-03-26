package com.genius.tech.library.dto.response;

public class TimeSlotResponse {
    private Integer id;
    private String slotName;
    private String startTime;
    private String endTime;
    private String slotType;
    private Integer durationMinutes;

    public TimeSlotResponse() {
    }

    public TimeSlotResponse(Integer id, String slotName, String startTime, String endTime, String slotType, Integer durationMinutes) {
        this.id = id;
        this.slotName = slotName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.slotType = slotType;
        this.durationMinutes = durationMinutes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
