package com.norkys.backend.config;

import com.norkys.backend.entity.Producto;
import com.norkys.backend.entity.Usuario;
import com.norkys.backend.repository.ProductoRepository;
import com.norkys.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {

        if (productoRepository.count() == 0) {
            productoRepository.saveAll(List.of(
                    // ── POLLOS ──────────────────────────────────────────
                    p("1 Pollo a la Brasa", "Pollo entero, papas familiares, ensalada fresca, cremas.", 75.90, "pollos",
                            "imgs/1 Pollo a la Brasa.webp"),
                    p("1/2 Pollo a la Brasa", "Medio pollo, papas medianas, ensalada.", 45.90, "pollos",
                            "imgs/MedioPollo.webp"),
                    p("1/4 Pollo a la Brasa", "Cuarto de pollo, porción de papas, ensalada clásica.", 26.90, "pollos",
                            "imgs/UnCuartoPollo.webp"),
                    p("Promoción 2 Pollos", "2 Pollos enteros, doble porción de papas familiares, ensalada gigante.",
                            139.90, "pollos", "imgs/DosPollos.webp"),
                    p("1/8 Pollo a la Brasa", "Octavo de pollo, porción personal de papas, cremas.", 15.90, "pollos",
                            "imgs/UnOctavoPollo.webp"),
                    // ── PARRILLAS ────────────────────────────────────────
                    p("Parrilla Mixta Personal", "Bife, chuleta, chorizo, pollo, papas y ensalada.", 85.00, "parrillas",
                            "imgs/ParrillaMixta.webp"),
                    p("Bife Ancho a la Parrilla", "Corte premium de bife ancho, papas doradas, chimichurri.", 65.90,
                            "parrillas", "imgs/BifeAncho.webp"),
                    p("Chuleta de Cerdo", "Doble chuleta de cerdo a la parrilla, ensalada cocida, papas.", 42.50,
                            "parrillas", "imgs/ChuletaCerdo.webp"),
                    p("Medio Pollo Parrillero", "Medio pollo deshuesado a la parrilla, papas, ensalada.", 50.90,
                            "parrillas", "imgs/PolloParrillero.webp"),
                    p("Parrillada Familiar", "2 Bifes, 2 chuletas, 4 chorizos, medio pollo, anticuchos.", 145.00,
                            "parrillas", "imgs/ParrilladaFamiliar.webp"),
                    // ── COMBOS ───────────────────────────────────────────
                    p("Mega Combo Familiar", "1 Pollo + Porción de Papas + Ensalada + Jarra Chicha 1L.", 89.90,
                            "combos", "imgs/MegaComboFamiliar.webp"),
                    p("Combo Pareja", "1/2 Pollo a la brasa + Papas + Ensalada + 2 Gaseosas.", 55.90, "combos",
                            "imgs/ComboPareja.webp"),
                    p("Combo Anticuchero", "1/4 Pollo + 2 Palitos Anticucho + Papas + Gaseosa.", 38.90, "combos",
                            "imgs/ComboAnticuchera.webp"),
                    p("Combo Chaufa", "1/4 Pollo + Porción Arroz Chaufa + Gaseosa.", 34.90, "combos",
                            "imgs/ComboChaufa.webp"),
                    p("Combo Trío", "3/4 Pollo + Papas Grandes + Ensalada + Gaseosa 1.5L.", 65.00, "combos",
                            "imgs/ComboTrio.webp"),
                    // ── HAMBURGUESAS ─────────────────────────────────────
                    p("Hamburguesa Royal", "Carne, huevo, queso, tocino, lechuga y papas.", 22.90, "hamburguesas",
                            "imgs/Hamburguesa.webp"),
                    p("Hamburguesa Clásica", "Carne artesanal, lechuga, tomate, papas fritas.", 16.90, "hamburguesas",
                            "imgs/HamburguesaClasica.webp"),
                    p("Hamburguesa a lo Pobre", "Carne, plátano frito, huevo frito, papas al hilo.", 24.90,
                            "hamburguesas", "imgs/HamburguesaPobre.webp"),
                    p("Doble Queso Bacon", "Doble carne, queso cheddar, doble tocino, salsa especial.", 28.90,
                            "hamburguesas", "imgs/HamburguesaDobleQueso.webp"),
                    p("Burger Parrillera", "Carne a la parrilla, chorizo en rodajas, chimichurri.", 25.00,
                            "hamburguesas", "imgs/HamburguesaParrillera.webp"),
                    // ── BEBIDAS ──────────────────────────────────────────
                    p("Jarra de Chicha Morada 1L", "Preparada en casa, bien helada.", 14.90, "bebidas",
                            "imgs/Jarra de Chicha Morada 1L.webp"),
                    p("Gaseosa Inca Kola 1.5L", "Gaseosa familiar helada.", 11.00, "bebidas",
                            "imgs/Gaseosa Inca Kola 1.5L.webp"),
                    p("Gaseosa Coca Cola 1.5L", "Gaseosa familiar helada.", 11.00, "bebidas",
                            "imgs/Gaseosa Coca Cola 1.5L.webp"),
                    p("Limonada Frozen 1L", "Jarra de limonada frozen recién preparada.", 16.90, "bebidas",
                            "imgs/Limonada Frozen 1L.webp"),
                    p("Gaseosa Personal 500ml", "A elección: Inca Kola o Coca Cola.", 5.50, "bebidas",
                            "imgs/Gaseosa Personal 500ml.webp"),
                    // ── POSTRES ──────────────────────────────────────────
                    p("Torta de Chocolate", "Porción de torta húmeda de chocolate con fudge.", 12.00, "postres",
                            "imgs/Torta de Chocolate.webp"),
                    p("Suspiro Limeño", "Postre tradicional peruano con manjar blanco y merengue.", 10.00, "postres",
                            "imgs/SuspiroLimeno.webp"),
                    p("Mousse de Maracuyá", "Cremoso mousse de maracuyá con base de galleta.", 11.00, "postres",
                            "imgs/MousseMaracuya.webp"),
                    // ── PORCIONES ────────────────────────────────────────
                    p("Porción de Tequeños", "8 unidades rellenos de queso con guacamole.", 15.90, "porciones",
                            "imgs/Porción de Tequeños.webp"),
                    p("Porción de Papas Fritas", "Papas crujientes doradas, porción familiar.", 12.00, "porciones",
                            "imgs/PapasFritas.webp"),
                    p("Causa Rellena", "Causa limeña rellena de pollo desmenuzado con mayonesa.", 14.90, "porciones",
                            "imgs/img_sideBanner2.png"),
                    p("Ensalada Fresca", "Ensalada mixta con vinagreta de la casa.", 8.00, "porciones",
                            "imgs/Ensalada.webp"),
                    p("Arroz Chaufa Personal", "Arroz chaufa con pollo, verduras y sillao.", 16.00, "porciones",
                            "imgs/ArrozChaufa.webp")));
            System.out.println("✅ " + productoRepository.count() + " productos cargados en la BD");
        }

        if (usuarioRepository.count() == 0) {
            usuarioRepository.saveAll(List.of(
                    Usuario.builder().nombres("Jeremy").apellidos("Kalheb").correo("jeremy@ejemplo.com")
                            .celular("999808501").password("123").rol("cliente").activo(true).build(),
                    Usuario.builder().nombres("Admin").apellidos("Norkys").correo("admin@norkys.pe")
                            .celular("999999999").password("admin").rol("admin").activo(true).build()));
            System.out.println("✅ Usuarios de prueba cargados en la BD");
        }
    }

    private Producto p(String nombre, String desc, double precio, String cat, String img) {
        return Producto.builder()
                .nombre(nombre).descripcion(desc).precio(precio)
                .categoria(cat).imgUrl(img).activo(true).build();
    }
}