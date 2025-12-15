package com.proyectos.tiendaDeMusica.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectos.tiendaDeMusica.Entity.Carrito;
import com.proyectos.tiendaDeMusica.Entity.DetallesPedido;
import com.proyectos.tiendaDeMusica.Entity.ItemCarrito;
import com.proyectos.tiendaDeMusica.Entity.Pedido;
import com.proyectos.tiendaDeMusica.Entity.Producto;
import com.proyectos.tiendaDeMusica.Entity.Usuario;
import com.proyectos.tiendaDeMusica.Enums.EstadoPedido;
import com.proyectos.tiendaDeMusica.Repository.PedidoRepository;
import com.proyectos.tiendaDeMusica.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;
    private final UsuarioRepository usuarioRepository;
    private final CarritoService carritoService;

    @Transactional
    public Pedido realizarCheckout(Long usuarioId) {

        Carrito carrito = carritoService.obtenerCarrito(usuarioId);

        if (carrito.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío. No se puede realizar el checkout.");
        }

        Pedido nuevoPedido = new Pedido();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        nuevoPedido.setUsuario(usuario);
        nuevoPedido.setFechaCreacion(LocalDateTime.now());
        nuevoPedido.setEstado(EstadoPedido.PENDIENTE);

        BigDecimal total = BigDecimal.ZERO;
        Set<DetallesPedido> detalles = new HashSet<>();

        // Descuento de Stock y creación de Detalles de Pedido
        for (ItemCarrito itemCarrito : carrito.getItems()) {

            Producto producto = itemCarrito.getProducto();
            int cantidad = itemCarrito.getCantidad();
            // Descuento el stock del producto
            productoService.actualizarStock(producto.getId(), cantidad);
            // Creo el detalle del pedido
            DetallesPedido detalle = new DetallesPedido();
            detalle.setPedido(nuevoPedido);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);

            // Fijo el precio de venta en el detalle para el historial
            BigDecimal precioUnitario = producto.getPrecio();
            detalle.setPrecioVenta(precioUnitario);

            total = total.add(precioUnitario.multiply(BigDecimal.valueOf(cantidad)));
            detalles.add(detalle);
        }

        nuevoPedido.setTotalBruto(total);
        nuevoPedido.setDetalles(detalles);

        carritoService.eliminarCarrito(carrito.getId());

        return pedidoRepository.save(nuevoPedido);
    }

    @Transactional(readOnly = true)
    public List<Pedido> obtenerHistorialPedidos(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    }

    @Transactional(readOnly = true)
    public Pedido obtenerDetallePedido(Long pedidoId, Long usuarioId) {
        
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado."));

        if (!pedido.getUsuario().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("No tienes permiso para ver este pedido.");
        }
        return pedido;
    }

}
