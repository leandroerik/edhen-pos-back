package com.edhen.pos.controller;

import com.edhen.pos.util.BackupUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BackupUtil backupUtil;

    // Exportar base de datos
    @GetMapping("/exportar-bdd")
    public ResponseEntity<FileSystemResource> exportarBdd() {
        File dbFile = backupUtil.getDatabaseFile();
        FileSystemResource resource = new FileSystemResource(dbFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dbFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // Importar base de datos
    @PostMapping("/importar-bdd")
    public ResponseEntity<String> importarBdd(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().endsWith(".db")) {
            return ResponseEntity.badRequest().body("El archivo debe ser .db");
        }
        File tempFile = File.createTempFile("import", ".db");
        file.transferTo(tempFile);
        backupUtil.importDatabase(tempFile);
        tempFile.delete();
        return ResponseEntity.ok("Base de datos importada correctamente. Reinicie la aplicación para aplicar los cambios.");
    }

}

