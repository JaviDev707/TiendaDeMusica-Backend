package com.proyectos.tiendaDeMusica.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectos.tiendaDeMusica.DTO.DiscoDTO;
import com.proyectos.tiendaDeMusica.DTO.InstrumentoDTO;
import com.proyectos.tiendaDeMusica.DTO.ProductoDTO;
import com.proyectos.tiendaDeMusica.DTO.VariosDTO;
import com.proyectos.tiendaDeMusica.Entity.Disco;
import com.proyectos.tiendaDeMusica.Entity.Instrumento;
import com.proyectos.tiendaDeMusica.Entity.Producto;
import com.proyectos.tiendaDeMusica.Entity.Varios;
import com.proyectos.tiendaDeMusica.Repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> listarTodosLosProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    }
    /**
     * Actualiza el stock de un producto después de una venta.
     */
    @Transactional
    public Producto actualizarStock(Long id, int cantidadVendida) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        if (producto.getStock() < cantidadVendida) {
            throw new IllegalArgumentException("Stock insuficiente para el producto con ID: " + id);
        }

        int nuevoStock = producto.getStock() - cantidadVendida;
        producto.setStock(nuevoStock);

        return productoRepository.save(producto);
    }
    /**
     * Elimina un producto por su ID.
     */
    @Transactional
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto a eliminar no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
    /**
     * Crea un nuevo producto en la base de datos.
     */
    @Transactional
    public Producto crearProducto(ProductoDTO productoRequest) {
        Producto producto;

        if (productoRequest instanceof DiscoDTO) {
            producto = mapDisco((DiscoDTO) productoRequest);
        } else if (productoRequest instanceof InstrumentoDTO) {
            producto = mapInstrumento((InstrumentoDTO) productoRequest);
        } else if (productoRequest instanceof VariosDTO) {
            producto = mapVarios((VariosDTO) productoRequest);
        } else {
            throw new IllegalArgumentException("Tipo de producto no soportado: " +
                    productoRequest.getClass().getSimpleName());
        }
        return productoRepository.save(producto);
    }
    /**
     * Actualiza un producto existente en la base de datos.
     */
    @Transactional
    public Producto actualizarProducto(ProductoDTO productoRequest) {

        if (productoRequest.getId() == null) {
            throw new IllegalArgumentException("El ID del producto es obligatorio para la actualización.");
        }

        Producto productoExistente = productoRepository.findById(productoRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Producto no encontrado con ID: " + productoRequest.getId()));


        if (productoRequest instanceof DiscoDTO discoRequest) {
            if (!(productoExistente instanceof Disco)) {
                throw new IllegalArgumentException("El producto ID " + productoRequest.getId() + " es de tipo "
                        + productoExistente.getClass().getSimpleName() + " y no DISCO.");
            }
            return actualizarDisco((Disco) productoExistente, discoRequest);

        } else if (productoRequest instanceof InstrumentoDTO instrumentoRequest) {
            if (!(productoExistente instanceof Instrumento)) {
                throw new IllegalArgumentException(
                        "El producto ID " + productoRequest.getId() + " no es de tipo INSTRUMENTO.");
            }
            return actualizarInstrumento((Instrumento) productoExistente, instrumentoRequest);

        } else if (productoRequest instanceof VariosDTO variosRequest) {
            if (!(productoExistente instanceof Varios)) {
                throw new IllegalArgumentException(
                        "El producto ID " + productoRequest.getId() + " no es de tipo VARIOS.");
            }
            return actualizarVarios((Varios) productoExistente, variosRequest);

        } else {
            throw new IllegalArgumentException("Tipo de producto no soportado para la actualización.");
        }
    }
    /**
     * Crea una lista de productos en la base de datos.
     */
    @Transactional
    public List<Producto> crearListaDeProductos(List<ProductoDTO> productosRequest) {
        return productosRequest.stream()
                .map(this::crearProducto)
                .toList();
    }
    /*
     * Metodos auxiliares para mapear campos comunes y específicos de cada tipo de producto.
     */
    private void mapBaseFields(Producto target, ProductoDTO source) {
        target.setNombre(source.getNombre());
        target.setPrecio(source.getPrecio());
        target.setStock(source.getStock());
        target.setDescripcion(source.getDescripcion());
    }

    private Disco mapDisco(DiscoDTO discoRequest) {
        Disco disco = new Disco();
        mapBaseFields(disco, discoRequest);

        disco.setGenero(discoRequest.getGenero());
        disco.setArtista(discoRequest.getArtista());
        disco.setYear(discoRequest.getYear());
        return disco;
    }

    private Instrumento mapInstrumento(InstrumentoDTO instrumentoRequest) {
        Instrumento instrumento = new Instrumento();
        mapBaseFields(instrumento, instrumentoRequest);

        instrumento.setTipo(instrumentoRequest.getTipo());
        instrumento.setMarca(instrumentoRequest.getMarca());
        instrumento.setModelo(instrumentoRequest.getModelo());
        return instrumento;
    }

    private Varios mapVarios(VariosDTO variosRequest) {
        Varios varios = new Varios();
        mapBaseFields(varios, variosRequest);

        varios.setTipo(variosRequest.getTipo());
        varios.setMarca(variosRequest.getMarca());
        return varios;
    }

    private Disco actualizarDisco(Disco discoExistente, DiscoDTO discoRequest) {
        mapBaseFields(discoExistente, discoRequest);

        discoExistente.setGenero(discoRequest.getGenero());
        discoExistente.setArtista(discoRequest.getArtista());
        discoExistente.setYear(discoRequest.getYear());

        return productoRepository.save(discoExistente);
    }

    private Instrumento actualizarInstrumento(Instrumento instrumentoExistente, InstrumentoDTO instrumentoRequest) {
        mapBaseFields(instrumentoExistente, instrumentoRequest);

        instrumentoExistente.setTipo(instrumentoRequest.getTipo());
        instrumentoExistente.setMarca(instrumentoRequest.getMarca());
        instrumentoExistente.setModelo(instrumentoRequest.getModelo());

        return productoRepository.save(instrumentoExistente);
    }

    private Varios actualizarVarios(Varios variosExistente, VariosDTO variosRequest) {
        mapBaseFields(variosExistente, variosRequest);

        variosExistente.setTipo(variosRequest.getTipo());
        variosExistente.setNombre(variosRequest.getNombre());
        variosExistente.setMarca(variosRequest.getMarca());

        return productoRepository.save(variosExistente);
    }

}
