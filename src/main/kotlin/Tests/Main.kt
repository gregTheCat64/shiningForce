package Tests

import kotlin.random.Random


class Man(
    val name:String,
    val age: Int
){
    override fun toString(): String {
        return "Man(name='$name', age=$age)"
    }
}

val list = arrayListOf<Man>()

fun main() {
    list.add(Man("Борман", 35))
    list.add(Man("Мичман", 23))
    list.add(Man("Врунгель", 43))
    list.add(Man("Врунгель", 23))
    list.add(Man("Врунгель", 27))

    val ages = arrayListOf<Int>()

//    for ((index,v) in list.withIndex()){
//        ages.add(v.age) }
//    println(ages.size)
//
//    val min = list.minByOrNull { it.age }
//    println(min)

    var random: Int
    for (i in 0..100){
        random = Random.nextInt(0,list.size)
        println(random)
    }

}