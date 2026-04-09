import java.io.File
import java.io.BufferedReader
import java.util.PriorityQueue
//funciòn auxiliar para crear elementos en la clase AlfonsoJose
fun crearEnClaseAlfonsoJose(elem: Int?, i:Int,j:Int): AlfonsoJose?{
    if(i<0 || j <0) return null
    if(elem != null){
        var elemEnClase = AlfonsoJose(tamaño = elem,PosI= i,PosJ=j)
        return elemEnClase
    }
    return null
}
//funcion auxiliar para conectar elementos
fun añadirAlGrafo(elemSeleccioando:AlfonsoJose ,elemAConectar: AlfonsoJose?, GrafoEntrada: Grafo<AlfonsoJose>){
    if(elemAConectar != null){
        GrafoEntrada.agregarVertice(elemAConectar)
        GrafoEntrada.conectar(elemSeleccioando, elemAConectar)
    }

}
//funcion auxiliar para comprobar si un elemento con agua está en el grafo sin agua
fun ComprobarAguaVsSinAgua(GrafoEntrada: Grafo<AlfonsoJose>,  elem:AlfonsoJoseConAgua):AlfonsoJose?{
    //inicializar valores a usar
    val iElem:Int = elem.obtenerI()
    val jElem:Int = elem.obtenerJ()
    val tamElem:Int = elem.obtenerTamaño()
    //se crea el "elemento" a buscar, en versión sin agua
    val elemSinAgua = AlfonsoJose(tamaño = tamElem,PosI= iElem,PosJ=jElem)
    //se busca el elemento
    if(GrafoEntrada.contiene(elemSinAgua)){
        //si se encuentra, se retorna el elemento sin auga
        return elemSinAgua
    }
    return null
}

