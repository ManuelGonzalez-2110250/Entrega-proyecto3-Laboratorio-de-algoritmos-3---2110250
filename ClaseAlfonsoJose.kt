data class AlfonsoJose(val tamaño: Int, var PosI: Int ,var PosJ:Int ){
    override fun toString(): String {
    	return "${tamaño} en ${PosI}, ${PosJ}"
    }
    fun obtenerTamaño(): Int{
        return tamaño
    }
    fun obtenerI(): Int{
        return PosI
    }
    fun obtenerJ(): Int{
        return PosJ
    }
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is AlfonsoJose) return false
        return tamaño == other.tamaño && PosI == other.PosI && PosJ == other.PosJ
    }
    override fun hashCode(): Int {
        var hash = tamaño.hashCode()
        hash = (31 * hash) + PosI.hashCode()
        hash = (31 * hash) + PosJ.hashCode()
        return hash
    }


}
data class AlfonsoJoseConAgua(val tamaño: Int, var agua: Int, var PosI: Int ,var PosJ:Int ){
    override fun toString(): String {
    	return "${tamaño} en ${PosI}, ${PosJ}"
    }
    fun TamTotal(): Int {
    	return tamaño + agua
    }
    fun obtenerTamaño(): Int{
        return tamaño
    }
    fun obtenerAgua(): Int{
        return agua
    }
    fun obtenerI(): Int{
        return PosI
    }
    fun obtenerJ(): Int{
        return PosJ
    }
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is AlfonsoJoseConAgua) return false
        return tamaño == other.tamaño && PosI == other.PosI && PosJ == other.PosJ
    }
    override fun hashCode(): Int {
        var hash = tamaño.hashCode()
        hash = (31 * hash) + PosI.hashCode()
        hash = (31 * hash) + PosJ.hashCode()
        return hash
    }

}
