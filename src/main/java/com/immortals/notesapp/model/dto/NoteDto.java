package com.immortals.notesapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteDto {


    @NotNull(message = "Title Must Not Be Null")
    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;


    @NotNull(message = "User Id Must Not Be Null")
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("pinedInd")
    private Boolean pinedInd;

}
