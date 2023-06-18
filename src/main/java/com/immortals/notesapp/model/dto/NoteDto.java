package com.immortals.notesapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteDto {

    @NotNull
    @JsonProperty("title")
    private String title;


    @JsonProperty("content")
    private String content;

    @NotNull
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("pinedInd")
    private Boolean pinedInd;


}
