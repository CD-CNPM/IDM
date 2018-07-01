package com.idm;

public enum ErrorMessage {
	INVALID_URL {
		public String toString() {
			return "Invalid Download URL";
		}
	},
	REMOVE_ROW {
		public String toString() {
			return "Please select a row to remove";
		}
	};
}