fun cargarDatos(file: File): Pair<Grafo<AlfonsoJose>,List<List<Int>>>?{
    val bufferedReader: BufferedReader = file.bufferedReader() 
    var Datos : Grafo<AlfonsoJose> = ListaAdyacenciaGrafo()
    var nombres: Set<String> = mutableSetOf<String>()
    var matrizEntrada = mutableListOf<MutableList<Int>>()
    var linea: String? = bufferedReader.readLine()
    //se añaden los elementos a una matriz, para luego procesarlos
    while (linea != null) {
        var elementos: List<String> = linea.split("\\s+".toRegex())
        var lineasMatriz= mutableListOf<Int>()
        for(elem in elementos){
            lineasMatriz.add(elem.toInt())
        }
        matrizEntrada.add(lineasMatriz)
        linea = bufferedReader.readLine()
    }
    //si no hay elemento en 0, o se añadió nada como primer elemento (está vacía la matriz, retorna null) 
    if(matrizEntrada.getOrNull(0) == null || matrizEntrada.getOrNull(0)?.size == 0) return null
    //se comprueba los elementos, junto con sus 4 adyacentes, se añaden(si no estaban en el grafo)y se conectan
    var i: Int = 0
    var nPasado: Int = matrizEntrada.size
    var mPasado: Int = matrizEntrada[0].size
    var elementosTot: Int = 0
    //se ve cada lista de la matriz
    for(matrizLinea in matrizEntrada){
        var j: Int = 0
        if(i >= nPasado) continue
        //se ve cada elemento de la matriz
    	for(elementoMatriz in matrizLinea){
    	    //se añade el elemento i
    	    var ElemAlfonso = AlfonsoJose(tamaño = elementoMatriz,PosI= i,PosJ=j)
    	    Datos.agregarVertice(ElemAlfonso)
    	    //se busca el "tamaño" del elemento a conectar por los 4 lados
    	    val ElementoDer: Int? = matrizLinea.getOrNull(j + 1)
    	    val ElementoIzq: Int? = matrizLinea.getOrNull(j - 1)
    	    val LineaArriba: MutableList<Int>? = matrizEntrada.getOrNull(i - 1)
    	    var ElementoArriba: Int? = null
    	    if(LineaArriba != null){
    	     	ElementoArriba = LineaArriba.getOrNull(j)
    	    }
    	    val LineaAbajo: MutableList<Int>? = matrizEntrada.getOrNull(i + 1)
    	    var ElementoAbajo: Int? = null
    	    if(LineaAbajo != null){
    	     	ElementoAbajo = LineaAbajo.getOrNull(j)
    	    }
    	    //se crean los 4 lados como elementos de la clase AlfonsoJose
    	    var TorreSinAguaArriba: AlfonsoJose? = crearEnClaseAlfonsoJose(ElementoArriba,i-1,j)
    	    var TorreSinAguaAbajo: AlfonsoJose? = null
    	    if(i+1 < nPasado){
    	         TorreSinAguaAbajo = crearEnClaseAlfonsoJose(ElementoAbajo,i+1,j)
    	    }
    	    var TorreSinAguaDer: AlfonsoJose? = null
    	    if(mPasado > j+1){
    	         TorreSinAguaDer = crearEnClaseAlfonsoJose(ElementoDer,i,j+1)
    	    }
    	    var TorreSinAguaIzq: AlfonsoJose? = crearEnClaseAlfonsoJose(ElementoIzq,i,j-1)
    	    //se añaden al grafo los elementos de la case alfonsoJose
    	    añadirAlGrafo (ElemAlfonso ,TorreSinAguaArriba, Datos)
    	    añadirAlGrafo (ElemAlfonso ,TorreSinAguaAbajo, Datos)
    	    añadirAlGrafo (ElemAlfonso ,TorreSinAguaDer, Datos)
    	    añadirAlGrafo (ElemAlfonso ,TorreSinAguaIzq, Datos)
    	    j++
    	    
    	}
    	//si es la primera ejecución
    	if(mPasado == 0){
	    mPasado = j
	//si alguna línea tiene tamaño distinto
    	}else if(mPasado != j){
    	    println("Error: la longitud de alguna fila de la matriz es diferente")
    	    return null
    	}
        i++
    }
    //se crea un pair, para retornar tanto el grafo como la matriz
    val datosPair = Pair(Datos, matrizEntrada.map { it.toList() })
    return datosPair
}
//se asume que se trata de una matriz nxm, dígase no hay huecos en la matriz
fun ayudarAlfonsoJoseDijkstra(grafo: Grafo<AlfonsoJose>, matrizValores:List<List<Int>>):Int{
    //se inicializan variables
    val n = matrizValores.size
    val m = matrizValores[0].size
    //matriz de nxm para comprobar cuales elementos ya se han revisado
    var visitados = Array(n){BooleanArray(m){false}}
    //cola de prioridad
    var pq = PriorityQueue<AlfonsoJoseConAgua>(compareBy { it.TamTotal() })
    //se añade a la cola todos aquellos elementos del borde
    for(i in 0 until n){
        for(j in 0 until m){
            //si está en el borde
            if(i == 0 || i == n - 1 || j == 0 || j == m - 1){
                //se añade el elemento
		pq.offer(AlfonsoJoseConAgua(matrizValores[i][j],0, i,j))
		visitados[i][j] = true
            }
        }
    }
    var AguaAñadida: Int = 0
    //mientras haya elementos en la cola de prioridad
    while(!(pq.isEmpty())){
        //se descencola el elemento de menor valor
        var elementoAComprobar = pq.poll()!!
        //se revisa el equivalente en el grafo del elemento con agua
        var elemACompGraf = ComprobarAguaVsSinAgua(grafo, elementoAComprobar)
        // prueba de error en la implementación, ignorar
        if(elemACompGraf == null){
        println("Error: algo falló")
        //si no hay error
        }else{
            //se revisan todos los adyacentes
            for(adyacente in grafo.obtenerArcosSalida(elemACompGraf)){
                val nivelAguaElemComp = elementoAComprobar.TamTotal()
                val iAdy = adyacente.obtenerI()
                val jAdy = adyacente.obtenerJ()
                val tamAdy = adyacente.obtenerTamaño()
                //si no se ha revisado el adyacente
                if(visitados[iAdy][jAdy] == false){
                    //se ve cuanta agua se va añadir
                    val aguaAAñadir = maxOf(0, elementoAComprobar.TamTotal() - tamAdy)
                    //se marca que se visitó
                    visitados[iAdy][jAdy] = true
                    //se crea un elemento de la case con agua, con la nueva agua
                    val NuevoAComp = (AlfonsoJoseConAgua(tamAdy,aguaAAñadir,iAdy,jAdy))
                    //se encola el siguiente elemento
                    pq.offer(NuevoAComp)
                    //se suma el agua añadida
                    AguaAñadida = AguaAñadida + aguaAAñadir
                }
            }
        }
    }
    //se retorna el valor total sumado de agua que fue añadida
    return AguaAñadida

}
    

fun main(){
    //se busca el archivo
    val ruta: String = "atlantis.txt"
    val file: File = File(ruta)
    //se comprueba que exista el archivo
    if(!file.exists()){
         println("Error: No existe atlantis.txt en la carpeta raiz, intente de nuevo")
         return
    }
    //se inicializan los datos
    val parEntrada:Pair<Grafo<AlfonsoJose>,List<List<Int>>>? = cargarDatos(file)
    //se comprueba si hubo error en la entrada
    if(parEntrada == null ){
        println("Error en la entrada de datos")
    }
    //si no hay error, se ejecuta la ayuda a luis alfonso usando dijkstra, que atlantis no se va a construir solo
    else{
        var grafo = parEntrada.first
        var matrizValores = parEntrada.second
        val TotAgua = ayudarAlfonsoJoseDijkstra(grafo,matrizValores)
        println("$TotAgua")
    }
}
