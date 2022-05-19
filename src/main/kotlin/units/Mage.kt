package units

import separateLine
import kotlin.random.Random

class Mage(name:String = "Волдом", var mana: Int = 40, isGood: Boolean = true):Unit(name, UnitType.MAGE, 30, strength = 10,defence = 7, isGood = isGood) {

    fun cure(unit: Unit){
        val restore = Random.nextInt(10,20)
        println("\n Лечение")
        println(separateLine)
        println("$this ---> $unit")
        println("Восстановлено $restore очков жизни")

        unit.health += restore
        if (unit.health > unit.maxHealth) unit.health = unit.maxHealth
        mana -= 5


    }

    fun fireBall(unit: Unit){
        if (unit.health>0){
            val damage = Random.nextInt(10, 20)
            println("\n Запуск фаерболла!!!")
            println("____________________")
            println("$this ---> $unit ")
            println("Ущерб $damage")

            unit.health -= damage
            mana -= 10

            deathUnit(unit)
        } else println("${unit.name} уже мертв! Прекратите издеваться над бедолагой")

    }

    override fun toString(): String {
        return "${type} $name HP: $health ATT: $strength DEFF: $defence MAN: $mana"
    }
}