import greenfoot.*;

public class MundoCarros extends World
{
    private static final int ANCHO = 600;
    private static final int ALTO = 700;

    private static final int POS_X_JUGADOR = 300;
    private static final int POS_Y_JUGADOR = 560;

    private static final int POS_X_PANEL = 155;
    private static final int POS_Y_PANEL = 200;

    private static final int POS_X_LINEA = 300;

    private static final int POS_X_LUZ_IZQUIERDA = 55;
    private static final int POS_X_LUZ_DERECHA = 545;

    private static final int CARRIL_IZQUIERDA = 120;
    private static final int CARRIL_CENTRO_IZQUIERDA = 210;
    private static final int CARRIL_CENTRO = 300;
    private static final int CARRIL_CENTRO_DERECHA = 390;
    private static final int CARRIL_DERECHA = 480;

    private int contadorLineas = 0;
    private int contadorLuces = 0;

    private CarroJugador jugador;

    private int score = 0;
    private int contadorScore = 0;

    public MundoCarros()
    {    
        super(ANCHO, ALTO, 1);

        setPaintOrder(
            PanelMarchas.class,
            CarroJugador.class,
            carrorojoo.class,
            Carroazull.class,
            Carroblanco.class,
            Carronegro.class,
            Carroverde.class,
            Persona.class,
            SemaforoVerde.class,
            SemaforoRojo.class,
            LuzCarretera.class,
            LineaCarretera.class
        );

        prepararMundo();
    }

    private void prepararMundo()
    {
        crearFondo();

        jugador = new CarroJugador();
        addObject(jugador, POS_X_JUGADOR, POS_Y_JUGADOR);

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

        Persona persona = new Persona();
        addObject(persona, CARRIL_DERECHA, 300);

        SemaforoVerde semaforoverde = new SemaforoVerde();
        addObject(semaforoverde, CARRIL_IZQUIERDA, 430);

        SemaforoRojo semafororojo = new SemaforoRojo();
        addObject(semafororojo, CARRIL_IZQUIERDA, 130);

        PanelMarchas panelMarchas = new PanelMarchas(jugador);
        addObject(panelMarchas, POS_X_PANEL, POS_Y_PANEL);

        agregarLineasIniciales();
        agregarLucesIniciales();
    }

    private void crearFondo()
    {
        GreenfootImage fondo = new GreenfootImage(ANCHO, ALTO);

        fondo.setColor(new Color(25, 80, 25));
        fondo.fill();

        fondo.setColor(Color.DARK_GRAY);
        fondo.fillRect(70, 0, 460, ALTO);

        fondo.setColor(Color.LIGHT_GRAY);
        fondo.fillRect(35, 0, 35, ALTO);
        fondo.fillRect(530, 0, 35, ALTO);

        fondo.setColor(Color.WHITE);
        fondo.fillRect(70, 0, 5, ALTO);
        fondo.fillRect(525, 0, 5, ALTO);

        fondo.setColor(Color.GRAY);
        fondo.fillRect(33, 0, 4, ALTO);
        fondo.fillRect(563, 0, 4, ALTO);

        setBackground(fondo);
    }

    public void act()
    {
        generarLineasCarretera();
        generarLucesCarretera();

        actualizarScore();
    }

  
    private void actualizarScore()
    {
        if (jugador == null) return;

        contadorScore++;

        int marcha = jugador.getMarcha();

        int factor;
        if (marcha == 1) factor = 8;
        else if (marcha == 2) factor = 6;
        else if (marcha == 3) factor = 4;
        else if (marcha == 4) factor = 3;
        else if (marcha == 5) factor = 2;
        else factor = 8;

        if (contadorScore >= factor)
        {
            score++;
            contadorScore = 0;
        }
    }

    public int getScore()
    {
        return score;
    }

    private void agregarLineasIniciales()
    {
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 100);
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 300);
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 500);
    }

    private void agregarLucesIniciales()
    {
        addObject(new LuzCarretera(jugador, true), POS_X_LUZ_IZQUIERDA, 80);
        addObject(new LuzCarretera(jugador, false), POS_X_LUZ_DERECHA, 220);

        addObject(new LuzCarretera(jugador, true), POS_X_LUZ_IZQUIERDA, 360);
        addObject(new LuzCarretera(jugador, false), POS_X_LUZ_DERECHA, 500);
    }

    private void generarLineasCarretera()
    {
        int velocidad = jugador.getVelocidadVisual();

        if (velocidad <= 0) return;

        contadorLineas++;

        int frecuencia = 90 - velocidad * 5;
        if (frecuencia < 30) frecuencia = 30;

        if (contadorLineas >= frecuencia)
        {
            addObject(new LineaCarretera(jugador), POS_X_LINEA, -60);
            contadorLineas = 0;
        }
    }

    private void generarLucesCarretera()
    {
        int velocidad = jugador.getVelocidadVisual();

        if (velocidad <= 0) return;

        contadorLuces++;

        int frecuencia = 180 - velocidad * 8;
        if (frecuencia < 70) frecuencia = 70;

        if (contadorLuces >= frecuencia)
        {
            addObject(new LuzCarretera(jugador, true), POS_X_LUZ_IZQUIERDA, -80);
            addObject(new LuzCarretera(jugador, false), POS_X_LUZ_DERECHA, -220);
            contadorLuces = 0;
        }
    }
}