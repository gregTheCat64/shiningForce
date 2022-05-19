package units

import badForce
import goodForce
import kotlin.random.Random

enum class UnitType{
    HERO, MAGE, FIGHTER, ORC
}

abstract class Unit (
    var name: String,
    var type: UnitType,
    val maxHealth: Int,
    var health: Int = maxHealth,
    var strength: Int,
    var defence: Int,
    var isGood: Boolean
) {
    fun attack(unit: Unit){
        if (unit.health>0){
            println("\n АТАКА")
            println("____________________")
            println("$this ---> $unit")
            val damage = strength + (Random.nextInt(6))  - unit.defence
            println("Ущерб: $damage очков жизни")
            unit.health -= damage

            deathUnit(unit)
        } else println("${unit.name} мертвее некуда. Хватит мучить тело")


    }

    override fun toString(): String {
        return "${type} $name HP: $health ATT: $strength DEFF: $defence"
    }

}

    fun deathUnit(unit: Unit){
        if (unit.health <= 0) {
            if (unit.isGood) {
                goodForce.remove(unit)
            } else badForce.remove(unit)
            println("${unit.name} погиб в праведном бою")
        }
    }