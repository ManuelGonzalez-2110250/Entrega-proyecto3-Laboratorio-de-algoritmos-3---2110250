class NodoLad<T>(var v: T?, var siguiente: NodoLad<T>? = null){
    override fun toString(): String {
    	return "${v}"
    }
}

class NodoVer<T>(var ver: T?, var nodoLadAsoc: NodoLad<T>? = null, var nodoVerSig: NodoVer<T>? = null ){
    override fun toString(): String {
    	return "${ver}"
    }
}

