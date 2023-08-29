package com.timescape.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"path","status","message"})
public class AuthenticationFailureResponse {
	private String path;
	private String status;
	private String message;
}
