# Compilador Java
JC = javac
JFLAGS = -g -Xlint

# Archivos fuente Java
CLASSES = \
	Main.java \
	Utilidades.java \
	Javaling.java \
	Agua.java \
	Fuego.java \
	Planta.java \
	Dragon.java \
	Jugador.java \
	Entrenador.java \
	Piso.java \
	Objeto.java \
	Movimiento.java \
	Batalla.java \
	Tipo.java \
	TipoObjeto.java \

# Regla por defecto: compilar todo
default: classes

# Regla para compilar todos los .java
classes: $(CLASSES:.java=.class)

# Regla para compilar cada archivo individualmente
%.class: %.java
	$(JC) $(JFLAGS) $<

# Limpiar archivos compilados
clean:
	$(RM) *.class