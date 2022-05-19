import units.*
import units.Unit
import java.util.*
import kotlin.random.Random

val goodForce = arrayListOf<Unit>()
val badForce = mutableListOf<Unit>()
var isBadForceTurn: Boolean = false
const val separateLine = "________________________________"

fun main() {
    val scanner = Scanner(System.`in`)
    //формируем отряды:
    goodForce.add(Hero("Аскольд", isGood = true))
    goodForce.add(Mage("Архимаг"))
    for (i in 1..3) {
        goodForce.add(Fighter("Воин-$i"))
    }

    badForce.add(Hero("Локтар", isGood = false))
    badForce.add(Mage("Варлок", isGood = false))
    for (i in 1..3) {
        badForce.add(Orc("Орк-$i"))
    }

    while (true) {
       if (finishCheck()) return
   //     Thread.sleep(2000)
        printInfo()
        println("Нажмите 'Enter' чтобы продолжить")
        val input = scanner.nextLine()

        choosingAttackingUnit()
    }
}

fun printInfo() {
    println(separateLine)
    println("Наш отряд:")
    for (i in 0 until goodForce.size) {
        println("${i + 1}. ${goodForce[i]}")
    }

    println(separateLine)
    println("Вражеский отряд:")
    for (i in 0 until badForce.size) {
        println("${i + 1}. ${badForce[i]}")
    }


}

fun choosingAttackingUnit() {

    println(separateLine)

    var attackingUnit: Int
    if (!isBadForceTurn) {
        println("Размер отряда: ${goodForce.size}")
        attackingUnit = Random.nextInt(0, goodForce.size) //доработать, проблему когда остается один персонаж
        println("атакуемый номер - $attackingUnit")
        println("Атакует: ${goodForce[attackingUnit].name}")
 //       Thread.sleep(2000)
        goodUnitsTurn(goodForce[attackingUnit])
    } else {

        println("Размер отряда орков: ${badForce.size}")
        attackingUnit = Random.nextInt(0, badForce.size)
        println("атакуемый номер - $attackingUnit")
        println("Атакует: ${badForce[attackingUnit].name}")
 //       Thread.sleep(2000)
        badUnitTurn(badForce[attackingUnit])
    }

}


fun goodUnitsTurn(unit: Unit) {

    isBadForceTurn = true
    println("Выберите действие")
    when (unit.type) {
        UnitType.HERO -> {
            println("1. Атаковать \n 2.Вдохновить отряд")
        }
        UnitType.FIGHTER -> {
            println("1. Атаковать")
        }
        UnitType.MAGE -> {
            println("1. Атаковать \n2. Лечить \n3. Запустить Файерболл")
        }
    }
    val scanner = Scanner(System.`in`)
    var input = scanner.nextInt()
    when (input) {
        1 -> {
            println("Выберите номер цели")
            var inputAim = scanner.nextInt()
            if (unit.isGood) {
                unit.attack(badForce[inputAim - 1])
            } else {
                unit.attack(goodForce[inputAim - 1])
            }
        }
        2 -> {
            when  {
                (unit is Mage) ->{
                    println("Выберите номер бойца кого нужно излечить")
                    println("Выберите номер цели")
                    val inputAim = scanner.nextInt()
                    if (unit.isGood) {
                        unit.cure(goodForce[inputAim - 1])
                    } else {
                        unit.cure(badForce[inputAim - 1])
                    }
                }
                unit is Hero -> {
                    if (unit.isGood){
                        unit.useForce(goodForce)
                    }
                    else {
                        unit.useForce(badForce)
                    }
                }
            }

        }
        3 -> {
            if (unit is Mage) {
                println("Выберите, в кого запускаем огненный шар?")
                println("Выберите номер цели цель")
                val inputAim = scanner.nextInt()
                if (unit.isGood) {
                    unit.fireBall(badForce[inputAim - 1])
                } else {
                    unit.fireBall(goodForce[inputAim - 1])
                }
            }

        }
    }
}

fun badUnitTurn(unit: Unit) {
    isBadForceTurn = false
    var maxDamage: Int = 0
    val maxDamagedGoodArray = arrayListOf<Int>()
    val maxDamagedBadArray = arrayListOf<Int>()
    var maxDamagedBadIndex: Int
    var maxDamagedGoodIndex:Int
    var aimedUnit: Unit

    // цикл для определения самого раненого бойца в Пользовательском отряде:

    for ((index, v) in goodForce.withIndex()) {
        maxDamage = v.maxHealth - v.health
        maxDamagedGoodArray.add(maxDamage)
    }

    if (maxDamage == 0) {           //доработать !!!
        maxDamagedGoodIndex = Random.nextInt(0,goodForce.size)
    } else {
        maxDamagedGoodIndex = 0
        for (i in 1 until maxDamagedGoodArray.size) {
            if (maxDamagedGoodArray[i] > maxDamagedGoodArray[maxDamagedGoodIndex]) {
                maxDamagedGoodIndex = i
            }
        }
           }
    //цель для атаки:
    aimedUnit = goodForce[maxDamagedGoodIndex]


    // определения самого раненого бойца среди своих:

    if (badForce.size>1){
        for ((index, v) in badForce.withIndex()) {
            maxDamage = v.maxHealth - v.health
            maxDamagedBadArray.add(maxDamage)
        }

        maxDamagedBadIndex = 0
        for (i in 1 until maxDamagedBadArray.size) {
            if (maxDamagedBadArray[i] > maxDamagedBadArray[maxDamagedBadIndex]) {
                maxDamagedBadIndex = i
            }
        }
    } else maxDamagedBadIndex = 0

    var curingUnit = badForce[maxDamagedBadIndex]



    when (unit) {
        is Orc -> unit.attack(aimedUnit)
        is Mage -> if (curingUnit.health < 5) {
            unit.cure(curingUnit)
        } else
            if (unit.mana >= 10) {
                unit.fireBall(aimedUnit)
            } else unit.attack(aimedUnit)
        is Hero -> unit.attack(aimedUnit)
    }
}

fun finishCheck():Boolean{
    when  {
        goodForce.size == 0 -> {
            println("Игра проиграна. Орда Варваров одержала верх!")
            return true
        }
        badForce.size == 0 -> {
            println("Племя варваров разбито в пух и прах. Вы победили!")
            return true
        }
    }
    return false

}

