package units

import separateLine

class Hero(name:String, var forcePoints: Int = 3, isGood: Boolean): Unit (name, UnitType.HERO, 50, strength = 15, defence = 10, isGood = isGood) {
    fun useForce(force: MutableList<Unit>){

        if (isGood) {
            for (i in 0 until force.size) {
                if (force[i] is Fighter){
                    force[i].strength += 5
                }


            }
            forcePoints--
            println("\nВдохновление")
            println(separateLine)
            println("$name произносит вдохновляющую речь и сила всех воинов увеличивается на 5")
        }
        else {
            for (i in 0 until force.size) {
                if (force[i] is Orc){
                    force[i].strength += 5
                }
            }
            forcePoints--
            println("\nВдохновление")
            println(separateLine)
            println("$name произносит вдохновляющую речь и сила всех орков увеличивается на 5")

        }


    }
    override fun toString(): String {
        return "${type} $name HP: $health ATT: $strength DEFF: $defence FORCE: $forcePoints"
    }
}