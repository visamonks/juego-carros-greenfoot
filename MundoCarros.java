import greenfoot.*;

public class MundoCarros extends World
{
    private static final int ANCHO = 600;
    private static final int ALTO = 700;

    // Posición inicial del carro del jugador
    private static final int POS_X_JUGADOR = 300;
    private static final int POS_Y_JUGADOR = 560;

    // Posición del panel de información
    private static final int POS_X_PANEL = 155;
    private static final int POS_Y_PANEL = 200;

    // Centro de la línea punteada
    private static final int POS_X_LINEA = 300;

    // Posiciones de las luces a los lados
    private static final int POS_X_LUZ_IZQUIERDA = 55;
    private static final int POS_X_LUZ_DERECHA = 545;

    // Carriles seguros para que los carros no se salgan
    private static final int CARRIL_IZQUIERDA = 120;
    private static final int CARRIL_CENTRO_IZQUIERDA = 210;
    private static final int CARRIL_CENTRO = 300;
    private static final int CARRIL_CENTRO_DERECHA = 390;
    private static final int CARRIL_DERECHA = 480;

    private int contadorLineas = 0;
    private int contadorLuces = 0;

    private CarroJugador jugador;

    public MundoCarros()
    {    
        super(ANCHO, ALTO, 1);

        // Orden visual de los objetos
        setPaintOrder(
            PanelMarchas.class,
            CarroJugador.class,
            carrorojoo.class,
            Carroazull.class,
            Carroblanco.class,
            Carronegro.class,
            Carroverde.class,
            LuzCarretera.class,
            LineaCarretera.class
        );

        prepararMundo();
    }

    private void prepararMundo()
    {
        crearFondo();

        // Se crea el carro principal en el centro
        jugador = new CarroJugador();
        addObject(jugador, POS_X_JUGADOR, POS_Y_JUGADOR);
        
        // Carros colocados dentro de carriles más seguros
        carrorojoo rojo = new carrorojoo();
        addObject(rojo, CARRIL_DERECHA, 300);
        
        Carroazull azul = new Carroazull();
        addObject(azul, CARRIL_CENTRO_DERECHA, 120);
        
        Carroblanco blanco = new Carroblanco();
        addObject(blanco, CARRIL_CENTRO_IZQUIERDA, 160);
        
        Carronegro negro = new Carronegro();
        addObject(negro, CARRIL_CENTRO, 260);
        
        Carroverde verde = new Carroverde();
        addObject(verde, CARRIL_DERECHA, 430);

        PanelMarchas panelMarchas = new PanelMarchas(jugador);
        addObject(panelMarchas, POS_X_PANEL, POS_Y_PANEL);

        agregarLineasIniciales();
        agregarLucesIniciales();
    }

    private void crearFondo()
    {
        GreenfootImage fondo = new GreenfootImage(ANCHO, ALTO);

        // Pasto o parte exterior
        fondo.setColor(new Color(25, 80, 25));
        fondo.fill();

        // Carretera más ancha
        fondo.setColor(Color.DARK_GRAY);
        fondo.fillRect(70, 0, 460, ALTO);

        // Aceras laterales
        fondo.setColor(Color.LIGHT_GRAY);
        fondo.fillRect(35, 0, 35, ALTO);
        fondo.fillRect(530, 0, 35, ALTO);

        // Bordes blancos de la carretera
        fondo.setColor(Color.WHITE);
        fondo.fillRect(70, 0, 5, ALTO);
        fondo.fillRect(525, 0, 5, ALTO);

        // Bordes exteriores de las aceras
        fondo.setColor(Color.GRAY);
        fondo.fillRect(33, 0, 4, ALTO);
        fondo.fillRect(563, 0, 4, ALTO);

        setBackground(fondo);
    }

    private void agregarLineasIniciales()
    {
        // Líneas del centro de la carretera
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 100);
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 300);
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 500);
    }

    private void agregarLucesIniciales()
    {
        // Luces laterales iniciales
        addObject(new LuzCarretera(jugador, true), POS_X_LUZ_IZQUIERDA, 80);
        addObject(new LuzCarretera(jugador, false), POS_X_LUZ_DERECHA, 220);

        addObject(new LuzCarretera(jugador, true), POS_X_LUZ_IZQUIERDA, 360);
        addObject(new LuzCarretera(jugador, false), POS_X_LUZ_DERECHA, 500);
    }

    public void act()
    {
        generarLineasCarretera();
        generarLucesCarretera();
    }

    private void generarLineasCarretera()
    {
        int velocidad = jugador.getVelocidadVisual();

        if (velocidad <= 0) {
            return;
        }

        contadorLineas++;

        int frecuencia = calcularFrecuenciaLineas(velocidad);

        if (contadorLineas >= frecuencia) {
            addObject(new LineaCarretera(jugador), POS_X_LINEA, -60);
            contadorLineas = 0;
        }
    }

    private void generarLucesCarretera()
    {
        int velocidad = jugador.getVelocidadVisual();

        if (velocidad <= 0) {
            return;
        }

        contadorLuces++;

        int frecuencia = calcularFrecuenciaLuces(velocidad);

        if (contadorLuces >= frecuencia) {
            addObject(new LuzCarretera(jugador, true), POS_X_LUZ_IZQUIERDA, -80);
            addObject(new LuzCarretera(jugador, false), POS_X_LUZ_DERECHA, -220);
            contadorLuces = 0;
        }
    }

    private int calcularFrecuenciaLineas(int velocidad)
    {
        // Entre más velocidad, más seguido salen líneas
        int frecuencia = 90 - velocidad * 5;

        if (frecuencia < 30) {
            frecuencia = 30;
        }

        return frecuencia;
    }

    private int calcularFrecuenciaLuces(int velocidad)
    {
        // Entre más velocidad, más seguido salen las luces
        int frecuencia = 180 - velocidad * 8;

        if (frecuencia < 70) {
            frecuencia = 70;
        }

        return frecuencia;
    }
}