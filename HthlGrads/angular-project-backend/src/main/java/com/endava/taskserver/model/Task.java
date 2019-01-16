package com.endava.taskserver.model;

import com.endava.taskserver.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {

    private int id;
    private String name;
    private String description;

    private String startDate;

    private String endDate;
    private TaskStatus status;

}
