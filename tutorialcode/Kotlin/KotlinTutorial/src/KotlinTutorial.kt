fun main() {
    println(getMax(1,2,3,4,4,1,2,3,213,2))
}



fun getMax(first :Int, vararg  numbers: Int): Int{
    var max =0
    for (number in numbers) {
        if (number > max) max = number
    }
    return max
}