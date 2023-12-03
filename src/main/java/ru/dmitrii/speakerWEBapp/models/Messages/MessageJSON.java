package ru.dmitrii.speakerWEBapp.models.Messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


//@JsonInclude(JsonInclude.Include.NON_NULL)
public record MessageJSON(
        @JsonProperty(value = "songsADD", required = true)Integer[] songsADD,
        @JsonProperty(value = "songsDELETE", required = true)Integer[] songsDELETE,
        @JsonProperty(value = "albumsADD", required = true)Integer[] albumsADD,
        @JsonProperty(value = "albumsDELETE", required = true)Integer[] albumsDELETE,
        @JsonProperty(value = "username") String username

        ) {
}
