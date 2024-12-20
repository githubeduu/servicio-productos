package com.example.servicio_productos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.servicio_productos.model.Productos;
import com.example.servicio_productos.repository.ProductosRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServicelmpl implements ProductoService {
    
    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public List<Productos> getAllProductos(){
        return productosRepository.findAll();
    }

    @Override
    public Optional<Productos> getProductosById(Long id){
        return productosRepository.findById(id);
    }

    @Override
    public List<Productos> getProductosByCategoriaId(Long categoriaId) {
        return productosRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public Productos crearProducto(Productos producto) {
        return productosRepository.save(producto);
    }

    @Override
    public Productos actualizarProducto(Productos producto) {
        return productosRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        productosRepository.deleteById(id);
    }

    @Transactional
    public void reducirStock(Long productoId, int cantidad) {
        productosRepository.reducirStock(productoId, cantidad);
    }

}
