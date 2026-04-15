package com.universidad.patrones.controller;

import com.universidad.patrones.model.Libro;
import com.universidad.patrones.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService service;
    public LibroController(LibroService service) { this.service = service; }

    @GetMapping    // GET /api/libros
    public List<Libro> listar() { return service.findAll(); }

    @GetMapping("/{id}")    // GET /api/libros/1
    public ResponseEntity<Libro> obtener(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping                                 // POST /api/libros
    public ResponseEntity<Libro> crear(@RequestBody @Valid Libro libro)
    {
        return ResponseEntity.status(201).body(service.save(libro));
    }

    @PutMapping("/{id}")// PUT /api/libros/1

    public ResponseEntity<Libro> actualizar(@PathVariable Long id,
                                            @RequestBody @Valid Libro
                                                    datos) {
        return ResponseEntity.ok(service.update(id, datos));
    }

    @DeleteMapping("/{id}")                      // DELETE /api/libros/1
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar") // GET/api/libros/buscar?q=java
    public List<Libro> buscar(@RequestParam String q) {
        return service.buscarPorPalabra(q);
    }

}
