package com.edhen.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import com.edhen.pos.util.BackupUtil;

@SpringBootApplication
public class PosApplication {

	@Autowired
	private BackupUtil backupUtil;

	public static void main(String[] args) {
		SpringApplication.run(PosApplication.class, args);
	}

	@PostConstruct
	public void backupOnStartup() {
		try {
			backupUtil.backupDatabase();
		} catch (Exception e) {
			System.err.println("[Backup] Error al crear backup diario: " + e.getMessage());
		}
	}

}
