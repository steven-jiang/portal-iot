package com.kii.extension.sdk.exception;

public class UnauthorizedAccessException extends KiiCloudException {



	private String authenticatedAppID;

	private String authenticatedPrincipalID;

	public String getAuthenticatedAppID() {
		return authenticatedAppID;
	}

	public void setAuthenticatedAppID(String authenticatedAppID) {
		this.authenticatedAppID = authenticatedAppID;
	}

	public String getAuthenticatedPrincipalID() {
		return authenticatedPrincipalID;
	}

	public void setAuthenticatedPrincipalID(String authenticatedPrincipalID) {
		this.authenticatedPrincipalID = authenticatedPrincipalID;
	}
}
