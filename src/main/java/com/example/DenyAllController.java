package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DenyAllController {

	@RequestMapping("/denyAll")
	public ResponseEntity<String> denyAll() {
		// change the status so we know if this endpoint processed just by
		// seeing http status
		return new ResponseEntity<>("oops", HttpStatus.CREATED);
	}
}
