package com.edhen.pos.util;

import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BackupUtil {
    private static final String DB_PATH = "pos.db";
    private static final String BACKUP_DIR = "backups";
    private static final int MAX_BACKUPS = 10;

    public List<String> listBackups() throws IOException {
        Path path = Paths.get(BACKUP_DIR);
        if (!Files.exists(path)) {
            return List.of();
        }
        try (Stream<Path> stream = Files.list(path)) {
            return stream
                .filter(file -> !Files.isDirectory(file))
                .map(Path::getFileName)
                .map(Path::toString)
                .sorted(Comparator.reverseOrder()) // Mostrar los más recientes primero
                .collect(Collectors.toList());
        }
    }

    public File getBackupFile(String fileName) {
        return new File(BACKUP_DIR, fileName);
    }


    public void backupDatabase() throws IOException {
        Files.createDirectories(Paths.get(BACKUP_DIR));
        
        // Formato con hora, minutos y segundos: 2024-01-24_15-30-45
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDateTime.now().format(formatter);
        
        String backupName = String.format("pos_backup_%s.db", timestamp);
        Path backupPath = Paths.get(BACKUP_DIR, backupName);
        
        Files.copy(Paths.get(DB_PATH), backupPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("[Backup] Creado exitosamente: " + backupName);
        
        cleanOldBackups();
    }


    public void cleanOldBackups() throws IOException {
        try (Stream<Path> files = Files.list(Paths.get(BACKUP_DIR))) {
            files.filter(f -> f.getFileName().toString().startsWith("pos_backup_") && f.getFileName().toString().endsWith(".db"))
                .sorted(Comparator.comparingLong(f -> ((Path)f).toFile().lastModified()).reversed())
                .skip(MAX_BACKUPS)
                .forEach(f -> {
                    File file = ((Path)f).toFile();
                    boolean deleted = file.delete();
                    if (!deleted) {
                        System.err.println("[Backup] No se pudo eliminar: " + file.getName());
                    }
                });
        }
    }

    public File getDatabaseFile() {
        return new File(DB_PATH);
    }

    public void importDatabase(File newDbFile) throws IOException {
        // Backup actual antes de reemplazar
        backupDatabase();
        Files.copy(newDbFile.toPath(), Paths.get(DB_PATH), StandardCopyOption.REPLACE_EXISTING);
    }
}
