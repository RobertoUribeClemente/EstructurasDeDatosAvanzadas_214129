# Compresor Huffman

Proyecto en Java --- Implementación del algoritmo de Huffman

## Autores

-   Alan Paredes Vallejo
-   José Roberto Uribe Clemente

## Descripción del Proyecto

Este proyecto consiste en la implementación en Java del algoritmo de
compresión de Huffman.

El programa: 1. Lee un alfabeto, sus frecuencias y un texto desde un
archivo. 2. Construye el árbol de Huffman óptimo utilizando una cola de
prioridad personalizada. 3. Codifica el texto a su representación
binaria. 4. Decodifica la cadena de bits para verificar que el proceso
sea sin pérdidas.

La cola de prioridad se implementa con una lista enlazada ordenada,
asegurando que `extractMin()` siempre retorne el nodo de menor
frecuencia.

## Archivos del Proyecto

### Pruebas.java

-   Clase principal.
-   Pide la ruta del archivo al usuario.
-   Lee y parsea el archivo de entrada.
-   Orquesta el proceso completo (build → encode → decode).
-   Imprime estadísticas y resultados.

### Huffman.java

Contiene la lógica del algoritmo de Huffman: - buildHuffman() -
huffmanEncode() - huffmanDecode() - getCodeForChar()

### Cola.java

Cola de prioridad (Min-Priority Queue) con lista enlazada ordenada.\
Complejidad: - insert() → O(n) - extractMin() → O(1)

### Nodo.java

Define la estructura del nodo utilizado por la cola.

### input.txt

Archivo de entrada de ejemplo.

## Instrucciones de Compilación y Ejecución

### Requerimientos

-   Java JDK 8 o superior.

### Compilación

Asegúrate de que todos los `.java` estén en la misma carpeta.

Compilar el principal:

``` bash
javac Pruebas.java
```

O compilar todo:

``` bash
javac *.java
```

### Ejecución

``` bash
java Pruebas
```

Ejemplo:

    --- Compresor Huffman ---
    Introduce la ruta del archivo de entrada (ej. input.txt): input.txt

## Formato del Archivo de Entrada

El archivo debe contener exactamente 3 líneas:

1.  Símbolos separados por espacios.\
2.  Frecuencias (en el mismo orden).\
3.  Texto a codificar.

### Ejemplo (input.txt)

    a b c d e f
    45 13 12 16 9 5
    face

## Salida de Ejemplo

    --- RESULTADOS ---
    Alfabeto: [a, b, c, d, e, f]
    Códigos Huffman:
    0 101 100 111 1101 1100

    Estadísticas:
    Símbolos antes: 4 | Bits codificado: 14 | Bits UTF-8 (8-bit): 32
    Tiempo (Build + Encode): 146100 ns (0.1461 ms)

    Texto Codificado:
    110001001101

    Texto Decodificado:
    face

    Verificación (Original == Decodificado): Éxito

## Discusión

### Análisis Teórico

Complejidades considerando textos de longitud n ≈ m:

-   Recursivo puro → O(3\^n)\
    No reutiliza subproblemas, crecimiento exponencial, impráctico.

-   Memoización → O(n × m)\
    Evita recomputaciones, pero depende de la pila → riesgo de
    StackOverflow.

-   Programación Dinámica (Bottom-Up) → O(n × m)\
    Robusta y rápida. Su límite real es el espacio requerido: O(n × m).

## Resultados Experimentales

La gráfica (en escala logarítmica) solo muestra casos exitosos.\
Los casos con L = 50,000 no aparecen por fallar en Timeout /
OutOfMemory.

### Resumen:

-   Recursivo: Únicamente funciona para L = 5. Timeout para L = 50.\
-   Memoización: Rápida para L=5, 50, 500. Falla en L=5000
    (StackOverflowError).\
-   DP Iterativa: Única en completar L=5000. Falla en L=50000 por
    OutOfMemory.

## Conclusión

La programación dinámica iterativa es la única técnica robusta y
escalable para este problema.

-   Recursividad pura → inviable.\
-   Memoización → eficiente pero limitada por la pila.\
-   DP iterativa → limitada únicamente por la memoria (O(n²)).
