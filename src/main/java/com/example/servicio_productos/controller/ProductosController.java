package com.example.servicio_productos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.servicio_productos.model.Productos;
import com.example.servicio_productos.service.ProductoService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/productos")
public class ProductosController {
    
    private final ProductoService productosService;

    @Autowired
    public ProductosController(ProductoService productosService) {
        this.productosService = productosService;
    }

    @GetMapping
    public ResponseEntity<List<Productos>> getProductos() {
        List<Productos> productos = productosService.getAllProductos();
        return ResponseEntity.ok(productos);
    }    

    @PostMapping
    public ResponseEntity<Productos> crearProducto(@RequestBody Productos producto) {
        Productos nuevoProducto = productosService.crearProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos> actualizarProducto(@PathVariable Long id, @RequestBody Productos producto) {
        Optional<Productos> productoExistente = productosService.getProductosById(id);
        
        if (productoExistente.isPresent()) {
            producto.setId(id); // Asegura que el producto a actualizar tenga el mismo ID que el solicitado
            Productos productoActualizado = productosService.actualizarProducto(producto);
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        Optional<Productos> productoExistente = productosService.getProductosById(id);
        
        if (productoExistente.isPresent()) {
            productosService.eliminarProducto(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Productos>> getProductosByCategoriaId(@PathVariable Long categoriaId) {
        List<Productos> productos = productosService.getProductosByCategoriaId(categoriaId);
        return ResponseEntity.ok(productos);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
