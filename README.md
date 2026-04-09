>Manuel González_21-10250

##Para ejecutar:
Antes de nada, se debe crear un archivo de nombre **"atlantis.txt"** que se encuentre en la misma carpeta dónde se encuentren los archivos descargados. Luego se debe ejecutar el CMD en la carpeta dónde está descargado el archivo, luego se usa el comando **"make"** para compilar el archivo. Una vez se compile se ejecuta usando el comando **./runMundoChiquito.sh**. El archivo *"atlantis.txt"* es una matriz de números de dimensión n x m similar al siguiente ejemplo:

>3 3 4 4 4 2
3 1 3 2 1 4
7 3 1 6 4 1


##Para el diseño de la funciones:

**AlfonsoJose**: Clase creada para facilitar el acceso entre el tamaño de un elemento y sus coordenadas en una matriz. Dispone de funciones para acceder a sus valores (Tamaño, posición en I, Posición en J). Un elemento fundamental para el funcionamiento de la clase es la utilización de una "data class". Dado que eso en conjunto del override del "equals" es lo que permite a la función comparar elementos dentro del grafo. De lo contrario no se compararían correctamente los elementos dentro de la clase. Con el fin de mantener un modelo estandarizado se creó la subfunción "hashCode" para mantener estandar con el cambio realizado en equals

**AlfonsoJoseConAgua**: Clase auxiliar para poder comprobar cuanta agua hay durante las iteraciones de la función principal. Se diseñó de manera idéntica a la clase AlfonsoJose, con la diferencia de que hay una función "TamTotal" que se encarga de comprobar cual es el tamaño del elemento junto con el agua.

**crearEnClaseAlfonsoJose**: Funcion auxiliar para evitar copiar y pegar 4 veces la misma línea de código, se encarga de comprogar si un elemento se encuentra en la matriz y existe. Si se cumple, entoces se crea un elemento de la clase AlfonsoJose y lo retorna. 

**añadirAlGrafo**: Funcion auxiliar para añadir y conectar los elementos al grafo si se cumple que existe el elemento de clase AlfonsoJose. 

**cargarDatos:** Funcion para verificar la entrada, se revisa si la entrada tiene el formato adecuado, crea una matriz con los elementos, y el grafo donde los elementos estan conectados de tal forma que cubra tanto norte, sur, este y oeste del elemento. Se verifica para no añadir elementos con índice mayor o menor al indicado. Y se regresan en formato de pair, puesto que se quieren retornar dos elementos

**ComprobarAguaVsSinAgua**: Función auxiliar para transformar un elemento de la clase AlfonsoJoseConAgua a la clase AlfonsoJose. Adicionalmente comprueba si el elemento está en el grafo, para garantizar que si se retorna, es que el elemento se puede usar. 

**ayudarAlfonsoJoseDijkstra**: La función principal. Dijkstra para ver todos los elementos e ir comparando los costos. EL algoritmo primero verifica los bordes, puesto que los mismos contienen los lugares donde el agua puede "botarse" (definiendo botar agua como salirse de los límites de la matriz, puesto que al intentar colocar agua fuera, se puede asumiar que el agua se esparse y no llega a llenar un bloque). Así utilizando colas de prioridad que van sacando de menor tamaño (incluyendo en el tamaño el contenido en agua) a mayor, se pueden ver los elementos que son adyacentes a los bordes, y si no fue visitado previamente y el "coste" (siendo el tamaño de la torre de agua a añadir), se suma a un total de agua añadida.

**nodos:** clases auxiliares para el funcionamiento del grafo, explicados en el proyecto 1

**ListaAdyacenciaGrafo:** Clase utlizada en el proyecto 2

**main:** el main asume la posición del "atlantis.txt" puesto que pide que se encuentre en la raiz del programa, si no se encuentra, retorna error. Si existe el archivo, se usa cargarDatos para obtener el grafo y la matriz de datos, de ahì se usa ayudarAlfonsoJoseDijkstra para ontener el número de agua a añadir. 

