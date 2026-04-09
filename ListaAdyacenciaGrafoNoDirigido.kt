class ListaAdyacenciaGrafo<T>: Grafo<T> {
    private var tamanio: Int = 0
    private var head: NodoVer<T>? = null
    private var tail: NodoVer<T>? = null
    
    override fun tamano(): Int = tamanio
    
    //se revisa si contiene v, para ello hace un while hasta conseguirlo o anularse
    override fun contiene(v: T): Boolean {
    	var nodoAct: NodoVer<T>? = head
    	while (nodoAct != null){
    	    if(nodoAct.ver == v) return true
    	    nodoAct = nodoAct.nodoVerSig
    	}
        return false
    }
    
    override fun agregarVertice(v: T): Boolean{
    	if(contiene(v)) return false
    	var nodoNuevo: NodoVer<T> = NodoVer(ver = v, nodoLadAsoc = null, nodoVerSig= null)
    	if(head == null){
    	    head = nodoNuevo
    	    tail = nodoNuevo
    	} else {
    	    tail?.nodoVerSig = nodoNuevo
    	    tail = nodoNuevo    	    
    	}
    	tamanio++
    	return true
    }
    override fun eliminarVertice(v: T): Boolean{
        var SeBorroAlgo: Boolean = false
        //mientras no sea nulo el nodo actual
        if(tamanio == 0) return false
        if(v == head?.ver){
            //si el tamaño es 1
            if(head == tail){
                head = null
                tail = null
            //si hay más de un elemento
            }else{
                var nodoSiguiente: NodoVer<T>? = head?.nodoVerSig
                head = nodoSiguiente    
            }
            tamanio--
            SeBorroAlgo = true
        }
        var nodoAct: NodoVer<T>? = head
    	while (nodoAct != null){
    	    var nodoAEliminar: NodoVer<T>? = nodoAct.nodoVerSig
    	    //si el siguiente no es nulo y es v
    	    if(nodoAEliminar != null && nodoAEliminar.ver == v){
    	    	var nodoSiguiente: NodoVer<T>? = nodoAEliminar.nodoVerSig
    	    	nodoAct.nodoVerSig = nodoSiguiente
    	    	tamanio--
    	    	SeBorroAlgo = true
    	    	//acomodar head o tail en caso de ser el borrado, como pudiesen ser iguales
    	    	if(nodoAEliminar == tail) tail = nodoAct
    	    	//si no se va a borrar el nodo completo, se revisa si el vértice está en los arcos
  	    }else{
    	    	var nodoLadoAct: NodoLad<T>? = nodoAct.nodoLadAsoc
    	    	//si no hay nada continuo
    	    	if(nodoLadoAct != null) {
    		    if(nodoLadoAct.v == v){
    	            	val nodoSig:NodoLad<T>? = nodoLadoAct.siguiente
    	                nodoAct.nodoLadAsoc = nodoSig
    	                SeBorroAlgo = true
    	            }else{
    	            //si no es el primero se hace un bucle hasta encontrarlo, o hasta anular
    	                while(nodoLadoAct!!.siguiente != null){
    	                    var nodoLadoAEliminar: NodoLad<T> = nodoLadoAct.siguiente!!
    	            	    if(nodoLadoAEliminar.v == v){
    	            	        val nodoSiguiente: NodoLad<T>? = nodoLadoAEliminar.siguiente
    	            	        nodoLadoAct.siguiente = nodoSiguiente
    	            	        SeBorroAlgo = true
    	            	        //se sale del while, puesto que no está dos veces el mismo elemento
    	            	        break   	            	
    	                    }
    	                    nodoLadoAct = nodoLadoAct.siguiente
    	                }
    	            }
    	        }
    		nodoAct = nodoAct.nodoVerSig
    	    }
    	}
    	//si nunca retornó true, retorna false 
    	return SeBorroAlgo
    	
    }
    override fun conectar(desde: T, hasta: T): Boolean{
        if (!contiene(desde) || !contiene(hasta)) return false
        //dado que contiene desde y hasta puedo garantizar que existe el nodo
        var nodoDesde: NodoVer<T> = buscarNodoVer(desde)!!
        var nodoHasta: NodoVer<T> = buscarNodoVer(hasta)!!
        if ((insertarLado(nodoDesde, hasta)) && (insertarLado(nodoHasta, desde))) {
            return true
        }else{
            return false
        }
    }

    //funcion auxiliar para insertar
    fun insertarLado(nodoVer: NodoVer<T>, valDest: T): Boolean{
        var ladoNuevo: NodoLad<T> = NodoLad(v = valDest, siguiente = null) 
        var nodoLadAct: NodoLad<T>?  = nodoVer.nodoLadAsoc
        //si verifica si ya tiene lados, de no tenerlos los crea
        if(nodoLadAct == null){
            nodoVer.nodoLadAsoc = ladoNuevo
            return true
        //si tiene lados
        } else {
            //verifica por todos si es que el valor a añadir se encuentra
            while (nodoLadAct!!.siguiente != null){
                if (nodoLadAct.v == valDest) return false
                nodoLadAct = nodoLadAct.siguiente
            }
            //verifica de nuevo si se encuentra, puesto que la verificación para 1 antes
            if (nodoLadAct.v == valDest) return false
            //si no lo encuentra, lo asigna
            nodoLadAct.siguiente = ladoNuevo
            return true
        }
        
    }
    //funcion auxiliar para buscar el nodo del vértice, similar a contiene(), pero para retornar nodo, en lugar de booleano
    fun buscarNodoVer(v: T): NodoVer<T>? {
        var nodoAct: NodoVer<T>? = head
    	while (nodoAct != null){
    	    if(nodoAct.ver == v) return nodoAct
    	    nodoAct = nodoAct.nodoVerSig
    	}
        return null
    }
    //función para buscar un valor en específico
    override fun buscarVer(i: Int): T? {
        var nodoAct: NodoVer<T>? = head
        var ind: Int = 0
    	while (nodoAct != null){
    	    if(ind == i) return nodoAct.ver
    	    nodoAct = nodoAct.nodoVerSig
    	    ind++
    	}
        return null
    }

    override fun obtenerArcosSalida(v: T): List<T>{
        var listaArcSalv: MutableList<T> = mutableListOf()
    	if(contiene(v)){
    	    var nodoAct: NodoVer<T>? = head
    	    while (nodoAct?.ver != v){
    	    	nodoAct = nodoAct?.nodoVerSig
    	    }
    	    var NodoLadAct: NodoLad<T>? = nodoAct.nodoLadAsoc
    	    while (NodoLadAct != null){
		listaArcSalv.add(NodoLadAct.v!!)
    	    	NodoLadAct = NodoLadAct.siguiente
    	    }
    	    
    	}
    	//si retorna una lista sin nada, falló en algún lado, o el grado era 0
        return listaArcSalv
    }
    
    override fun obtenerArcosEntrada(v: T): List<T>{
        var listaArcEntv: MutableList<T> = mutableListOf()
    	if(contiene(v)){
    	    var nodoAct: NodoVer<T>? = head
    	    //se ven todos los elementos
    	    while(nodoAct != null){
    	        var NodoLadAct: NodoLad<T>? = nodoAct.nodoLadAsoc
    	        //se ven los arcos
    		while (NodoLadAct != null){
    		    //si el arco conecta al vèrtice que buscamos
		    if(NodoLadAct.v == v){
		    	listaArcEntv.add (nodoAct.ver!!)
		    	//se hace un break por que no puede haber más de una conexion
		    	break
		    }
		    //si no lo consigue, entonces 
    	    	    NodoLadAct = NodoLadAct.siguiente
    	        }
    	    	nodoAct = nodoAct.nodoVerSig
    	    }
    	}
        return listaArcEntv
    }
    
    override fun subgrafo(vertices: Collection<T>): Grafo<T>{
        var nodoAct: NodoVer<T>? = head
        var grafoReturn: ListaAdyacenciaGrafo<T> = ListaAdyacenciaGrafo()
        //se ven todos los elementos
        while(nodoAct != null){ 
    	    //si el elemento a ver está en la colecció
            if(vertices.contains(nodoAct.ver)){
                //se añade el vertice al grafo
                grafoReturn.agregarVertice(nodoAct.ver!!)
                var nodoLadAct: NodoLad<T>? = nodoAct.nodoLadAsoc
                //mientras el arco a ver no sea nulo
	        while (nodoLadAct != null){
	            //si la salida coincide con un valor dentro de la colección
	            if(vertices.contains(nodoLadAct.v)){
	            	//se conecta en el grafo el valor de nodo y el valor del lado
		        grafoReturn.conectar(nodoAct.ver!!, nodoLadAct.v!!)
	            }
  	         //si no lo consigue, entonces 
                 nodoLadAct = nodoLadAct.siguiente
	         }
    	    }
    	    nodoAct = nodoAct.nodoVerSig
       }
       return grafoReturn
   }
   override fun elementos():List<T>{
       var elementosEnGrafo = mutableListOf<T>()
       if (tamanio < 0) return elementosEnGrafo
       var nodoAct: NodoVer<T>? = head
    	while (nodoAct != null){
    	    if(nodoAct.ver == null){
    	        nodoAct = nodoAct.nodoVerSig
    	    }else{ 
    	        val elem = nodoAct.ver!!
    	        nodoAct = nodoAct.nodoVerSig
    	        elementosEnGrafo.add(elem)
    	    }
    	}
        return elementosEnGrafo
   }
}
