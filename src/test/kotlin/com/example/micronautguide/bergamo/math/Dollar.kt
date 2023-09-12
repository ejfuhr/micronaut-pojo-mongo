package com.example.micronautguide.bergamo.math

class Dollar(val amount: Int) {
    operator fun times(multiplier: Int) : Dollar {
        if (multiplier == 0 || multiplier == null) {
            throw NoMoneyException("You can't multiply by zero, you'll end up with no money")
        }
        return Dollar(amount.times(multiplier))
    }

    override fun toString(): String {
        return "Dollar(amount=$amount)"
    }

}
